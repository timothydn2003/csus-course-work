#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <unistd.h>
#include <ctype.h>
#include <sys/wait.h>

// Global variables
bool isPrintCommand = false;
bool isNoInputNoRun = false;
int maxNumofArgs;
char characterToReplace[10];

// List of valid flags
char *basicOptions[] = {"-n", "-I", "-t", "-r"};
char *sanitizeList[] = {";", "&", "|", "<", ">", "*", "?", "(", ")", "$"};

char stdinput[100000];
char commandToExecute[32][128];
char *stdInputSplit[100000];

// Variables to track the length stdinput and command we are passing to xargs
int numStringsInArgs = 0;
int stdinput_len = 0;
int stdinputLineNum = 0;

// Function to check if argument being passed in is a basic function or command needing to be executed
bool checkBasicOptions(char *args){
    for(int i = 0; i < 4; i++){
        if(strcmp(args, basicOptions[i]) == 0){
            return true;
        }
    }
    return false;
}

// Function to check if -n argument is negative, if it is, act as if -n is not enabled
bool isNegative(char argsList[]){
    if(argsList[0] == '-' && isdigit(argsList[1])){
        return true;
    }else{
        return false;
    }
}

// Function to print usage string when user input is incorrect
void printUsageString(){
    fprintf(stderr, "Usage: myxargs [-n num] [-I replace] [-t] [-r] command\n");
    exit(1);
}

// Function when -t is enabled
void printCommand(char *commands[numStringsInArgs + 2]){
    printf("+ ");
    for (int i = 0; i <= numStringsInArgs; i++) {
            printf("%s ", commands[i]);
        }
    printf("\n");
}

// Function to check if the argument we are in has any spaces, meaning it has multiple words
bool checkIfArgsIsMultiple(char *arg){
    // Loop through argument and check for a space
    for(int i = 0; arg[i] != '\0'; i++){
        if(arg[i] == ' '){
            return true;
        }
    }
    return false;
}

// Function to split argumetns that are passed in as quotes
void splitArgs(){
    char temp[32][128];
    int count = 0;

    // Loop through the command we will execute
    for(int i = 0; i < numStringsInArgs; i++){
        // Check if any argument has spaces
        if(checkIfArgsIsMultiple(commandToExecute[i])){
            // Split based on spaces
            char *output = strtok(commandToExecute[i], " ");
            while (output != NULL) {
                strcpy(temp[count++], output);
                output = strtok(NULL, " ");
            }
        }else{
            strcpy(temp[count++], commandToExecute[i]);
        }
    }

    // Copy temp string back over
    for(int i =0; i < count; i++){
        strcpy(commandToExecute[i], temp[i]);
    }

    // Set the new number of arguments
    numStringsInArgs = count;
}

// Function to check if a character is a special character
bool checkIfCharacterIsSpecialCharacter(char arg){
    // Loop through list of special characters
    for(int i = 0; i < 10; i++){
        if(sanitizeList[i][0] == arg){
            return true;
        }
    }
    return false;
}

// Function to sanitize input from stdinput
void sanitizeInput(char *arg){
    char temp[100000];
    int count = 0;

    // Loop through stdin
    for(int i = 0; i < strlen(arg); i++){
        // Check if any character needs to be filtered out
        if(!checkIfCharacterIsSpecialCharacter(arg[i])){
            temp[count++] = arg[i];
        }
    }

    // Copy sanitized string to stdin
    temp[count] = '\0';
    strcpy(arg, temp);

    // if stdin has multiple lines (ie ls), remove new line
    size_t len = strcspn(arg, "\n");
    arg[len] = '\0';
    strcat(arg, " ");

    // Increment line count for stdin
    stdinputLineNum++;
}

// Function to check if any argument in the command matches the argument being passed to -I
bool checkIfStringContainsReplace(char *arg){
    char *position = strstr(arg, characterToReplace);

    if(position != NULL){
        return true;
    }

    return false;
}

// Function to process arguments being passed into xargs
void processArguments(int args, char *argsList[]){
    for(int i = 1; i < args; i++){
        // Loop through and check if any basic option flags are in the command
        if(checkBasicOptions(argsList[i])){
            if(strcmp("-n", argsList[i]) == 0){
                if(isNegative(argsList[i+1])){
                    i++;
                    continue;
                }else{
                    maxNumofArgs = atoi(argsList[i+1]);
                    // if 0 is passed to -n, error out
                    if(maxNumofArgs == 0){
                        fprintf(stderr, "xargs: illegal argument count\n");
                        exit(1);
                    }
                }
                i++;
            }else if(strcmp("-I", argsList[i]) == 0){
                strcpy(characterToReplace, argsList[i+1]);
                i++;
            }else if(strcmp("-t", argsList[i]) == 0){
                isPrintCommand = true;
            }else if(strcmp("-r", argsList[i]) == 0){
                isNoInputNoRun = true;
            }
        }else{
            // Get the command that will be executed
            strcpy(commandToExecute[numStringsInArgs++], argsList[i]);
        }
    }
}

