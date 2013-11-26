package frontend.classes;

import helper.Size;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HelpDialog extends JDialog 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 56835094942683956L;
	private Size choosedSize;
	public HelpDialog(JFrame f) {
		super(f);

		setTitle("Hilfe");
		setResizable(false);
		setModal(true);
		setLayout(new BorderLayout());

		//		setPreferredSize(new Dimension(200, 100));
		//		setMinimumSize(this.getPreferredSize());
		//		setMaximumSize(this.getPreferredSize());

		JPanel p = new JPanel(new GridBagLayout());
		add(p, BorderLayout.CENTER);
		JLabel label = new JLabel("Dies ist die Hilfe!");

		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		p.add(label, gc);
		pack();
		}

		public Size getChoosedSize() {
			return choosedSize;
		}
	}
