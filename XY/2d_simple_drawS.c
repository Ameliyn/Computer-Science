#include "../FPToolkit.c"
#define MAXOBJECTS 10
#define MAXPTS 50000
#define MAXPOLYS 30000


int numpoints[MAXOBJECTS];
int numpolys[MAXOBJECTS];
double x[MAXOBJECTS][MAXPTS];
double y[MAXOBJECTS][MAXPTS];
int psize[MAXOBJECTS][MAXPOLYS];
int cont[MAXOBJECTS][MAXPOLYS][20];
double red[MAXOBJECTS][MAXPOLYS];
double grn[MAXOBJECTS][MAXPOLYS];
double blu[MAXOBJECTS][MAXPOLYS];

void center_object(int objNumber){

  double xMax = x[objNumber][0];
  double xMin = x[objNumber][0];
  double yMax = y[objNumber][0];
  double yMin = y[objNumber][0];
  double xCOM = 0;
  double yCOM = 0;

  //magnify object
  for(int i = 1; i < numpoints[objNumber]; i++){
    if(xMin > x[objNumber][i]) xMin = x[objNumber][i];
    if(xMax < x[objNumber][i]) xMax = x[objNumber][i];
    if(yMin > y[objNumber][i]) yMin = y[objNumber][i];
    if(yMax < y[objNumber][i]) yMax = y[objNumber][i];
  }
  
  double xLen = xMax - xMin;
  double yLen = yMax - yMin;
  if(xLen > yLen){
    double magnifier = 500 / xLen;
    for(int i = 0; i < numpoints[objNumber]; i++){
      x[objNumber][i] *= magnifier;
      y[objNumber][i] *= magnifier;
    }
  }
  else{
    double magnifier = 500 / yLen;
    for(int i = 0; i < numpoints[objNumber]; i++){
      x[objNumber][i] *= magnifier;
      y[objNumber][i] *= magnifier;
    }
  }

  //transform Object
  for(int i = 0; i < numpoints[objNumber]; i++){
    xCOM += x[objNumber][i];
    yCOM += y[objNumber][i];    
  }

  xCOM = xCOM / numpoints[objNumber];
  yCOM = yCOM / numpoints[objNumber];
  
  double xTRANS = 400 - xCOM;
  double yTRANS = 400 - yCOM;
  
  for(int i = 0; i < numpoints[objNumber]; i++){
    x[objNumber][i] += xTRANS;
    y[objNumber][i] += yTRANS;
  }
  
}

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
    center_object(fileNumber);
    fclose(f);
  }
  
}




void rotate_object(int objNumber, double rotation){

  printf("Rotating Object %d, with numpoints %d\n",objNumber, numpoints[objNumber]);
  double xCOM = 0;
  double yCOM = 0;
  
  //find COM
  for(int i = 0; i < numpoints[objNumber]; i++){
    xCOM += x[objNumber][i];
    yCOM += y[objNumber][i];    
  }

  xCOM = xCOM / numpoints[objNumber];
  yCOM = yCOM / numpoints[objNumber];

  //rotate points
  //new x = xcos(0.1) - ysin(0.1)
  //new y = xsin(0.1) + ycos(0.1)
  
  for(int i = 0; i < numpoints[objNumber]; i++)
  {
    double xZero = x[objNumber][i] - xCOM;
    double yZero = y[objNumber][i] - yCOM;
    x[objNumber][i] = (xZero*cos(rotation)) - (yZero*sin(rotation)) + xCOM;
    y[objNumber][i] = (xZero*sin(rotation)) + (yZero*cos(rotation)) + yCOM;
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
  int previousObj = 0;
  G_init_graphics(800,800);
  G_rgb(0,0,0);
  G_clear();

  do{
    if(input >= 48 && input < 48 + argc - 1){
      draw_object(input - 48);
      previousObj = input-48;
    }
    if(input == ',')
    {
      rotate_object(previousObj, M_PI/32);
      draw_object(previousObj);
    }
    else if(input == '.')
    {
      rotate_object(previousObj, -M_PI/32);
      draw_object(previousObj);
    }
    
    input = G_wait_key();
    if(input == 'q' || input == 'Q'){break;}
    
  }while(1);

  return 0;
}
