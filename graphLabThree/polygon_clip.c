#include "../FPToolkit.c"
#include "M2d_matrix_toolsS.c"
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
double clipX[20];
double clipY[20];
int clipnumpoints;

int scrnsize = 1000;

void center_object_matrix(int objNumber){

  double A[3][3];
  double B[3][3];
  M2d_make_identity(A);
  M2d_make_identity(B);

    
  double xMax = x[objNumber][0];
  double xMin = x[objNumber][0];
  double yMax = y[objNumber][0];
  double yMin = y[objNumber][0];
  double xCOM = 0;
  double yCOM = 0;

  //transform Object
  for(int i = 0; i < numpoints[objNumber]; i++){
    xCOM += x[objNumber][i];
    yCOM += y[objNumber][i];
  }

  xCOM = xCOM / numpoints[objNumber];
  yCOM = yCOM / numpoints[objNumber];
  
  double xTRANS = -xCOM;
  double yTRANS = -yCOM;
  
  M2d_make_translation(B,xTRANS,yTRANS);
  M2d_mat_mult(A, B, A);

  
  //magnify object
  for(int i = 1; i < numpoints[objNumber]; i++){
    if(xMin > x[objNumber][i]) xMin = x[objNumber][i];
    if(xMax < x[objNumber][i]) xMax = x[objNumber][i];
    if(yMin > y[objNumber][i]) yMin = y[objNumber][i];
    if(yMax < y[objNumber][i]) yMax = y[objNumber][i];
  }
  
  double xLen = xMax - xMin;
  double yLen = yMax - yMin;
  double magnifier;
  if(xLen > yLen){ magnifier = (scrnsize * 0.625) / xLen; }
  else{ magnifier = (scrnsize * 0.625) / yLen; }
  
  M2d_make_scaling(B, magnifier, magnifier);
  M2d_mat_mult(A, B, A);

  //transform Object
  xTRANS = (scrnsize / 2);
  yTRANS = (scrnsize / 2);
  
  M2d_make_translation(B,xTRANS,yTRANS);
  M2d_mat_mult(A, B, A);

  M2d_mat_mult_points(x[objNumber], y[objNumber], A, x[objNumber], y[objNumber], numpoints[objNumber]); 
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
    //center_object(fileNumber);
    center_object_matrix(fileNumber);
    
    fclose(f);
  }
  
}

//assumes object is centered to screen
void rotate_object_matrix(int objNumber, double rotation){

  double A[3][3];
  double B[3][3];
  M2d_make_identity(A);
  M2d_make_translation(B, -scrnsize / 2, -scrnsize / 2);
  M2d_mat_mult(A,B,A);
  M2d_make_rotation(B, rotation);
  M2d_mat_mult(A,B,A);
  M2d_make_translation(B, scrnsize / 2, scrnsize / 2);
  M2d_mat_mult(A,B,A);
  M2d_mat_mult_points(x[objNumber], y[objNumber], A, x[objNumber],
		      y[objNumber], numpoints[objNumber]);

}

int intersect_2_lines (double A[2], double B[2],
                       double C[2], double D[2],
                       double intersection[2])
// return 0 if lines do NOT intersect
// return 1 if they do  
{
  double aOne = B[1] - A[1];
  double bOne = B[0] - A[0];
  double cOne = (-aOne*A[0] + bOne*A[1]) * -1;
  double aTwo = D[1] - C[1];
  double bTwo = D[0] - C[0];
  double cTwo = (-aTwo*C[0] + bTwo*C[1]) * -1;

  if(C[0] == A[0] && D[0] == B[0]){
    intersection[0] = A[0];
    intersection[1] = (A[1] + B[1] + C[1] + D[1]) / 4;
    return 1;

  }
  
  if(((aOne*bTwo) - (aTwo*bOne)) == 0){
    if(A[1] == C[1]){
      intersection[0] = (A[0] + B[0] + C[0] + D[0]) / 4;;
      intersection[1] = A[1];
      return 1;

    }
    return 0;
  }
  
  double xInt = ((bTwo*cOne) - (bOne*cTwo)) / ((aOne*bTwo) - (aTwo*bOne));
  double yInt = -1 * ((cTwo*aOne) - (cOne*aTwo)) / ((aOne*bTwo) - (aTwo*bOne));

  //if intercept off the screen, return 0
  if(xInt < 0 || xInt > scrnsize || yInt < 0 || yInt > scrnsize) return 0; 
  else{
    intersection[0] = xInt;
    intersection[1] = yInt;
    return 1;
  }
  
}

//checks if point is inside of polygon
int good(double lineOne[], double lineTwo[], double point[]){
  
  double center[2];
  double minX = clipX[0];
  double minY = clipY[0];
  double maxX = clipX[0];
  double maxY = clipY[0];
  for(int i = 1; i < clipnumpoints; i++){
    if(clipX[i] > maxX) maxX = clipX[i];
    if(clipY[i] > maxY) maxY = clipY[i];
    if(clipX[i] < minX) minX = clipX[i];
    if(clipY[i] < minY) minY = clipY[i];  
  }
  center[0] = (maxX + minX) / 2;
  center[1] = (maxY + minY) / 2;
  //find center
  
  if(center[1] > lineOne[1] && center[1] > lineTwo[1]){ //if line below
    if(point[1] > lineOne[1] || point[1] > lineTwo[1]) return 1;
  }
  else if(center[1] < lineOne[1] && center[1] < lineTwo[1]){ //if line above
    if(point[1] < lineOne[1] || point[1] < lineTwo[1]) return 1;
  }
  else if(center[0] > lineOne[0] && center[0] > lineTwo[0]){ //if line on left
    if(point[0] > lineOne[0] || point[0] > lineTwo[0]) return 1;
  }
  else if(center[0] < lineOne[0] && center[0] < lineTwo[0]){ //if line on right
    if(point[0] < lineOne[0] || point[0] < lineTwo[0]) return 1;
  }
  //handle triangles
  return 0;
}

