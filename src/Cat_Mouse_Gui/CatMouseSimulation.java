package Cat_Mouse_Gui;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;


public class CatMouseSimulation {

	private static Cat cat;
	private static Mouse mouse;
	private static int times = 0;
	private static String[] inputArgs = null;
	private boolean catchingFlag = false;
    
    public static void printInit() {
		/**
		 * Print initial states and nice headers.
		 * First line of terminal output.
		 */
		String headerString = String.format("%-4s %-8s  %-15s %-9s", "TIME", "STATUS", "MOUSE", "CAT");
		System.out.println(headerString);
		
		/**
		 * print initial state.
		 * Second line of terminal output.
		 */
		Position initPositionMouse = mouse.getPosition();
		Position initPositionCat = cat.getPosition();
		String initString = String.format("%-4d %-9s %-15s %-9s", times, "init", initPositionMouse.toString(), initPositionCat.toString());
		System.out.println(initString);
    }
    
    public static void printRunning() {
		Position positionMouse = mouse.getPosition();
		Position positionCat = cat.getPosition();
		String iterString = new String();
		iterString = String.format("%-4d %-9s %-15s %-9s", times, "running", positionMouse.toString(), positionCat.toString());
		System.out.println(iterString);
    }
    
    public static void printEaten() {
		Position positionMouse = mouse.getPosition();
		Position positionCat = cat.getPosition();
		String iterString = new String();
		iterString = String.format("%-4d %-9s %-15s %-9s", times, "eaten", positionMouse.toString(), positionCat.toString());
		System.out.println(iterString);
    }
    
	/**
	 * Run the simulation
	 * @param cat
	 * @param mouse
	 */
    public static void runChase(Cat cat, Mouse mouse, MyDrawPanel drawPanel, JLabel countLabel) {
		/**
		 * run-catch starts.
		 */
		times++;
		while (times < 31) {
			//cat moves first, and determine whether catches the mouse during the move.
			boolean catEatsMouse = cat.move(mouse.getPosition());
			
			//if cat didn't eat mouse during its move.
			if (!catEatsMouse) {
				//mouse moves second.
				mouse.move();
				printRunning();
				updateUI (countLabel, drawPanel);
				
			//if cat ate mouse during its move.
			} else if (catEatsMouse) {

				printEaten();
				updateUI (countLabel, drawPanel);
				System.exit(1);
			}
			times++;
			updateUI (countLabel, drawPanel);
			
            // freeze for 300ms
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
		}
		
		
		boolean catEatsMouse = cat.move(mouse.getPosition());
		String iterString = new String();
		//if cat caught the mouse at the 31th step (one more step than the limit).
		if (catEatsMouse) {
			iterString = String.format("%-4d %-9s", 31, "so close to caught, just need one more round... :(");
			System.out.println(iterString);
			updateUI (countLabel, drawPanel);
		} else {
			iterString = String.format("%-4d %-9s", 31, "far from catching the mouse... :(");
			System.out.println(iterString);
			updateUI (countLabel, drawPanel);
			
		}
	}

	
	
    public void go() {
        // Frame
        JFrame frame = new JFrame("Cat and Mouse GUI");

        
        // Panel
        JPanel buttonPanel = new JPanel();
        MyDrawPanel drawPanel = new MyDrawPanel();
 
        // Buttons
        JButton reset = new JButton("reset");
        JButton step = new JButton("step");
        JButton run = new JButton("run");
        JButton quit = new JButton("quit");
 
        buttonPanel.add(reset);
        buttonPanel.add(step);
        buttonPanel.add(run);
        buttonPanel.add(quit);
 
        // Labels
        JLabel timeLabel = new JLabel("Time:");
        JLabel countLabel = new JLabel(" " + times);
 
        buttonPanel.add(timeLabel);
        buttonPanel.add(countLabel);
        
        
        // Listeners
        reset.addActionListener(new MyResetListener(drawPanel, countLabel));
        step.addActionListener(new MyStepListener(drawPanel, countLabel));
        run.addActionListener(new MyRunListener(drawPanel, countLabel));
        quit.addActionListener(new MyQuitListener(frame));
 
        // Draw UI
        frame.getContentPane().add(BorderLayout.SOUTH, buttonPanel);
        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setSize(700, 700);
        frame.setVisible(true);
    }
	
