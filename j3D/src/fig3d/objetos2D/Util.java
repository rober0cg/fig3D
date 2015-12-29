package fig3d.objetos2D;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    static Pattern pC=Pattern.compile("\\d+");

    private Util() {
    }

    public static Color stringToColor(String c) {
        int v[] = new int[4];
        Matcher mC = pC.matcher(c);
        for ( int i=0; mC.find() && i<4 ; i++) {
            v[i] = Integer.valueOf(mC.group());
        }
        return new Color(v[0],v[1],v[2],v[3]);
    }

    public static int stringToInt(String x) {
        int j=0;
        Matcher mC = pC.matcher(x);
        for ( int i=0; mC.find() && i<1 ; i++) {
            j = Integer.valueOf(mC.group());
        }
        return j;
    }
}
