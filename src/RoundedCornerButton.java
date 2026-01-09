package iseProject;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
public class RoundedCornerButton extends JButton {
    private static final int RADIUS = 40;  //controls how rounded the corners should be

    public RoundedCornerButton(String text) {
        super(text); //text jo button mai show hoga
        setContentAreaFilled(false); //dont fill the button like the default way
        setFocusPainted(false); //dont paint the button
        setBorderPainted(false); // dont set any sort of borders
        setOpaque(false);
    }
    @Override // this method is called everytime j button is created but hum override kerhe hain so we can make our own custom jbutton
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create(); //essential for drawing the button
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);// clean smooth ends obtain kerne k liye


        if (getModel().isArmed()) {
            g2.setColor(Color.decode("#34920d"));// ager button clicked hai tou this color
        } else {
            g2.setColor(Color.decode("#32620e")); // else this
        }

        g2.fillRoundRect(0, 0, getWidth(), getHeight(), RADIUS, RADIUS); // drawing the rounded button
        g2.dispose(); // destroy the graphic object
        super.paintComponent(g); // optional hai
    }
    @Override
    public boolean contains(int x, int y) {// for better click cause the buttons are rounded now not rectangles
        Shape shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), RADIUS, RADIUS);
        return shape.contains(x, y);
    }
}




