package frontend.impl;

import frontend.impl.items.CircleTool;
import frontend.impl.items.FinishTool;
import frontend.impl.items.LineTool;
import frontend.impl.items.RemoveTool;
import frontend.impl.items.RobotTool;
import frontend.impl.items.selection.SelectionTool;
import frontend.impl.view.View;
import frontend.interfaces.Item;
import frontend.interfaces.Tool;
import helper.Size;

import java.awt.BorderLayout;
import java.awt.Color;
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

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.UIManager;

import robot.impl.RobotController;

public class SearchRobotEditor {

	/** The title of the Programm */
	private final String PROGRAM_TITLE = "Search Robot";
	/** The initial size of the field */
	private Size field_size = new Size(800, 500);
	/** The robot size */
	private final Size ROBOT_SIZE = new Size(10, 10);
	
	/** The initial size of the field */
	private int robotSpeed = 10;
	private JButton addRobot, addFinish, addLine, addCircle, startButton, selection, remove;
	private JMenuBar menuBar;
	private JMenu fileMenu, editMenu, helpMenu;
	private JMenuItem newMenuItem, openMenuItem, saveMenuItem, exitMenuItem, helpMenuItem, bgColorMenuItem, itemColorMenuItem, robotSpeedMenuItem;
	private JPanel mainPanel;
	private View view;
	private JToolBar toolBar;
	private ActionListener buttonEvent;
	private List<Tool> tools = new ArrayList<Tool>();
	private JFrame frame;
	private boolean isStarted, showGrid;
	private RobotController src;
	private List<Item> items;
	private JLabel jl;
	private final Color BUTTON_COLOR = new Color(238,238,238);
	private final Color BUTTON_PRESSED_COLOR = Color.LIGHT_GRAY;

	/**
	 * Constructor of the Editor, it will initialize the components
	 */
	public SearchRobotEditor() {
		initComponents();
	}

