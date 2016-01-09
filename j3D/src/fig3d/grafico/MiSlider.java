package fig3d.grafico;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class MiSlider extends JPanel {
    private static final long serialVersionUID = 19178324L;

    JSlider s;  // posición o ángulo
    JLabel l;   // variable y posición
    JSpinner v; // velocidad de cambio
    JSpinner g; // velocidad de giro sobre eje
    String t;   // nombre de la variable

    static final int freq = 0; // repintado por segundo
    int r=0;

    public MiSlider( String lab, int min, int max, int ini) {

        t = lab;

        l = new JLabel();
        l.setPreferredSize(new Dimension(36,28));
        l.setText(t + ": "+ Integer.toString(ini));
        l.setHorizontalAlignment(SwingConstants.LEFT);

        s = new JSlider(JSlider.HORIZONTAL, min, max, ini);
        s.setPreferredSize( new Dimension(200,28));
        s.setPaintTicks(true);
        s.setMajorTickSpacing((max-min)/4);
        s.setMinorTickSpacing((max-min)/20);
//        s.setPaintLabels(true);
        s.addChangeListener(new MiSliderAccion());

        v = new JSpinner(new SpinnerNumberModel(0, -10, 10, 1));
        v.setPreferredSize( new Dimension(24,24));

        g = new JSpinner(new SpinnerNumberModel(0, -10, 10, 1));
        g.setPreferredSize( new Dimension(24,24));

        this.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.8;
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(0,0,0,0);
        this.add(s,c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(0,0,0,0);
        c.anchor = GridBagConstraints.LINE_END;
        this.add(l,c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.05;
        c.gridx = 2;
        c.gridy = 0;
        c.insets = new Insets(0,0,0,2);
        c.anchor = GridBagConstraints.LINE_END;
        this.add(v,c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.05;
        c.gridx = 3;
        c.gridy = 0;
        c.insets = new Insets(0,0,0,2);
        c.anchor = GridBagConstraints.LINE_END;
        this.add(g,c);

    }

    public int getValue(){
        int n = s.getValue();
        if ( ++r>freq ) {
            int i = (int)v.getValue();
            if ( i!= 0) {
                n += i;
                s.setValue(n);
            }
            r=0;
        }
        return n;
    }
    public void setValue(int val){
        s.setValue(val);
    }

    public int getVelocidad(){
        return (int)v.getValue();
    }

    public int getGiro(){
        return (int)g.getValue();
    }


    class MiSliderAccion implements ChangeListener{
        public void stateChanged(ChangeEvent e){
            l.setText(t + ": "+ Integer.toString(s.getValue()));
        }
    }
    
}
