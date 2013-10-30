package frontend.classes.items;

import frontend.classes.view.Field;
import frontend.interfaces.Tool;

public abstract class AbstractTool implements Tool {

	private Field field;
	
	public AbstractTool(Field field) {
		this.field = field;
	}

	public Field getField() {
		return this.field;
	}	
}
