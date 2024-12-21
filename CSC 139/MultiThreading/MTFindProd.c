// Timothy Doan

// Tested on Linux
// Architecture:             x86_64
//   CPU op-mode(s):         32-bit, 64-bit
//   Address sizes:          43 bits physical, 48 bits virtual
//   Byte Order:             Little Endian
// CPU(s):                   4
//   On-line CPU(s) list:    0-3
// Vendor ID:                GenuineIntel
//   Model name:             Intel(R) Xeon(R) Gold 6254 CPU @ 3.10GHz
//     CPU family:           6
//     Model:                58
//     Thread(s) per core:   1
//     Core(s) per socket:   2
//     Socket(s):            2
//     Stepping:             0
//     BogoMIPS:             6185.46
//     Flags:                fpu vme de pse tsc msr pae mce cx8 apic sep mtrr pge m
//                           ca cmov pat pse36 clflush mmx fxsr sse sse2 ss ht sysc
//                           all nx rdtscp lm constant_tsc arch_perfmon nopl xtopol
//                           ogy tsc_reliable nonstop_tsc cpuid tsc_known_freq pni
//                           pclmulqdq ssse3 cx16 pcid sse4_1 sse4_2 x2apic popcnt
//                           tsc_deadline_timer aes xsave avx f16c rdrand hyperviso
//                           r lahf_lm pti ssbd ibrs ibpb stibp fsgsbase tsc_adjust
//                            smep arat md_clear flush_l1d arch_capabilities
// Virtualization features:
//   Hypervisor vendor:      VMware
//   Virtualization type:    full
// Caches (sum of all):
//   L1d:                    128 KiB (4 instances)
//   L1i:                    128 KiB (4 instances)
//   L2:                     4 MiB (4 instances)
//   L3:                     49.5 MiB (2 instances)
// NUMA:
//   NUMA node(s):           1
//   NUMA node0 CPU(s):      0-3

#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <sys/timeb.h>
#include <semaphore.h>
#include <stdbool.h> // For bool, true, false

#define MAX_SIZE 100000000
#define MAX_THREADS 16
#define RANDOM_SEED 7649
#define MAX_RANDOM_NUMBER 3000
#define NUM_LIMIT 9973

// Global variables
long gRefTime; //For timing
int gData[MAX_SIZE]; //The array that will hold the data

int gThreadCount; //Number of threads
int gDoneThreadCount; //Number of threads that are done at a certain point. Whenever a thread is done, it increments this. Used with the semaphore-based solution
int gThreadProd[MAX_THREADS]; //The modular product for each array division that a single thread is responsible for
volatile bool gThreadDone[MAX_THREADS]; //Is this thread done? Used when the parent is continually checking on child threads

// Semaphores
sem_t completed; //To notify parent that all threads have completed or one of them found a zero
sem_t mutex; //Binary semaphore to protect the shared variable gDoneThreadCount

// Function prototypes
int SqFindProd(int size); //Sequential FindProduct (no threads) computes the product of all the elements in the array mod NUM_LIMIT
void* ThFindProd(void* param); //Thread FindProduct but without semaphores
void* ThFindProdWithSemaphore(void* param); //Thread FindProduct with semaphores
int ComputeTotalProduct(); // Multiply the division products to compute the total modular product
void InitSharedVars(); //Initialize shared variables
void GenerateInput(int size, int indexForZero); //Generate the input array
void CalculateIndices(int arraySize, int thrdCnt, int indices[MAX_THREADS][3]); //Calculate the indices to divide the array into T divisions, one division per thread
int GetRand(int min, int max); //Get a random number between min and max

//Timing functions
long GetMilliSecondTime(struct timeb timeBuf);
long GetCurrentTime(void);
void SetTime(void);
long GetTime(void);

