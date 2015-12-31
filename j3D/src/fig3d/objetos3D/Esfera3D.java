package fig3d.objetos3D;

import org.apache.log4j.Logger;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fig3d.objetos2D.Punto;
import fig3d.objetos2D.Triangulo2D;
import fig3d.objetos2D.Rectangulo2D;
import fig3d.objetos2D.Util;

import fig3d.calculo.Universo;

public class Esfera3D {
    private static final Logger LOG = Logger.getLogger(Esfera3D.class);

    public Punto c;
    public int r;
    public Color C;

    static double Math_PI_2 = Math.PI / 2.0;

    private int numMeridianos = 5; // 0 para ejeX, PI/2 para ejeY
    private double incMeridiano = Math_PI_2  / numMeridianos; 

    private int numLatitudes = 9; // 0 para Ecuador y max(PI/2) para PoloN/PoloS 
    private double incLatitud = Math_PI_2 / numLatitudes;

    //ESFERA3D(c(-100,-100,100),r(100),C(100,100,100,100))
    static Pattern pT=Pattern.compile("([c][(][+-]*\\d+,[+-]*\\d+,[+-]*\\d+[)])|(r[(]\\d+[)])|(l[(]\\d+[)])|(m[(]\\d+[)])|(C[(]\\d+,\\d+,\\d+,\\d+[)])");

    public Esfera3D(String s) {
        LOG.trace("Esfera3D");
    
        String aux1 = s.replace(" ","");
        LOG.trace("Esfera3D ["+aux1+"]");
    
        Matcher mT = pT.matcher(aux1);
        while ( mT.find() ){
            String aux2 = mT.group();
            LOG.trace("Esfera3D group ["+aux2+"]");
            char pc = aux2.charAt(0);
            switch(pc) {
                case 'c': c = new Punto(aux2) ; break;
                case 'r': r = Util.stringToInt(aux2); break;
                case 'C': C = Util.stringToColor(aux2); break;
                case 'm':
                    numMeridianos = Util.stringToInt(aux2);
                    incMeridiano = Math_PI_2  / numMeridianos;
                    break;
                case 'l':
                    numLatitudes = Util.stringToInt(aux2);
                    incLatitud = Math_PI_2 / numLatitudes;
                    break;
            }
        }
    }


    // Eje cz => PoloNorte = ( cx, cy, cz+r ) y PoloSur = ( cx, cy, cz-r )
    // Casquetes polares = triangulos
    // Resto de latitudes = cuadrados
    // Coordenadas punt en función de latitud y meridiano:
    //      p(l,m) = ( cx+(r.cos(l).cos(m)) , cy+(r.cos(l).sen(m)), cz+(r.sen(l)) ) 

