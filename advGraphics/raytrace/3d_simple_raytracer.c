#include "../FPToolkit.c"
#include "../M3d_matrix_tools.c"

double eye_in_object_space[3];
double coi[3];
double up[3];
double eyemat[4][4];
double eyeinv[4][4];
double hither = 1;
double yon = 1e50;
double obmat[100][4][4] ;
double obinv[100][4][4] ;
int obtype[100];
double color[100][3] ;
int    num_objects ;
int numBounces = 6 ;
int scrnsize = 800;


double hyperbola_deriv(double xyz[3], int n){
  if(n == 1)
    return -1*xyz[n]*2;
  return xyz[n]*2;
}

double sphere_deriv(double xyz[3], int n){
  return xyz[n]*2;
}

double plane_deriv(double xyz[3], int n){
  if (n == 2)
    return 1;
  return 0;
}

/////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////

double sphere_intercept(double rayA[3], double rayB[3], double t[2]){

  double dx = rayB[0] - rayA[0];
  double dy = rayB[1] - rayA[1];
  double dz = rayB[2] - rayA[2];

  double A = dx*dx + dy*dy + dz*dz;
  double B = 2*rayA[0]*dx + 2*rayA[1]*dy + 2*rayA[2]*dz;
  double C = rayA[0]*rayA[0] + rayA[1]*rayA[1] + rayA[2]*rayA[2] - 1;
  
  if(B*B - 4*A*C < 0) return 0;
  if(B*B - 4*A*C == 0) {
    t[0] = (-B + sqrt(B*B - 4*A*C)) / (2*A);
    return 1;
  }
  t[0] = (-B + sqrt(B*B - 4*A*C)) / (2*A);
  t[1] = (-B - sqrt(B*B - 4*A*C)) / (2*A);
  return 2; 

}

double hyperbola_intercept(double rayA[3], double rayB[3], double t[2]){

  double dx = rayB[0] - rayA[0];
  double dy = rayB[1] - rayA[1];
  double dz = rayB[2] - rayA[2];

  double A = dx*dx - dy*dy + dz*dz;
  double B = 2*rayA[0]*dx - 2*rayA[1]*dy + 2*rayA[2]*dz;
  double C = rayA[0]*rayA[0] - rayA[1]*rayA[1] + rayA[2]*rayA[2] - 1;

  if(B*B - 4*A*C < 0) return 0;

  if(B*B - 4*A*C == 0) {
    t[0] = (-B + sqrt(B*B - 4*A*C)) / (2*A);
    if(rayA[1] + t[0]*dy > 1 || rayA[1] + t[0]*dy < -1) return 0;
    return 1;
  }
  t[0] = (-B + sqrt(B*B - 4*A*C)) / (2*A);
  if(rayA[1] + t[0]*dy > 1 || rayA[1] + t[0]*dy < -1){
    t[0] = (-B - sqrt(B*B - 4*A*C)) / (2*A);
    if(rayA[1] + t[0]*dy > 1 || rayA[1] + t[0]*dy < -1)
      return 0;
    return 1;
  }
  t[1] = (-B - sqrt(B*B - 4*A*C)) / (2*A);
  if(rayA[1] + t[1]*dy > 1 || rayA[1] + t[1]*dy < -1)
      return 1;
  return 2; 

}

double plane_intercept(double rayA[3], double rayB[3], double t[2]){

  double dx = rayB[0] - rayA[0];
  double dy = rayB[1] - rayA[1];
  double dz = rayB[2] - rayA[2];

  
  if(dz == 0) {
    if(rayA[0] == 0 && dx != 0) {
      t[0] = (1 - rayA[0]) / dx;
      return 1;
    }
    return 0;
  }

  t[0] = -1*rayA[2] / dz;
  if(rayA[0] + t[0]*dx < -1 || rayA[0] + t[0]*dx > 1 ||
     rayA[1] + t[0]*dy < -1 || rayA[1] + t[0]*dy > 1)
    return 0;
  return 1;
}

int normalize(double in[3], double res[3]){

  double len ;
  len = sqrt(in[0]*in[0] + in[1]*in[1] + in[2]*in[2]) ;
  if (len == 0) return 0 ;
  res[0] = in[0]/len ;  res[1] = in[1]/len ;  res[2] = in[2]/len ;
  return 1;
}

