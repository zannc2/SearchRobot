package frontend.classes.view;

import helper.Position;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import frontend.interfaces.Item;
import frontend.interfaces.Tool;
import frontend.interfaces.View;

public class ViewImpl extends JPanel implements View{

	private List<Item> items = new ArrayList<Item>();
	private Tool tool = null;
	
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
		
		System.out.println("Draw");
		for (Item i : this.items) {
			i.draw(g);
		}
    }


	@Override
	public void addItem(Item item) {
		this.items.add(item);
	}
	
	@Override
	public void setTool(Tool t)
	{
		this.tool = t;
	}
	
	@Override
	public Tool getTool()
	{
		if(tool != null)
			return this.tool;
		return null;
	}
	
	private class ViewMouseMotionListener implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			getTool().mouseDrag(new Position(e.getX(), e.getY()));
			System.out.println("Mouse dragged");
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub
		}
	}
	
	private class ViewMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			getTool().mouseDown(new Position(e.getX(), e.getY()));
			System.out.println("Mouse Down");
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			getTool().mouseUp(new Position(e.getX(), e.getY()));
			System.out.println("Mouse Up");
		}
	
	}

	@Override
	public boolean removeItem(Item i) {
		// TODO Auto-generated method stub
		return false;
	}
}
