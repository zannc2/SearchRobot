package frontend.classes;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class Help extends JDialog 
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 56835094942683956L;

	public Help(JFrame f) {
		super(f);
		
		setTitle("Hilfe");
		setResizable(false);
		setModal(true);
		
		
		pack();
	}
	
}