    /**
     * 
     * @author Shan
     *
     */
    private class MyResetListener implements ActionListener {
        private final MyDrawPanel drawPanel;
        private final JLabel countLabel;
         
        public MyResetListener (final MyDrawPanel drawPanel, final JLabel countLabel) {
            this.drawPanel = drawPanel;
            this.countLabel = countLabel;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
        	processArgs(inputArgs);
        	System.out.println("Simulation Reset!");
        	CatMouseSimulation.times = 0;
        	catchingFlag = false;
            updateUI(countLabel, drawPanel);
        }
    }
    
    public static void reset() {
    	
    }
    
    /**
     * 
     * @author Shan
     *
     */
    private class MyStepListener implements ActionListener {
        private final MyDrawPanel drawPanel;
        private final JLabel countLabel;
         
        public MyStepListener (MyDrawPanel drawPanel, JLabel countLabel) {
            this.drawPanel = drawPanel;
            this.countLabel = countLabel;
        }
        @Override
		public void actionPerformed(ActionEvent e) {
			cat.move(mouse.getPosition());
			mouse.move();
			CatMouseSimulation.times++;

			if (catchingFlag) {
				printEaten();
	        	processArgs(inputArgs);
	        	System.out.println("Simulation Reset!");
	        	CatMouseSimulation.times = 0;
	        	catchingFlag = false;
	            updateUI(countLabel, drawPanel);
	            printInit();
			} else {
				printRunning();
				catchingFlag = Position.isCatching(cat.getPosition(), mouse.getPosition());
				// repaint UI
				updateUI(countLabel, drawPanel);
			}
		}
    }
    
    /**
     * 
     * @author Shan
     *
     */
    private class MyRunListener implements ActionListener {
        private final MyDrawPanel drawPanel;
        private final JLabel countLabel;
         
        public MyRunListener (MyDrawPanel drawPanel, JLabel countLabel) {
            this.drawPanel = drawPanel;
            this.countLabel = countLabel;
        }
 
        @Override
        public void actionPerformed(ActionEvent e) {
            SwingWorker<Object, Object> worker = new SwingWorker <Object, Object>() {
                 
                @Override
                protected Object doInBackground() throws Exception {
                    runChase(cat, mouse, drawPanel, countLabel);
                    return null;
                }      
            };
            worker.execute();
        }
    }
    
    /**
     * 
     * @author Shan
     *
     */
    private class MyQuitListener implements ActionListener {
        private final JFrame frame;
         
        public MyQuitListener (final JFrame frame) {
            this.frame = frame;
        }
     
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
            System.out.println("Quit!");
            System.exit(0);
        }
    }
    
    private static void updateUI (JLabel countLabel, MyDrawPanel drawPanel) {
        drawPanel.onPaint();
        countLabel.setText("" + times);
        drawPanel.updateUI();
    }

	
	private void processArgs(String[] args) {
		inputArgs = args;
		if (args.length != 3) {
			System.err.println("Usage:  java CatMouseSimulation catRadius catAngle mouseAngle");
			System.exit(1);
		}
		try {
			double catRad = Double.parseDouble(args[0]);
			double catAng = (Math.PI / 180) * (((Double.parseDouble(args[1])) % 360));
			Position p = new Position(catRad, catAng);
			cat = new Cat(p);// intializes cat
		} catch (Exception ex) {
			System.err.println("The first argument must specify a radius. The second argument must specify an angle.");
			System.exit(1);
		}
		try {
			double mouseRad = 1.0;
			double mouseAng = (Math.PI / 180) * (((Double.parseDouble(args[2])) % 360));
			Position p = new Position(mouseRad, mouseAng);
			mouse = new Mouse(p);// intializes mouse
		} catch (Exception ex) {
			System.err.println("The third argument must specify an angle.");
			System.exit(1);
		}
	}
	
	public static Cat getMyCat() {
		return cat;
	}

	public static Mouse getMyMouse() {
		return mouse;
	}
	
	/**
	 * Set up the arguments and then call runChase to run the simulation
	 * @param args
	 */
	public static void main(String[] args) {
		CatMouseSimulation simulation = new CatMouseSimulation();
		simulation.processArgs(args);
		printInit();
        simulation.go();
	}
}