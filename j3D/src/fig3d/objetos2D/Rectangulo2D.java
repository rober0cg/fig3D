package fig3d.objetos2D;

import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.awt.Color;

public class Rectangulo2D implements Base2D {
    private static final Logger LOG = Logger.getLogger(Rectangulo2D.class);

    public Punto a, b, c, d;
    public Color C;

    static Pattern pT=Pattern.compile("([abcd][(][+-]*\\d+,[+-]*\\d+,[+-]*\\d+[)])|(C[(]\\d+,\\d+,\\d+,\\d+[)])");

    public Rectangulo2D( Punto _a, Punto _b, Punto _c, Punto _d, Color _C) {
        a=_a;
        b=_b;
        c=_c;
        d=_d;
        C=_C;
    }

// R2D(a(ax,ay,az),b(bx,by,bz),c(cx,cy,cz),d(dx,dy,dz),C(Cr,Cg,Cb,Ca))
    public Rectangulo2D(String s){
        LOG.trace("Rectangulo2D");

        String aux1 = s.replace(" ","");
        LOG.trace("Rectangulo2D ["+aux1+"]");

        Matcher mT = pT.matcher(aux1);
        while ( mT.find() ){
            String aux2 = mT.group();
            LOG.trace("Rectangulo2D group ["+aux2+"]");
            char pc = aux2.charAt(0);
            switch(pc) {
                case 'a': a = new Punto(aux2); break;
                case 'b': b = new Punto(aux2); break;
                case 'c': c = new Punto(aux2); break;
                case 'd': d = new Punto(aux2); break;
                case 'C': C = Util.stringToColor(aux2); break;
            }
        }
    }

    @Override
    public String toString() {
        return "R3D("+a+","+b+","+c+","+d+","+C+")" ;
    }

    @Override
    public int[] getIdxPuntos() {
        int i[] = new int[4];
        i[0] = a.i;
        i[1] = b.i;
        i[2] = c.i;
        i[3] = d.i;
        return i ;
    }

    @Override
    public Color getColor() {
        return C;
    }

}
