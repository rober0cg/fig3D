package fig3d.objetos2D;

import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.awt.Color;

public class Linea2D implements Base2D {
    private static final Logger LOG = Logger.getLogger(Linea2D.class);

    public Punto a, b;
    public Color C;

    static Pattern pT=Pattern.compile("([ab][(][+-]*\\d+,[+-]*\\d+,[+-]*\\d+[)])|(C[(]\\d+,\\d+,\\d+,\\d+[)])");

    public Linea2D( Punto _a, Punto _b, Color _C) {
        a=_a;
        b=_b;
        C=_C;
    }

// L2D(a(ax,ay,az),b(bx,by,bz),C(Cr,Cg,Cb,Ca))
    public Linea2D(String t){
        LOG.trace("Linea2D");

        String aux1 = t.replace(" ","");
        LOG.trace("Linea2D ["+aux1+"]");

        Matcher mT = pT.matcher(aux1);
        while ( mT.find() ){
            String aux2 = mT.group();
            LOG.trace("Linea2D group ["+aux2+"]");
            char pc = aux2.charAt(0);
            switch(pc) {
                case 'a': a = new Punto(aux2); break;
                case 'b': b = new Punto(aux2); break;
                case 'C': C = Util.stringToColor(aux2); break;
            }
        }
    }

    @Override
    public String toString() {
        return "L3D("+a+","+b+","+C+")" ;
    }

    @Override
    public int[] getIdxPuntos() {
        int i[] = new int[2];
        i[0] = a.i;
        i[1] = b.i;
        return i ;
    }

    @Override
    public Color getColor() {
        return C;
    }

}
