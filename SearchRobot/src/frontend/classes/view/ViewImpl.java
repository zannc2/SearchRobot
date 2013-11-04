package frontend.classes.view;

import helper.Position;

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

import frontend.classes.items.selection.MyStateFactory;
import frontend.classes.items.selection.SelectionTool;
import frontend.interfaces.FieldChangedListener;
import frontend.interfaces.Item;
import frontend.interfaces.ItemHandler;
import frontend.interfaces.StateFactory;
import frontend.interfaces.Tool;
import frontend.interfaces.View;

public class ViewImpl extends JPanel implements View{

	private Field field = new Field(this);
	private Tool tool = new SelectionTool(field);
	

	private List<Item> selection = new LinkedList<Item>(); 
	private List<ItemHandler> handlers = new LinkedList<ItemHandler>();
	
	private ViewFieldChangedListener l = new ViewFieldChangedListener();
	
	private class ViewFieldChangedListener implements FieldChangedListener {

		@Override
		public void fieldChanged(FieldChangedEvent e) {
			repaint();
		}
		
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ViewImpl() {
		super();
		
		this.setPreferredSize(new Dimension(800, 500));
		this.setMinimumSize(this.getPreferredSize());
		this.setMaximumSize(this.getPreferredSize());
		
		this.addMouseMotionListener(new ViewMouseMotionListener());
		this.addMouseListener(new ViewMouseListener());
		
		field.addListener(l);
		
		setOpaque(false);
	}
	

	@Override
    public void paintComponent(Graphics g) {	
		
		// If there are selections, draw the selection's handles.
		for (ItemHandler h : this.handlers) {
			h.draw(g);
		}
		
		for (Item i : this.field.getItems()) {
			i.draw(g);
		}
    }


	@Override
	public void setTool(Tool t)
	{
		this.tool = t;
	}
	
	@Override
	public Tool getTool()
	{
		return this.tool;
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
		public void mouseEntered(MouseEvent e) {
//			for (Item i : getItems()) {
//				if(i instanceof Robot)
//					i.move(new Vector(2, 1));
//			}
//			repaint();
		}

		@Override
		public void mouseExited(MouseEvent e) {
//			for (Item i : getItems()) {
//				if(i instanceof Robot)
//					i.move(new Vector(2, 1));
//			}
//			repaint();
		}

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
	
	
	@Override
	public StateFactory getStateFactory() {
		return new MyStateFactory();
	}
	
	@Override
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
	
	@Override
	public void removeFromSelection(Item i) {
		this.selection.remove(i);
		for (Iterator<ItemHandler> it = this.handlers.iterator(); it.hasNext();) {
			if (it.next().getOwner() == i) {
				it.remove();
			}
		}
		repaint();
	}
	
	@Override
	public List<Item> getSelection() {
		return this.selection;
	}

	@Override
	public void clearSelection() {
		this.selection.clear();
		this.handlers.clear();
		setCursor(Cursor.getDefaultCursor());
		repaint();
	}
	@Override
	public List<ItemHandler> getSelectionHandles() {
		return this.handlers;
	}
}


