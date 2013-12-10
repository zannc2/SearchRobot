package frontend.impl.items.selection;

import helper.Position;

import java.util.HashMap;
import java.util.Map;

public class Moving extends SelectionToolState{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 275781069975424852L;

	protected Moving(SelectionTool context) {
		super(context);
	}

	@Override
	protected void mouseDragEvent(Position p) {
		/**** Moving ****/
		moveSelectedShapes(p);
	}
	
	@Override
	protected void mouseUpEvent(Position p) {
		/**** Init ****/
		endMoveSelectedShapes(p);
		setDefaultCursor();
		setToolState(getNewInitState());
	}

}