	/**
	 * Initializes all the components for this program
	 */
	private void initComponents() {
		frame = new JFrame(PROGRAM_TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLayout(new BorderLayout());
		frame.setResizable(false);


		/*************** JMenu ********************/
		menuBar = new JMenuBar();

		/*********** File Menu *************/
		fileMenu = new JMenu("Datei");
		menuBar.add(fileMenu);

		newMenuItem = new JMenuItem("Neu");
		fileMenu.add(newMenuItem);
		newMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				NewDialog neu = new NewDialog(frame);
				neu.setLocationRelativeTo(frame);
				neu.setVisible(true);
				Size newFieldSize = neu.getChosenSize();

				if(newFieldSize != null)
				{
					field_size = newFieldSize;
					view.setField(null, newFieldSize);
				}
				setJLabelText();
				frame.pack();
			}
		});

		openMenuItem = new JMenuItem("Öffnen");
		fileMenu.add(openMenuItem);
		openMenuItem.addActionListener(new ActionListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent e) {

				JFileChooser jfc = new JFileChooser();
				boolean done = false;
				while(!done)
				{
					int returnDialog = jfc.showOpenDialog(null);
					if(returnDialog == JFileChooser.APPROVE_OPTION)
					{
						File f = jfc.getSelectedFile();
						try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(f))) {
							Size fieldSize = (Size) in.readObject();
							view.setField((List<Item>) in.readObject(), fieldSize);
							field_size = fieldSize;
							frame.pack();
							setJLabelText();
							done = true;
						}
						catch (Exception ex) {
							System.out.println(ex.getMessage());
							System.out.println("Deserialization failed");
							JOptionPane.showMessageDialog(null, "Bitte eine korrekte *.robot Datei auswählen!");
						}
					}
					else
					{
						done = true;
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
					String s = jfc.getSelectedFile().toString().replaceAll("\\.robot", "") + ".robot";				
					try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(s))) {
						out.writeObject(field_size);
						out.writeObject(view.getField().getItems());
					} catch (Exception e) {
						System.out.println(e.getMessage());
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

		bgColorMenuItem = new JMenuItem("Hintergrundfarbe");
		editMenu.add(bgColorMenuItem);
		bgColorMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				view.setBackground(JColorChooser.showDialog(null,  "Wähle die Farbe", view.getBackground()));
			}
		});

		itemColorMenuItem = new JMenuItem("Hindernissfarbe");
		editMenu.add(itemColorMenuItem);		
		itemColorMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				view.setItemColor(JColorChooser.showDialog(null,  "Wähle die Farbe", view.getBackground()));
			}
		});

		robotSpeedMenuItem = new JMenuItem("Einstellungen");
		editMenu.add(robotSpeedMenuItem);		
		robotSpeedMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SearchSettingsDialog sd = new SearchSettingsDialog(frame, robotSpeed, showGrid);
				sd.setLocationRelativeTo(frame);
				sd.setVisible(true);
				
				robotSpeed = sd.getChosenSpeed();
				showGrid = sd.isShowGrid();
				setJLabelText();
			}
		});


		/************ Help Menu *************/
		helpMenu = new JMenu("Hilfe");
		menuBar.add(helpMenu);

		helpMenuItem = new JMenuItem("Hilfe");
		helpMenu.add(helpMenuItem);
		helpMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				HelpDialog help = new HelpDialog(frame);
				help.setLocationRelativeTo(frame);
				help.setVisible(true);
			}
		});
		frame.setJMenuBar(menuBar);

		/****************************** Main Panel ***********************/
		// Create draw panel
		view = new View(field_size, ROBOT_SIZE, Color.BLACK);
		view.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		/**************** Tool Bar ************************/
		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		buttonEvent = new ButtonEvent();
		addButtons(toolBar);
		toolBar.addSeparator();
		jl = new JLabel();
		setJLabelText();		
		toolBar.add(jl);

		/******************* Draw Area *******************/
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
		mainPanel.add(view, gc);

		
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		frame.validate();
		frame.pack();
		frame.setLocationRelativeTo(null);
	}


	/**
	 * Creates and adds the tool buttons
	 * 
	 * @param toolBar The toolbar component
	 */
	private void addButtons(JToolBar toolBar) {

		// Button Selection
		selection = makeNavigationButton("selection", "Auswählen", "Auswählen", new Dimension(40, 40));
		toolBar.add(selection);
		selection.setBackground(BUTTON_COLOR);
		tools.add(new SelectionTool(view.getField()));

		// Button Remove
		remove = makeNavigationButton("remove", "Löschen", "Löschen", new Dimension(40, 40));
		toolBar.add(remove);
		remove.setBackground(BUTTON_COLOR);
		tools.add(new RemoveTool(view.getField()));

		toolBar.addSeparator(new Dimension(10,0));

		// Button Robot
		addRobot = makeNavigationButton("robot", "Start setzen", "Start", new Dimension(40, 40));
		toolBar.add(addRobot);
		addRobot.setBackground(BUTTON_COLOR);
		tools.add(new RobotTool(view.getField(), ROBOT_SIZE));

		// Button Finish
		addFinish = makeNavigationButton("finish", "Ziel setzen", "Ziel", new Dimension(40, 40));
		toolBar.add(addFinish);
		addFinish.setBackground(BUTTON_COLOR);
		tools.add(new FinishTool(view.getField()));

		// Button Line
		addLine = makeNavigationButton("line", "Hinderniss: Linie", "Linie", new Dimension(40, 40));
		toolBar.add(addLine);
		addLine.setBackground(BUTTON_COLOR);
		tools.add(new LineTool(view.getField()));

		// Button Circle
		addCircle = makeNavigationButton("circle", "Hinderniss: Kreis", "Kreis", new Dimension(40, 40));
		toolBar.add(addCircle);
		addCircle.setBackground(BUTTON_COLOR);
		tools.add(new CircleTool(view.getField()));
		toolBar.addSeparator(new Dimension(20,0));

		// Button Start
		startButton = makeNavigationButton("search", "Suche starten", "Suche starten", new Dimension(90, 40));
		startButton.setBackground(BUTTON_COLOR);
		toolBar.add(startButton);
	}

	/**
	 * Creates the tool buttons
	 * 
	 * @param imageName Name of the button image
	 * @param toolTipText Tool tip text
	 * @param altText Alternative text if the resource could not be loaded
	 * @param size Dimension of the button
	 * @return The created button
	 */
	private JButton makeNavigationButton(String imageName,
			String toolTipText,
			String altText,
			Dimension size) {
		//Look for the image.
		String imgLocation = "/" + imageName
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

	/**
	 * This class manages all the button events
	 * 
	 * @author zannc2 & gfels4
	 *
	 */
	private class ButtonEvent implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			view.clearSelection();
			if(e.getSource() == selection)
			{
				setSelected(0);
				view.setTool(tools.get(0));
			}
			else if(e.getSource() == remove)
			{
				setSelected(1);
				view.setTool(tools.get(1));
			}
			else if(e.getSource() == addRobot)
			{
				setSelected(2);
				view.setTool(tools.get(2));
			}
			else if(e.getSource() == addFinish)
			{
				setSelected(3);
				view.setTool(tools.get(3));
			}
			else if(e.getSource() == addLine)
			{
				view.setTool(tools.get(4));
				setSelected(4);
			}
			else if(e.getSource() == addCircle)
			{
				setSelected(5);
				view.setTool(tools.get(5));
			}
			else if (e.getSource() == startButton)
			{
				if(isStarted)
				{
					stopSearch();
				}
				else 
				{
					startSearch();
				}
			}
		}

		/**
		 * Changes the color of the selected button
		 * 
		 * @param selected The number of the button which should be selected
		 */
		private void setSelected(int selected)
		{
			addRobot.setBackground(BUTTON_COLOR);			
			addFinish.setBackground(BUTTON_COLOR);
			addLine.setBackground(BUTTON_COLOR);
			addCircle.setBackground(BUTTON_COLOR);
			selection.setBackground(BUTTON_COLOR);
			remove.setBackground(BUTTON_COLOR);
			
			switch (selected) {
			case 0:
				selection.setBackground(BUTTON_PRESSED_COLOR);
				break;
			case 1:
				remove.setBackground(BUTTON_PRESSED_COLOR);
				break;
			case 2:
				addRobot.setBackground(BUTTON_PRESSED_COLOR);
				break;
			case 3:
				addFinish.setBackground(BUTTON_PRESSED_COLOR);
				break;
			case 4:
				addLine.setBackground(BUTTON_PRESSED_COLOR);
				break;
			case 5:
				addCircle.setBackground(BUTTON_PRESSED_COLOR);
				break;
			}
		}
	}


	/**
	 * Stops the robot search
	 */
	public void stopSearch(){

		if(isStarted)
		{
			if(src != null)
			{
				src.stop();
			}

			selection.setEnabled(true);
			remove.setEnabled(true);
			addRobot.setEnabled(true);
			addFinish.setEnabled(true);
			addLine.setEnabled(true);
			addCircle.setEnabled(true);
			newMenuItem.setEnabled(true);
			openMenuItem.setEnabled(true);
			saveMenuItem.setEnabled(true);
			robotSpeedMenuItem.setEnabled(true);
			startButton.setIcon(new ImageIcon(getClass().getResource("/search.png")));

			view.setField(items, field_size);

			isStarted = false;
		}
	}

	/**
	 * Starts the robot search
	 */
	public void startSearch()
	{
		if(view.hasRobotAndFinish() && !isStarted)
		{
			items = new ArrayList<>(view.getField().getItems());
			selection.setEnabled(false);
			remove.setEnabled(false);
			addRobot.setEnabled(false);
			addFinish.setEnabled(false);
			addLine.setEnabled(false);
			addCircle.setEnabled(false);
			newMenuItem.setEnabled(false);
			openMenuItem.setEnabled(false);
			saveMenuItem.setEnabled(false);
			robotSpeedMenuItem.setEnabled(false);

			startButton.setIcon(new ImageIcon(getClass().getResource("/abort.png")));
			src = new RobotController(this, view.getField(), robotSpeed);
			src.start();
			isStarted = true;
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Bitte Roboter und Ziel setzen!");
		}
	}

	/**
	 * Creates the text on the top with the field size and the robot speed
	 */
	private void setJLabelText()
	{
		jl.setText("Zeichenfläche: " + field_size.getWidth()+ " x " + field_size.getHeight() + " / Robotergeschwindigkeit: " + 1000 / robotSpeed + " Pixel/Sekunde");
	}

	/**
	 * Start of the Programm
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
		    UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
		 } catch (Exception e) {
		            e.printStackTrace();
		 }
		
		new SearchRobotEditor();
	}

	/**
	 * Returns if the user can see what the robot sees or not (the robot view setting)
	 * 
	 * @return true if the robot view is enabled
	 */
	public boolean isShowGrid() {
		return showGrid;
	}	
}
