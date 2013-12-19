package frontend.impl.items.selection;

import helper.Position;


public class DragArea extends SelectionToolState{

	/**
	 * 
	 */
	private static final long serialVersionUID = -514474959236110633L;

	DragArea(SelectionTool context) {
		super(context);
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
