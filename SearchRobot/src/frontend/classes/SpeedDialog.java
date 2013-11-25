package frontend.classes;

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

public class SpeedDialog extends JDialog 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 56835094942683956L;
	private int choosedSpeed;
	private String[] speed = {"25", "50", "100", "200"};
	private JComboBox<String> jcb;
	public SpeedDialog(JFrame f) {
		super(f);

		setTitle("Geschwindigkeit ändern");
		setResizable(false);
		setModal(true);
		setLayout(new BorderLayout());

		//		setPreferredSize(new Dimension(200, 100));
		//		setMinimumSize(this.getPreferredSize());
		//		setMaximumSize(this.getPreferredSize());

		JPanel p = new JPanel(new GridBagLayout());
		add(p, BorderLayout.CENTER);
		JLabel label = new JLabel("Bitte Geschwindigkeit wählen: (Pixel/Sekunde)");
		jcb = new JComboBox<>(speed);
		JButton button = new JButton("OK");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (speed[0].equals(jcb.getSelectedItem() ))
				{
					choosedSpeed = 40;
				}
				else if (speed[1].equals(jcb.getSelectedItem() ))
				{
					choosedSpeed = 20;
				}
				else if (speed[2].equals(jcb.getSelectedItem() ))
				{
					choosedSpeed = 10;
				} 
				else if (speed[3].equals(jcb.getSelectedItem() ))
				{
					choosedSpeed = 5;
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

		public int getChoosedSpeed() {
			return choosedSpeed;
		}
	}
