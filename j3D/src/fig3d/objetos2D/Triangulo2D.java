package fig3d.objetos2D;

import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.awt.Color;

public class Triangulo2D implements Base2D {
    private static final Logger LOG = Logger.getLogger(Triangulo2D.class);

    public Punto a, b, c;
    public Color C;

    static Pattern pT=Pattern.compile("([abc][(][+-]*\\d+,[+-]*\\d+,[+-]*\\d+[)])|(C[(]\\d+,\\d+,\\d+,\\d+[)])");
    static Pattern pC=Pattern.compile("\\d+");

    public Triangulo2D( Punto _a, Punto _b, Punto _c, Color _C) {
        a=_a;
        b=_b;
        c=_c;
        C=_C;
    }

// T3D(a(ax,ay,az),b(bx,by,bz),c(cx,cy,cz),C(Cr,Cg,Cb,Ca))
    public Triangulo2D(String t){
        LOG.trace("Triangulo3D");

        String aux1 = t.replace(" ","");
        LOG.trace("Triangulo3D ["+aux1+"]");

        Matcher mT = pT.matcher(aux1);
        while ( mT.find() ){
            String aux2 = mT.group();
            LOG.trace("Triangulo3D group ["+aux2+"]");
            char pc = aux2.charAt(0);
            switch(pc) {
                case 'a': a = new Punto(aux2) ; break ;
                case 'b': b = new Punto(aux2) ; break ;
                case 'c': c = new Punto(aux2) ; break ;
                case 'C': C = Util.stringToColor(aux2);
            }
        }
    }

    @Override
    public String toString() {
        return "T3D("+a+","+b+","+c+","+C+")" ;
    }

    @Override
    public int[] getIdxPuntos() {
        int i[] = new int[3];
        i[0] = a.i;
        i[1] = b.i;
        i[2] = c.i;
        return i ;
    }

    @Override
    public Color getColor() {
        return C;
    }
    
}
