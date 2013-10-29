package frontend.classes.view;

import helper.Position;
import helper.Vector;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

import frontend.classes.items.Robot;
import frontend.classes.items.SelectionTool;
import frontend.interfaces.Item;
import frontend.interfaces.ItemChangedListener;
import frontend.interfaces.Tool;
import frontend.interfaces.View;

public class ViewImpl extends JPanel implements View{

	private List<Item> items = new ArrayList<Item>();
	private Tool tool = new SelectionTool(this);
	
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
		
		
		setOpaque(false);
	}
	

	@Override
    public void paintComponent(Graphics g) {		
		//System.out.println("Draw");
		for (Item i : this.items) {
			i.draw(g);
		}
    }


	@Override
	public void addItem(Item item) {
		this.items.add(item);
		item.addItemChangedListener(l);
		notifyView();
	}
	
	private void notifyView() {
		repaint();
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
	
	private ViewItemChangedListener l = new ViewItemChangedListener();
	
	private class ViewItemChangedListener implements ItemChangedListener {

		@Override
		public void itemChanged(ItemChangedEvent e) {
			System.out.println("ViewItemChangedListener");
			repaint();
		}
		
	}
	
	private class ViewMouseMotionListener implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			getTool().mouseDrag(new Position(e.getX(), e.getY()));
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			
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

	@Override
	public boolean removeItem(Item i) {
		return items.remove(i);
	}


	public List<Item> getItems() {
		return Collections.unmodifiableList(items);
	}
	
	
}


