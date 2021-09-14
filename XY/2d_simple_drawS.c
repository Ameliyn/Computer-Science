#include "../FPToolkit.c"
#define MAXOBJECTS 120


int numpoints[MAXOBJECTS];
int numpolys[MAXOBJECTS];
double x[MAXOBJECTS][30000];
double y[MAXOBJECTS][30000];
int psize[MAXOBJECTS][12000];
int cont[MAXOBJECTS][12000][20];
double red[MAXOBJECTS][12000];
double grn[MAXOBJECTS][12000];
double blu[MAXOBJECTS][12000];


void load_files(int numFiles, char** fileNames){

  FILE *f;

  for(int fileNumber = 0; fileNumber < numFiles; fileNumber++){

    printf("Loading %s\n",fileNames[fileNumber+1]);
    f = fopen(fileNames[fileNumber+1], "r");
    if(f == NULL)
    {
       printf("Cannot open file %s... Skipping...\n",fileNames[fileNumber+1]);
       continue;
    }

    fscanf(f,"%d",&numpoints[fileNumber]);
    
    for(int i = 0; i < numpoints[fileNumber]; i++){
      fscanf(f,"%lf %lf",&x[fileNumber][i],&y[fileNumber][i]);
    }

    fscanf(f,"%d",&numpolys[fileNumber]);

    for(int i = 0; i < numpolys[fileNumber]; i++){
      fscanf(f,"%d",&psize[fileNumber][i]);
      for(int j = 0; j < psize[fileNumber][i]; j++){
	fscanf(f,"%d",&cont[fileNumber][i][j]);
      }
    }

    for(int i = 0; i < numpolys[fileNumber]; i++){
      fscanf(f,"%lf %lf %lf",&red[fileNumber][i],&grn[fileNumber][i],&blu[fileNumber][i]);
    }

    fclose(f);
  }
  
}


void draw_object(int input)
{
  G_rgb(0,0,0);
  G_clear();

  double xp[numpoints[input]];
  double yp[numpoints[input]];
  
  for(int i = 0; i < numpolys[input]; i++){

    for(int j = 0; j < psize[input][i]; j++){
      xp[j] = x[input][cont[input][i][j]];
      yp[j] = y[input][cont[input][i][j]];
    }

    G_rgb(red[input][i],grn[input][i],blu[input][i]);
    G_fill_polygon(xp,yp,psize[input][i]);
  }
  

}

int main(int argc, char **argv){


  if(argc < 2) {printf("Usage: 2d_poly polygon.xy\n"); exit(0);}
  load_files(argc - 1, argv);

  char input = 48;
  G_init_graphics(800,800);
  G_rgb(0,0,0);
  G_clear();

  do{
    if(input >= 48 && input < 48 + argc - 1)
      draw_object(input - 48);

    input = G_wait_key();
    if(input == 'q' || input == 'Q'){break;}
  }while(1);

  return 0;
}