int cut_poly(double xp[], double yp[], int numpoints, double newxp[], double newyp[]){

  double intOne[2], intTwo[2];
  double curOne[2], curTwo[2];
  int newsize = 0;
  for(int i = 0; i < clipnumpoints; i++){

    //find current intersection line
    if(i + 1 == clipnumpoints){
      intOne[0] = clipX[i];
      intOne[1] = clipY[i];
      intTwo[0] = clipX[0];
      intTwo[1] = clipY[0];
    }
    else{
      intOne[0] = clipX[i];
      intOne[1] = clipY[i];
      intTwo[0] = clipX[i+1];
      intTwo[1] = clipY[i+1];
    }

    
    for(int j = 0; j < numpoints; j++){

      //find current line
      if(j + 1 == numpoints){
	curOne[0] = xp[j];
	curOne[1] = yp[j];
	curTwo[0] = xp[0];
	curTwo[1] = yp[0];
      }
      else{
	curOne[0] = xp[j];
	curOne[1] = yp[j];
	curTwo[0] = xp[j+1];
	curTwo[1] = yp[j+1];
      }

      //g - g keep g
      //g - b keep intersect
      //b - g keep intersect, keep g
      //b - b keep nothing
      //some logic

      int goodOne = good(intOne, intTwo, curOne);
      int goodTwo = good(intOne, intTwo, curTwo);
      if(goodOne && goodTwo){//if both good
        newxp[newsize] = xp[j];
        newyp[newsize] = yp[j];
        newsize++;
      }
      else if(goodOne && !goodTwo){ //if 1 good
	double intersection[2];
	intersect_2_lines(intOne, intTwo, curTwo, curOne, intersection);
        newxp[newsize] = intersection[0];
        newyp[newsize] = intersection[1];
        newsize++;
      }
      else if(!goodOne && goodTwo){//if 2 good
        double intersection[2];
	intersect_2_lines(intOne, intTwo, curTwo, curOne, intersection);
        newxp[newsize] = intersection[0];
        newyp[newsize] = intersection[1];
        newsize++;
	newxp[newsize] = xp[j];
        newyp[newsize] = yp[j];
        newsize++;
      }
      else{//else both bad
      }
    }
   
  }
   return newsize;

}


void draw_object(int input)
{
  G_rgb(0,0,0);
  G_clear();

  double xp[numpoints[input]];
  double yp[numpoints[input]];
  double newxp[numpoints[input]*2];
  double newyp[numpoints[input]*2];
  int newsize;
  
  for(int i = 0; i < numpolys[input]; i++){

    for(int j = 0; j < psize[input][i]; j++){
      xp[j] = x[input][cont[input][i][j]];
      yp[j] = y[input][cont[input][i][j]];
    }

    if(clipnumpoints > 0)
      newsize = cut_poly(xp, yp, psize[input][i], newxp, newyp);
    
    G_rgb(red[input][i],grn[input][i],blu[input][i]);
    G_fill_polygon(newxp,newyp,newsize);
    G_fill_polygon(xp,yp,psize[input][i]);
  }  

  /*if(clipnumpoints > 0){
      G_rgb(0,0.3,0.4);
      G_fill_polygon(clipX,clipY, clipnumpoints);
      }*/
}

//No grid, no snaps.
int click_and_save(double xp[], double yp[]){

  double q[2];
  int i = 0;

  G_rgb(0,1,1);
  G_fill_rectangle(0,0,scrnsize,20);

  G_rgb(1,0,0);
  do{
     G_wait_click(q);
     if(q[1] <= 20) break;
     else{
       G_point(q[0],q[1]);
       if(i>0){
	 G_line(xp[i-1],yp[i-1],q[0],q[1]);
       }
       xp[i] = q[0];
       yp[i] = q[1];
       i = i + 1;
     }
  }while(True);

  G_line(xp[i-1],yp[i-1],xp[0],yp[0]);
  
  return i;
  
}

int clip_screen(){

  clipnumpoints = click_and_save(clipX, clipY);
  G_rgb(0.2,0.4,0);
  G_fill_polygon(clipX, clipY, clipnumpoints);

}

int main(int argc, char **argv){

  clipnumpoints = 0;
  if(argc < 2) {printf("Usage: 2d_poly polygon.xy\n"); exit(0);}
  load_files(argc - 1, argv);

  char input = 48;
  int previousObj = 0;
  G_init_graphics(scrnsize,scrnsize);
  G_rgb(0,0,0);
  G_clear();

  do{
    if(input >= 48 && input < 48 + argc - 1){
      draw_object(input - 48);
      previousObj = input-48;
    }
    if(input == ',')
    {
      rotate_object_matrix(previousObj, M_PI/32);
      draw_object(previousObj);
    }
    else if(input == '.')
    {
      rotate_object_matrix(previousObj, -M_PI/32);
      draw_object(previousObj);
    }
    
    input = G_wait_key();
    if(input == 'q' || input == 'Q'){break;}
    if(input == 's' || input == 'S'){clip_screen();}
    
  }while(1);

  return 0;
}
