package frontend.classes.view;

import helper.Position;
import helper.Vector;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import frontend.interfaces.FieldChangedListener;
import frontend.interfaces.Item;
import frontend.interfaces.ItemChangedListener;
import frontend.interfaces.View;

public class Field implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4010939498931240714L;
	private View view;
	private List<Item> items = new ArrayList<Item>();
	private List<FieldChangedListener> listeners = new ArrayList<FieldChangedListener>();
	private Item robotItem;
	
	private FieldItemChangedListener l = new FieldItemChangedListener();
	
	private class FieldItemChangedListener implements ItemChangedListener {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void itemChanged(ItemChangedEvent e) {
//			System.out.println("ViewItemChangedListener");
			notifyFieldChangedListeners();
		}
		
	}
	
	public Field(View v) {
		this.view = v;
	}
	
	public View getView() {
		return this.view;
	}
	
	public List<Item> getItems() {
		return Collections.unmodifiableList(this.items);
	}
	
	public boolean removeItem(Item i) {
		notifyFieldChangedListeners();
		return items.remove(i);
	}
	
	public void addItem(Item item) {
		this.items.add(item);
		item.addItemChangedListener(this.l);
		notifyFieldChangedListeners();
	}

	private void notifyFieldChangedListeners() {
		FieldChangedEvent e = new FieldChangedEvent(this);
		for (FieldChangedListener l : this.listeners) {
			l.fieldChanged(e);
		}
	}
	
	public void addListener(FieldChangedListener l) {
		this.listeners.add(l);
	}
	
	public boolean removeListener(FieldChangedListener l) {
		return this.listeners.remove(l);
	}
	
	public boolean contains(Position p, int epsilon) {
		for(Item i : this.items) {
			if(i.contains(p, epsilon)) {
				return true;
			}
		}
		return false;
	}
	
	public Position getRobotPosition() {
		return this.robotItem.getPosition();
	}

	public void setRoboter(Item item) {
		this.robotItem = item;
	}
	
	public void moveRobotTo(Position p) {
		//TODO
//		Position oldP =this.robotItem.getPosition();
//		int deltaX = p.getOriginX() - oldP.getOriginX();
//		int deltaY = p.getOriginY() - oldP.getOriginY();
//		this.robotItem.move(new Vector(deltaX, deltaY));
	}
	

}
