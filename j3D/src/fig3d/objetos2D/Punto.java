package fig3d.objetos2D;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Punto {
    public int x, y, z; // Coordenadas cartesianas 3D
    public int i; // índice en el universo de puntos
    static Pattern pP=Pattern.compile("[+-]*\\d+");

    Punto ( int _x, int _y, int _z) {
        x=_x;
        y=_y;
        z=_z;
    }

// P(x,y,z)
    Punto (String p) {
        int v[] = new int[3];
        Matcher mP = pP.matcher(p);
        for ( int i=0; mP.find() && i<3 ; i++) {
            v[i] = Integer.valueOf(mP.group());
        }
        x = v[0];
        y = v[1];
        z = v[2];
    }
    
    public String toString() {
        return "P3D("+x+","+y+","+z+")<"+i+">" ;
    }

}
