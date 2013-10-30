package frontend.classes.view;

import helper.Position;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import frontend.classes.items.SelectionTool;
import frontend.interfaces.FieldChangedListener;
import frontend.interfaces.Item;
import frontend.interfaces.Tool;
import frontend.interfaces.View;

public class ViewImpl extends JPanel implements View{

	private Field field = new Field();
	private Tool tool = new SelectionTool(field);
	
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

	public Field getField() {
		return field;
	}	
}


