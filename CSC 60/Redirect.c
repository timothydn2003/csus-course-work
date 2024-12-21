/* Author(s): Timothy Doan

/* ----------------------------------------------------------------- */
/*                  Redirect, part of a child process                */
/* ----------------------------------------------------------------- */

#include "lab9_10.h"

void Redirect(int argc, char *argv[])
{
    int i;	     // loop counter
    int out = 0;  // track position of location Out redirection, >
    int in = 0;   // track position of location In redirection, <
    int fd;
    int fd1;

  

    for(i = 0; i < argc; i++){
        if(strcmp(">", argv[i]) ==  0){
            if(out != 0){
                perror("Cannot ouput to more than one file");
                exit(EXIT_FAILURE);
            }else if(i == 0){
                perror("No command entered");
                exit(EXIT_FAILURE);
            }
            out = i;
        }else if(strcmp("<", argv[i]) == 0){
            if(in != 0 ){
                perror("Cannot input more than one file");
                exit(EXIT_FAILURE);
            }else if(i == 0){
                perror("No command entered");
                exit(EXIT_FAILURE);
            }
            in = i;
        }
    }

    if(out != 0){
        if(argv[out + 1] == NULL){
            perror("There is no file");
            exit(EXIT_FAILURE);
        }

        fd = open(argv[out+1], O_RDWR | O_CREAT | O_TRUNC, S_IRUSR | S_IWUSR | S_IRUSR);
        
        if(fd == -1){
            perror("Error opening file");
            exit(EXIT_FAILURE);
        }
            
        int returnvalue = dup2(fd, 1);

        if(returnvalue == -1){
            perror("close file error\n");
            exit(EXIT_FAILURE);
        }
        
        close(fd);
        
        argv[out] = NULL;
    }

    if(in != 0){
        if(argv[in+1] == NULL){
            perror("There is no file");
            exit(EXIT_FAILURE);
        }
        fd1 = open(argv[in + 1], O_RDONLY, S_IRUSR | S_IWUSR);

        if(fd1 == -1){
            perror("Error opening file");
            exit(EXIT_FAILURE);
        }
        dup2(fd1, 0);

        int returnvalue = close(fd1);

        if(returnvalue == -1){
            perror("close file error\n");
            exit(EXIT_FAILURE);
        }

        argv[in] = NULL;
    }
}  /*end of function*/