int find_normal(int onum, double intersection[3], double Rsource[3], double res[3],
		double(*F)(double pt[3], int n))

// onum = object number
// intersection = intersection point
// Rsource = ray source (probably the eye)
// res = normal vector (filled by function)
// F = partial derivative function
{

  double temp[3];
  M3d_mat_mult_pt(temp, obinv[onum], intersection);
  res[0] = obinv[onum][0][0]*F(temp, 0) + obinv[onum][1][0]*F(temp, 1) + obinv[onum][2][0]*F(temp, 2);
  res[1] = obinv[onum][0][1]*F(temp, 0) + obinv[onum][1][1]*F(temp, 1) + obinv[onum][2][1]*F(temp, 2);
  res[2] = obinv[onum][0][2]*F(temp, 0) + obinv[onum][1][2]*F(temp, 1) + obinv[onum][2][2]*F(temp, 2);

  normalize(res,res);
  
  double E[3] ;
  E[0] = Rsource[0] - intersection[0] ; 
  E[1] = Rsource[1] - intersection[1] ; 
  E[2] = Rsource[2] - intersection[2] ; 
  normalize(E,E);
  double NdotE = res[0]*E[0] + res[1]*E[1] + res[2]*E[2] ;

  if(NdotE < 0){
    res[0] *= (-1.0) ;    res[1] *= (-1.0) ;    res[2] *= (-1.0) ; 
  }

  return 1;  
}

double vec_dot(double A[3], double B[3]){
  return A[0]*B[0] + A[1]*B[1] + A[2] * B[2];
}

int find_reflection(double Rtip[3], double intersection[3], double normal[3], double res[3]){

  double T[4][4], tmp[4][4];
  double reflect[4][4] = {
			  {1 - 2*normal[0]*normal[0],   -2*normal[0]*normal[1],    -2*normal[0]*normal[2], 0},
			  {-2*normal[0]*normal[1],   1 - 2*normal[1]*normal[1],    -2*normal[1]*normal[2], 0},
			  {-2*normal[0]*normal[2],      -2*normal[1]*normal[2], 1 - 2*normal[2]*normal[2], 0},
			  {0, 0, 0, 0}
  };

  M3d_make_translation(T, -intersection[0], -intersection[1], -intersection[2]);
  M3d_make_scaling(tmp, -1, -1, -1);
  M3d_mat_mult(T, tmp, T);
  M3d_mat_mult(T, reflect, T);
  M3d_make_translation(tmp, intersection[0], intersection[1], intersection[2]);
  M3d_mat_mult(T, tmp, T);
    
  M3d_mat_mult_pt(res, T, Rtip);
  normalize(res,res);
  
  return 1;
  
}

int find_intersection(double Rsource[3], double Rtip[3], double intersection[3], double normal[3]){

  double t[2];
  double rayA[3];
  double rayB[3];
  int n;
  double minT = 1e50;
  int saved_onum;
  
  for(int i = 0; i < num_objects; i++){
    M3d_mat_mult_pt(rayA, obinv[i], Rsource);
    M3d_mat_mult_pt(rayB, obinv[i], Rtip);
   
    if(obtype[i] == 1)
      n = plane_intercept(rayA,rayB,t);
    else if(obtype[i] == 0)
      n = sphere_intercept(rayA, rayB, t);
    else if(obtype[i] == 2)
      n = hyperbola_intercept(rayA,rayB,t);
    
    if (n == 0) {continue; }
    for(int j = 0; j < n; j++){
      if(t[j] > 0 && t[j] < minT) {
	minT = t[j];
	saved_onum = i;
      }
    }
  }

  if(minT == 1e50){
    return -1;
  }

  
  intersection[0] = Rsource[0] + minT*(Rtip[0] - Rsource[0]);
  intersection[1] = Rsource[1] + minT*(Rtip[1] - Rsource[1]);
  intersection[2] = Rsource[2] + minT*(Rtip[2] - Rsource[2]);

  if(obtype[saved_onum] == 1)
    find_normal(saved_onum, intersection, Rsource,    normal,plane_deriv);
  else if(obtype[saved_onum] == 2)
    find_normal(saved_onum, intersection, Rsource,    normal,hyperbola_deriv);
  else if (obtype[saved_onum] == 0)
    find_normal(saved_onum, intersection, Rsource,    normal,sphere_deriv);

  return saved_onum;
}

