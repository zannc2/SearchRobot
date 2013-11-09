package frontend.classes.view;

import helper.Position;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import frontend.classes.items.Finish;
import frontend.classes.items.FinishTool;
import frontend.classes.items.Robot;
import frontend.classes.items.RobotTool;
import frontend.classes.items.selection.MyStateFactory;
import frontend.classes.items.selection.SelectionTool;
import frontend.interfaces.FieldChangedListener;
import frontend.interfaces.Item;
import frontend.interfaces.ItemHandler;
import frontend.interfaces.StateFactory;
import frontend.interfaces.Tool;
import frontend.interfaces.View;

public class ViewImpl extends JPanel implements View{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1400303408929046896L;
	private Field field = new Field(this);
	private Tool tool = new SelectionTool(field);
	private List<Tool> tools = new ArrayList<Tool>();
	

	public void setTools(List<Tool> tools) {
		this.tools = tools;
	}
	
	private List<Item> selection = new LinkedList<Item>(); 
	private List<ItemHandler> handlers = new LinkedList<ItemHandler>();
	
	private ViewFieldChangedListener l = new ViewFieldChangedListener();
	
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
		System.out.println("Tool Changed");
		
		this.tool = t;
		System.out.println(tool.getClass().toString());
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
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {
			Tool t = getTool();
			if(t instanceof RobotTool)
			{
				List<Item> l = getField().getItems();
				boolean draw = true;
				for(int i = 0; i < l.size(); i++){
					if(l.get(i) instanceof Robot) draw = false;
				}
				if(draw == true) t.mouseDown(new Position(e.getX(), e.getY()));
			}
			else if(t instanceof FinishTool)
			{
				List<Item> l = getField().getItems();
				boolean draw = true;
				for(int i = 0; i < l.size(); i++){
					if(l.get(i) instanceof Finish) draw = false;
				}
				if(draw == true) t.mouseDown(new Position(e.getX(), e.getY()));
			}
			else{
			t.mouseDown(new Position(e.getX(), e.getY()));
			};
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


	@Override
	public void setField(Field f) {
		this.field = f;
		field.addListener(l);
		for(Tool t: this.tools)
		{
			t.setField(this.field);
		}
	}
}


