/*  Timothy Doan                            */
/*  lab8.h - Olympic woman's javelin throw  */

#define NCOMPETITORS 4
#define N_TRIES 4

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define OUT_FILENAME "lab8.txt"
//#define OUT_FILENAME "lab8sample.txt"

#define IN_FILENAME "lab8.dat"
//#define IN_FILENAME "lab8sample.dat"

// ADD the two structs required: thrower_t and stats_t.
typedef struct{
    char name[20];
    double tries[N_TRIES];
    double best_throw;
    double deviation;
}thrower_t;

typedef struct{
    double average_of_best_throws;
    double winning_throw;
}stats_t;





/* FUNCTION PROTOTYPES */
/* get_data is a function to get and read data */
void get_data (char *filename,                        /* input  */ 
	       thrower_t throw_list[NCOMPETITORS] );  /* output */

/* open_out_file is a function to open the output file */
FILE * open_out_file (void);
		
/* get_stats is a function to do figure the best throw for each      */
/* thrower, compute the all-over average of the best throws, and     */
/* find the longest throw  on the track and each thrower's deviation */
/* from that winning long throw                                      */
void get_stats(thrower_t throw_list[NCOMPETITORS], /* in & out    */
 	       stats_t *throw_stats);              /* output      */
			
/*  print_all is a function to print things out.
    all of its arguments are input  */
void print_all(FILE * out_file,
	           thrower_t throw_list[NCOMPETITORS],
	           stats_t *throw_stats);				 
         
