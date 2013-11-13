package frontend.classes;

import helper.Direction;
import helper.Size;
import helper.Vector;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import robot.classes.FieldMatrix;
import robot.classes.RobotController;
import frontend.classes.items.CircleTool;
import frontend.classes.items.FinishTool;
import frontend.classes.items.LineTool;
import frontend.classes.items.RemoveTool;
import frontend.classes.items.Robot;
import frontend.classes.items.RobotTool;
import frontend.classes.items.selection.SelectionTool;
import frontend.classes.view.Field;
import frontend.classes.view.ViewImpl;
import frontend.interfaces.Item;
import frontend.interfaces.Tool;

public class SearchRobotEditor {

	/** The name of the Programm */
	private static final String PROGRAM_TITLE = "Search Robot";
	private static final Size FIELD_SIZE = new Size(800,500);
	private static final Size ROBOT_SIZE = new Size(10, 10);

	private JButton addRobot, addFinish, addLine, addCircle, startButton, selection, remove;
	private JMenuBar menuBar;
	private JMenu fileMenu, editMenu, helpMenu;
	private JMenuItem openMenuItem, saveMenuItem, exitMenuItem, colorMenuItem, helpMenuItem;
	private JPanel mainPanel;
	private ViewImpl view;
	private JToolBar toolBar;
	private ActionListener buttonEvent;
	private JScrollPane scrollPane;
	private Help help;
	private List<Tool> tools = new ArrayList<Tool>();
	private JFrame frame;
	private boolean isStarted;

	private RobotController robotController;

	public SearchRobotEditor() {
		initComponents();
	}

