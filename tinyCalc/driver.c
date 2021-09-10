/**
 * driver.c
 *
 * TinyCalc - A simple, terminal-based calculator application.
 *
 * This is the driver part of the application.
 *
 * Build: cc -std=c99 driver.c tinycalc.c -o tinycalc
 * Run:   ./tinycalc
 *
 * Written by Skye Russ
 */

#include <stdio.h>
#include "tinycalc.h"

/* put your application code in this file. */

int main()
{
  printf("\nWelcome to TinyCalc!\n\n Enter an operation <+, - , *, /, ^>");
  printf(" followed by a real number.\n\n Enter 'q' or 'Q' to quit.\n\n");
  printf(" Enter 'm' or 'M' followed by a location (0-4) to load a previous\n");
  printf(" result from memory.\n");
  printf("\n> ");

  char command;
  double operand;
  while(True){
    scanf("%c%lf",command,operand);
    if(check_command(command) == TC_COMMAND_OK){
      if(read_command(*command,*operand) == TC_COMMAND_QUIT) break;
      else if(command == 'm' || command == 'M')
	printf("\n>%.2f", mem_read(tc_memory_t, (int)operand))
      else{
	execute_calculation(command,mem_read(tc_memory_t, 0),operand);
	printf("\n%.2f", mem_read(tc_memory_t, 0));
      }
      
    }

    printf("\n> ");
  }
  
  return 0;
}
