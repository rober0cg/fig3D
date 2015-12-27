package calculo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Punto3D {
    int x, y, z; // Coordenadas cartesianas 3D
    int i; // �ndice en el universo de puntos
    static Pattern pP=Pattern.compile("[+-]*\\d+");

    Punto3D ( int _x, int _y, int _z) {
        x=_x;
        y=_y;
        z=_z;
    }

// P(x,y,z)
    Punto3D (String p) {
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
