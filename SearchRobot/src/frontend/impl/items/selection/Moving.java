package frontend.impl.items.selection;

import helper.Position;

/**
 * Implementation of the AbstractState. The Moving specifies the action when the user
 * moving a selection. 
 * @author zannc2 & gfells4
 *
 */
public class Moving extends AbstractState{

	private static final long serialVersionUID = 6697433763386223150L;

	/**
	 * Constructor which defines the context of this state. 
	 * @param context the SelectionTool
	 */
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
