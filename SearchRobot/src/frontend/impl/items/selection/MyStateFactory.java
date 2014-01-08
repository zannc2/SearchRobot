package frontend.impl.items.selection;

import frontend.interfaces.StateFactory;

/**
 * Implementation of the StateFactory. It creates the new states. 
 * @author zannc2 & gfells4
 *
 */
public class MyStateFactory implements StateFactory {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4025141794203365232L;

	@Override
	public AbstractState createDragAreaState(SelectionTool context) {
		return new DragArea(context);
	}

	@Override
	public AbstractState createDragHandleState(SelectionTool context) {
		return new DragHandle(context);
	}

	@Override
	public AbstractState createInitState(SelectionTool context) {
		return new Init(context);
	}

	@Override
	public AbstractState createMovingState(SelectionTool context) {
		return new Moving(context);
	}
}
