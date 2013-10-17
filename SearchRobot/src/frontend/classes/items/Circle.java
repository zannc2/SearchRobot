package frontend.classes.items;

import helper.Position;
import helper.Size;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import frontend.interfaces.Item;
import frontend.interfaces.ItemChangedListener;
import frontend.interfaces.ItemHandler;


public class Circle implements Item {
	
	private Position position;
	private Size size;
	
	private List<ItemHandler> itemHandler;
	private List<ItemChangedListener> listeners;

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
	public void move(Vector delta) {
		// TODO Auto-generated method stub
		
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

}
