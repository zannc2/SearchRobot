package frontend.interfaces;

import java.io.Serializable;

import frontend.classes.items.selection.SelectionTool;
import frontend.classes.items.selection.SelectionToolState;

public interface StateFactory extends Serializable{

	public SelectionToolState createDragAreaState(SelectionTool context);
	
	public SelectionToolState createDragHandleState(SelectionTool context);
	
	public SelectionToolState createInitState(SelectionTool context);
	
	public SelectionToolState createMovingState(SelectionTool context);
}
