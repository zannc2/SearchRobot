package frontend.impl.view;

import helper.Position;
import helper.Size;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import frontend.impl.items.Finish;
import frontend.impl.items.Robot;
import frontend.impl.items.selection.MyStateFactory;
import frontend.impl.items.selection.SelectionTool;
import frontend.interfaces.FieldChangedListener;
import frontend.interfaces.Item;
import frontend.interfaces.ItemHandler;
import frontend.interfaces.StateFactory;
import frontend.interfaces.Tool;

/**
 * This Class extends JPanel and controls the mouse events to draw, select or remove items.
 * If the user clicks or releases the mouse button or moves the mouse over this panel, 
 * the events would be redirected to the selected tool. (e.g. a mouse click if the line tool
 * is selected would create a new line, move would resize the line and release fix the
 * size and position of the line)
 * 
 * If items were deleted, added or changed, this class will be informed and repaints
 * 
 * @author zannc2 & gfels4
 *
 */
public class View extends JPanel {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -317820392042833100L;
	
	private Field field;
	private Tool tool;
	private List<Item> selection = new LinkedList<Item>(); 
	private List<ItemHandler> handlers = new LinkedList<ItemHandler>();
	private ViewFieldChangedListener l = new ViewFieldChangedListener();
	private Color itemColor;
	private boolean started;

	/**
	 * Inner class to handle changes on the field
	 * 
	 * @author zannc2 & gfels4
	 *
	 */
	private class ViewFieldChangedListener implements FieldChangedListener {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1790883301541690796L;
		
		/**
		 * Repaints the view
		 */
		@Override
		public void fieldChanged(FieldChangedEvent e) {
			repaint();
		}

	}

	/**
	 * Constructor of the View class, generates a new field and adds the needed listeners
	 * 
	 * @param fieldSize	The field size
	 * @param robotSize	The robot size
	 * @param itemColor	The item color
	 */
	public View(Size fieldSize, Size robotSize, Color itemColor) {
		super();
		field = new Field(this, fieldSize, robotSize);
		field.addListener(l);
		tool = new SelectionTool(field);
		this.itemColor = itemColor;

		this.setPreferredSize(new Dimension(fieldSize.getWidth(), fieldSize.getHeight()));
		this.setMinimumSize(this.getPreferredSize());
		this.setMaximumSize(this.getPreferredSize());
		this.addMouseMotionListener(new ViewMouseMotionListener());
		this.addMouseListener(new ViewMouseListener());
	}

	/**¨
	 * Setter for a new item color
	 * 
	 * @param itemColor The new item color
	 */
	public void setItemColor(Color itemColor) {
		this.itemColor = itemColor;
		repaint();
	}
	
	/**
	 * Getter for the item color
	 * 
	 * @return Returns the item color
	 */
	public Color getItemColor()
	{
		return this.itemColor;
	}

	/**
	 * Override of the paintComponent method to draw the items on the field
	 */
	@Override
	public void paintComponent(Graphics g) {	
		super.paintComponent(g);
		g.setColor(getItemColor());
		// If there are selections, draw the selection's handles.
		for (ItemHandler h : this.handlers) {
			h.draw(g);
		}
		for(int i = 0; i < this.field.getItems().size(); i++)
		{
			this.field.getItems().get(i).draw(g);
		}
	}

	/**
	 * Setter for the selected tool
	 * 
	 * @param t The selected tool
	 */
	public void setTool(Tool t)
	{
		this.tool = t;
	}

	/**
	 * Getter for the currently selected tool 
	 * 
	 * @return	The selected tool
	 */
	public Tool getTool()
	{
		return this.tool;
	}
	
	/**
	 * Mouse motion listener adapter
	 * 
	 * @author gfels4 & zannc1
	 */
	private class ViewMouseMotionListener implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			if(!isStarted()) getTool().mouseDrag(new Position(e.getX(), e.getY()));
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if(!isStarted()) getTool().mouseOver(new Position(e.getX(), e.getY()));
		}
	}

	/**
	 * Mouse listener adapter
	 * 
	 * @author gfels4 & zannc1
	 *
	 */
	private class ViewMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {
			if(!isStarted()) getTool().mouseDown(new Position(e.getX(), e.getY()));
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if(!isStarted()) getTool().mouseUp(new Position(e.getX(), e.getY()));
		}

	}

	/**
	 * Getter for the field
	 * 
	 * @return The field
	 */
	public Field getField() {
		return field;
	}	

	/**
	 * Creates a new state factory
	 * 
	 * @return A new StateFactory
	 */
	public StateFactory getStateFactory() {
		return new MyStateFactory();
	}

	/**
	 * Adds an item to the selection and repaints
	 * 
	 * @param i The item to add
	 */
	public void addToSelection(Item i) {
		if (!this.selection.contains(i)) {
			this.selection.add(i);
			List<ItemHandler> hList = i.getItemHandler();
			if (hList != null) {
				this.handlers.addAll(hList);
			}
		}
		repaint();
	}

	/**
	 * Removes an item from the selection and repaints
	 * 
	 * @param i The item to remove
	 */
	public void removeFromSelection(Item i) {
		this.selection.remove(i);
		for (Iterator<ItemHandler> it = this.handlers.iterator(); it.hasNext();) {
			if (it.next().getOwner() == i) {
				it.remove();
			}
		}
		repaint();
	}

	/**
	 * Returns a list with the currently selected items
	 * 
	 * @return List of items
	 */
	public List<Item> getSelection() {
		return this.selection;
	}

	/**
	 * Clears the current selection, without deleting the items
	 */
	public void clearSelection() {
		this.selection.clear();
		this.handlers.clear();
		setCursor(Cursor.getDefaultCursor());
		repaint();
	}

	/**
	 * Returns the handlers of the current selected items
	 * 
	 * @return List of handlers
	 */
	public List<ItemHandler> getSelectionHandles() {
		return this.handlers;
	}

	/**
	 * Removes all items from the field and adds the items frotm the param itemList.
	 * This method is used to load a new field
	 * 
	 * @param itemList	Items to add t the field
	 * @param fieldSize	New field size
	 */
	public void setField(List<Item> itemList, Size fieldSize) { 
		List<Item> l = field.getItems();
		int j = l.size();
		for(int i = 0; i < j; i++)
		{
			field.removeItem(l.get(0));
		}

		if(itemList != null)
		{
			for(Item i: itemList)
			{
				field.addItem(i);
			}
		}
		setFieldSize(fieldSize);
		repaint();
	}

	/**
	 * Changes the field size
	 * 
	 * @param fieldSize New field size
	 */
	public void setFieldSize(Size fieldSize)
	{
		this.setPreferredSize(new Dimension(fieldSize.getWidth(), fieldSize.getHeight()));
		this.setMinimumSize(this.getPreferredSize());
		this.setMaximumSize(this.getPreferredSize());
		field.setFieldSize(fieldSize);
	}

	/**
	 * Checks if the field contains a robot and a finish
	 * 
	 * @return true if a robot and a finish exists
	 */
	public boolean hasRobotAndFinish()
	{
		List<Item> l = getField().getItems();
		boolean robot = false;
		boolean finish = false;

		for(int i = 0; i < l.size(); i++){
			Item item = l.get(i);
			if(item instanceof Robot) robot = true;
			if(item instanceof Finish) finish = true;
		}

		if(robot && finish) return true;
		else return false;
	}

	public void setStarted(boolean b) {
				started = b;
	}
	
	public boolean isStarted()
	{
		return started;
	}
}

