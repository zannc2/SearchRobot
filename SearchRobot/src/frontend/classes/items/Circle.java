package frontend.classes.items;

import helper.Position;
import helper.Size;
import helper.Vector;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import frontend.interfaces.Item;
import frontend.interfaces.ItemChangedListener;
import frontend.interfaces.ItemHandler;


public class Circle implements Item {
	
	private Position position;
	private Size size;
	
	private List<ItemHandler> itemHandler = new ArrayList<ItemHandler>();
	private List<ItemChangedListener> listeners = new ArrayList<ItemChangedListener>();

	public Circle(Position p) {
		this.position = p;
		
		//TODO greate Handler
	}
	
	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawOval(this.position.getOriginX(), this.position.getOriginY(), 
				this.size.getWidth(), this.size.getHeight());
	}

	@Override
	public void setPosition(Position position) {
		this.position = position;
	}

	@Override
	public boolean contains(Position p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ItemHandler> getItemHandler() {
		return this.itemHandler;	//TODO return just a copy
	}

	@Override
	public void addItemChangedListener(ItemChangedListener listener) {
		this.listeners.add(listener);
	}

	@Override
	public boolean removeItemChangedListener(ItemChangedListener listener) {
		return this.listeners.remove(listener);
	}

	@Override
	public void move(Vector delta) {
		/* origin cordinates*/
		int x = this.position.getOriginX();
		int y = this.position.getOriginY();
		
		/* Move */
		x = x + delta.getXComponent();
		y = y + delta.getYComponent();
		
		
		this.position = new Position(x, y);
		
		//TODO
//		notifyShapeChangedListeners();

		//TODO
		/* set handles */
//		Coord c = new Coord(this.bb.getX0(), this.bb.getY0());
//		shapeHandles.get(0).setPosition(c);
//
//		Coord end = new Coord(this.bb.getX0() + this.bb.getWidth(), 
//				this.bb.getY0() + this.bb.getHeight());
//		shapeHandles.get(1).setPosition(end);
	}

	@Override
	public Position getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSize(Size size) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Size getSize() {
		// TODO Auto-generated method stub
		return null;
	}

}
