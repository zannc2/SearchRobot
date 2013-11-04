package frontend.classes.items;

import java.util.List;

import frontend.classes.view.Field;
import frontend.interfaces.Item;
import frontend.interfaces.Tool;
import frontend.interfaces.View;

public abstract class AbstractTool implements Tool {

	private Field field;
	
	public AbstractTool(Field field) {
		this.field = field;
	}

	public Field getField() {
		return this.field;
	}	
	
	public List<Item> getItems() {
		return this.field.getItems();
	}
}
