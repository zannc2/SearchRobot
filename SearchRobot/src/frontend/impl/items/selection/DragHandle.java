package frontend.impl.items.selection;

import helper.Position;

public class DragHandle extends SelectionToolState {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3020215542566739149L;

	protected DragHandle(SelectionTool context) {
		super(context);
	}
	
	@Override
	protected void mouseDownEvent(Position p) {
		getCurrentHandle().startInteraction(p);
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
