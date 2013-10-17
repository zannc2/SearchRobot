package frontend.classes.items;

import helper.Position;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import java.util.Vector;

import frontend.interfaces.Item;
import frontend.interfaces.ItemChangedListener;
import frontend.interfaces.ItemHandler;


public class Line implements Item {
	
	public Line(Position p) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawLine(this.position.getOriginX(), this.position.getOriginY(), 
				this.size.getWidth(), this.size.getHeight());
	}

	@Override
	public void setPosition(Position position) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addItemChangedListener(ItemChangedListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean removeItemChangedListener(ItemChangedListener listener) {
		// TODO Auto-generated method stub
		return false;
	}

}
