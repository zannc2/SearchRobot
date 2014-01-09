package frontend.impl.items;

import java.util.List;

import frontend.impl.view.Field;
import frontend.interfaces.Item;
import frontend.interfaces.Tool;

/**
 * Implements the common methods and variables of all tools
 * @author zannc2 & gfels4
 *
 */
public abstract class AbstractTool implements Tool {

	private static final long serialVersionUID = -7143795048863567172L;
	
	private Field field;
	
	/**
	 * Constructor
	 * 
	 * @param field the field which contains to this tool
	 */
	public AbstractTool(Field field) {
		this.field = field;
	}

	/**
	 * Getter for the Field
	 * 
	 * @return the field which contains to this tool
	 */
	public Field getField() {
		return this.field;
	}	
	
	/**
	 * Returns a List of all items on the field
	 * 
	 * @return list with items
	 */
	public List<Item> getItems() {
		return this.field.getItems();
	}
}
