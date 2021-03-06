package frontend.impl.items.selection;

import helper.Position;

/**
 * Implementation of the AbstractState. The Init state is the beginning state of the 
 * selection. This state manages the start events of the user and creates the corresponding
 * state. 
 * @author zannc2 & gfells4
 *
 */
public class Init extends AbstractState{

	private static final long serialVersionUID = 1987797483271556684L;

	public Init(SelectionTool context) {
		super(context);
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
			AbstractState dragHandle = getNewDragHandleState();
			setToolState(dragHandle);
			dragHandle.mouseDown(p);
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
