#include <stdio.h>
#include <string.h>

int main(){

  char *file1 = "Hello ";
  char *file2 = "World!";
  strcat(file1,file2);

  printf("%s",file1);

}