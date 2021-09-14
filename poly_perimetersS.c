#include "FPToolkit.c"

int swidth, sheight ;


void print_poly(double x[], double y[], int n)
{
  int i ;
  printf("\n") ;
  printf("%d\n",n) ;
  for (i = 0 ; i < n ; i++) {
    printf("%7.2lf %7.2lf\n",x[i],y[i]) ;
  }
  printf("\n") ;
}


double polygon_perimeter (double x[], double y[], int n)
{
  double a,b,c,p ;
  int i,j ;
  
  p = 0.0 ;
  for (i = 0 ; i < n ; i++) {
    j = i+1 ; if (j == n) { j = 0 ; }
    a = x[j] - x[i] ; b = y[j] - y[i] ;
    c = sqrt(a*a + b*b) ;
    p += c ;
  }
  
  return p ;
}

void sort(double *xp, int numpoints){
  double min;
  int min_index;
  
  for(int i = 0; i < numpoints; i++){
    min = xp[i];
    min_index = i ;
    for(int j = i+1; j < numpoints; j++){
      if(xp[j] < min) {
        min = xp[j];
	min_index = j;
      }
    }
    xp[min_index] = xp[i];
    xp[i] = min;
  }

}

//enter four points and a horizontal line and returns x value of intersection point (-1 if none)
double find_intersection(double xOne, double xTwo, double yOne, double yTwo, double yInt)
{
  //y - y1 = m(x-x1)
  //((y - y1) / m) + x1 = x
  if((yInt > yOne && yInt > yTwo) || (yInt < yOne && yInt < yTwo)) return -1.0; //if no inside intercept
  if(xTwo - xOne == 0) return xOne; //if vertical line
  
  double m = (yTwo - yOne) / (xTwo - xOne); //find slope
  double x = ((yInt - yOne) / m) + xOne; //find x intercept with horizontal line

  if(x < 0 || x > swidth) return -1.0; //if x intercept off the screen, return -1
  else return x;
}

void my_fill_polygon(double xp[], double yp[], int numpoints){
  
  double xpositions[1000], intersection;
  int xpoints, hcounter;
  
  for(int y = 0; y < sheight; y++)
  {
    xpoints = 0;
    for(int i = 0; i < numpoints; i++)
    {
      
      if(i+1 < numpoints){
	intersection = find_intersection(xp[i],xp[i+1],yp[i],yp[i+1],y);
	if(intersection > 0) {
	  xpositions[xpoints] = intersection;
	  xpoints++;
	  /*if(yp[i] != y && xp[i] != intersection)
	  {
	    xpositions[xpoints] = intersection;
	    xpoints++;
	  }
	  else
	  {
	    xpositions[xpoints] = intersection;
	    xpoints++;
	    i++;
	    }*/
	}
      }
      else{
	intersection = find_intersection(xp[i],xp[0],yp[i],yp[0],y);
	if(intersection > 0) {
	  xpositions[xpoints] = intersection;
	  xpoints++;
	  /*if(yp[i] != y && xp[i] != intersection)
	  {
	    xpositions[xpoints] = intersection;
	    xpoints++;
	  }
	  else
	  {
	    xpositions[xpoints] = intersection;
	    xpoints++;
	    i++;
	    }*/
	}
      }

      
    } // end for i
    if(xpoints == 0) continue;
    sort(xpositions, xpoints);
    
    for(int i = 0; i < xpoints; i+=2)
    {	
      if(i+1 < xpoints){
	G_line(xpositions[i],y,xpositions[i+1],y);
      }
      
      //if(i%10 == 0) G_wait_key();
    }
    
  }
}

//Creates grid, and snaps to grid.
int click_and_save(double x[], double y[]){

  double q[2];
  int i = 0;

  G_rgb(0,1,1);
  G_fill_rectangle(0,0,swidth,20);


  G_rgb(1,0,0);
  do{
     G_wait_click(q);
     q[0] = floor(q[0]/10 + 0.5)*10;
     q[1] = floor(q[1]/10 + 0.5)*10;
     if(q[1] <= 20) break;
     else{
       G_point(q[0],q[1]);
       if(i>0){
	 G_line(x[i-1],y[i-1],q[0],q[1]);
       }
       x[i] = q[0];
       y[i] = q[1];
       i = i + 1;
     }
  }while(True);

  G_line(x[i-1],y[i-1],x[0],y[0]);
  
  return i;

}

//No grid, no snaps.
int click_and_save_old(double x[], double y[]){

  double q[2];
  int i = 0;

  G_rgb(0,1,1);
  G_fill_rectangle(0,0,swidth,20);

  G_rgb(1,0,0);
  do{
     G_wait_click(q);
     if(q[1] <= 20) break;
     else{
       G_point(q[0],q[1]);
       if(i>0){
	 G_line(x[i-1],y[i-1],q[0],q[1]);
       }
       x[i] = q[0];
       y[i] = q[1];
       i = i + 1;
     }
  }while(True);

  G_line(x[i-1],y[i-1],x[0],y[0]);
  
  return i;
  
}


