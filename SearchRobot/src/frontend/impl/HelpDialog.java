package frontend.impl;

import helper.Size;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

/**
 * The dialog for the help, which reaches the user over the menu -> help
 * 
 * @author zannc2 & gfels4
 *
 */
public class HelpDialog extends JDialog 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 56835094942683956L;
	private Size choosedSize;
	private Image finish, selection, line, circle, robot, remove;
	private JPanel picPanel;
	
	/**
	 * Constructor builds the help dialog
	 * 
	 * @param f The frame to which this dialog belongs
	 */
	public HelpDialog(JFrame f) {
		super(f);

		setTitle("Hilfe");
		setResizable(false);
		setModal(true);
		setLayout(new BorderLayout());
		setSize(new Dimension(600, 400));

		JPanel mainPanel = new JPanel(new GridBagLayout());
		mainPanel.setBorder(BorderFactory.createTitledBorder("Anleitung Search Robot"));
		JScrollPane jsp = new JScrollPane(mainPanel);
		add(jsp, BorderLayout.CENTER);
		JTextArea jta = new JTextArea("Sie müssen auf dem Spielfeld einen Roboter, ein Ziel und beliebig viele Hindernisse setzen. "
				+ "Sobald Sie das gemacht haben, können Sie auf  'Suche starten' drücken und der Roboter wird "
				+ "versuchen das Ziel zu finden.");
		jta.setLineWrap(true);
		jta.setWrapStyleWord(true);
		jta.setOpaque(false);
		
		try {
			finish = ImageIO.read(getClass().getResource("/finish.png"));
			selection = ImageIO.read(getClass().getResource("/selection.png"));
			line = ImageIO.read(getClass().getResource("/line.png"));
			robot = ImageIO.read(getClass().getResource("/robot.png"));
			remove = ImageIO.read(getClass().getResource("/remove.png"));
			circle = ImageIO.read(getClass().getResource("/circle.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 2;
		gc.insets = new Insets(1,5,20,5);
		gc.anchor = GridBagConstraints.NORTHWEST;
		gc.fill = GridBagConstraints.HORIZONTAL;
		mainPanel.add(jta, gc);
		
		gc.gridy = 1;
		JSeparator js = new JSeparator(JSeparator.HORIZONTAL);
		mainPanel.add(js, gc);
		
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(1,1,10,5);
		gc.gridwidth = 1;
		gc.gridy = 2;
		mainPanel.add(new JLabel(new ImageIcon(selection)),gc);
		gc.gridx = 1;
		JTextArea selectionA = new JTextArea("Mit dem Selection-Tool können Sie ein oder mehrere Objekte auswählen, ihre Grösse verändern oder "
				+ "auch verschieben!");
		selectionA.setLineWrap(true);
		selectionA.setWrapStyleWord(true);
		selectionA.setOpaque(false);
		mainPanel.add(selectionA, gc);
		
		gc.gridy = 3;
		gc.gridx = 0;
		mainPanel.add(new JLabel(new ImageIcon(remove)), gc);
		gc.gridx = 1;
		JTextArea selectionRm = new JTextArea("Mit dem Remove-Tool können Sie das Ziel, den Roboter oder gezeichnete Hindernisse wieder löschen.");
		selectionRm.setLineWrap(true);
		selectionRm.setWrapStyleWord(true);
		selectionRm.setOpaque(false);
		mainPanel.add(selectionRm, gc);
		
		gc.gridy = 4;
		gc.gridx = 0;
		mainPanel.add(new JLabel(new ImageIcon(line)), gc);
		gc.gridx = 1;
		JTextArea selectionL = new JTextArea("Mit dem Line-Tool können Sie Linien zeichnen, welche als Hindernisse für den Roboter dienen.");
		selectionL.setLineWrap(true);
		selectionL.setWrapStyleWord(true);
		selectionL.setOpaque(false);
		mainPanel.add(selectionL, gc);
		
		gc.gridy = 5;
		gc.gridx = 0;
		mainPanel.add(new JLabel(new ImageIcon(circle)), gc);
		gc.gridx = 1;
		JTextArea selectionC = new JTextArea("Mit dem Circle-Tool können Sie Kreise zeichnen, welche als Hindernisse für den Roboter dienen.");
		selectionC.setLineWrap(true);
		selectionC.setWrapStyleWord(true);
		selectionC.setOpaque(false);
		mainPanel.add(selectionC, gc);
		
		gc.gridy = 6;
		gc.gridx = 0;
		mainPanel.add(new JLabel(new ImageIcon(robot)), gc);
		gc.gridx = 1;
		JTextArea selectionR = new JTextArea("Mit dem Robot-Tool können Sie den Roboter auf der Spielfläche platzieren, diese Position gilt dann als"
				+ " Startpunkt für die Suche.");
		selectionR.setLineWrap(true);
		selectionR.setWrapStyleWord(true);
		selectionR.setOpaque(false);
		mainPanel.add(selectionR, gc);
		
		gc.gridy = 7;
		gc.gridx = 0;
		mainPanel.add(new JLabel(new ImageIcon(finish)), gc);
		gc.gridx = 1;
		gc.weightx = 1;
		JTextArea selectionF = new JTextArea("Mit dem Finish-Tool können Sie das Ziel auf der Spielfläche platzieren, welches der Roboter dann"
				+ " finden muss.");
		selectionF.setLineWrap(true);
		selectionF.setWrapStyleWord(true);
		selectionF.setOpaque(false);
		mainPanel.add(selectionF, gc);
		}

		public Size getChoosedSize() {
			return choosedSize;
		}
	}
