package frontend.classes.items.handler;

import helper.Position;
import helper.Size;

import java.awt.Cursor;
import java.awt.Graphics;

import frontend.interfaces.Item;
import frontend.interfaces.ItemHandler;

public class AbstractHandler implements ItemHandler {

	@Override
	public Item getOwner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPosition(Position p) {
		// TODO Auto-generated method stub

	}

	@Override
	public Position getPositioin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Size getSize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub

	}

	@Override
	public Cursor getCursor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void dragInteraction(Position p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void stopInteraction(Position p) {
		// TODO Auto-generated method stub

	}

}
