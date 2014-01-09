package frontend.impl;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The dialog to choose different preferences of the program, e.g the speed of the robot
 * 
 * @author zannc2 & gfels4
 *
 */
public class SearchSettingsDialog extends JDialog 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 56835094942683956L;
	private int choosedSpeed;
	private boolean showGrid;
	private String[] speed = {"25", "50", "100", "200"};
	private JComboBox<String> jcb;
	private JCheckBox checkBoxView;

	/**
	 * Constructor builds the search settings dialog
	 * 
	 * @param f The frame to which this dialog belongs
	 */
	public SearchSettingsDialog(JFrame f, int robotSpeed, boolean showGrid) {
		super(f);

		setTitle("Robotereinstellungen");
		setResizable(false);
		setModal(true);
		setLayout(new BorderLayout());
		choosedSpeed = robotSpeed;
		this.showGrid = showGrid;

		JPanel p = new JPanel(new GridBagLayout());
		add(p, BorderLayout.CENTER);
		JLabel labelSpeed = new JLabel("Robotergeschwindigkeit in Pixel/Sek: ");
		jcb = new JComboBox<>(speed);
		int selectedIndex = 0;
		
		// get the actual speed an set it as default selected
		switch (choosedSpeed) {
		case 40: selectedIndex = 0;
		break;
		case 20: selectedIndex = 1;
		break;
		case 10: selectedIndex = 2;
		break;
		case 5: selectedIndex = 3;
		break;
		}
		jcb.setSelectedIndex(selectedIndex);
		
		// settings for the robot view (user can see what the robot sees or not)
		JLabel labelView = new JLabel("Robotersicht darstellen: ");
		checkBoxView = new JCheckBox();
		checkBoxView.setSelected(showGrid);
		
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
				setShowGrid(checkBoxView.isSelected());
				setVisible(false);
				dispose();	
			}});
		
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gc.insets = new Insets(5, 20, 5, 20);
		p.add(labelSpeed, gc);
		gc.gridx = 1;
		p.add(jcb, gc);

		gc.gridx = 0;
		gc.gridy = 1;
		gc.anchor = GridBagConstraints.EAST;
		p.add(labelView, gc);
		gc.anchor = GridBagConstraints.CENTER;
		gc.gridx = 1;
		p.add(checkBoxView, gc);
		gc.gridx = 0;
		gc.gridy = 2;
		gc.gridwidth = 2;
		p.add(button, gc);
		pack();
	}

	/**
	 * Getter for the new speed of the robot
	 * 
	 * @return the speed which the user has chosen
	 */
	public int getChosenSpeed() {
		return choosedSpeed;
	}

	/**
	 * Getter for the setting if the user can see what the robot sees or not
	 * 
	 * @return true if the robot-view for the user is activated
	 */
	public boolean isShowGrid() {
		return showGrid;
	}

	/**
	 * Change the state of this setting
	 * 
	 * @param showGrid true if the robot view should be enabled
	 */
	private void setShowGrid(boolean showGrid) {
		this.showGrid = showGrid;
	}
}