int ray (double Rtip[3], double argb[3]){
  double Rsource[3];
  Rsource[0] = 0;
  Rsource[1] = 0;
  Rsource[2] = 0;
  double intersection[3], normal[3];
  int saved_onum = find_intersection(Rsource,Rtip,intersection, normal);
  if (saved_onum == -1) {
    
    argb[0] = 0;
    argb[1] = 0;
    argb[2] = 0;
    return -1;
  }
  argb[0] = color[saved_onum][0];
  argb[1] = color[saved_onum][1];
  argb[2] = color[saved_onum][2];
  


  /*
  double res[3];
  double temp[3];
  for(int i = 0; i < numBounces; i++){
    
    find_reflection(Rtip, intersection, normal, res);


    temp[0] = intersection[0] + 2*res[0];
    temp[1] = intersection[1] + 2*res[1];
    temp[2] = intersection[2] + 2*res[2];
    intersection[0] += 0.1*res[0];
    intersection[1] += 0.1*res[1];
    intersection[2] += 0.1*res[2];
    
    G_rgb(1,0,0);
    G_fill_circle(temp[0],temp[1],2);
    G_rgb(0,1,0);
    G_fill_circle(intersection[0],intersection[1],1);


    saved_onum = find_intersection(intersection,temp,res, normal);
    if (saved_onum == -1) return -1;
    
    
    argb[0] = color[saved_onum][0];
    argb[1] = color[saved_onum][1];
    argb[2] = color[saved_onum][2];
    G_rgb(argb[0],argb[1],argb[2]);
    G_line(intersection[0], intersection[1], res[0], res[1]);

    Rtip[0] = intersection[0];
    Rtip[1] = intersection[1];
    Rtip[2] = intersection[2];
    intersection[0] = res[0];
    intersection[1] = res[1];
    intersection[2] = res[2];
  }
  */
  return 1;
  
}

void Draw_the_scene()
{
  double temp[3], argb[3];
  temp[0] = -1;
  temp[1] = -1;
  temp[2] = hither;
  normalize(temp,temp);
  for(int x = 0; x < scrnsize; x++){
    for(int y = 0; y < scrnsize; y++){
      temp[0] = x / (scrnsize/2.0) - 1;
      temp[1] = y / (scrnsize/2.0) - 1;
      ray (temp, argb) ;
      G_rgb(argb[0],argb[1],argb[2]);
      G_point(x,y);
      //G_wait_key();
    }
  }
}


/////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////





int test01()
{
  double vm[4][4], vi[4][4];
  double Tvlist[100];
  int Tn, Ttypelist[100];
  double m[4][4], mi[4][4];
  double Rsource[3];
  double Rtip[3];
  double argb[3] ;

    //////////////////////////////////////////////////////////////////////
    M3d_make_identity(vm) ;    M3d_make_identity(vi) ; // OVERRIDE for 2d
    //////////////////////////////////////////////////////////////////////

    num_objects = 0 ;

    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    obtype[num_objects] = 1;
    color[num_objects][0] = 0.0 ;
    color[num_objects][1] = 0.8 ; 
    color[num_objects][2] = 0.0 ;
	
    Tn = 0 ;
    Ttypelist[Tn] = SX ; Tvlist[Tn] =  5    ; Tn++ ;
    Ttypelist[Tn] = SY ; Tvlist[Tn] =  5    ; Tn++ ;
    Ttypelist[Tn] = SZ ; Tvlist[Tn] =  5    ; Tn++ ;
    Ttypelist[Tn] = RX ; Tvlist[Tn] =  20   ; Tn++ ;
    Ttypelist[Tn] = TZ ; Tvlist[Tn] =  20   ; Tn++ ;
	
    M3d_make_movement_sequence_matrix(m, mi, Tn, Ttypelist, Tvlist);
    M3d_mat_mult(obmat[num_objects], vm, m) ;
    M3d_mat_mult(obinv[num_objects], mi, vi) ;

    num_objects++ ; // don't forget to do this
    //////////////////////////////////////////////////////////////

    G_rgb(0,0,0) ;
    G_clear() ;
    Draw_the_scene() ;

    int c;
    while(1){
      c = G_wait_key();
      if(c == 'q') break;
      Draw_the_scene() ;
      
    }
}




//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////




int main()
{
  G_init_graphics(scrnsize,scrnsize);
  test01() ;
}
