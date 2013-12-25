package frontend.impl.items.selection;

import helper.Position;

public class Moving extends AbstractState{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6697433763386223150L;

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