int main(int argc, char* argv[]) {
    pthread_t tid[MAX_THREADS];
    pthread_attr_t attr[MAX_THREADS];
    int indices[MAX_THREADS][3];
    int i, indexForZero, arraySize, prod;

    // Code for parsing and checking command-line arguments
    if (argc != 4) {
        fprintf(stderr, "Invalid number of arguments!\n");
        exit(-1);
    }

    if ((arraySize = atoi(argv[1])) <= 0 || arraySize > MAX_SIZE) {
        fprintf(stderr, "Invalid Array Size\n");
        exit(-1);
    }
    gThreadCount = atoi(argv[2]);

    if (gThreadCount > MAX_THREADS || gThreadCount <= 0) {
        fprintf(stderr, "Invalid Thread Count\n");
        exit(-1);
    }

    indexForZero = atoi(argv[3]);

    if (indexForZero < -1 || indexForZero >= arraySize) {
        fprintf(stderr, "Invalid index for zero!\n");
        exit(-1);
    }

    GenerateInput(arraySize, indexForZero);
    CalculateIndices(arraySize, gThreadCount, indices);

    // Code for the sequential part
    SetTime();
    prod = SqFindProd(arraySize);
    printf("Sequential multiplication completed in %ld ms. Product = %d\n", GetTime(), prod);

    // Threaded with parent waiting for all child threads
    InitSharedVars();
    SetTime();

    // Write your code here
    // Initialize threads, create threads, and then let the parent wait for all threads using pthread_join
    // The thread start function is ThFindProd
    // Don't forget to properly initialize shared variables

    // Create threads
    for(int i = 0; i < gThreadCount; i++){
        pthread_create(&tid[i], NULL, ThFindProd, &indices[i]);
    }

    // Wait for threads to finish
    for(int i = 0; i < gThreadCount; i++){
        pthread_join(tid[i], NULL);
    }

    prod = ComputeTotalProduct();
    printf("Threaded multiplication with parent waiting for all children completed in %ld ms. Product = %d\n", GetTime(), prod);

    // Multi-threaded with busy waiting (parent continually checking on child threads without using semaphores)
    InitSharedVars();
    SetTime();

    // Write your code here
    // Don't use any semaphores in this part
    // Initialize threads, create threads, and then make the parent continually check on all child threads
    // The thread start function is ThFindProd
    // Don't forget to properly initialize shared variables
    for(int i = 0; i < gThreadCount; i++){
        pthread_create(&tid[i], NULL, ThFindProd, &indices[i]);
    }

    // Continually check child threads with this while loop
    while(gDoneThreadCount < gThreadCount){
        // bool to check if we have found 0
        bool found = false;

        // loop through all threads
        for(int i = 0; i < gThreadCount; i++){
            // check if thread is done, if yes, increment
            if(gThreadDone[i]){
                gDoneThreadCount++;
                // Set to false so on next interation, it doesnt
                // increment counter again
                gThreadDone[i] = false;
                // If 0 was found in a thread, terminate
                if(gThreadProd[i] == 0){
                    found = true;
                    break;
                }
            }
        }
        // 0 was found, break out of while loop
        if(found){
            break;
        }
    }

    // Cancel all threads
    for(int i = 0; i < gThreadCount; i++){
        pthread_cancel(tid[i]);
    }

    prod = ComputeTotalProduct();
    printf("Threaded multiplication with parent continually checking on children completed in %ld ms. Product = %d\n", GetTime(), prod);

    // Multi-threaded with semaphores
    InitSharedVars();
    // Initialize your semaphores here
    sem_init(&completed, 0, 0);
    sem_init(&mutex, 0, 1);
    SetTime();

    // Write your code here
    // Initialize threads, create threads, and then make the parent wait on the "completed" semaphore
    // The thread start function is ThFindProdWithSemaphore
    // Don't forget to properly initialize shared variables and semaphores using sem_init

    // Create all threads
    for(int i = 0; i < gThreadCount; i++){
        pthread_create(&tid[i], NULL, ThFindProdWithSemaphore, &indices[i]);
    }

    // Wait for a child thread to post completed
    sem_wait(&completed);

    // Cancel all threads
    for(int i = 0; i < gThreadCount; i++){
        pthread_cancel(tid[i]);
    }

    prod = ComputeTotalProduct();
    printf("Threaded multiplication with parent waiting on a semaphore completed in %ld ms. Product = %d\n", GetTime(), prod);

    // Cleanup semaphores
    sem_destroy(&completed);
    sem_destroy(&mutex);
    return 0;
}

// Write a regular sequential function to multiply all the elements in gData mod NUM_LIMIT
// REMEMBER TO MOD BY NUM_LIMIT AFTER EACH MULTIPLICATION TO PREVENT YOUR PRODUCT VARIABLE FROM OVERFLOWING
int SqFindProd(int size) {
    // TODO: Implement this function
    int temp = 1;
    for(int i = 0; i < size; i ++){
        if(gData[i] != 0){
            temp = (temp * gData[i]) % NUM_LIMIT;
        }else{
            return 0;
        }
    }
    return temp;
}

