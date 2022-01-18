#include <stdio.h>

int main(){
  char w[100];
  for(int i = 0; i < 30; i++){

    sprintf(w,"GrowingSun%04d.xwd",i);
    
    printf("%s\n",w);
    //G_save_image_to_file(w);
  }
  
}