	private void initComponents() {
		frame = new JFrame(PROGRAM_TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLayout(new BorderLayout());
		frame.setResizable(false);

		view = new ViewImpl(FIELD_SIZE);
		robotController = new RobotController(view);


		/*************** JMenu ********************/
		menuBar = new JMenuBar();

		/*********** File Menu *************/
		fileMenu = new JMenu("Datei");
		menuBar.add(fileMenu);

		openMenuItem = new JMenuItem("Öffnen");
		fileMenu.add(openMenuItem);
		openMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JFileChooser jfc = new JFileChooser();
				int returnDialog = jfc.showOpenDialog(null);
				if(returnDialog == JFileChooser.APPROVE_OPTION)
				{
					File f = jfc.getSelectedFile();
					try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(f))) {

//						view = (ViewImpl) in.readObject();
						view.setField((Field) in.readObject());
						frame.repaint();
						System.out.println("Deserialization succeeded");
						System.out.println();
					}
					catch (Exception ex) {
						System.out.println(ex.getMessage());
						System.out.println("Deserialization failed");
						System.out.println();
					}
				}	
			}
		});

		saveMenuItem = new JMenuItem("Speichern");
		fileMenu.add(saveMenuItem);
		saveMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfc = new JFileChooser();
				int returnDialog = jfc.showSaveDialog(null);
				if(returnDialog == JFileChooser.APPROVE_OPTION)
				{
				System.out.println(jfc.getSelectedFile());
				String s = jfc.getSelectedFile().toString().replaceAll(".robot", "") + ".robot";				
				try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(s))) {
					out.writeObject(view.getField());
					System.out.println("Serialization succeeded");
				} catch (Exception e) {
					System.out.println(e.getMessage());
					System.out.println("Serialization failed");
				}
			}
			}
		});

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

		/* funktioniert nicht mehr :-(
		colorMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.setBackground(JColorChooser.showDialog(null,  "W�hle die Farbe", view.getBackground()));
			}
		});
		 */
		//TODO: Add Menu Items


		/************ Help Menu *************/
		helpMenu = new JMenu("Hilfe");
		menuBar.add(helpMenu);

		helpMenuItem = new JMenuItem("Hilfe");
		helpMenu.add(helpMenuItem);
		helpMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				help = new Help(frame);
				help.setLocationRelativeTo(frame);
				help.setVisible(true);

				// TODO: help class design
			}
		});


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
		view.setTools(tools);


		/******************* Draw Area *******************/
		scrollPane = new JScrollPane(view, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


		gc.gridx = 0;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.fill = GridBagConstraints.HORIZONTAL;
		mainPanel.add(toolBar, gc);


		gc.gridx = 0;
		gc.gridy = 1;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.NONE;
		mainPanel.add(scrollPane, gc);

		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);

		frame.pack();
		frame.setLocationRelativeTo(null);
	}


	private void addButtons(JToolBar toolBar) {

		// Button Selection
		selection = makeNavigationButton("selection", "Auswählen", "Auswählen", new Dimension(40, 40));
		toolBar.add(selection);
		tools.add(new SelectionTool(view.getField()));

		// Button Remove
		remove = makeNavigationButton("remove", "Löschen", "Löschen", new Dimension(40, 40));
		toolBar.add(remove);
		tools.add(new RemoveTool(view.getField()));

		toolBar.addSeparator(new Dimension(10,0));

		// Button Robot
		addRobot = makeNavigationButton("robot", "Start setzen", "Start", new Dimension(40, 40));
		toolBar.add(addRobot);
		tools.add(new RobotTool(view.getField(), ROBOT_SIZE));

		// Button Finish
		addFinish = makeNavigationButton("finish", "Ziel setzen", "Ziel", new Dimension(40, 40));
		toolBar.add(addFinish);
		tools.add(new FinishTool(view.getField()));

		// Button Line
		addLine = makeNavigationButton("line", "Hinderniss: Linie", "Linie", new Dimension(40, 40));
		toolBar.add(addLine);
		tools.add(new LineTool(view.getField()));

		// Button Circle
		addCircle = makeNavigationButton("circle", "Hinderniss: Kreis", "Kreis", new Dimension(40, 40));
		toolBar.add(addCircle);
		tools.add(new CircleTool(view.getField()));

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
		String imgLocation = "resources/" + imageName
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
			if(e.getSource() == selection)
			{
				System.out.println("Selection Button");
				view.setTool(tools.get(0));
			}
			else if(e.getSource() == remove)
			{
				System.out.println("Remove Button");
				view.setTool(tools.get(1));
			}
			else if(e.getSource() == addRobot)
			{
				System.out.println("Roboter Button");
				view.setTool(tools.get(2));
			}
			else if(e.getSource() == addFinish)
			{
				System.out.println("Ziel Button");
				view.setTool(tools.get(3));
			}
			else if(e.getSource() == addLine)
			{
				view.setTool(tools.get(4));
				System.out.println("Linie Tool Button");
			}
			else if(e.getSource() == addCircle)
			{
				System.out.println("Kreis Tool Button");
				view.setTool(tools.get(5));
			}
			else if (e.getSource() == startButton)
			{
				frame.repaint();
				if(isStarted)
				{
					selection.setEnabled(true);
					remove.setEnabled(true);
					addRobot.setEnabled(true);
					addFinish.setEnabled(true);
					addLine.setEnabled(true);
					addCircle.setEnabled(true);
					isStarted = false;
					startButton.setIcon(new ImageIcon(getClass().getResource("resources/search.png")));
					System.out.println("Suche beendet");

					//TODO stop search
				}
				else
				{
					selection.setEnabled(false);
					remove.setEnabled(false);
					addRobot.setEnabled(false);
					addFinish.setEnabled(false);
					addLine.setEnabled(false);
					addCircle.setEnabled(false);
					isStarted = true;
					startButton.setIcon(new ImageIcon(getClass().getResource("resources/abort.png")));
					System.out.println("Suche gestartet");
					
					//TODO werte setzten
					FieldMatrix fieldMatrix = new FieldMatrix(FIELD_SIZE, view.getField());
					robotController.startRobotSearch(new Size(800, 500), view.getField().getRobotPosition(), 
							new Size(10,10), new Vector(1,0), fieldMatrix);
				}
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
