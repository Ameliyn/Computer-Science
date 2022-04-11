#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int generate_dataset(int num_points, double limit_a, double limit_b, int integer, FILE *f){

    char linename[100];
    sprintf(linename,"%s%d%s%.1f%s%.1f <- = c(", "C_",num_points,"_",limit_a,"_",limit_b);
    fputs(linename,f);
    char temp[100];
    for(int i = 0; i < num_points; i++){
      
      if(integer == 1){
	if(i+1 == num_points){
	  sprintf(temp,"%d)\n", (rand()%(int)limit_b) + (int)limit_a);
	}
	else{
	  sprintf(temp,"%d, ", (rand()%(int)limit_b) + (int)limit_a);
	}
      }
      else{
	if(i+1 == num_points){
	  sprintf(temp,"%lf)\n", (rand()/(RAND_MAX / limit_b)) + limit_a);
	}
	else{
	  sprintf(temp,"%lf, ", (rand()/(RAND_MAX / limit_b)) + limit_a);
	}
      }
      fputs(temp,f);
      if(i%20==0){
        fputs("\n",f);
      }
    }
}

int main(){

  FILE *f;
  f = fopen("Cdataset.R", "a");
  time_t t;
  
  srand((unsigned) time(&t));

  generate_dataset(128, 0, 1, 0, f);
  generate_dataset(128, 1, 128, 1, f);
  generate_dataset(128, 1, 512, 1, f);
  generate_dataset(128, 1, 1024, 1, f);
  generate_dataset(128, 1, 2048, 1, f);

  generate_dataset(512, 0, 1, 0, f);
  generate_dataset(512, 1, 128, 1, f);
  generate_dataset(512, 1, 512, 1, f);
  generate_dataset(512, 1, 1024, 1, f);
  generate_dataset(512, 1, 2048, 1, f);

  generate_dataset(1024, 0, 1, 0, f);
  generate_dataset(1024, 1, 128, 1, f);
  generate_dataset(1024, 1, 512, 1, f);
  generate_dataset(1024, 1, 1024, 1, f);
  generate_dataset(1024, 1, 2048, 1, f);

  generate_dataset(2048, 0, 1, 0, f);
  generate_dataset(2048, 1, 128, 1, f);
  generate_dataset(2048, 1, 512, 1, f);
  generate_dataset(2048, 1, 1024, 1, f);
  generate_dataset(2048, 1, 2048, 1, f);

}

