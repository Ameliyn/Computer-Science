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
 
  return 0;
}
