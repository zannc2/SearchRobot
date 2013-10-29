package frontend.classes.items;

import helper.Position;
import helper.Size;
import helper.Vector;

import java.io.File;
import java.util.List;

import frontend.interfaces.Item;
import frontend.interfaces.Item.ItemType;

public class FrontendEntityManager {
	
	private List<Item> items;
	
	public boolean createItem(ItemType type, Position p) {
		if(type.equals(ItemType.LINE)) {
			Item i = new Line(p);
			addItem(i);
			return true;
		}
		else if(type.equals(ItemType.CIRCLE)){
			Item i = new Circle(p);
			addItem(i);
			return true;
		}
		return false;
	}
	
	public boolean addItem(Item item){
		return this.items.add(item);
	}
	
	public boolean removeItem(Item item){
		return this.items.remove(item);
	}
	
	public boolean addToField(Item item) {
		return false;
	}
	
	public Position getRobotPosition() {
		return null;
	}
	
	public Vector getRobotDirection() {
		return null;
	}
	
	public Size getFieldSize() {
		return null;
	}
	
	public boolean deleteAllItems() {
		boolean value = true;
		for(Item i:items) {
			if(value && !removeItem(i)) value = false;
		}
		return value;
	}
	
	//TODO
	public boolean createField(File f) {
		return false;
	}
	
	public List<Item> getItems() {
		return this.items;
	}

}
