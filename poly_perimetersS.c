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
  } else if (p2 > p1) {
    G_fill_polygon(xq,yq,n) ;
  } else {
    G_fill_polygon(xp,yp,m) ;
    G_fill_polygon(xq,yq,n) ;    
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
  } else if (p2 > p1) {
    G_fill_polygon(xq,yq,n) ;
  } else {
    G_fill_polygon(xp,yp,m) ;
    G_fill_polygon(xq,yq,n) ;    
  }
  
  
  G_wait_key() ;
  
  print_poly(xp,yp,m) ;
  print_poly(xq,yq,n) ;  
	     
}

int main(int argc, char **argv)
{
  if(argc == 1){test02(); exit(0);}

  
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
    G_fill_polygon(xp,yp,numpoints);

    int fclose(FILE *fp);
  }

  G_wait_key();
  
}
