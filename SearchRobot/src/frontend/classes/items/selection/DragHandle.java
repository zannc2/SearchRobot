package frontend.classes.items.selection;

import helper.Position;

public class DragHandle extends SelectionToolState{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7061646586902127359L;

	protected DragHandle(SelectionTool context) {
		super(context);
	}

	@Override
	protected void mouseDragEvent(Position p) {
		/**** drag Handle ****/
		getCurrentHandle().dragInteraction(p);
	}
	
	@Override
	protected void mouseUpEvent(Position p) {
		/**** Init ****/
		getCurrentHandle().stopInteraction(p);
		setToolState(getNewInitState());
	}
	
}
