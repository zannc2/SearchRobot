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

	public AbstractState createDragAreaState(SelectionTool context);
	
	public AbstractState createDragHandleState(SelectionTool context);
	
	public AbstractState createInitState(SelectionTool context);
	
	public AbstractState createMovingState(SelectionTool context);
}
