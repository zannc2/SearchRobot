package frontend.classes.items.selection;

import helper.Position;

import java.awt.Cursor;

public class Init extends SelectionToolState{

	
	protected Init(SelectionTool context) {
		super(context);
		System.out.println("Init State");
	}

	@Override
	protected void mouseDownEvent(Position p) {
		if(isOnUnselectedItem(p)) {
			/**** moving ****/
			clearSelection();
			addToSelection(getItemByCoord(p));
			setMoveCursor();
			setToolState(getNewMovingState());
		}
		else if(isOnItemHandle(p)) {
			/**** drag Handle ****/
			setCurrentHandle(getItemHandlerByPosition(p));
			setItemHandleCursor(getItemHandlerByPosition(p).getCursor());
			setToolState(getNewDragHandleState());
		}
		else if(isOnSelectedItem(p)) {
			/**** moving ****/
			setToolState(getNewMovingState());
		}
		else if(isOnEmptyArea(p)) {
			/**** drag Area ****/
			clearSelection();
			initSelectionArea(p);
			setToolState(getNewDragAreaState());
		}
	}

	@Override
	protected void mouseOverEvent(Position p) {
		if(isOnItemHandle(p)) {
			/**** Init ****/
			setItemHandleCursor(getItemHandlerByPosition(p).getCursor());
		}
		else if(isOnSelectedItem(p)) {
			/**** Init ****/
			setMoveCursor();
		}
		else if(isOnUnselectedItem(p)){
			setMoveCursor();
		}
		else {
			setDefaultCursor();
		}
	}


}
