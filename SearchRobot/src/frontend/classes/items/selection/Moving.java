package frontend.classes.items.selection;

import helper.Position;

public class Moving extends SelectionToolState{

	/**
	 * 
	 */
	private static final long serialVersionUID = 275781069975424852L;

	protected Moving(SelectionTool context) {
		super(context);
		System.out.println("moving State");
	}

	@Override
	protected void mouseDragEvent(Position p) {
		/**** Moving ****/
		moveSelectedShapes(p);
	}
	
	@Override
	protected void mouseUpEvent(Position p) {
		/**** Init ****/
		moveSelectedShapes(p);
		setDefaultCursor();
		setToolState(getNewInitState());
	}

}
