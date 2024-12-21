/*--------------------------------------------*/
/* Timothy Doan                               */
/* Lab 5                                      */
/* Figure the area of a parabola using files  */

#include <stdio.h>
#include <stdlib.h>

#define IN_FILE_NAME "lab5.dat"
#define OUT_FILE_NAME "lab5.txt"

int main(void)
{
    double length, depth, area;
    FILE *infile;
    FILE *outfile;

    // Insert code to open and error-check both files.
    infile = fopen("lab5.dat", "r");
    if(infile == NULL){
        printf("Error on opening input file/n");
        exit(EXIT_FAILURE); 
    }

    outfile = fopen("lab5.txt", "w");
    if(outfile == NULL){
        printf("Error on opening output file/n");
        exit(EXIT_FAILURE);
    }


    fprintf(outfile, "\nTimothy Doan.  Lab 5. \n\n");
    fprintf(outfile, "Data on Parabolas \n\n");
    fprintf(outfile, " Length      Depth        Area   \n");
    fprintf(outfile, "--------   ---------   --------- \n");
                               
    while((fscanf(infile, "%lf%lf", &length, &depth)) == 2){
        area = (2*length*depth)/3;
        fprintf(outfile,"%7.2lf   %7.2lf    %10.3lf", length, depth, area);
        fprintf(outfile, "\n\n"); 

    }

    // insert a while loop to read the data, 2 numbers at a time, 
    // and then print the length, depth, and area as shown in the Output
 
    fprintf(outfile, "\n\n");

    // Close the two files
    fclose(infile);
    fclose(outfile);
    return EXIT_SUCCESS;
}
/*---------------------------------------------------*/
