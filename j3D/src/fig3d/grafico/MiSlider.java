package fig3d.grafico;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.JLabel;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class MiSlider extends JPanel {
    private static final long serialVersionUID = 19178324L;

    JSlider s;
    JLabel l;
    String t;
    
    public MiSlider( String lab, int min, int max, int ini) {

        t = lab;

        l = new JLabel();
        l.setPreferredSize(new Dimension(32,30));
        l.setText(t + ": "+ Integer.toString(ini));
        l.setHorizontalAlignment(SwingConstants.RIGHT);

        s = new JSlider(JSlider.HORIZONTAL, min, max, ini);
        s.setPreferredSize( new Dimension(200,30));
        s.setPaintTicks(true);
        s.setMajorTickSpacing((max-min)/4);
        s.setMinorTickSpacing((max-min)/20);
//        s.setPaintLabels(true);
        s.addChangeListener(new MiSliderAccion());

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
        c.insets = new Insets(0,0,0,8);
        c.anchor = GridBagConstraints.LINE_END;
        this.add(l,c);


    }

    public int getValue (){
        return s.getValue();
    }

    class MiSliderAccion implements ChangeListener{
        public void stateChanged(ChangeEvent e){
            l.setText(t + ": "+ Integer.toString(s.getValue()));
        }
    }
    
}
