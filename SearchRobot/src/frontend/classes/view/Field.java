package frontend.classes.view;

import helper.Direction;
import helper.Position;
import helper.Size;
import helper.Vector;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import frontend.classes.items.Robot;
import frontend.interfaces.FieldChangedListener;
import frontend.interfaces.Item;
import frontend.interfaces.ItemChangedListener;
import frontend.interfaces.View;

public class Field implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4010939498931240714L;
	private View view;
	private List<Item> items = new ArrayList<Item>();
	private List<FieldChangedListener> listeners = new ArrayList<FieldChangedListener>();
	private Robot robot;
	
	private FieldItemChangedListener l = new FieldItemChangedListener();
	private final Size robotSize;
	private Size fieldSize;
	
	private class FieldItemChangedListener implements ItemChangedListener {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void itemChanged(ItemChangedEvent e) {
//			System.out.println("ViewItemChangedListener");
			notifyFieldChangedListeners();
		}
		
	}
	
	public Field(Size fieldsize, Size robotSize) {
		this.robotSize = robotSize;
		this.fieldSize = fieldsize;
	}
	/**
	 * Constructor
	 * @param v view
	 * @param robotSize size of the robot
	 */
	public Field(View v, Size fieldsize, Size robotSize) {
		this.view = v;
		this.robotSize = robotSize;
		this.fieldSize = fieldsize;
	}
	
	/**
	 * Returns the view panel
	 * @return view
	 */
	public View getView() {
		return this.view;
	}
	
	/**
	 * Returns a copy of the items on the field
	 * @return unmodifiable copy if items
	 */
	public List<Item> getItems() {
		return Collections.unmodifiableList(items);
	}
	
	/**
	 * Removes an item from the field
	 * @param item the item to remove
	 * @return true if removed successful
	 */
	public boolean removeItem(Item item) {
		notifyFieldChangedListeners();
		if(item instanceof Robot) this.robot = null;
		return items.remove(item);
	}
	
	/**
	 * Adds a new Item to the view
	 * @param item the item to add
	 */
	public void addItem(Item item) {
		this.items.add(item);
		if(this.robot != null) 
		{
			this.items.remove(robot);
			this.items.add(robot);
		}
		item.addItemChangedListener(this.l);
		if(item instanceof Robot) this.robot = (Robot) item;
		notifyFieldChangedListeners();
	}

	/**
	 * Notifier for all registered FieldChangeListeners
	 */
	private void notifyFieldChangedListeners() {
		FieldChangedEvent e = new FieldChangedEvent(this);
		for (FieldChangedListener l : this.listeners) {
			l.fieldChanged(e);
		}
	}
	
	/**
	 * Adds a FieldChangeListener
	 * @param l the listener to add
	 */
	public void addListener(FieldChangedListener l) {
		this.listeners.add(l);
	}
	
	/**
	 * Removes an FieldChangeListener
	 * @param l the listener to remove
	 * @return true if removed successful
	 */
	public boolean removeListener(FieldChangedListener l) {
		return this.listeners.remove(l);
	}
	
	/**
	 * Tests whether an item touches position p 
	 * @param p position to test
	 * @param epsilon 
	 * @return true if an item touches the position p
	 */
	public boolean contains(Position p, int epsilon) {
		for(Item i : this.items) {
			if(i.contains(p, epsilon)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * getter for the robot position
	 * @return returns the robot position
	 */
	public Position getRobotPosition() {
		return robot.getPosition();
	}
	
	/**
	 * Move the robot width a given Vector
	 * @param delta Vector
	 */
	public void moveRobot(Vector delta) {
		if(this.robot != null) this.robot.move(delta);
	}
	
	/**
	 * This method sets the Robot to a new Position
	 * @param p new position of the robt
	 */
	public void setRobotPosition(Position p) {
		if(this.robot != null) this.robot.setPosition(p);
	}

	/**
	 * Getter for the robot size
	 * @return robot size
	 */
	public Size getRobotSize() {
		return robotSize;
	}

	/**
	 * Getter for the robot direction
	 * @return robot direction
	 */
	public Direction getRobotDirection() {
		if (robot != null) return robot.getDirection();
		else return null;
	}
	
	/**
	 * Turns the robot to another direction
	 * @param d new robot direction
	 */
	public void setRobotDirection(Direction d) {
		if(this.robot != null)
		{
			this.robot.setDirection(d);
		}
	}
	
	public Size getFieldSize()
	{
		return fieldSize;
	}
	
	public void setFieldSize(Size s) {
		this.fieldSize = s;
	}
}
