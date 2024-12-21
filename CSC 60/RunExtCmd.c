/* ----------------------------------------------------------------- */
/*                 RunExtCmd                            CLASS        */
/*------------------------------------------------------------------ */
// Timothy Doan

#include "lab9_10.h"

void RunExtCmd(int argc, char **argv) 
{                             
    int ret;
    //Add the call to the function Redirect
    Redirect(argc,argv);
    ret = execvp(argv[0], argv);

    // Add the call to execvp
    
    if (ret == -1)    // error check for the exec call
    {                                       
        fprintf(stderr, "Error on the exec call\n");             
        _exit(EXIT_FAILURE);                                   
    }                                                         
                                                          
    return;
}
/* ----------------------------------------------------------------- */
