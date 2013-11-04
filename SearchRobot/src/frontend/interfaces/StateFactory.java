package frontend.interfaces;

import frontend.classes.items.selection.SelectionTool;
import frontend.classes.items.selection.SelectionToolState;

public interface StateFactory {

	public SelectionToolState createDragAreaState(SelectionTool context);
	
	public SelectionToolState createDragHandleState(SelectionTool context);
	
	public SelectionToolState createInitState(SelectionTool context);
	
	public SelectionToolState createMovingState(SelectionTool context);
}
