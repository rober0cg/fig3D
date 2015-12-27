package calculo;

import org.apache.log4j.Logger;

public class Punto3Dcp  {
    private static final Logger LOG = Logger.getLogger(Punto3Dcp.class);
    double x, y, z;
    double r, a, b;
    public Punto3Dcp( double _x, double _y, double _z ) {
        x=_x;
        y=_y;
        z=_z;
    }

    public String toString() {
        return "C("+x+","+y+","+z+")" ;
//        return "C("+x+","+y+","+z+") S("+r+","+a+","+b+")" ;
    }
    
    @Override
    public boolean equals (Object o) {
        if (!(o instanceof Punto3Dcp))
            return false;
        if ( o== this )
            return true;
        Punto3Dcp p = (Punto3Dcp)o;
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