// Write a thread function that computes the product of all the elements in one division of the array mod NUM_LIMIT
// REMEMBER TO MOD BY NUM_LIMIT AFTER EACH MULTIPLICATION TO PREVENT YOUR PRODUCT VARIABLE FROM OVERFLOWING
// When it is done, this function should store the product in gThreadProd[threadNum] and set gThreadDone[threadNum] to true
void* ThFindProd(void* param) {
    int* arg = (int*)param;

    // Get start and end indexes
    int start = arg[1];
    int end = arg[2];

    // Temp product variable
    int temp = 1;

    for(int i = start; i <= end; i++){
        temp = (temp * gData[i]) % NUM_LIMIT;
        // Find a 0, break out of the loop
        if(temp == 0){
            break;
        }
    }

    // Set the product to temp and set the thread to done
    gThreadProd[arg[0]] = temp;
    gThreadDone[arg[0]] = true;

    return NULL;
}

// Write a thread function that computes the product of all the elements in one division of the array mod NUM_LIMIT
// REMEMBER TO MOD BY NUM_LIMIT AFTER EACH MULTIPLICATION TO PREVENT YOUR PRODUCT VARIABLE FROM OVERFLOWING
// When it is done, this function should store the product in gThreadProd[threadNum]
// If the product value in this division is zero, this function should post the "completed" semaphore
// If the product value in this division is not zero, this function should increment gDoneThreadCount and
// post the "completed" semaphore if it is the last thread to be done
// Don't forget to protect access to gDoneThreadCount with the "mutex" semaphore
void* ThFindProdWithSemaphore(void* param) {
    int* arg = (int*)param;

    int start = arg[1];
    int end = arg[2];
    int temp = 1;

    for(int i = start; i <= end; i++){
        temp = (temp * gData[i]) % NUM_LIMIT;
        if(temp == 0){
            // 0 is in this division
            gThreadProd[arg[0]] = 0;
            sem_post(&completed);
            return NULL;
        }
    }
    gThreadProd[arg[0]] = temp;

    // Since gDoneThreadCount is shared data
    // it is part of the critical section, must lock
    sem_wait(&mutex);
    gDoneThreadCount++;
    sem_post(&mutex);

    // If all threads are done, post
    if(gDoneThreadCount == gThreadCount){
        sem_post(&completed);
    }
    return NULL;
}

int ComputeTotalProduct() {
    int i, prod = 1;

    for (i = 0; i < gThreadCount; i++) {
        prod *= gThreadProd[i];
        prod %= NUM_LIMIT;
    }

    return prod;
}

void InitSharedVars() {
    int i;

    for (i = 0; i < gThreadCount; i++) {
        gThreadDone[i] = false;
        gThreadProd[i] = 1;
    }
    gDoneThreadCount = 0;
}

// Write a function that fills the gData array with random numbers between 1 and MAX_RANDOM_NUMBER
// If indexForZero is valid and non-negative, set the value at that index to zero
void GenerateInput(int size, int indexForZero) {
    // TODO: Implement this function
    srand(RANDOM_SEED);
    for(int i = 0; i < size; i++){
        if(i == indexForZero){
            gData[i] = 0;
        }else{
            gData[i] = GetRand(1, MAX_RANDOM_NUMBER);
        }
    }
}

// Write a function that calculates the right indices to divide the array into thrdCnt equal divisions
// For each division i, indices[i][0] should be set to the division number i,
// indices[i][1] should be set to the start index, and indices[i][2] should be set to the end index
void CalculateIndices(int arraySize, int thrdCnt, int indices[MAX_THREADS][3]) {
    // TODO: Implement this function

    // If there are more threads than array size
    if(arraySize < thrdCnt){
        fprintf(stderr, "Array size can't be smaller than thread count\n");
        exit(1);
    }

    // Split the array size into portions totaling the thrdCnt
    int split = (arraySize/thrdCnt);

    int beg = 0;
    int end = split - 1;

    for(int i = 0; i < thrdCnt; i++){
        // division number
        indices[i][0] = i;
        // start index
        indices[i][1] = beg;
        // end index
        indices[i][2] = end;

        // We add split to beg and end after each interation
        // bcs it adds the next portion of the arraySize
        beg = beg + split;
        end = end + split;
    }
}

// Get a random number in the range [x, y]
int GetRand(int x, int y) {
    int r = rand();
    r = x + r % (y - x + 1);
    return r;
}

long GetMilliSecondTime(struct timeb timeBuf) {
    long mliScndTime;
    mliScndTime = timeBuf.time;
    mliScndTime *= 1000;
    mliScndTime += timeBuf.millitm;
    return mliScndTime;
}

long GetCurrentTime(void) {
    long crntTime = 0;
    struct timeb timeBuf;
    ftime(&timeBuf);
    crntTime = GetMilliSecondTime(timeBuf);
    return crntTime;
}

void SetTime(void) {
    gRefTime = GetCurrentTime();
}

long GetTime(void) {
    long crntTime = GetCurrentTime();
    return (crntTime - gRefTime);
}
