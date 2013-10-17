package frontend.classes.items;

import helper.Position;

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

}
