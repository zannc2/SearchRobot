package frontend.classes;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class Help extends JDialog 
{
	
	public Help(JFrame f) {
		super(f);
		
		setTitle("Hilfe");
		setResizable(false);
		setModal(true);
		
		
		pack();
	}
	
}
