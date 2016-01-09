package fig3d.objetos2D;

import org.apache.log4j.Logger;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    private static final Logger LOG = Logger.getLogger(Util.class);

    static Pattern pC=Pattern.compile("\\d+");
    static Pattern pN=Pattern.compile("[+-]*\\d+");

    private Util() {
    }

    public static Color stringToColor(String c) {
        int v[] = new int[4];
        Matcher mC = pC.matcher(c);
        for ( int i=0; mC.find() && i<4 ; i++) {
            v[i] = Integer.valueOf(mC.group());
        }
//        LOG.trace("Util.stringToColor String("+c+") = "+v[0]+","+v[1]+","+v[2]+","+v[3]);
        return new Color(v[0],v[1],v[2],v[3]);
    }

    public static int stringToInt(String x) {
        int j=0;
        Matcher mN = pN.matcher(x);
        for ( int i=0; mN.find() && i<1 ; i++) {
            j = Integer.valueOf(mN.group());
        }
        return j;
    }

    public static int[] stringToIntArray(String x) {
        int v[] = new int[10];
        Matcher mN = pN.matcher(x);
        for ( int i=0; mN.find() && i<10 ; i++) {
            v[i] = Integer.valueOf(mN.group());
        }
//        LOG.trace("Util.stringToIntArray String("+x+") = "+v[0]+","+v[1]+","+v[2]+","+v[3]);
        return v;
    }
}
