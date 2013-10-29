package frontend.classes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import frontend.classes.items.CircleTool;
import frontend.classes.items.FinishTool;
import frontend.classes.items.LineTool;
import frontend.classes.items.RobotTool;
import frontend.classes.view.ViewImpl;
import frontend.interfaces.Tool;

public class SearchRobotEditor {

	/** The name of the Programm */
	private static final String PROGRAM_TITLE = "Search Robot";

	private JButton addRobot, addFinish, addLine, addCircle, startButton;
	private JMenuBar menuBar;
	private JMenu fileMenu, editMenu, helpMenu;
	private JMenuItem openMenuItem, saveMenuItem, exitMenuItem, colorMenuItem, helpMenuItem;
	private JPanel mainPanel;
	private ViewImpl view;
	private JToolBar toolBar;
	private ActionListener buttonEvent;
	private JScrollPane scrollPane;
	private List<Tool> tools = new ArrayList<Tool>();
	private JFrame frame;
	private boolean isStarted;

	public SearchRobotEditor() {
		initComponents();
	}

	private void initComponents() {
		frame = new JFrame(PROGRAM_TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLayout(new BorderLayout());
		frame.setResizable(false);

		view = new ViewImpl();


		/*************** JMenu ********************/
		menuBar = new JMenuBar();

		/*********** File Menu *************/
		fileMenu = new JMenu("Datei");
		menuBar.add(fileMenu);

		openMenuItem = new JMenuItem("�ffnen");
		fileMenu.add(openMenuItem);
		openMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				int returnDialog = jfc.showOpenDialog(null);
				File f = jfc.getSelectedFile();
				if(returnDialog == JFileChooser.ERROR_OPTION)
					System.out.println(f.getPath());

				// TODO: Do something with the file

			}
		});
		//TODO: Add action listener	

		saveMenuItem = new JMenuItem("Speichern");
		fileMenu.add(saveMenuItem);
		//TODO: Add action listener

		exitMenuItem = new JMenuItem("Beenden");
		fileMenu.add(exitMenuItem);
		exitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});


		/************ Edit Menu *************/
		editMenu = new JMenu("Bearbeiten");
		menuBar.add(editMenu);

		colorMenuItem = new JMenuItem("Hintergrundfarbe �ndern");
		editMenu.add(colorMenuItem);
		colorMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.setBackground(JColorChooser.showDialog(null,  "W�hle die Farbe", view.getBackground()));
			}
		});
		//TODO: Add Menu Items


		/************ Help Menu *************/
		helpMenu = new JMenu("Hilfe");
		menuBar.add(helpMenu);

		helpMenuItem = new JMenuItem("Hilfe");
		helpMenu.add(helpMenuItem);
		//TODO: Add action listener

		frame.setJMenuBar(menuBar);




		/****************************** Main Panel ***********************/

		mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		/**************** Tool Bar ************************/
		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		buttonEvent = new ButtonEvent();
		addButtons(toolBar);


		/******************* Draw Area *******************/
		scrollPane = new JScrollPane(view);


		gc.gridx = 0;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.fill = GridBagConstraints.HORIZONTAL;
		mainPanel.add(toolBar, gc);


		gc.gridx = 0;
		gc.gridy = 1;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.BOTH;
		mainPanel.add(scrollPane, gc);

		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);

		frame.pack();
		frame.setLocationRelativeTo(null);
	}


	private void addButtons(JToolBar toolBar) {

		// Button Robot
		addRobot = makeNavigationButton("robot", "Start setzen", "Start", new Dimension(40, 40));
		toolBar.add(addRobot);
		tools.add(new RobotTool(view));

		// Button Finish
		addFinish = makeNavigationButton("finish", "Ziel setzen", "Ziel", new Dimension(40, 40));
		toolBar.add(addFinish);
		tools.add(new FinishTool(view));

		// Button Line
		addLine = makeNavigationButton("line", "Hinderniss: Linie", "Linie", new Dimension(40, 40));
		toolBar.add(addLine);
		tools.add(new LineTool(view));

		// Button Circle
		addCircle = makeNavigationButton("circle", "Hinderniss: Kreis", "Kreis", new Dimension(40, 40));
		toolBar.add(addCircle);
		tools.add(new CircleTool(view));

		toolBar.addSeparator(new Dimension(20,0));

		// Button Start
		startButton = makeNavigationButton("search", "Suche starten", "Suche starten", new Dimension(90, 40));
		toolBar.add(startButton);
	}

	protected JButton makeNavigationButton(String imageName,
			String toolTipText,
			String altText,
			Dimension size) {
		//Look for the image.
		String imgLocation = imageName
				+ ".png";
		URL imageURL = getClass().getResource(imgLocation);

		//Create and initialize the button.
		JButton button = new JButton();
		button.setToolTipText(toolTipText);
		button.addActionListener(buttonEvent);

		if (imageURL != null) {                      //image found
			button.setIcon(new ImageIcon(imageURL, altText));
		} else {                                     //no image found
			button.setText(altText);
			System.err.println("Resource not found: " + imgLocation);
		}

		button.setPreferredSize(size);
		return button;
	}

	private class ButtonEvent implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == addRobot)
			{
				System.out.println("Roboter Button");
				view.setTool(tools.get(0));
			}
			else if(e.getSource() == addFinish)
			{
				System.out.println("Ziel Button");
				view.setTool(tools.get(1));
				view.repaint();
			}
			else if(e.getSource() == addLine)
			{
				view.setTool(tools.get(2));
				System.out.println("Linie Tool Button");
			}
			else if(e.getSource() == addCircle)
			{
				System.out.println("Kreis Tool Button");
				view.setTool(tools.get(3));
			}
			else if (e.getSource() == startButton)
			{
				if(isStarted)
				{
					addRobot.setEnabled(true);
					addFinish.setEnabled(true);
					addLine.setEnabled(true);
					addCircle.setEnabled(true);
					isStarted = false;
					startButton.setIcon(new ImageIcon(getClass().getResource("search.png")));
				}
				else
				{
					addRobot.setEnabled(false);
					addFinish.setEnabled(false);
					addLine.setEnabled(false);
					addCircle.setEnabled(false);
					isStarted = true;
					startButton.setIcon(new ImageIcon(getClass().getResource("abort.png")));					
				}
			}
			else
			{
				System.err.println("Wrong ButtonEvent");
			}
		}

	}


	/**
	 * Start of the Programm
	 * @param args
	 */
	public static void main(String[] args) {
		new SearchRobotEditor();
	}	
}
