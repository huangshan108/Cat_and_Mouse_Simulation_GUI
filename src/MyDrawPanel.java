import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;


public class MyDrawPanel extends JPanel {
    private static final long serialVersionUID = -1451876344365614067L;
    private Graphics g;
     
    public void onPaint() {
        paintComponent(g);
    }
     
    public void paintComponent(Graphics graphics) {
        g = graphics;
         
        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        g.setColor(Color.gray);
        g.fillOval(200, 200, 200, 200);
        
        double catRadius = CatMouseSimulation.getMyCat().getPosition().getMyRadius() * 100;
        double catAngle = CatMouseSimulation.getMyCat().getPosition().getMyAngle();
        
        double mouseRadius = 1 * 100;
        double mouseAngle = CatMouseSimulation.getMyMouse().getPosition().getMyAngle();
        
        int catX = (int) (300 + catRadius * Math.cos(catAngle));
        int catY = (int) (300 - catRadius * Math.sin(catAngle));
        
        int mouseX = (int) (300 + mouseRadius * Math.cos(mouseAngle));
        int mouseY = (int) (300 - mouseRadius * Math.sin(mouseAngle));

        
//        System.err.println("catDis1: " + catX + " catDis2: " + catY);
//        System.err.println("mouseDis1: " + mouseX + " mouseDis2: " + mouseY);
        
		g.setColor(Color.yellow); // cat is yellow color
		g.fillOval(catX, catY, 10, 10);

		g.setColor(Color.blue); // mouse is blue color
		g.fillOval(mouseX, mouseY, 10, 10);

		g.setColor(Color.black);
		g.drawLine(catX, catY, mouseX, mouseY);
	}
}
