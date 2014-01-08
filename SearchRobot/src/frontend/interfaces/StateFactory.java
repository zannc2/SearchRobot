package frontend.interfaces;

import java.io.Serializable;

import frontend.impl.items.selection.SelectionTool;
import frontend.impl.items.selection.AbstractState;

/**
 * A factory to create state object for the selection tool
 * @author zannc2 & gfells4
 *
 */
public interface StateFactory extends Serializable {

	/**
	 * Creates and returns the state object used while the user drags on the
	 * area of a sheet.
	 * @param context the SelectionTool
	 * @return created DragArea state
	 */
	public AbstractState createDragAreaState(SelectionTool context);
	
	/**
	 * Creates and returns the state object used while the user drags on the handler
	 * of an Item.
	 * @param context the SelectionTool
	 * @return created DragHandle State
	 */
	public AbstractState createDragHandleState(SelectionTool context);
	
	/**
	 * Creates and returns the state object used while the user starts using
	 * the selection tool. 
	 * @param context the SelectionTool
	 * @return created Init State
	 */
	public AbstractState createInitState(SelectionTool context);
	
	/**
	 * Creates and returns the state object used while the user is moving a selection. 
	 * @param context the SelectionTool
	 * @return created Moving State
	 */
	public AbstractState createMovingState(SelectionTool context);
}
