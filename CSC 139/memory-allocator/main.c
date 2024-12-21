#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <string.h>

#include "umem.h"
#define PASS "\033[1;32mPassed\033[0m"
#define FAIL "\033[1;31mFailed\033[0m"

// Test to see if we are calling umeminit twice in 1 process
void test_umeminit_callTwice(){
    // Call umeminit twice
    int init = umeminit(20000, BEST_FIT);
    init = umeminit(40000, BEST_FIT);

    // Expect to return -1
    if(init == -1){
        printf("%s - Returned -1 on multiple umeminit calls.\n", PASS);
        exit(0);
    }

    // Didn't return -1, print error
    fprintf(stderr, "%s - Passed on calling umeminit multiple times.\n", FAIL);
    exit(0);
}

// Test to pass in a negative number to umeminit
void test_umeminit_negativeNum(){
    // Pass negative into umeminit
    int init = umeminit(-1, BEST_FIT);

    // Expect it to return -1
    if(init == -1){
        printf("%s - Returned -1 on passing in a negative number.\n", PASS);
        exit(0);
    }

    // Didn't return -1, print error
    fprintf(stderr, "%s - Passed on passing in a negative number.\n", FAIL);
    exit(0);
}

// Test to pass in a non valid algo
void test_umeminit_unKnownAlgo(){
    // Pass an unknown algo to umeminit
    int init = umeminit(1, 7);

    // Expect it to return -1
    if(init == -1){
        printf("%s - Returned -1 on passing in non valid algo.\n", PASS);
        exit(0);
    }

    // Didn't return -1, print error
    fprintf(stderr, "%s - Passed on passing in a not valid algo.\n", FAIL);
    exit(0);
}

// Test to run umalloc with 0
void test_umalloc_zero(){
    int init = umeminit(1, 2);

    if(init == -1){
        printf("%s - Returned -1 on umeminit\n", FAIL);
        exit(0);
    }

    // Try running umalloc wil 0 or a negative
    void *ptr = umalloc(0);
    void *ptr2 = umalloc(-1);

    // Expect both to return NULL
    if(ptr == NULL && ptr2 == NULL){
        printf("%s - Returned NULL on passing in zero/negative to umalloc.\n", PASS);
        exit(0);
    }

    // Didn't return NULL for both, print error
    fprintf(stderr, "%s - Passed on passing in zero to umalloc.\n", FAIL);
    exit(0);
}

// Test to run umalloc before umeminit
void test_umalloc_runWithNoInit(){
    // Call umalloc with no umeminit
    void *ptr = umalloc(8);

    // Expect NULL
    if(ptr == NULL){
        printf("%s - Returned NULL on running umalloc before umeminit.\n", PASS);
        exit(0);
    }

    // Didn't return NULL, print error
    fprintf(stderr, "%s - Passed on running umalloc before umeminit.\n", FAIL);
    exit(0);
}

// Test to run umalloc with a BEST FIT
void test_umalloc_bestFit(){
    // Simple umalloc with best fit
    int init = umeminit(1, 1);

    if(init == -1){
        printf("%s - Returned -1 on umeminit\n", FAIL);
        exit(0);
    }

    int *ptr = umalloc(20);

    // Malloc failed, print error
    if(ptr == NULL){
        fprintf(stderr, "%s - Returned NULL on umalloc using BEST FIT.\n", FAIL);
        exit(0);
    }

    // More umalloc calls
    int *ptr2 = umalloc(30);
    int *ptr3 = umalloc(40);
    int *ptr4 = umalloc(50);

    // Free pointers so that we have a free list
    ufree(ptr);
    ufree(ptr3);

    // free list looks like this
    // ptr3(40)->ptr(24)->original block(16152)
    int *ptr5 = umalloc(8);
    ufree(ptr4);
    ufree(ptr2);

    // Since we are using best fit, ptr(24)
    // should be allocated for ptr5
    if((char*)ptr5 == (char*)ptr){
        printf("%s - BEST FIT - correct block was allocated\n", PASS);
        exit(0);
    }

    // Ptr's weren't equal, print error
    fprintf(stderr, "%s - BEST FIT - Incorrect block was allocated.\n", FAIL);
    exit(0);
}

