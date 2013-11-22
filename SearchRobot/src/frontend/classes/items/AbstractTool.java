package frontend.classes.items;

import java.util.List;

import frontend.classes.view.Field;
import frontend.interfaces.Item;
import frontend.interfaces.Tool;

public abstract class AbstractTool implements Tool {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8629131851697054206L;
	protected Field field;
	
	public AbstractTool(Field field) {
		this.field = field;
	}

	public Field getField() {
		return this.field;
	}	
	
	public List<Item> getItems() {
		return this.field.getItems();
	}
	
	@Override
	public void setField(Field f)
	{
		this.field = f;
	}
}
