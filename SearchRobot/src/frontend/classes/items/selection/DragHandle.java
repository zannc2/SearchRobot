package frontend.classes.items.selection;

import helper.Position;

public class DragHandle extends SelectionToolState{

	protected DragHandle(SelectionTool context) {
		super(context);
		System.out.println("DragHandle state");
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
