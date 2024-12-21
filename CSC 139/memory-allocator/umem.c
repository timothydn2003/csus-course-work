#include <stddef.h>
#include <sys/mman.h>
#include <unistd.h>
#include <math.h>
#include <stdbool.h>
#include <stdlib.h>

#include "umem.h"

int global_alloc = -1; // Alloc algo
int total_alloc = 0;
int total_dealloc = 0;
int total_realloc = 0;
size_t whole_mem; // Track total memory requested and track if we called umeminit yet


node_t *head = NULL; // Head of free list
node_t *next_head = NULL; // To track the last searched node in next fit
node_t *next_head_prev = NULL; // To track the last searched node in next fit

// Round up the number of bytes so that the requested memory is in units of page size
int sizeRoundedUpByPage(int size){
    double pageSize = getpagesize();

    // Number of bytes we are requesting is smaller than page size
    if(size < pageSize){
        return pageSize;
    }

    // Number of bytes we are requesting is larger than page size
    double remainder = (double)size/pageSize;
    int numOfPages = ceil(remainder);

    return numOfPages * pageSize;

}

// Round up number of bytes so that it is a multiple of 8
int mallocSizeBy8(int size){
    // Number of bytes is smaller than 8
    if(size < 8){
        return 8;
    }

    // If number of bytes is greater than 8
    double remainder = (double)size/8;
    int numOfBytes = ceil(remainder);

    return numOfBytes * 8;
}

int umeminit(size_t sizeOfRegion, int allocationAlgo){
    // Check if sizeOfRegion is less than 0, if we are calling this function
    // twice in 1 process or we passed in a non valid algo
    if((int)sizeOfRegion <= 0 || whole_mem ){
        return -1;
    }
    if(allocationAlgo > 4 || allocationAlgo < 1){
        return -1;
    }

    int sizeToAllocate = sizeRoundedUpByPage(sizeOfRegion);

    head = mmap(NULL, sizeToAllocate, PROT_READ | PROT_WRITE, MAP_ANON | MAP_PRIVATE, -1, 0);

    head->size = sizeToAllocate - sizeof(node_t);
    head->next = NULL;

    if(head == MAP_FAILED){
        return -1;
    }

    // Set allocation alog
    global_alloc = allocationAlgo;
    // To track the amount of memory we orignally start with
    // this number is not including the header size we have for
    // this original block.
    whole_mem = sizeToAllocate;

    return 0;
}

// Allocate a block from the free list
void *allocate(node_t *ptr, node_t *prev, int size){
    if(ptr->size > size + sizeof(node_t)){
        // Split the block and update the size in the free list
        // The address of this block now becomes original address + size + size of header
        node_t *remainder = (node_t*)((char*)ptr + size);
        remainder->size = ptr->size - size;
        remainder->next = ptr->next;

        // We are at the front of the list
        if(head == ptr){
            head = remainder;
        }else{
            prev->next = remainder;
        }

        // Header of new allocated block
        header_t *allocated_block = (header_t*)((char*)ptr);
        allocated_block->magic = MAGIC;
        allocated_block->size = size - sizeof(header_t);

        return (void*) (allocated_block+1);

    }else{
        // Allocate the whole block, need to remove it from the free list
        if(prev!=NULL){
            prev->next = ptr->next;
        }else{
            head = ptr->next;
        }

        // Header of new allocated block
        header_t *allocated_block = (header_t*)((char*)ptr);
        allocated_block->magic = MAGIC;
        allocated_block->size = size - sizeof(header_t);

        return (void*) (allocated_block+1);
    }

    return NULL;
}

// BEST FIT allocation
void *best_fit(int size){
    node_t *temp = head;
    node_t *best = NULL;
    node_t *prev = NULL;
    node_t *prev_best = NULL;

    while(temp != NULL){
        // Check if block is big enough to satisfy request
        if(temp->size >= size){
            // Check if block is smaller than current best
            if(best == NULL || temp->size < best->size){
                best = temp;

                // Save the previous node of the best node
                prev_best = prev;
            }
        }

        // Store the current node in prev
        prev = temp;
        // Go to next node
        temp = temp->next;
    }
    // No block found
    if(best == NULL){
        return NULL;
    }

    return allocate(best, prev_best, size);
}

// WORST FIT allocation
void *worstFit(int size){
    node_t *temp = head;
    node_t *worst = NULL;
    node_t *prev = NULL;
    node_t *prev_worst = NULL;

    while(temp != NULL){
        // Check if block is big enough to satisfy request
        if(temp->size >= size){
            // Check if block is larger than current worst
            if(worst == NULL || temp->size > worst->size){
                worst = temp;

                // Save the previous node of the worst node
                prev_worst = prev;
            }
        }
        // Store the current node in prev
        prev = temp;
        // Go to next node
        temp = temp->next;
    }

    // No block found
    if(worst == NULL){
        return NULL;
    }

    return allocate(worst, prev_worst, size);
}

