package fig3d.grafico;

import java.awt.Graphics;

import javax.swing.JPanel;

import fig3d.calculo.Universo;

public class MiUniverso extends JPanel {
    private static final long serialVersionUID = 118734516L;
    Universo U;
    
    public MiUniverso( Universo u) {
        U = u;
    }

    @Override
    public void paintComponent ( Graphics g ){
        int x0 = getSize().width / 2; // centrar en ventana
        int y0 = getSize().height / 2;
        U.pinta(g,x0,y0);
    }
    
}
