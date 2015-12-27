package calculo;

import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.awt.Color;

public class Linea3D {
    private static final Logger LOG = Logger.getLogger(Linea3D.class);

    Punto3D a, b;
    Color C;

    static Pattern pT=Pattern.compile("([ab][(][+-]*\\d+,[+-]*\\d+,[+-]*\\d+[)])|(C[(]\\d+,\\d+,\\d+,\\d+[)])");
    static Pattern pC=Pattern.compile("\\d+");

    public Linea3D( Punto3D _a, Punto3D _b, Color _C) {
        a=_a;
        b=_b;
        C=_C;
    }

// L3D(a(ax,ay,az),b(bx,by,bz),C(Cr,Cg,Cb,Ca))
    public Linea3D(String t){
        LOG.trace("Linea3D");

        String aux1 = t.replace(" ","");
        LOG.trace("Linea3D ["+aux1+"]");

        Matcher mT = pT.matcher(aux1);
        while ( mT.find() ){
            String aux2 = mT.group();
            LOG.trace("Linea3D group ["+aux2+"]");
            char pc = aux2.charAt(0);
            if ( pc=='a' || pc=='b') {
                switch(pc) {
                case 'a': a = new Punto3D(aux2) ; break ;
                case 'b': b = new Punto3D(aux2) ; break ;
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
        return "L3D("+a+","+b+","+C+")" ;
    }
    
}