int test01()
{
  double xp[500] = { 100, 200, 400, 400} ;
  double yp[500] = { 200, 500, 500, 300} ;
  int m = 4 ; ;
  double p1 ;

  double xq[500] = { 300, 400, 500, 600, 600} ;
  double yq[500] = { 400, 100, 100, 200, 500} ;
  int n = 5 ;
  double p2 ;
  
  swidth = 700 ; sheight = 700 ;
  G_init_graphics(swidth, sheight) ;
  G_rgb(0,0,0) ;
  G_clear() ;

  G_rgb(0,0,1) ;
  G_polygon(xp,yp,m) ;
  G_polygon(xq,yq,n) ;

  G_wait_key() ;
  
  p1 = polygon_perimeter (xp,yp,m) ;
  p2 = polygon_perimeter (xq,yq,n) ;
  printf("p1 = %lf  p2 = %lf\n",p1,p2) ;
  
  G_rgb(0,1,0) ;
  if (p1 > p2) {
    G_fill_polygon(xp,yp,m) ;
    //G_fill_polygon(xp,yp,m);
    my_fill_polygon(xp,yp,m);
  } else if (p2 > p1) {
    //G_fill_polygon(xq,yq,n) ;
    my_fill_polygon(xq,yq,n);
  } else {
    //G_fill_polygon(xp,yp,m) ;
    //G_fill_polygon(xq,yq,n) ;
    my_fill_polygon(xp,yp,m);
    my_fill_polygon(xq,yq,n);
  }
  
  
  G_wait_key() ;
  
  print_poly(xp,yp,m) ;
  print_poly(xq,yq,n) ;  
	     
}



int test02()
{
  double xp[1000],yp[1000],p1 ;
  int m ;
  double xq[500], yq[500],p2 ;
  int n ;
  double P[2] ;

  swidth = 700 ; sheight = 700 ;
  G_init_graphics(swidth, sheight) ;
  G_rgb(0,0,0) ;
  G_clear() ;


  //print grid
  G_rgb(0.4,0.4,0.4);

  for(int i = 0; i < sheight; i = i + 10){
    G_line(0,i,swidth,i);
  }

  for(int i = 0; i < swidth; i = i+10){
    G_line(i,0,i,sheight);
  }
  //end print grid
  
  G_rgb(1,0,0) ;
  m = click_and_save(xp,yp) ;

  G_rgb(1,0,0) ;
  n = click_and_save(xq,yq) ;

  p1 = polygon_perimeter (xp,yp,m) ;
  p2 = polygon_perimeter (xq,yq,n) ;
  printf("p1 = %lf  p2 = %lf\n",p1,p2) ;

  G_rgb(0,1,0) ;
  if (p1 > p2) {
    G_fill_polygon(xp,yp,m) ;
    //G_fill_polygon(xp,yp,m);
    my_fill_polygon(xp,yp,m);
  } else if (p2 > p1) {
    //G_fill_polygon(xq,yq,n) ;
    my_fill_polygon(xq,yq,n);
  } else {
    //G_fill_polygon(xp,yp,m) ;
    //G_fill_polygon(xq,yq,n) ;
    my_fill_polygon(xp,yp,m);
    my_fill_polygon(xq,yq,n);
  }
  
  
  G_wait_key() ;
  
  print_poly(xp,yp,m) ;
  print_poly(xq,yq,n) ;  
	     
}






//test sort function
void test03(){

  double xp[6] = {10.3,20.5,4.3,80.4,100.17,2.01};

  printf("Original Array: [%.2f,%.2f,%.2f,%.2f,%.2f,%.2f]\n"
	   ,xp[0],xp[1],xp[2],xp[3],xp[4],xp[5]);

  sort(xp, 6);

  printf("Sorted Array: [%.2f,%.2f,%.2f,%.2f,%.2f,%.2f]\n"
	   ,xp[0],xp[1],xp[2],xp[3],xp[4],xp[5]);

}

void test04(char *argv){

  double xp[500], yp[500];
  int numpoints;

  swidth = 900 ; sheight = 900 ;
  G_init_graphics(swidth, sheight) ;
  G_rgb(0,0,0) ;
  G_clear() ;


    FILE *fp;
    fp = fopen(argv,"r");
    if(fp == NULL){printf("Can't read the file\n"); return;}

    fscanf(fp, "%d", &numpoints);

    for(int j = 0; j < numpoints; j++){
      fscanf(fp, "%lf %lf", &xp[j], &yp[j]);
    }

    G_rgb(0,1,0);

    G_fill_polygon(xp,yp,numpoints);
    
    G_rgb(1,1,0.4);
    
    print_poly(xp,yp,numpoints);
    //G_fill_polygon(xp,yp,numpoints);
    my_fill_polygon(xp,yp,numpoints);



    int fclose(FILE *fp);

  G_wait_key();
}


int main(int argc, char **argv)
{
  if(argc == 1){test02(); exit(0);}
  if(argc == 2){test04(argv[1]); exit(0);}

  
  double xp[500], yp[500];
  int numpoints;

  swidth = 900 ; sheight = 900 ;
  G_init_graphics(swidth, sheight) ;
  G_rgb(0,0,0) ;
  G_clear() ;

  for(int i = 1; i < argc; i++){

    FILE *fp;
    fp = fopen(argv[i],"r");
    if(fp == NULL){printf("Can't read the file\n"); continue;}

    fscanf(fp, "%d", &numpoints);

    for(int j = 0; j < numpoints; j++){
      fscanf(fp, "%lf %lf", &xp[j], &yp[j]);
    }

    if(i%5 == 0) G_rgb(0.69,0.4,1);
    else if(i%5 == 1) G_rgb(1,0,0);
    else if(i%5 == 2) G_rgb(1,0.6,0.2);
    else if(i%5 == 3) G_rgb(1,1,0.4);
    else if(i%5 == 4) G_rgb(0.4,1,0.4);
    
    print_poly(xp,yp,numpoints);
    //G_fill_polygon(xp,yp,numpoints);
    my_fill_polygon(xp,yp,numpoints);

    int fclose(FILE *fp);
  }

  G_wait_key();
  
}
