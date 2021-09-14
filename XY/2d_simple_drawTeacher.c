
// open a sequence of .xy  files specified on the commmand line
// and draw them.

#include "FPToolkit.c"

#define MAXOBJS 10
#define MAXPTS 59000
#define MAXPOLYS 57500

int numobjects ;
int numpoints[MAXOBJS] ;
int numpolys[MAXOBJS] ;
double x[MAXOBJS][MAXPTS] ;
double y[MAXOBJS][MAXPTS] ;
int psize[MAXOBJS][MAXPOLYS] ;
int con[MAXOBJS][MAXPOLYS][20] ;
double red[MAXOBJS][MAXPOLYS],grn[MAXOBJS][MAXPOLYS],blu[MAXOBJS][MAXPOLYS] ;



int read_object(FILE *f, int onum)
{
  int i,j,k ;

    // point info
    fscanf(f,"%d",&numpoints[onum]) ;

    if (numpoints[onum] >= MAXPTS) {
      // need an extra for object centering
      printf("MAXPTS = %d :  exceeded.\n",MAXPTS) ;
      exit(1) ;
    }

    for (i = 0 ; i < numpoints[onum] ; i++) {
      fscanf(f,"%lf %lf",&x[onum][i],&y[onum][i]) ;
    }

    // connectivity info
    fscanf(f,"%d",&numpolys[onum]) ;
    if (numpolys[onum] > MAXPOLYS) {
      printf("MAXPOLYS = %d :  exceeded.\n",MAXPOLYS) ;
      exit(1) ;
    }
    
    k = 0 ;
    for (i = 0 ; i < numpolys[onum] ; i++) {
      fscanf(f,"%d",&psize[onum][i]) ;
      for (j = 0 ; j < psize[onum][i] ; j++) {
        fscanf(f,"%d",&con[onum][i][j]) ;
      } // end for j
    } // end for i

    
    // color info :
    for (i = 0 ; i < numpolys[onum] ; i++) {
      fscanf(f,"%lf %lf %lf",&red[onum][i],&grn[onum][i],&blu[onum][i]) ;
    }    
}






int main(int argc, char **argv)
{
  FILE *fin ;
  int onum,key,w ;

  if (argc < 2) {
    printf("pgm  in0.xy  in1.xy ... \n") ;
    exit(1) ;
  }


  numobjects = argc - 1 ;
  if (numobjects > MAXOBJS) {
    printf("numobjects = %d is greater than MAXOBJS = %d\n", numobjects, MAXOBJS) ;
    printf("numobjects truncated to %d\n", MAXOBJS) ;
    numobjects = MAXOBJS ;
  }

  
  for (onum = 0 ; onum < numobjects ; onum++) {

    fin = fopen(argv[onum+1],"r") ;
    if (fin == NULL) {
      printf("can't read file, %s\n",argv[onum+1]) ;
      exit(1) ;
    }

    read_object(fin, onum) ;
  }

  
  // now you need to be able to draw any of objects ....

}