// FIRST FIT allocation
void *firstFit(int size){
    node_t *temp = head;
    node_t *prev = NULL;

    while(temp != NULL){
        // Get first block that is large enough to satisfy request
        if(temp->size >= size){
            return allocate(temp, prev, size);
        }
        // Store the current node in prev
        prev = temp;
        // Go to next node
        temp = temp->next;
    }

    // No block found
    return NULL;
}

// NEXT FIT allocation
void *nextFit(int size){
    node_t *temp;
    node_t *prev = NULL;

    // Condition to check if we have already done an allocation with next fit
    if(next_head != NULL){
        // If next head is not NULL, set the current node to next_head
        temp = next_head;
        prev = next_head_prev;
    }else{
        // If next_head is NULL, set the current node to head
        temp = head;
    }

    // Loop from temp to end of list,find first available block
    while(temp != NULL){
        if(temp->size >= size){
            // Set next_head to the found block
            next_head_prev = prev;
            next_head = temp->next;
            return allocate(temp, prev, size);
        }
        // Store the current node in prev
        prev = temp;
        // Go to next node
        temp = temp->next;
    }

    // If we go from next_head to the end of the list without
    // finding a block, we need to wrap around and start
    // from the beginning until we reach next_head again
    temp = head;
    prev = NULL;
    while(temp != next_head){
        if(temp->size >= size){
            // Set next_head to the found block
            next_head = temp->next;
            return allocate(temp, prev, size);
        }
        // Store the current node in prev
        prev = temp;
        // Go to next node
        temp = temp->next;
    }

    // No block found
    return NULL;
}

void *umalloc(size_t size){
    // When we are trying to allocate 0 or less bytes
    // or we are calling umalloc before umeminit
    if((int)size <= 0 || head == NULL){
        return NULL;
        exit(1);
    }

    void *ptr = NULL;

    // Align size by 8 and add space for header
    size = mallocSizeBy8(size) + sizeof(header_t);

    // Function we will go into based on algo we choose
    switch(global_alloc){
        case BEST_FIT:
            ptr = best_fit(size);
            break;
        case WORST_FIT:
            ptr = worstFit(size);
            break;
        case FIRST_FIT:
            ptr = firstFit(size);
            break;
        case NEXT_FIT:
            ptr = nextFit(size);
            break;
        default:
            break;
    }
    total_alloc++;
    return ptr;
}

// Combine blocks that are adjacent in memory
void coalesce(node_t *ptr){
    node_t *temp = head;
    node_t *prev = NULL;

    while(temp != NULL){
        // ptr is the block with smaller address
        if((node_t*)((char*)ptr + ptr->size + sizeof(header_t)) == (node_t*)(char*)temp){
            // We are in the middle of the list and ptr is the one with the smaller address
            // Remove temp from the list since it is now merged into ptr
            if(prev != NULL){
                ptr->size += temp->size + sizeof(header_t);
                prev->next = temp->next;

                if(temp == next_head){
                    next_head = ptr;
                }
            }

        // temp is the block with smaller address
        }else if((node_t*)((char*)temp + temp->size + sizeof(header_t)) == (node_t*)(char*)ptr){
            // Merge temp and ptr, remove ptr from the list
            temp->size += ptr->size + sizeof(header_t);
            // head gets set to ptr->next because ptr was originally the head
            head = ptr->next;
            ptr = temp;

            if(ptr == next_head){
                next_head = temp;
            }
        }
        prev = temp;
        temp = temp->next;
    }
}

// Check if we are double freeing
void checkDoubleFree(header_t *ptr){
    node_t *temp = head;

    while(temp != NULL){
        // Check if address isalready in free list
        if((node_t*)(char*)ptr == (node_t*)(char*)temp){
            fprintf(stderr, "ERROR - Pointer already free'd\n");
            exit(1);
        }
        temp = temp->next;
    }
}

void ufree(void *ptr){
    // Exit on trying to free a NULL pointer
    if(ptr == NULL){
        exit(1);
    }

    // Calculate address of associated header
    header_t *headPtr = (header_t*)ptr - 1;
    checkDoubleFree(headPtr);

    // Sanity check
    if(headPtr->magic != MAGIC){
        fprintf(stderr, "Error: Memory corruption detected at block %p\n", headPtr);
        exit(1);
    }

    // Create free block to add to head free list
    node_t *freeBlock = (node_t*)((char*)headPtr);
    freeBlock->next = head;
    freeBlock->size = headPtr->size;

    // Set head to this new block
    head = freeBlock;

    // Merge adjacent free blocks
    coalesce(freeBlock);

    // Increment the deallocation count
    total_dealloc++;
}

// Loop through list and see if the ptr is in it
bool inFreeList(char *ptr){
    node_t *temp = head;

    // Loop through list
    while(temp != NULL){
        // Compare the temp to the ptr
        if((char*)temp == (char*)ptr){
            return true;
        }
        temp = temp->next;
    }

    return false;
}