// Function to split stdin
void splitStdInput(){
    char *output = strtok(stdinput, " ");

    // Variables for if -n is greater than 1
    int count = 0;
    char tempString[128] = "";

    while (output != NULL) {
        char *nextToken = strtok(NULL, " ");
        // Argument being passed to to -n is 1
        if(maxNumofArgs == 1){
            stdInputSplit[stdinput_len] = output;
            stdinput_len++;
        }else{
            // Argument being passed to -n is greater than 1

            // Current amount of tokens is less than argument passed to -n
            if(count < maxNumofArgs - 1){
                strcat(tempString, output);
                strcat(tempString, " ");
                count++;

                // If next token is null, that means we are at the end of the line
                if(nextToken == NULL){
                    stdInputSplit[stdinput_len] = strdup(tempString);
                    stdinput_len++;
                }
            }else{
                // The amount of tokens matches the argument passed to -n
                strcat(tempString, output);
                stdInputSplit[stdinput_len] = strdup(tempString);
                stdinput_len++;

                // reset variables
                count = 0;
                strcpy(tempString, "\0");
            }
        }
        output = nextToken;
    }
}

// Function that executes the command passed into xargs
void runCommand(char input[]){
    char *commands[numStringsInArgs + 2];
    pid_t pid = fork();

    if(isNoInputNoRun && stdinput[0] == '\0'){
        exit(0);
    }

    if (pid < 0) {
        // Fork failed
        perror("fork");
        exit(1);
    } else if (pid == 0) {
        for(int i = 0; i < numStringsInArgs; i++){
            commands[i] = commandToExecute[i];
        }

        if(maxNumofArgs){
            // Check if -n flag is set and if it is handle it
            commands[numStringsInArgs] = input;
        }else if(strcmp(characterToReplace, "") != 0){
            // Check if -I flag is set and if it is handle it
            commands[numStringsInArgs] = "";
        }else{
            // -n and -I are both not passed
            if(strcmp(commandToExecute[0], "echo") == 0){
                commands[numStringsInArgs] = stdinput;
            }else{
                commands[numStringsInArgs] = input;
            }
        }

        // Set null
        commands[numStringsInArgs + 1] = NULL;

        // If -t is enabled, print the command being executed
        if(isPrintCommand){
            printCommand(commands);
        }

        // Execute command
        execvp(commands[0], commands);
        exit(1);

    } else {
        // Parent process: wait for the child to finish
        int status;
        wait(&status);

        // If the process failed, print this error message and exit
        if(status == 256){
            fprintf(stderr, "xargs: %s: No such file or directory\n", commandToExecute[0]);
            exit(1);
        }
    }
}

// Function to split the standard input if -n is enabled
void runCommandWithN(){
    // Split standard input
    splitStdInput();

    // Run the command for each argument from standard input
    for(int i = 0; i < stdinput_len; i++){
        runCommand(stdInputSplit[i]);
    }
}

// Function to handle when -I flag is passed
void runCommandWithI(){
    // Incase arguments are passed through a quote, we need to split them
    splitArgs();

    // Find the index in the command string where the pattern is
    int indexFound;
    for(int i = 1; i < numStringsInArgs; i++){
        if(checkIfStringContainsReplace(commandToExecute[i])){
            indexFound = i;
        }
    }

    // Save stdinput as a temp variable before splitting
    char temp[1024] = "";
    strcpy(temp, stdinput);

    // Split stdinput by spaces
    splitStdInput();

    // If index isnt found, then the run the command with out replacing
    if(indexFound == 0){
        runCommand(NULL);
    }

    // In the case that stdinput is multiple lines (ie ls),
    // then we need to execute each line individually
    if(stdinputLineNum > 1){
        for(int i = 0; i < stdinput_len; i++){
            strcpy(commandToExecute[indexFound], stdInputSplit[i]);
            runCommand(NULL);
        }
    // stdinput is only 1 line (ie echo), so we just pass the whole line to the command
    }else{
        strcpy(commandToExecute[indexFound], temp);
        runCommand(NULL);
    }
}

int main(int args, char **argsList){
    // Read from stdin
    char temp[128];
    while(fgets(temp, sizeof(temp), stdin) != NULL){
        sanitizeInput(temp);
        strcat(stdinput, temp);
    }

    stdinput[strlen(stdinput)-1] = '\0';

    // If no arguments are passed, then print usage string and exit
    if(args == 1){
        printUsageString();
    }

    // Process arguments that are being passed to xargs
    processArguments(args, argsList);

    // If -I and -n are both being passed, print usage string
    if(strcmp(characterToReplace, "") != 0 && maxNumofArgs){
        printUsageString();
    }

    if(maxNumofArgs){
        // Run command with -n flag
        runCommandWithN();
    }else if(strcmp(characterToReplace, "") != 0){
        // Run command with -I flag
        runCommandWithI();
    }else{
        // Run command without -n or -I flag that is passed into xargs

        // If command we are executing is echo, just run
        if(strcmp(commandToExecute[0], "echo") == 0){
            runCommand(NULL);
        }else{
            // If command we are executing is not echo (ie ls or wc),
            // split stdin back to multiple lines and run command for each
            splitStdInput();

            // If stdin is not multiple lines (ie echo)
            if(stdinputLineNum < 2 && strcmp(commandToExecute[0], "echo") == 0){
                runCommand(NULL);
            }else{
                for(int i = 0; i < stdinput_len; i++){
                    runCommand(stdInputSplit[i]);
                }
            }

        }
    }

    return 0;
}