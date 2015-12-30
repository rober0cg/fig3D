package fig3d.objetos2D;

import org.apache.log4j.Logger;

import fig3d.calculo.PuntoCS;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.awt.Color;

public class PolRegular2D implements Base2D {

    private static final Logger LOG = Logger.getLogger(PolRegular2D.class);

    public Punto c0, v0, n0, p[]; // centro, vertice(p[0]), normal y puntos.
    public int nv; // numero de lados/vertices
    public Color C;

    static double Math_PI_2 = Math.PI/2 ;
    
    static Pattern pR=Pattern.compile("([cvn][(][+-]*\\d+,[+-]*\\d+,[+-]*\\d+[)])|[l][(]\\d+[)]|(C[(]\\d+,\\d+,\\d+,\\d+[)])");

    public PolRegular2D( Punto _c, Punto _v, Punto _n, int _l, Color _C) {
        c0=_c;
        v0=_v;
        n0=_n;
        nv=_l;
        C=_C;
        calculaPuntos();
    }

// P2D(c(cx,cy,cz),v(vx,vy,vz),n(nx,ny,nz),r(n),C(Cr,Cg,Cb,Ca))
    public PolRegular2D(String t){
        LOG.trace("PolRegular2D");

        String aux1 = t.replace(" ","");
        LOG.trace("PolRegular2D ["+aux1+"]");

        Matcher mT = pR.matcher(aux1);
        while ( mT.find() ){
            String aux2 = mT.group();
            LOG.trace("PolRegular2D group ["+aux2+"]");
            char pc = aux2.charAt(0);
            switch(pc) {
            case 'c': c0 = new Punto(aux2) ; break ;
            case 'v': v0 = new Punto(aux2) ; break ;
            case 'n': n0 = new Punto(aux2) ; break ;
            case 'l': nv = Util.stringToInt(aux2);
            case 'C': C = Util.stringToColor(aux2);
            }
        }
        calculaPuntos();
    }

// Calculo a partir del cual obtenemos los puntos de los vértices
    private void calculaPuntos(){

// Cambiamos coordenadas cartesianas al centro del polígono para normal y vertice
        double xn, yn, zn, xv, yv, zv;
        xn = n0.x - c0.x;
        yn = n0.y - c0.y;
        zn = n0.z - c0.z;
        xv = v0.x - c0.x;
        yv = v0.y - c0.y;
        zv = v0.z - c0.z;

// Calculamos en esfericas la normal y el vertice
        PuntoCS pn = new PuntoCS(xn,yn,zn); 
        PuntoCS pv = new PuntoCS(xv,yv,zv); 
        pn.ctop();
        pv.ctop();

// Array de puntos para los vertices, y el primero ya lo tenemos
        p = new Punto[nv];
        p[0] = v0;

// Giramos sobre la normal desde las coordenadas del vertice en incrementos de 360/lados
        if ( pn.b==0.0 ) { // si normal tiene b=0 simplifica calculos.
            double sin_a = Math.sin(pn.a + Math_PI_2);
            double cos_a = Math.cos(pn.a + Math_PI_2);

            double d = pv.b;
            for ( int i=1  ; i<nv ; i++){
                d += (Math.PI/nv) ;

                double sin_b = Math.sin(d);
                double cos_b = Math.cos(d);

                xv = pv.r * cos_b * cos_a ;
                yv = pv.r * cos_b * sin_a ;
                zv = pv.r * sin_b ;

                // Volvemos a la referencia original
                xv += c0.x;
                yv += c0.y;
                zv += c0.z;
                p[i] = new Punto ( (int)xv, (int)yv, (int)zv);
            }
            
        }
        else {
            double tan_b = Math.tanh(pn.b + Math_PI_2);

            double d = pv.b;
            for ( int i=1  ; i<nv ; i++){
                d += (Math.PI/nv) ;

                double bv = Math.atan(Math.cos(d) * tan_b);
                
                double sin_b = Math.sin(bv);
                double cos_b = Math.cos(bv);

                double sin_a = Math.sin(pn.a + d);
                double cos_a = Math.cos(pn.a + d);

                xv = pv.r * cos_b * cos_a ;
                yv = pv.r * cos_b * sin_a ;
                zv = pv.r * sin_b ;

                // Volvemos a la referencia original
                xv += c0.x;
                yv += c0.y;
                zv += c0.z;
                p[i] = new Punto ( (int)xv, (int)yv, (int)zv);
            }
        }
        
        
    }


    @Override
    public String toString() {
        return "P3D("+c0+","+v0+","+n0+","+nv+","+C+")" ;
    }

    @Override
    public int[] getIdxPuntos() {
        int i[] = new int[nv];
        for ( int j=0; j<nv ; j++ ) {
            i[j]=p[j].i;
        }
        return i ;
    }

    @Override
    public Color getColor() {
        return C;
    }
    
}