// Test to run umalloc with a WORST FIT
void test_umalloc_worstFit(){
    int init = umeminit(1, 2);

    if(init == -1){
        printf("%s - Returned -1 on umeminit\n", FAIL);
        exit(0);
    }
    // umalloc some ptrs
    void *ptr = umalloc(2000);

    if(ptr == NULL){
        fprintf(stderr, "%s - Returned NULL on umalloc using WORST FIT.\n", FAIL);
        exit(0);
    }
    void *ptr2 = umalloc(3000);
    void *ptr3 = umalloc(4000);
    void *ptr4 = umalloc(5000);
    void *ptr5 = umalloc(2000);

    // free ptrs so we have a free list
    ufree(ptr3);
    ufree(ptr);
    // free list looks like this
    // ptr(2000)->ptr3(4000)->->original block(288)
    void* ptr6 = umalloc(1000);
    ufree(ptr5);
    ufree(ptr2);
    ufree(ptr4);
    ufree(ptr6);
    // since we are using worst fit, the largest block
    // will be allocated, which is ptr(4000)

    if((char*)ptr6 == (char*)ptr3){
        printf("%s - WORST FIT - correct block was allocated\n", PASS);
        exit(0);
    }
    // Ptr's weren't equal, print error
    fprintf(stderr, "%s - WORST FIT - Incorrect block was allocated.\n", FAIL);
    exit(0);
}

// Test to run umalloc with a FIRST FIT
void test_umalloc_firstFit(){
    int init = umeminit(1, 3);

    if(init == -1){
        printf("%s - Returned -1 on umeminit\n", FAIL);
        exit(0);
    }

    // umalloc some ptrs
    void *ptr = umalloc(5);

    if(ptr == NULL){
        fprintf(stderr, "%s - Returned NULL on umalloc using FIRST FIT.\n", FAIL);
        exit(0);
    }

    int *ptr2 = umalloc(60);
    int *ptr3 = umalloc(20);
    int *ptr4 = umalloc(30);
    int *ptr5 = umalloc(50);

    // free ptrs so we have a free list
    ufree(ptr2);
    ufree(ptr4);
    // free list looks like this
    // ptr4(32)->ptr2(64)->original block(16104)
    int *ptr6 = umalloc(45);
    ufree(ptr5);
    ufree(ptr3);

    // since we are using first fit, ptr2 should be allocated
    if(ptr6 == ptr2){
        printf("%s - FIRST FIT - correct block was allocated\n", PASS);
        fflush(stdout);
        exit(0);
    }

    // Ptr's weren't equal, print error
    fprintf(stderr, "%s - FIRST FIT - Incorrect block was allocated.\n", FAIL);
    fflush(stdout);
    exit(0);
}

// Test to run umalloc with a NEXT FIT
void test_umalloc_nextFit(){
    int init = umeminit(1, 4);

    if(init == -1){
        printf("%s - Returned -1 on umeminit\n", FAIL);
        exit(0);
    }

    int *ptr = umalloc(50);

    if(ptr == NULL){
        fprintf(stderr, "%s - Returned NULL on umalloc using NEXT FIT.\n", FAIL);
        exit(0);
    }
    int *ptr2 = umalloc(150);
    int *ptr3 = umalloc(90);
    int *ptr4 = umalloc(150);
    int *ptr5 = umalloc(300);
    int *ptr6 = umalloc(500);
    int *ptr7 = umalloc(200);

    // Free some pointers so we have a free list
    ufree(ptr4);
    ufree(ptr6);
    ufree(ptr2);
    // free list looks like this
    // ptr2(152)->ptr6(504)->ptr4(152)->originalblock(14792)

    // Malloc again to allocate blocks
    int *ptr8 = umalloc(480);
    int *ptr9 = umalloc(130);
    ufree(ptr3);
    ufree(ptr5);
    ufree(ptr7);

    // Since we are using next fit, ptr8 should take ptr6
    // and then ptr9 should take ptr4. It doesnt take ptr2
    // bcs next fit starts its search from the last allocated block in
    // the free list

    // Make sure pointers are match as expected
    if((char*)ptr8 == (char*)ptr6 && (char*)ptr9 == (char*)ptr4){
        printf("%s - NEXT FIT - correct block was allocated\n", PASS);
        exit(0);
    }

    // Ptr's weren't equal, print error
    fprintf(stderr, "%s - NEXT FIT - Incorrect block was allocated.\n", FAIL);
    exit(0);
}

