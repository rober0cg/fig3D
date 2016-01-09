package fig3d.superficies3D;

import org.apache.log4j.Logger;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fig3d.objetos2D.Linea2D;
import fig3d.objetos2D.Punto;
import fig3d.objetos2D.Util;

import fig3d.calculo.Universo;

import sr.asr.*;

public class Fz3D {
    private static final Logger LOG = Logger.getLogger(Fz3D.class);

    public String expr;
    public int ix[], iy[];
    public Color C;

    public Punto Z[][]; // matriz de resultados z(x,y)

// FZ3D(e(x+y),x(-1000,1000,100),y(-1000,1000,100),C(140,86,138,80))
    static Pattern pZ=Pattern.compile("([e][(][+-[*()]/0-9.a-z]+[)])|([xy][(][+-]*\\d+,[+-]*\\d+,\\d+[)])|(C[(]\\d+,\\d+,\\d+,\\d+[)])");

    public Fz3D(String s) {
        LOG.trace("Fz3D");

        String aux1 = s.replace(" ","");
        LOG.trace("Fz3D ["+aux1+"]");

        Matcher mZ = pZ.matcher(aux1);
        while ( mZ.find() ){
            String aux2 = mZ.group();
            LOG.trace("Fz3D group ["+aux2+"]");
            char pc = aux2.charAt(0);
            switch(pc) {
                case 'e': expr = aux2.substring(2,aux2.length()-1); break;
                case 'x': ix = Util.stringToIntArray(aux2); break;
                case 'y': iy = Util.stringToIntArray(aux2); break;
                case 'C': C = Util.stringToColor(aux2); break;
            }
        }
    }

    public void add (Universo U){
        Expresion E = new Expresion(expr);
        Variable vx = new Variable("x");
        Variable vy = new Variable("y");
        
        int mx = ((ix[1]-ix[0]) / ix[2]) + 1 ; // número de muestras x
        int my = ((iy[1]-iy[0]) / iy[2]) + 1 ; // número de muestras y
        
        Z = new Punto[mx][my];
        
        for ( int cx=0, x=ix[0] ; cx<mx ; cx++, x+=ix[2] ) {
            for ( int cy=0, y=iy[0] ; cy<my ; cy++, y+=iy[2] ) {
                vx.set(x);
                vy.set(y);
                int z = (int) E.evalua();
                Z[cx][cy] = new Punto(x,y,z);
            }
        }

        for ( int cx=0 ; cx<mx-1 ; cx++ ) {
            for ( int cy=0 ; cy<my-1 ; cy++ ) {
                U.addLinea2D( new Linea2D(Z[cx][cy],Z[cx][cy+1],C));
                U.addLinea2D( new Linea2D(Z[cx][cy],Z[cx+1][cy],C));
            }
        }
        for ( int cx=0 ; cx<mx-1 ; cx++ ) {
            U.addLinea2D( new Linea2D(Z[cx][my-1],Z[cx+1][my-1],C));
        }
        
        for ( int cy=0 ; cy<my-1 ; cy++ ) {
            U.addLinea2D( new Linea2D(Z[mx-1][cy],Z[mx-1][cy+1],C));
        }
        
    }

    
}
