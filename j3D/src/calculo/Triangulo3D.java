package calculo;

import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.awt.Color;

public class Triangulo3D {
    private static final Logger LOG = Logger.getLogger(Triangulo3D.class);

    Punto3D a, b, c;
    Color C;

    static Pattern pT=Pattern.compile("([abc][(][+-]*\\d+,[+-]*\\d+,[+-]*\\d+[)])|(C[(]\\d+,\\d+,\\d+,\\d+[)])");
    static Pattern pC=Pattern.compile("\\d+");

    public Triangulo3D( Punto3D _a, Punto3D _b, Punto3D _c, Color _C) {
        a=_a;
        b=_b;
        c=_c;
        C=_C;
    }

// T3D(a(ax,ay,az),b(bx,by,bz),c(cx,cy,cz),C(Cr,Cg,Cb,Ca))
    public Triangulo3D(String t){
        LOG.trace("Triangulo3D");

        String aux1 = t.replace(" ","");
        LOG.trace("Triangulo3D ["+aux1+"]");

        Matcher mT = pT.matcher(aux1);
        while ( mT.find() ){
            String aux2 = mT.group();
            LOG.trace("Triangulo3D group ["+aux2+"]");
            char pc = aux2.charAt(0);
            if ( pc=='a' || pc=='b' || pc=='c') {
                switch(pc) {
                case 'a': a = new Punto3D(aux2) ; break ;
                case 'b': b = new Punto3D(aux2) ; break ;
                case 'c': c = new Punto3D(aux2) ; break ;
                }
            }
            else {// 'C'
                C = color3D(aux2);
            }
        }
    }

    private Color color3D(String c) {
        int v[] = new int[4];
        Matcher mC = pC.matcher(c);
        for ( int i=0; mC.find() && i<4 ; i++) {
            v[i] = Integer.valueOf(mC.group());
        }
        return new Color(v[0],v[1],v[2],v[3]);
    }

    public String toString() {
        return "T3D("+a+","+b+","+c+","+C+")" ;
    }
    
}