// Test to run umalloc with a NEXT FIT where we need to go through the list twice
void test_umalloc_nextFit_wrapAround(){
    int init = umeminit(1, 4);

    if(init == -1){
        printf("%s - Returned -1 on umeminit\n", FAIL);
        exit(0);
    }

    int *ptr = umalloc(2000);

    if(ptr == NULL){
        fprintf(stderr, "%s - Returned NULL on umalloc using NEXT FIT.\n", FAIL);
        exit(0);
    }
    int *ptr2 = umalloc(1000);
    int *ptr3 = umalloc(3000);
    int *ptr4 = umalloc(3000);
    int *ptr5 = umalloc(4000);
    int *ptr6 = umalloc(1500);
    int *ptr7 = umalloc(1000);

    ufree(ptr2);
    ufree(ptr4);
    ufree(ptr6);

    // free list looks like ptr6(1504)->ptr4(3000)->ptr2(1000)->original block(752)
    int *ptr8 = umalloc(2980);
    int *ptr9 = umalloc(900);
    int *ptr10 = umalloc(1480);
    // Since we are using next fit, ptr8 will take ptr6, ptr9 will take ptr2
    // and ptr8 will take ptr4. Ptr9 will take ptr2 bcs next fit starts from the
    // last allocated block. Since we start from the last allocated block, our
    // algo loops back around the list to allocate ptr2 for ptr9.

    if((char*)ptr10 == (char*)ptr6 && (char*)ptr9 == (char*)ptr2 && (char*)ptr8 == (char*)ptr4){
        ufree(ptr3);
        ufree(ptr5);
        ufree(ptr7);
        printf("%s - NEXT FIT - correct blocks were allocated.\n", PASS);
        exit(0);
    }

    fprintf(stderr, "%s - NEXT FIT - Incorrect block was allocated.\n", FAIL);
    exit(0);
}

// Test to call umalloc when there isnt enough space avaialable
void test_umalloc_notEnoughSpace(){
    int init = umeminit(1,1);

    if(init == -1){
        printf("%s - Returned -1 on umeminit\n", FAIL);
        exit(0);
    }

    // Try to allocate more meory than we have
    int *ptr = umalloc(17000);

    // Expect it to return NULL
    if(ptr == NULL){
        printf("%s - Returned NULL on umalloc when trying to allocate more memory than available.\n", PASS);
        exit(0);
    }

    // Didn't return NULL< print error
    fprintf(stderr, "%s - Didn't return NULL when trying to allocate more memory than available.\n", FAIL);
    exit(0);
}

// Test to double free a pointer - should print error message to stderr
void test_ufree_doubleFree(){
    int init = umeminit(1,1);

    if(init == -1){
        printf("%s - Returned -1 on umeminit\n", FAIL);
        exit(0);
    }

    int *ptr = umalloc(40);
    printf("If error returned then %s\n", PASS);
    // Ufree the same pointer twice, should see error message in stderr
    ufree(ptr);
    ufree(ptr);
    exit(0);
}

// Test to free a NULL pointer
void test_ufree_freeNULL(){
    int init = umeminit(1,1);

    if(init == -1){
        printf("%s - Returned -1 on umeminit\n", FAIL);
        exit(0);
    }

    int *ptr = NULL;
    ufree(ptr); // Should exit here, if it doesn't print error
    fprintf(stderr, "%s - Did not exit on trying to free a NULL ptr. \n", FAIL);
    exit(0);

}

