import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.*;

public class Main extends JFrame{
	private static final int WIDTH =1800;
	private static final int HEIGHT=1600;
	
	public Main () {
		super("KeyListener Demo");
		setSize(WIDTH, HEIGHT);
		Game play = new Game();
		((Component) play).setFocusable(true);
		
		Color RoyalBlue = new Color(22,13,193);
		
		
		setBackground(RoyalBlue);
		
		
		getContentPane().add(play);
		
		setVisible(true);

		addWindowListener(new WindowListener(){

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
			//	throw new UnsupportedOperationException("Unimplemented method 'windowOpened'");
			play.createFile();
			play.readFile();
			}


			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
			//	throw new UnsupportedOperationException("Unimplemented method 'windowClosing'");
			play.writeToFile();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				//throw new UnsupportedOperationException("Unimplemented method 'windowClosed'");
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				//throw new UnsupportedOperationException("Unimplemented method 'windowIconified'");
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				//throw new UnsupportedOperationException("Unimplemented method 'windowDeiconified'");
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				//throw new UnsupportedOperationException("Unimplemented method 'windowActivated'");
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				//throw new UnsupportedOperationException("Unimplemented method 'windowDeactivated'");
			}

		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	

	public static void main(String[] args) {
		Main run = new Main();
		

	}


}
