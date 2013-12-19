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

	private class ViewFieldChangedListener implements FieldChangedListener {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1790883301541690796L;

		@Override
		public void fieldChanged(FieldChangedEvent e) {
			repaint();
		}

	}

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


	public void setItemColor(Color itemColor) {
		this.itemColor = itemColor;
		repaint();
	}
	
	public Color getItemColor()
	{
		return this.itemColor;
	}

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

	public void setTool(Tool t)
	{
		this.tool = t;
		System.out.println(tool.getClass().toString());
	}

	public Tool getTool()
	{
		return this.tool;
	}

	public void deleteSelectedItems() 
	{
		List<Item> l = getSelection();
		int j = l.size();
		for(int i = 0; i < j; i++)
		{
			field.removeItem(l.get(i));
		}
		clearSelection();
	}

	private class ViewMouseMotionListener implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			getTool().mouseDrag(new Position(e.getX(), e.getY()));
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			getTool().mouseOver(new Position(e.getX(), e.getY()));
		}
	}

	private class ViewMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {


		}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {
			getTool().mouseDown(new Position(e.getX(), e.getY()));
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			getTool().mouseUp(new Position(e.getX(), e.getY()));
		}

	}

	public Field getField() {
		return field;
	}	

	/* Selection*/


	public StateFactory getStateFactory() {
		return new MyStateFactory();
	}

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

	public void removeFromSelection(Item i) {
		this.selection.remove(i);
		for (Iterator<ItemHandler> it = this.handlers.iterator(); it.hasNext();) {
			if (it.next().getOwner() == i) {
				it.remove();
			}
		}
		repaint();
	}

	public List<Item> getSelection() {
		return this.selection;
	}

	public void clearSelection() {
		this.selection.clear();
		this.handlers.clear();
		setCursor(Cursor.getDefaultCursor());
		repaint();
	}

	public List<ItemHandler> getSelectionHandles() {
		return this.handlers;
	}


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


	public void setFieldSize(Size fieldSize)
	{
		this.setPreferredSize(new Dimension(fieldSize.getWidth(), fieldSize.getHeight()));
		this.setMinimumSize(this.getPreferredSize());
		this.setMaximumSize(this.getPreferredSize());
		field.setFieldSize(fieldSize);
	}

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
}

