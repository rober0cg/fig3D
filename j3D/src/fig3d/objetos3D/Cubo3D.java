package fig3d.objetos3D;

import org.apache.log4j.Logger;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fig3d.objetos2D.Punto;
import fig3d.objetos2D.Rectangulo2D;
import fig3d.objetos2D.Util;

import fig3d.calculo.Universo;


public class Cubo3D {
    private static final Logger LOG = Logger.getLogger(Cubo3D.class);

    public Punto a, b, c, d, e, f, g, h;
    public Color C;


//    CUBO3D(a(0,200,0),b(0,250,0),c(50,250,0),d(50,200,0),e(0,200,50),f(0,250,50),g(50,250,50),h(50,200,50),C(80,160,160,120))
    static Pattern pT=Pattern.compile("([abcdefgh][(][+-]*\\d+,[+-]*\\d+,[+-]*\\d+[)])|(C[(]\\d+,\\d+,\\d+,\\d+[)])");

    public Cubo3D(String s) {
        LOG.trace("Cubo3D");

        String aux1 = s.replace(" ","");
        LOG.trace("Cubo3D ["+aux1+"]");

        Matcher mT = pT.matcher(aux1);
        while ( mT.find() ){
            String aux2 = mT.group();
            LOG.trace("Cubo3D group ["+aux2+"]");
            char pc = aux2.charAt(0);
            switch(pc) {
                case 'a': a = new Punto(aux2) ; break ;
                case 'b': b = new Punto(aux2) ; break ;
                case 'c': c = new Punto(aux2) ; break ;
                case 'd': d = new Punto(aux2) ; break ;
                case 'e': e = new Punto(aux2) ; break ;
                case 'f': f = new Punto(aux2) ; break ;
                case 'g': g = new Punto(aux2) ; break ;
                case 'h': h = new Punto(aux2) ; break ;
                case 'C': C = Util.stringToColor(aux2);
            }
        }

    }

    //    /h -/g
    //  e/-|f/ |
    //  | /d|-/c
    //  a/- b/
    public void add (Universo U){
        // abcd efgh
        U.addRectangulo2D(new Rectangulo2D(a,b,c,d,C));
        U.addRectangulo2D(new Rectangulo2D(e,f,g,h,C));
        // abfe dcgh
        U.addRectangulo2D(new Rectangulo2D(a,b,f,e,C));
        U.addRectangulo2D(new Rectangulo2D(d,c,g,h,C));
        // adhe bcgf
        U.addRectangulo2D(new Rectangulo2D(a,d,h,e,C));
        U.addRectangulo2D(new Rectangulo2D(b,c,g,f,C));
    }
}