// Test to make sure stats are being calculated correctly afte calling ufree
void test_ufree_normalFree(){
    int init = umeminit(1,1);

    if(init == -1){
        printf("%s - Returned -1 on umeminit\n", FAIL);
        exit(0);
    }

    int size = 1*getpagesize();
    // Allocate a new pointer
    int *ptr = umalloc(500);
    // Free the pointer
    ufree(ptr);

    printf("\nALLOCATE AND FREE ALL PTRS\n");
    // Currently free memory should be back to original
    umemstats();
    printf("Currently free memory should equal: \033[1;32m%d\033[0m\n", size);
    printf("Currently allocated memory should equal: \033[1;32m%d\033[0m\n\n", 0);


    // Allocate new pointers
    int *ptr1 = umalloc(300);
    int *ptr2 = umalloc(300);
    int *ptr3 = umalloc(300);
    int *ptr4 = umalloc(300);
    // Check if the currently allocated shows right number
    printf("\nALLOCATE NEW PTRS - 4 umalloc of size 300\n");
    umemstats();
    printf("Currently allocated memory should equal: \033[1;32m%d\033[0m\n", 1216);
    printf("Currently free memory should equal: \033[1;32m%d\033[0m\n\n", 15168);

    // Realloc ptr4
    ptr4 = urealloc(ptr4, 400);
    // Check stats
    printf("\nREALLOC PTR - realloc 1 of the previous pointers to size 400\n");
    umemstats();
    printf("Currently allocated memory should equal: \033[1;32m%d\033[0m\n", 1312);
    printf("Currently free memory should equal: \033[1;32m%d\033[0m\n\n", 15072);

    printf("\nFREE PTR\n");
    // Free ptr2 and check stats
    ufree(ptr2);
    umemstats();
    printf("Memory Fragmentation should equal: \033[1;32m%f\033[0m\n\n", 1.99);


    ufree(ptr1);
    ufree(ptr3);

    exit(0);
}

void test_ufree_coalesce(){
    int init = umeminit(1,1);

    if(init == -1){
        printf("%s - Returned -1 on umeminit\n", FAIL);
        exit(0);
    }
    // Allocate ptrs
    int *ptr3 = umalloc(300);
    int *ptr4 = umalloc(300);
    int *ptr5 = umalloc(300);
    int *ptr6 = umalloc(300);

    // Free ptr4, we should expect fragmentation because
    // ptr4 isnt adjacent to anything in the free list
    ufree(ptr4);
    printf("There should have fragmented memory because we are freeing non-adjacent blocks\n");
    umemstats();
    printf("Fragmented memory should equal: \033[1;32m%f\033[0m\n\n", 1.9);

    // Free all ptrs, should be no fragmentation bcs of coalescing
    printf("Freeing all blocks in random order, should coalesce\n");
    ufree(ptr3);
    ufree(ptr5);
    ufree(ptr6);
    umemstats();
    printf("Free memory should equal: \033[1;32m%d\033[0m\n", 16384);
    printf("Fragmented memory should equal: \033[1;32m%d\033[0m\n\n", 0);

    exit(0);
}

// Test to realloc more memory than avaialable
void test_urealloc_returnNull(){
    int init = umeminit(1,1);

    if(init == -1){
        printf("%s - Returned -1 on umeminit\n", FAIL);
        exit(0);
    }

    int *ptr = umalloc(20);
    int *ptr2 = umalloc(16000);

    // Try to realloc more meory than heap has avaialble
    ptr = urealloc(ptr, 400);

    // ptr returned NULL
    if(ptr == NULL){
        ufree(ptr2);
        printf("%s - Returned NULL on urealloc when trying to allocate more memory than available.\n", PASS);
        exit(0);
    }

    // ptr didnt return NULL, print error
    fprintf(stderr, "%s - Didn't return NULL when trying to allocate more memory than available.\n", FAIL);
    exit(0);
}

// Test to pass a NULL pointer in realloc
void test_urealloc_nullPointer(){
    int init = umeminit(1,1);

    if(init == -1){
        printf("%s - Returned -1 on umeminit\n", FAIL);
        exit(0);
    }

    void *ptr = NULL;
    // Pass NULL ptr to realloc
    ptr = urealloc(ptr,20);

    // Expect it to treat it as a regular alloc, should not return NULL
    if(ptr == NULL){
        fprintf(stderr, "%s - Returned NULL when passing a NULL pointer into urealloc.\n", FAIL);
    }

    // Didn't return NULL, print Success message
    printf("%s - Returned a ptr when passing a NULL pointer into urealloc.\n", PASS);
    exit(0);
}

// Test to pass a free'd ptr to urealloc
void test_urealloc_freeAndRealloc(){
    int init = umeminit(1,1);

    if(init == -1){
        printf("%s - Returned -1 on umeminit\n", FAIL);
        exit(0);
    }

    void *ptr = NULL;
    // Pass NULL ptr to realloc
    ptr = urealloc(ptr,20);
    ufree(ptr);

    ptr = urealloc(ptr, 50);
    printf("%s - Should exit when passing a free'd pointer into urealloc.\n", FAIL);
    exit(0);
}

