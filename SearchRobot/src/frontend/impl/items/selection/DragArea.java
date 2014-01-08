package frontend.impl.items.selection;

import helper.Position;

/**
 * Implementation of the AbstractState. The DragArea specifies the action when the user
 * for creating the selection area and so adjust the selection of items.  
 * @author zannc2 & gfells4
 *
 */
public class DragArea extends AbstractState{
	
	private static final long serialVersionUID = -514474959236110633L;

	/**
	 * Constructor which defines the context of this state. 
	 * @param context the SelectionTool
	 */
	DragArea(SelectionTool context) {
		super(context);
	}
	

	@Override
	protected void mouseDragEvent(Position p) {
		/**** Drag Area ****/
		setSelectionAreaTo(p);
		adjustSelections();
	}
	
	@Override
	protected void mouseUpEvent(Position p) {
		/**** Init ****/
		discardSelectionArea();
		setToolState(getNewInitState());
	}
}
