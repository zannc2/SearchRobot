package frontend.classes.items.selection;

import frontend.interfaces.StateFactory;


public class MyStateFactory implements StateFactory {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3013856088529493895L;

	@Override
	public SelectionToolState createDragAreaState(SelectionTool context) {
		return new DragArea(context);
	}

	@Override
	public SelectionToolState createDragHandleState(SelectionTool context) {
		return new DragHandle(context);
	}

	@Override
	public SelectionToolState createInitState(SelectionTool context) {
		return new Init(context);
	}

	@Override
	public SelectionToolState createMovingState(SelectionTool context) {
		return new Moving(context);
	}
}
