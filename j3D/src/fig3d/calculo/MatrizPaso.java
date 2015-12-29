package fig3d.calculo;

public class MatrizPaso {

    // punto de vista
    double xv, yv, zv ; // coordenadas cartesianas
    double av, bv; // coordenadas esfericas de la direccion de la vista
    double cv ; // giro sobre el propio eje vector de la direccion

    // matriz de paso
    double v11, v12, v13 ;
    double v21, v22, v23 ;
    double v31, v32, v33 ;

    public MatrizPaso( double x, double y, double z, double a, double b, double c ) {
        xv=x;
        yv=y;
        zv=z;
        av=a;
        bv=b;
        cv=c;
        calculaMatriz();
    }

    public void MueveVista( double x, double y, double z, double a, double b, double c ) {
        xv=x;
        yv=y;
        zv=z;
        av=a;
        bv=b;
        cv=c;
        return;
    }

// Calcula matriz de paso
// con coordinadas esfericas con b cambiada    
    public void calculaMatriz () {
        double i11, i12, i13, i21, i22, i23, i31, i32, i33 ;
        double j11, j12, j13, j21, j22, j23, j31, j32, j33 ;
        double p11, p12, p13, p21, p22, p23, p31, p32, p33 ;

    // optimizacion: reutilizacion de calculos
        double cos_a0 = Math.cos ( av ) ;
        double cos_b0 = Math.cos ( bv ) ;
        double cos_c0 = Math.cos ( cv ) ;
        double sin_a0 = Math.sin ( av ) ;
        double sin_b0 = Math.sin ( bv ) ;
        double sin_c0 = Math.sin ( cv ) ;

    // J Aplicar giro sobre el eje x
    //      |    1     0     0     |
    // Jx = |    0   cos(c) sin(c) |
    //      |    0  -sin(c) cos(c) |
        j11 =  1.0 ; j21 =  0.0    ; j31 =  0.0 ;
        j12 =  0.0 ; j22 =  cos_c0 ; j32 =  sin_c0 ;
        j13 =  0.0 ; j23 = -sin_c0 ; j33 =  cos_c0 ;

    // P paso de esfericas a cartesinas
    //     i |    cos(a)*cos(b)    sin(a)*cos(b)     sin(b) |
    // P = j |   -sin(a)           cos(a)            0      |
    //     k |   -cos(a)*sin(b)   -sin(a)*sin(b)     cos(b) |

    //  i
        p11 =  cos_a0 *  cos_b0 ;  p21 =  sin_a0 *  cos_b0 ;   p31 =  sin_b0 ;

    //  j
        p12 = -sin_a0 ;            p22 =  cos_a0 ;             p32 =     0.0 ;

    //  k
        p13 =  cos_a0 * -sin_b0 ;  p23 =  sin_a0 * -sin_b0 ;   p33 =  cos_b0 ;

    // Inversa para el paso de Cartesianas a Esfericas
    // I = 1 / P
        i11 = p22*p33 - p23*p32 ;
        i12 = p23*p31 - p21*p33 ;
        i13 = p21*p32 - p22*p31 ;

        i21 = p32*p13 - p33*p12 ;
        i22 = p33*p11 - p31*p13 ;
        i23 = p31*p12 - p32*p11 ;

        i31 = p12*p23 - p13*p22 ;
        i32 = p13*p21 - p11*p23 ;
        i33 = p11*p22 - p12*p21 ;

    // Matriz de Paso es el producto de Cartesianas a Polares y de Giro sobre eje.
    // M = I * J
        v11 = i11*j11 + i12*j21 + i13*j31 ;
        v12 = i11*j12 + i12*j22 + i13*j32 ;
        v13 = i11*j13 + i12*j23 + i13*j33 ;

        v21 = i21*j11 + i22*j21 + i23*j31 ;
        v22 = i21*j12 + i22*j22 + i23*j32 ;
        v23 = i21*j13 + i22*j23 + i23*j33 ;

        v31 = i31*j11 + i32*j21 + i33*j31 ;
        v32 = i31*j12 + i32*j22 + i33*j32 ;
        v33 = i31*j13 + i32*j23 + i33*j33 ; 

        return;
    }

    public void aplicaMatriz ( PuntoCS p ) {
        double xi , yi , zi ;
        double xo , yo , zo ;

    // Traslación de Cartesianas al Punto de Vista
        xi = p.x - xv ;
        yi = p.y - yv ;
        zi = p.z - zv ;

    // Cambio de Base
        xo = xi*v11 + yi*v21 + zi*v31 ;
        yo = xi*v12 + yi*v22 + zi*v32 ;
        zo = xi*v13 + yi*v23 + zi*v33 ;

    // Paso de Cartesianas (x,y,z) a Esfericas (r,a,b)
        PuntoCS po = new PuntoCS(xo,yo,zo);
        po.ctop();

        p.a = Math.sin ( po.a ) * Math.cos ( po.b ) ;
        p.b = Math.sin ( po.b ) ;
        p.r = po.r ;

        return ;
    }
    
}
