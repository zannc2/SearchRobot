package frontend.impl.items;

import java.util.List;

import frontend.impl.view.Field;
import frontend.interfaces.Item;
import frontend.interfaces.Tool;

/**
 * Implements the common methods and variables of all tools
 * @author zannc2 & gfells4
 *
 */
public abstract class AbstractTool implements Tool {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7143795048863567172L;
	
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
