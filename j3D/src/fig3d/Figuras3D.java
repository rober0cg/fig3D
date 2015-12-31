package fig3d;

import org.apache.log4j.Logger;

import fig3d.calculo.Universo;
import fig3d.objetos2D.Linea2D;
import fig3d.objetos2D.Triangulo2D;
import fig3d.objetos2D.Rectangulo2D;
import fig3d.objetos2D.PolRegular2D;

import fig3d.objetos3D.Cubo3D;
import fig3d.objetos3D.Octoedro3D;
import fig3d.objetos3D.Esfera3D;

import fig3d.grafico.MiSlider;
import fig3d.grafico.MiUniverso;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;


public class Figuras3D extends JFrame {
    private static final Logger LOG = Logger.getLogger(Figuras3D.class);
    private static final long serialVersionUID = 198932675L;

    protected boolean done = false;
    private static final int nSleep = 40;

    private MiSlider sX, sY, sZ, sA, sB, sC;
    private MiUniverso uU;
    private JPanel pnXYZ, pnABC, pnCTL;

    private static Universo U;
    double X, Y, Z, A, B, C;

    
    public static void main(String[] args) {
        U = new Universo();
        try {
            FileReader fr = new FileReader(args[0]);
            BufferedReader br = new BufferedReader(fr) ;
            for(String line; (line = br.readLine()) != null; ) {
                if ( line.startsWith("#"))
                    continue;
                if ( line.startsWith("L2D"))
                    U.addLinea2D( new Linea2D(line));
                if ( line.startsWith("T2D"))
                    U.addTriangulo2D( new Triangulo2D(line));
                if ( line.startsWith("R2D"))
                    U.addRectangulo2D( new Rectangulo2D(line));
                if ( line.startsWith("P2D"))
                    U.addPolRegular2D( new PolRegular2D(line));
                if ( line.startsWith("CUBO3D")) {
                    Cubo3D c = new Cubo3D(line);
                    c.add(U);
                }
                if ( line.startsWith("OCTO3D")) {
                    Octoedro3D o = new Octoedro3D(line);
                    o.add(U);
                }
                if ( line.startsWith("ESFERA3D")) {
                    Esfera3D o = new Esfera3D(line);
                    o.add(U);
                }
            }
            br.close();
            fr.close();
        } catch (FileNotFoundException e) {
            LOG.error("Figuras3D - FileNotFound: " + args[0]);
            return;
        } catch (IOException e) {
            LOG.error("Figuras3D - IOException: " + args[0]);
            return;
        }

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Figuras3D frame = new Figuras3D();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Figuras3D() {
        LOG.trace("Figuras3D.constructor INI");

        getContentPane().setBackground(Color.BLACK);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 900);
        setTitle("Figuras3D");

        uU = new MiUniverso(U);
        X = Y = Z = A = B = C = 0.0;

        sX = new MiSlider("x", -1000, +1000, +500);
        sY = new MiSlider("y", -1000, +1000, +500);
        sZ = new MiSlider("z", -1000, +1000, +500);
        sA = new MiSlider("a",  -180, +180, -135);
        sB = new MiSlider("b",   -90,  +90,  -36);
        sC = new MiSlider("c",  -180, +180,    0);

        pnXYZ = new JPanel();
        pnABC = new JPanel();
        pnCTL = new JPanel();

        pnXYZ.setLayout(new GridLayout(3,1));
        pnXYZ.add(sX);
        pnXYZ.add(sY);
        pnXYZ.add(sZ);

        pnABC.setLayout(new GridLayout(3,1));
        pnABC.add(sA);
        pnABC.add(sB);
        pnABC.add(sC);

        pnCTL.add(pnXYZ);
        pnCTL.add(pnABC);
        pnCTL.setLayout(new GridLayout(1,2));

        setLayout(new BorderLayout());
        add(pnCTL,BorderLayout.PAGE_START);
        add(uU,BorderLayout.CENTER);
        pack();

        new Thread(new Runnable() {
            public void run() {
                LOG.trace("Figuras3D.thread.start.run INI");
                while (!done) {
                    LOG.trace("Figuras3D.thread.start.run STEP");
                    Figuras3D.this.repaint();
                    try {
                        Thread.sleep(nSleep);
                    } catch (InterruptedException e) { /* do nothing */
                    }
                }
                LOG.trace("Figuras3D.thread.start.run END");
            }
        }).start();

        LOG.trace("Figuras3D.constructor END");
    }

    @Override
    public void paint(Graphics g) {
        LOG.trace("Figuras3D.paint INI");

        super.paint(g);

        double x, y, z, a, b, c;
        x = (double)sX.getValue();
        y = (double)sY.getValue();
        z = (double)sZ.getValue();
        a = (double)sA.getValue();
        b = (double)sB.getValue();
        c = (double)sC.getValue();

        if ( x!=X || y!=Y || z!=Z || a!=A || b!=B || c!=C ) { // calcular sólo si ha habido cambio
            X=x; Y=y; Z=z; A=a; B=b; C=c;
            A *= (Math.PI / 180 ); // pasar de grados a radianes
            B *= (Math.PI / 180 );
            C *= (Math.PI / 180 );
            U.calcula(X,Y,Z,A,B,C);
        }
        

        LOG.trace("Figuras3D.paint END");
    }
    
    @Override
    public Dimension getPreferredSize() {
        LOG.trace("Figuras3D.getPreferredSize");
        return new Dimension(800, 600);
    }
    @Override
    public Dimension getMinimumSize() {
        LOG.trace("Figuras3D.getMinimumSize");
        return new Dimension(640, 480);
    }
    @Override
    public Dimension getMaximumSize() {
        LOG.trace("Figuras3D.getMaximumSize");
        return new Dimension(900, 600);
    }

}
