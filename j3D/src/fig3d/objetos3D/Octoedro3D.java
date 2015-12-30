package fig3d.objetos3D;

import org.apache.log4j.Logger;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fig3d.objetos2D.Punto;
import fig3d.objetos2D.Triangulo2D;
import fig3d.objetos2D.Util;

import fig3d.calculo.Universo;

public class Octoedro3D {
    private static final Logger LOG = Logger.getLogger(Octoedro3D.class);

    public Punto a, b, c, d, e, f;
    public Color C;

//  OCTO3D(a(0,200,0),b(0,250,0),c(50,250,0),d(50,200,0),e(0,200,50),f(0,250,50),g(50,250,50),h(50,200,50),C(80,160,160,120))
    static Pattern pT=Pattern.compile("([abcdef][(][+-]*\\d+,[+-]*\\d+,[+-]*\\d+[)])|(C[(]\\d+,\\d+,\\d+,\\d+[)])");


    public Octoedro3D(String s) {
        LOG.trace("Octoedro3D");

        String aux1 = s.replace(" ","");
        LOG.trace("Octoedro3D ["+aux1+"]");

        Matcher mT = pT.matcher(aux1);
        while ( mT.find() ){
            String aux2 = mT.group();
            LOG.trace("Octoedro3D group ["+aux2+"]");
            char pc = aux2.charAt(0);
            switch(pc) {
                case 'a': a = new Punto(aux2) ; break ;
                case 'b': b = new Punto(aux2) ; break ;
                case 'c': c = new Punto(aux2) ; break ;
                case 'd': d = new Punto(aux2) ; break ;
                case 'e': e = new Punto(aux2) ; break ;
                case 'f': f = new Punto(aux2) ; break ;
                case 'C': C = Util.stringToColor(aux2);
            }
        }
    }

    //    /f\ 
    //  d/-|e\
    //  | /c\|
    //  a/- b\
    public void add (Universo U){
        // abc def
        U.addTriangulo2D(new Triangulo2D(a,b,c,C));
        U.addTriangulo2D(new Triangulo2D(d,e,f,C));
        // abd cef
        U.addTriangulo2D(new Triangulo2D(a,b,d,C));
        U.addTriangulo2D(new Triangulo2D(c,e,f,C));
        // adf bce
        U.addTriangulo2D(new Triangulo2D(a,d,f,C));
        U.addTriangulo2D(new Triangulo2D(b,c,e,C));
    }

    
}
