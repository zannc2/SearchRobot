package robot.classes;

import helper.Position;
import helper.Vector;

import java.awt.Graphics;
import java.util.List;

import frontend.interfaces.Item;
import frontend.interfaces.ItemChangedListener;
import frontend.interfaces.ItemHandler;

public class Robot implements Item {

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
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
