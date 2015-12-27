package calculo;

import org.apache.log4j.Logger;

import java.awt.Graphics;
import java.util.ArrayList;

public class Universo3D {
    private static final Logger LOG = Logger.getLogger(Universo3D.class);

    MatrizPaso3D M;
    ArrayList<Punto3Dcp> P; //distintos puntos

    ArrayList<Triangulo3D> T;
    ArrayList<Linea3D> L;
    
    static double X3FAC = (+2880.0/Math.PI);
    static double Y3FAC = (+2880.0/Math.PI);
    
    public Universo3D() {
        M = new MatrizPaso3D(100.0, 100.0, 100.0, 0.0, 0.0, 0.0);
        P = new ArrayList<Punto3Dcp>();
        T = new ArrayList<Triangulo3D>();
        L = new ArrayList<Linea3D>();
    }

    public void addTriangulo3D ( Triangulo3D t) {
        LOG.trace("Universo3D.addTriangulo3D t = " + t.toString());
        T.add(t);
        addPunto3D(t.a);
        addPunto3D(t.b);
        addPunto3D(t.c);
        LOG.debug("Universo3D.addTriangulo3D T = " + T.toString());
        LOG.debug("Universo3D.addTriangulo3D P = " + P.toString());
        return ;
    }
    public void addLinea3D ( Linea3D l) {
        LOG.trace("Universo3D.addLinea3D l = " + l.toString());
        L.add(l);
        addPunto3D(l.a);
        addPunto3D(l.b);
        LOG.debug("Universo3D.addLinea3D L = " + L.toString());
        LOG.debug("Universo3D.addLinea3D P = " + P.toString());
        return ;
    }
    public void addPunto3D ( Punto3D p ) {
        Punto3Dcp pu = new Punto3Dcp((double)p.x, (double)p.y, (double)p.z);
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
        for ( Punto3Dcp p: P) {
            M.aplicaMatriz(p);
            LOG.trace("Universo3D.calcula p = " + p );
        }
    }

    public void pinta (Graphics g, int x0, int y0 ) {
        LOG.trace("Universo3D.pinta x0,y0 = "+x0+","+y0);

        for ( Linea3D l: L) {
            int x[] = new int[2];
            int y[] = new int[2];
            x[0] = x0 - (int)(X3FAC * P.get(l.a.i).a) ;
            y[0] = y0 - (int)(Y3FAC * P.get(l.a.i).b) ;
            x[1] = x0 - (int)(X3FAC * P.get(l.b.i).a) ;
            y[1] = y0 - (int)(Y3FAC * P.get(l.b.i).b) ;
            g.setColor(l.C);
            g.drawLine(x[0],  y[0],  x[1],  y[1]);
            LOG.debug("Universo3D.pinta Line x="+x[0]+","+x[1]+", y="+y[0]+","+y[1]+", C="+l.C);
        }

        for ( Triangulo3D t: T) {
            int x[] = new int[3];
            int y[] = new int[3];
            x[0] = x0 - (int)(X3FAC * P.get(t.a.i).a) ;
            y[0] = y0 - (int)(Y3FAC * P.get(t.a.i).b) ;
            x[1] = x0 - (int)(X3FAC * P.get(t.b.i).a) ;
            y[1] = y0 - (int)(Y3FAC * P.get(t.b.i).b) ;
            x[2] = x0 - (int)(X3FAC * P.get(t.c.i).a) ;
            y[2] = y0 - (int)(Y3FAC * P.get(t.c.i).b) ;
            g.setColor(t.C);
            g.fillPolygon(x, y, 3);
            LOG.debug("Universo3D.pinta Polygon x="+x[0]+","+x[1]+","+x[2]+", y="+y[0]+","+y[1]+","+y[2]+", C="+t.C);
        }

    }

}