void stressTest(){
    // Kind of a stress test, will call umalloc, urealloc, and ufree
    // multiple times to see if anything fails
    printf("\nUREALLOC STRESS TEST\n");

    printf("\nALLOCATE 7 PTRS\n");
    int init = umeminit(1,1);
    if(init == -1){
        printf("%s - Returned -1 on umeminit\n", FAIL);
        exit(0);
    }

    int *ptr = umalloc(10);
    int *ptr1 = umalloc(10);
    int *ptr2 = umalloc(10);
    int *ptr3 = umalloc(10);
    int *ptr4 = umalloc(10);
    int *ptr5 = umalloc(10);
    int *ptr6 = umalloc(10);

    umemstats();
    printf("Allocated memory should equal: \033[1;32m%d\033[0m\n", 112);
    printf("Free memory should equal: \033[1;32m%d\033[0m\n", 16272);
    printf("Fragmented memory should equal: \033[1;32m%d\033[0m\n\n", 0);

    printf("\nREALLOCATE 7 PTRS\n");
    ptr = urealloc(ptr, 20);
    ptr1 = urealloc(ptr1, 20);
    ptr2 = urealloc(ptr2, 50);
    ptr3 = urealloc(ptr3, 80);
    ptr4 = urealloc(ptr4, 110);
    ptr5 = urealloc(ptr5, 140);
    ptr6 = urealloc(ptr6, 170);

    umemstats();
    printf("Allocated memory should equal: \033[1;32m%d\033[0m\n", 616);
    printf("Free memory should equal: \033[1;32m%d\033[0m\n", 15768);
    printf("Fragmented memory should equal: \033[1;32m%f\033[0m\n\n", 1.3);

    printf("\nFREE 7 PTRS\n");
    ufree(ptr);
    ufree(ptr1);
    ufree(ptr2);
    ufree(ptr3);
    ufree(ptr4);
    ufree(ptr5);
    ufree(ptr6);

    umemstats();
    printf("Allocated memory should equal: \033[1;32m%d\033[0m\n", 0);
    printf("Free memory should equal: \033[1;32m%d\033[0m\n", 16384);
    printf("Fragmented memory should equal: \033[1;32m%d\033[0m\n\n", 0);
    exit(0);
}

// Run all the tests in their own processes
void runTests(void (*testFunction)()){
    pid_t pid = fork();
    if (pid < 0) {
        perror("fork");
        exit(1);
    } else if (pid == 0) {
        testFunction();
    }else {
        wait(NULL);
    }
}
void test(){
    int init = umeminit(1,1);

    int *ptr = umalloc(100);
    ufree(ptr);
    int *ptr2 = umalloc(100);
    ufree(ptr2);

    umemstats();


}

// Call all test functions
void callFunctions(){
    // umeminit tests
    printf("UMEMINIT TESTS\n");
    runTests(test_umeminit_negativeNum);
    runTests(test_umeminit_callTwice);
    runTests(test_umeminit_unKnownAlgo);

    // umalloc tests
    printf("\nUMALLOC TESTS\n");
    runTests(test_umalloc_zero);
    runTests(test_umalloc_runWithNoInit);
    runTests(test_umalloc_notEnoughSpace);

    // basic use cases for each algo
    runTests(test_umalloc_bestFit);
    runTests(test_umalloc_worstFit);
    runTests(test_umalloc_firstFit);
    runTests(test_umalloc_nextFit);
    runTests(test_umalloc_nextFit_wrapAround);

    // ufree testcase
    printf("\nUFREE TESTS\n");
    runTests(test_ufree_doubleFree);
    runTests(test_ufree_freeNULL);
    runTests(test_ufree_normalFree);

    printf("\nCOALESCE TEST\n");
    runTests(test_ufree_coalesce);

    // urealloc test cases
    printf("\nUREALLOC TESTS\n");
    runTests(test_urealloc_returnNull);
    runTests(test_urealloc_nullPointer);
    runTests(test_urealloc_freeAndRealloc);

    runTests(stressTest);

    test();
}

int main(){
    // Run all tests
    callFunctions();
    return 0;
}