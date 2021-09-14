int numpoints;
int numpolys;
double x[30000];
double y[30000];
int psize[12000];
int cont[12000][20];
double red[12000];
double grn[12000];
double blu[12000];


void load_files(int numFiles, char** fileNames){


  File *f;

  for(int fileNumber = 0; fileNumber < numFiles; fileNumber++){

    f = fopen(fileNames[i], "r");
    if(f == NULL)
    {
       printf("Cannot open file %s... Skipping...\n",filenames[fileNumber]);
       continue;
    }

    fscanf(f,"%d",&numpoints);
    
    for(int i = 0; i < numpoints; i++){
      fscanf(f,"%lf %lf",&x[i],&y[i]);
    }

    fscanf(f,"%d",&numpolys);

    
  }
  




}


int main(int argc, char **argv){


  if(argc < 2) {printf("Usage: 2d_poly polygon.xy\n"); exit(0);}

}
