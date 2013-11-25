package frontend.classes;

import helper.Size;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class NewDialog extends JDialog 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 56835094942683956L;
	private Size choosedSize;
	private String[] size = {"800 x 500", "800 x 600", "1000 x 700", "1000 x 800"};
	private JComboBox<String> jcb;
	public NewDialog(JFrame f) {
		super(f);

		setTitle("Neues Feld");
		setResizable(false);
		setModal(true);
		setLayout(new BorderLayout());

		//		setPreferredSize(new Dimension(200, 100));
		//		setMinimumSize(this.getPreferredSize());
		//		setMaximumSize(this.getPreferredSize());

		JPanel p = new JPanel(new GridBagLayout());
		add(p, BorderLayout.CENTER);
		JLabel label = new JLabel("Bitte Grösse wählen:");
		jcb = new JComboBox<>(size);
		JButton button = new JButton("Neu");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (size[0].equals(jcb.getSelectedItem() ))
				{
					choosedSize = new Size(800,500);
				}
				else if (size[1].equals(jcb.getSelectedItem() ))
				{
					choosedSize = new Size(800,600);
				}
				else if (size[2].equals(jcb.getSelectedItem() ))
				{
					choosedSize = new Size(1000,700);
				} 
				else if (size[3].equals(jcb.getSelectedItem() ))
				{
					choosedSize = new Size(1000,800);
				}
				setVisible(false);
				dispose();	
			}});

		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		p.add(label, gc);
		gc.gridx = 1;
		p.add(jcb, gc);
		gc.gridx = 2;
		p.add(button, gc);
		pack();
		}

		public Size getChoosedSize() {
			return choosedSize;
		}
	}
