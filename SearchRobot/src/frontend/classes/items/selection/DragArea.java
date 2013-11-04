package frontend.classes.items.selection;

import helper.Position;


public class DragArea extends SelectionToolState{

	DragArea(SelectionTool context) {
		super(context);
//		System.out.println("DragArea State");
	}
	

	@Override
	protected void mouseDragEvent(Position p) {
		/**** Drag Area ****/
		setSelectionAreaTo(p);
		adjustSelections();
		setToolState(getNewDragAreaState());
	}
	
	@Override
	protected void mouseUpEvent(Position p) {
		/**** Init ****/
		discardSelectionArea();
		setToolState(getNewInitState());
	}
}
