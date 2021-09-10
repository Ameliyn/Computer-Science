/**
 * tinycalc.c
 *
 * TinyCalc - A simple, terminal-based calculator application.
 *
 * This file implements the behaviors defined in the interface
 * specified in tinycalc.h.
 *
 * Written by Skye Russ
 */

#include "tinycalc.h"
#include <stdio.h>

/* put your function implementations in here. */

int check_command(char command)
{
  if(command == '+' || command == '-' || command == '*' || command == '/' || command == '^'
     || command == 'm' || command == 'M' || command == 'q' || command == 'Q')
    return TC_COMMAND_OK;
  else
    return TC_COMMAND_INVALID;
}

int read_command(char *command, double *operand)
{ //INCOMPLETE
  char comm;
  double oper;
  do{
    scanf("%c%lf",comm,oper);
  }while(check_command(comm) != TC_COMMAND_OK);
  
  if(command[0] == 'q' || command[0] == 'Q') return TC_COMMAND_QUIT;
  
  return 0; 
}

void execute_calculation(char operator, double operand2, double *operand1)
{
  if(operator == '+')
    *operand1 = *operand1 + operand2;
  else if(operator == '-')
    *operand1 = *operand1 - operand2;
  else if(operator == '*')
    *operand1 = *operand1 * operand2;
  else if(operator == '/')
    *operand1 = *operand1 / operand2;
  else if(operator == '^'){
    for(int i = 2; i <= operand2; i++){
      *operand1 = *operand1 * *operand1;
    }
  }
}

double mem_read(tc_memory_t memory, int n)
{
  if(n > TC_MEM_SZ - 1) return memory.vals[memory.most_recent];
  else return memory.vals[n];
}

void mem_save(tc_memory_t *memory, double value)
{
  for(int i = TC_MEM_SZ - 1; i > 0; i--){
    memory->vals[i] = memory->vals[i-1];
  }
  memory->vals[0] = value;
  memory->most_recent = 0;
}
