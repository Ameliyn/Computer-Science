#include <stdio.h>

void change2dArray(double x[2][2]){

  x[0][0] = 5.0;
  x[0][1] = 6.0;
  x[1][0] = 7.0;
  x[1][1] = 8.0;

}



int main(){

  double x[2][2] = {{1.0,2.0},{3.0,4.0}};
  change2dArray(x);

  printf("x: %.2f,%.2f,%.2f,%.2f\n", x[0][0],x[0][1],x[1][0],x[1][1]);

}
