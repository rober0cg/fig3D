package calculo;

import java.awt.Graphics;

import javax.swing.JPanel;

public class MiUniverso extends JPanel {
    private static final long serialVersionUID = 118734516L;
    Universo3D U;
    
    public MiUniverso( Universo3D u) {
        U = u;
    }

    @Override
    public void paintComponent ( Graphics g ){
        int x0 = getSize().width / 2; // centrar en ventana
        int y0 = getSize().height / 2;
        U.pinta(g,x0,y0);
    }
    
}
