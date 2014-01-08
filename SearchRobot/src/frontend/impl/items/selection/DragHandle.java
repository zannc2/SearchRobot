package frontend.impl.items.selection;

import helper.Position;

/**
 * Implementation of the AbstractState. The Drag specifies the action when the user
 * drags an item handler.   
 * @author zannc2 & gfells4
 *
 */
public class DragHandle extends AbstractState {

	private static final long serialVersionUID = 3020215542566739149L;

	/**
	 * Constructor which defines the context of this state. 
	 * @param context the SelectionTool
	 */
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