void *urealloc(void *ptr, size_t size){
    // size is 0, treat as ufree
    if(size == (size_t)0){
        ufree(ptr);
        return ptr;
    }

    // Set the new size to be 8-byte aligned
    size = mallocSizeBy8(size);

    // ptr is NULL, treat as umalloc
    if(ptr == NULL){
        ptr = umalloc(size);
        return ptr;
    }
    // Get the header of the memory block
    header_t *block = (header_t*)ptr - 1;
    size_t difference = mallocSizeBy8(block->size - size);

    // ptr has already been free'd and we are trying to urealloc it
    if(inFreeList((char*)block)){
        fprintf(stderr, "ERROR - ptr already free'd cant realloc\n");
        exit(1);
    }

    // need to expand
    if(block->size < size){
        node_t *temp = head;
        node_t *prev = NULL;
        // Block adjacent to the block we are reallocing
        node_t *nextBlock = (node_t*)((char*)block + block->size + sizeof(header_t));
        // New block after we expand the current block
        node_t *newBlock = (node_t*)((char*)block + block->size + sizeof(header_t) + (size - block->size));

        // Next block is in free list and there is enough space to expand in place
        if(nextBlock->size > (size - block->size) && inFreeList((char*)nextBlock)){
            // Loop through list and find the block that is before the one we are splitting
            while(temp != NULL && temp != nextBlock){
                prev = temp;
                temp = temp->next;
            }
            // Set the new block size and next block
            newBlock->next = nextBlock->next;
            newBlock->size = nextBlock->size - (size - block->size);

            // remove the block
            if(prev!=NULL){
                prev->next = newBlock;
            }else{
                head = newBlock;
            }
            // set block size
            block->size = size;
            total_alloc++;
            total_realloc++;
            return block+1;
        // Next block is not free or there is not enough contiguous space
        }else{
            // Must free the current block and find space for the new block
            ptr = umalloc(size);
            ufree(block+1);
            if(total_realloc != 0){
                total_realloc--;
            }
            return ptr;
        }

    }else{ // need to shrink
        // Create new block after splitting current ptr
        header_t *splitBlock = (header_t*)((char*)block + size + sizeof(header_t));
        splitBlock->size = difference - sizeof(header_t);
        splitBlock->magic = MAGIC;
        // free this new block
        ufree(splitBlock+1);
        total_alloc++;

        // update the size of the block
        block->size = size;
        total_realloc++;
        return block+1;
    }

    return NULL;
}
size_t getCurrAllocated(size_t currFree){
    return whole_mem - currFree;
}

size_t getFreeSpace(){
    // Since my free list accounts for headers, ie a umalloc(24)
    // would result in 40 bytes being allocated, we need to add some
    // logic to add the headers back in when calculating the free space,
    // since umemstats doesnt account for headers
    size_t free = 0;
    node_t *temp = head;

    // Loop through free list and get total free bytes
    while(temp!=NULL){
        free += temp->size + sizeof(header_t);
        temp = temp->next;
    }
    int total;
    // List is at its original state
    if(free == whole_mem){
        return whole_mem;
    }else if(total_realloc!=0){
        // We subtract the total number of allocs by the
        // total reallocs because everytime we call realloc,
        // we increment alloc, so if we dont subtract, it
        // would think that there are extra blocks being allocated
        // meaning it would account for more headers than needed
        total = ((total_alloc-total_realloc-total_dealloc)*(int)sizeof(header_t));
    }else{
        total = ((total_alloc-total_dealloc)*(int)sizeof(header_t));
    }
    // Add header size to the total to accomadate for the
    // original header
    return free + total;
}

float getFragmentation(size_t free_space){
    node_t *temp = head;
    size_t max = head->size;
    size_t fragmentedBlocks = 0;

    // Get the largest block size
    while(temp != NULL){
        max = (max > temp->size) ? max : temp->size;
        temp = temp->next;
    }

    temp = head;
    // Get the total amount of memory thats in blocks which is
    // smaller than the largest block divided by 2
    while(temp != NULL){
        if(temp->size < max/2){
            fragmentedBlocks+= temp->size;
        }
        temp = temp->next;
    }

    // Calculate fragmentation, amount of fragmented memory divided total free memory
    double result = (double)fragmentedBlocks/(double)free_space;

    return result * 100;
}

void umemstats(){
    // Currently free is the original size of the heap - the bytes
    // thats been allocated from umalloc() ie, if we did
    // umalloc(20), umalloc(10), umalloc(30), then the
    // currently free would be whole heap size - (24 + 16 + 32)
    size_t currently_free = getFreeSpace();

    // Currently allocated memory is the the original size of heap
    // subtracted by the number of currently free bytes
    // (not including header size), ie. if we did umalloc(20),
    // the currently allocated would be 24, not 24 + sizeof(header_t)
    size_t currently_allocated = getCurrAllocated(currently_free);
    float fragmentation = getFragmentation(currently_free);

    // Print stats
    printumemstats(total_alloc, total_dealloc, currently_allocated, currently_free, fragmentation);
}