    public void add (Universo U){

// CASQUETES POLARES
        Punto PN = new Punto(c.x, c.y, c.z+r); // PN = ( cx, cy, cz+r )
        Punto PS = new Punto(c.x, c.y, c.z-r); // PS = ( cx, cy, cz-r )

        int vx[] = new int[numMeridianos+1];
        int vy[] = new int[numMeridianos+1];
        int vz;
        
        { // Casquetes polares => Triangulos
            double al = incLatitud * (numLatitudes - 1);
            vz = (int)(((double)r)*Math.sin(al));
            for ( int m=0 ; m<numMeridianos+1 ; m++ ) {
                int vr = (int)(((double)r)*Math.cos(al));
                double am = incMeridiano * ((double)m) ;
                vx[m] = (int)(((double)vr)*Math.cos(am));
                vy[m] = (int)(((double)vr)*Math.sin(am));
            }
            
            for ( int m=0 ; m<numMeridianos ; m++ ) { // cuadrante +x+y, +x-y, -x-y, -x+y
                int n=m+1; // meridiano siguiente
                // Casquete NORTE +z
                U.addTriangulo2D(new Triangulo2D( PN, new Punto(c.x+vx[m],c.y+vy[m],c.z+vz), new Punto(c.x+vx[n],c.y+vy[n],c.z+vz), C ));
                U.addTriangulo2D(new Triangulo2D( PN, new Punto(c.x+vx[m],c.y-vy[m],c.z+vz), new Punto(c.x+vx[n],c.y-vy[n],c.z+vz), C ));
                U.addTriangulo2D(new Triangulo2D( PN, new Punto(c.x-vx[m],c.y-vy[m],c.z+vz), new Punto(c.x-vx[n],c.y-vy[n],c.z+vz), C ));
                U.addTriangulo2D(new Triangulo2D( PN, new Punto(c.x-vx[m],c.y+vy[m],c.z+vz), new Punto(c.x-vx[n],c.y+vy[n],c.z+vz), C ));
                // Casquete SUR -z
                U.addTriangulo2D(new Triangulo2D( PS, new Punto(c.x+vx[m],c.y+vy[m],c.z-vz), new Punto(c.x+vx[n],c.y+vy[n],c.z-vz), C ));
                U.addTriangulo2D(new Triangulo2D( PS, new Punto(c.x+vx[m],c.y-vy[m],c.z-vz), new Punto(c.x+vx[n],c.y-vy[n],c.z-vz), C ));
                U.addTriangulo2D(new Triangulo2D( PS, new Punto(c.x-vx[m],c.y-vy[m],c.z-vz), new Punto(c.x-vx[n],c.y-vy[n],c.z-vz), C ));
                U.addTriangulo2D(new Triangulo2D( PS, new Punto(c.x-vx[m],c.y+vy[m],c.z-vz), new Punto(c.x-vx[n],c.y+vy[n],c.z-vz), C ));
            }
        }

        // Meridianos => Cuadrados
        // Tenemos la latitud del casquete polar (numLatitudes-1)

        int wx[][] = new int [numLatitudes][numMeridianos+1];
        int wy[][] = new int [numLatitudes][numMeridianos+1];
        int wz[] = new int [numLatitudes];

        for ( int l=0 ; l<numLatitudes ; l++ ) {
            double al = incLatitud * ((double)l);
            wz[l] = (int)(((double)r)*Math.sin(al));
            for ( int m=0 ; m<numMeridianos+1 ; m++ ) {
                int vr = (int)(((double)r)*Math.cos(al));
                double am = incMeridiano * ((double)m) ;
                wx[l][m] = (int)(((double)vr)*Math.cos(am));
                wy[l][m] = (int)(((double)vr)*Math.sin(am));
            }
        }
        { // copiamos la latitud del casquete polar
            int l = numLatitudes-1;
            wz[l] = vz;
            for ( int m=0 ; m<numMeridianos+1 ; m++ ) {
                wx[l][m] = vx[m];
                wy[l][m] = vy[m];
            }
        }
        
        for ( int l=0 ; l<numLatitudes-1 ; l++ ) {
            for ( int m=0 ; m<numMeridianos ; m++ ) { // cuadrante +x+y, +x-y, -x-y, -x+y
                int k=l+1; // latitud siguiente
                int n=m+1; // metidiano siguiente
                // Hemisferio NORTE +z
                U.addRectangulo2D(new Rectangulo2D( // +x+y
                    new Punto(c.x+wx[l][m],c.y+wy[l][m],c.z+wz[l]), new Punto(c.x+wx[l][n],c.y+wy[l][n],c.z+wz[l]),
                    new Punto(c.x+wx[k][n],c.y+wy[k][n],c.z+wz[k]), new Punto(c.x+wx[k][m],c.y+wy[k][m],c.z+wz[k]), C ));
                U.addRectangulo2D(new Rectangulo2D( // +x-y
                    new Punto(c.x+wx[l][m],c.y-wy[l][m],c.z+wz[l]), new Punto(c.x+wx[l][n],c.y-wy[l][n],c.z+wz[l]),
                    new Punto(c.x+wx[k][n],c.y-wy[k][n],c.z+wz[k]), new Punto(c.x+wx[k][m],c.y-wy[k][m],c.z+wz[k]), C ));
                U.addRectangulo2D(new Rectangulo2D( // -x-y
                    new Punto(c.x-wx[l][m],c.y-wy[l][m],c.z+wz[l]), new Punto(c.x-wx[l][n],c.y-wy[l][n],c.z+wz[l]),
                    new Punto(c.x-wx[k][n],c.y-wy[k][n],c.z+wz[k]), new Punto(c.x-wx[k][m],c.y-wy[k][m],c.z+wz[k]), C ));
                U.addRectangulo2D(new Rectangulo2D( // -x+y
                    new Punto(c.x-wx[l][m],c.y+wy[l][m],c.z+wz[l]), new Punto(c.x-wx[l][n],c.y+wy[l][n],c.z+wz[l]),
                    new Punto(c.x-wx[k][n],c.y+wy[k][n],c.z+wz[k]), new Punto(c.x-wx[k][m],c.y+wy[k][m],c.z+wz[k]), C ));

                // Hemisferio SUR -z
                U.addRectangulo2D(new Rectangulo2D( // +x+y
                    new Punto(c.x+wx[l][m],c.y+wy[l][m],c.z-wz[l]), new Punto(c.x+wx[l][n],c.y+wy[l][n],c.z-wz[l]),
                    new Punto(c.x+wx[k][n],c.y+wy[k][n],c.z-wz[k]), new Punto(c.x+wx[k][m],c.y+wy[k][m],c.z-wz[k]), C ));
                U.addRectangulo2D(new Rectangulo2D( // +x-y
                    new Punto(c.x+wx[l][m],c.y-wy[l][m],c.z-wz[l]), new Punto(c.x+wx[l][n],c.y-wy[l][n],c.z-wz[l]),
                    new Punto(c.x+wx[k][n],c.y-wy[k][n],c.z-wz[k]), new Punto(c.x+wx[k][m],c.y-wy[k][m],c.z-wz[k]), C ));
                U.addRectangulo2D(new Rectangulo2D( // -x-y
                    new Punto(c.x-wx[l][m],c.y-wy[l][m],c.z-wz[l]), new Punto(c.x-wx[l][n],c.y-wy[l][n],c.z-wz[l]),
                    new Punto(c.x-wx[k][n],c.y-wy[k][n],c.z-wz[k]), new Punto(c.x-wx[k][m],c.y-wy[k][m],c.z-wz[k]), C ));
                U.addRectangulo2D(new Rectangulo2D( // -x+y
                    new Punto(c.x-wx[l][m],c.y+wy[l][m],c.z-wz[l]), new Punto(c.x-wx[l][n],c.y+wy[l][n],c.z-wz[l]),
                    new Punto(c.x-wx[k][n],c.y+wy[k][n],c.z-wz[k]), new Punto(c.x-wx[k][m],c.y+wy[k][m],c.z-wz[k]), C ));
            }
        }
    }

}
