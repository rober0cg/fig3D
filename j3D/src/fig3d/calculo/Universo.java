package fig3d.calculo;

import org.apache.log4j.Logger;

import fig3d.objetos2D.Base2D;
import fig3d.objetos2D.Linea2D;
import fig3d.objetos2D.Triangulo2D;
import fig3d.objetos2D.Rectangulo2D;
import fig3d.objetos2D.PolRegular2D;
import fig3d.objetos2D.Punto;

import java.awt.Graphics;
import java.util.ArrayList;

import java.util.Arrays;

public class Universo {
    private static final Logger LOG = Logger.getLogger(Universo.class);

    MatrizPaso M;
    ArrayList<PuntoCS> P; //distintos puntos
    ArrayList<Base2D> O;

    // Factor de radianes a pixels
    static double X3FAC = (+2880.0/Math.PI);
    static double Y3FAC = (+2880.0/Math.PI);

    public Universo() {
        M = new MatrizPaso(100.0, 100.0, 100.0, 0.0, 0.0, 0.0);
        P = new ArrayList<PuntoCS>();
        O = new ArrayList<Base2D>();
    }

    public void addLinea2D ( Linea2D o) {
//        LOG.trace("Universo3D.addLinea3D = " + o.toString());
        O.add(o);
        addPunto(o.a);
        addPunto(o.b);
//        LOG.debug("Universo3D.addLinea3D O = " + O.toString());
//        LOG.debug("Universo3D.addLinea3D P = " + P.toString());
        return ;
    }
    public void addTriangulo2D ( Triangulo2D o) {
//        LOG.trace("Universo3D.addTriangulo3D = " + o.toString());
        O.add(o);
        addPunto(o.a);
        addPunto(o.b);
        addPunto(o.c);
//        LOG.debug("Universo3D.addTriangulo3D O = " + O.toString());
//        LOG.debug("Universo3D.addTriangulo3D P = " + P.toString());
        return ;
    }
    public void addRectangulo2D ( Rectangulo2D o) {
//        LOG.trace("Universo3D.addRectangulo2D = " + o.toString());
        O.add(o);
        addPunto(o.a);
        addPunto(o.b);
        addPunto(o.c);
        addPunto(o.d);
//        LOG.debug("Universo3D.addRectangulo2D O = " + O.toString());
//        LOG.debug("Universo3D.addRectangulo2D P = " + P.toString());
        return ;
    }
    public void addPolRegular2D ( PolRegular2D o) {
        LOG.trace("Universo3D.addPolRegular3D = " + o.toString());
        O.add(o);
        for ( int i=0; i<o.nv ; i++) {
            addPunto(o.p[i]);
        }
//        LOG.debug("Universo3D.addPolRegular3D O = " + O.toString());
//        LOG.debug("Universo3D.addPolRegular3D P = " + P.toString());
        return ;
    }
    
    public void addPunto ( Punto p ) {
        PuntoCS pu = new PuntoCS((double)p.x, (double)p.y, (double)p.z);
        p.i = P.indexOf(pu);
        if ( p.i < 0 ) {
            P.add(pu);
            p.i=P.size()-1;
        }
        return;
    }


    public void calcula (double x, double y, double z, double a, double b, double c) {
        M.MueveVista(x, y, z, a, b, c);
        M.calculaMatriz();
        for ( PuntoCS p: P) {
            M.aplicaMatriz(p);
//            LOG.trace("Universo3D.calcula p = " + p );
        }
    }

    public void pinta (Graphics g, int x0, int y0 ) {
//        LOG.trace("Universo3D.pinta x0,y0 = "+x0+","+y0);

        // cálculo de zOrder de cada O Base2D
        zOrderBase2D rDist[] = new zOrderBase2D[O.size()];
        for ( int i=0 ; i<O.size() ; i++ ) {
            int idxPtos[] = O.get(i).getIdxPuntos();
            int n = idxPtos.length;
            double r=0.0;
            for ( int v=0 ; v<n ; v++ )
                r += P.get(idxPtos[v]).r ;
            r /= (double)n;
            rDist[i] = new zOrderBase2D(i,r);
        }
        Arrays.sort(rDist);

        for ( int i=0 ; i<rDist.length ; i++ ) {
            Base2D o = O.get(rDist[i].idx) ;
       
//        for ( Base2D o: O) {
            boolean esVisible;
            int idxPtos[] = o.getIdxPuntos();
            int n = idxPtos.length;
            int x[] = new int[n];
            int y[] = new int[n];
            esVisible = true;
            for ( int v=0 ; v<n ; v++ ) {
                if ( P.get(idxPtos[v]).r<=0.0 ) // si hay algún punto 'noVisible'
                    esVisible = false;
                x[v] = x0 - (int)(Math.round(X3FAC * P.get(idxPtos[v]).a)) ;
                y[v] = y0 - (int)(Math.round(Y3FAC * P.get(idxPtos[v]).b)) ;
            }
            if (esVisible) {
                g.setColor(o.getColor());
                if ( n==1 )      g.drawLine(x[0],y[0],x[0],y[0]);
                else if ( n==2 ) g.drawLine(x[0],y[0],x[1],y[1]);
                else {           g.fillPolygon(x, y, n);
                                 g.drawPolygon(x, y, n);
                }
            //  LOG.debug("Universo3D.pinta poligono3D o="+o);
            }
        }
    }

    private class zOrderBase2D implements Comparable<zOrderBase2D> {
        int idx;
        double r;
        static final double precision = 1000000.0;

        public zOrderBase2D ( int _idx, double _r ) {
            idx = _idx;
            r = _r ;
        }

        @Override
        public int compareTo(zOrderBase2D o) {
            if ( this == o ) return 0;
            return (int) (precision * (o.r - this.r));
        }
    }
    
}
