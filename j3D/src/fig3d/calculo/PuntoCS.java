package fig3d.calculo;

import org.apache.log4j.Logger;

public class PuntoCS  {
    private static final Logger LOG = Logger.getLogger(PuntoCS.class);

    public double x, y, z; // coordenadas cartesianas
    public double r, a, b; // coordenadas esféricas con radio, azimut y latitud, en lugar de colatitud

    // inicialización típica para uso en el universo
    public PuntoCS( double _x, double _y, double _z ) {
        x=_x;
        y=_y;
        z=_z;
    }
    // inicialización para el uysar el paso de esféricas a cartesianas
    public PuntoCS( double _x, double _y, double _z, double _r, double _a, double _b ) {
        x=_x;
        y=_y;
        z=_z;
        r=_r;
        a=_a;
        b=_b;
    }

    public String toString() {
        return "C("+x+","+y+","+z+")" ;
//        return "C("+x+","+y+","+z+") S("+r+","+a+","+b+")" ;
    }

 // Paso de Cartesianas (x,y,z) a Esfericas (r,a,b) 
    public void ctop (){
        if ( x==0.0 ) {
            if ( y==0.0 ) {
                if ( z==0.0 ) { // (0,0,0)
                    a = 0.0 ;
                    b = 0.0 ;
                    r = 0.0 ;
                }
                else { // (0,0,z)
                    a = 0.0 ;
                    b = Math.signum(z) * (Math.PI / 2) ;
                    r = z ;
                }
            }
            else { // (0,y,z)
                a = Math.signum(y) * (Math.PI / 2) ;
                b = Math.atan2 ( z , y ) ;
                r = Math.sqrt ( y*y + z*z ) ;
            }
        }
        else { // (x,y,z)
            a = Math.atan2 ( y , x ) ;
            b = Math.atan2 ( z , Math.hypot ( x, y ) ) ;
            r = Math.sqrt ( x*x + y*y + z*z ) ;
        }
    }

// Paso de Esfericas (r,a,b) a Cartesianas (x,y,z)
    public void ptoc () {
        x = r * Math.cos(b) * Math.cos(a) ;
        y = r * Math.cos(b) * Math.sin(a) ;
        z = r * Math.sin(b) ;
    }

    @Override
    public boolean equals (Object o) {
        if (!(o instanceof PuntoCS))
            return false;
        if ( o== this )
            return true;
        PuntoCS p = (PuntoCS)o;
        LOG.trace("Punto3Dcp.equals ("+x+","+y+","+z+") = ("+p.x+","+p.y+","+p.z+")");
        return ( (x==p.x) && (y==p.y) && (z==p.z) );
    }

    @Override
    public int hashCode() {
        int X = (int)x;
        int Y = (int)y;
        int Z = (int)z;
        int hc=(int)(X<<20 | Y<<10 | Z);
        LOG.trace("Punto3Dcp.hashCode ("+x+","+y+","+z+")="+hc);
        return hc;
    }

}
