package frontend.impl.items.selection;

import frontend.interfaces.Item;
import frontend.interfaces.ItemHandler;
import helper.Position;

import java.awt.Cursor;
import java.io.Serializable;



public abstract class AbstractState implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7801388581385239555L;
	
	private SelectionTool myContext;
	
	protected AbstractState(SelectionTool context) {
		this.myContext = context;
	}
	
	final void mouseDown(Position p) {
		mouseDownEvent(p);
	}
	
	final void mouseDrag(Position p) {
		mouseDragEvent(p);
	}
	
	final void mouseUp(Position p) {
		mouseUpEvent(p);
	}
	
	final void mouseOver(Position p) {
		mouseOverEvent(p);
	}
	
	protected void mouseDownEvent(Position p) {
		// Default implementation, intentionally left empty.
	}
	
	protected void mouseDragEvent(Position p) {
		// Default implementation, intentionally left empty.
	}
	protected void mouseOverEvent(Position p) {
		// Default implementation, intentionally left empty.
	}
	protected void mouseUpEvent(Position p) {
		// Default implementation, intentionally left empty.
	}
	
	final protected void setToolState(AbstractState newState) {
		getContext().setToolState(newState);
	}
	
	final protected SelectionTool getContext() {
		return this.myContext;
	}
	final protected void setSelectionAreaTo(Position p) {
		getContext().doSetSelectionAreaTo(p);
	}
	final protected AbstractState getNewDragAreaState() {
		return getContext().getStateFactory().createDragAreaState(getContext());
	}
	
	final protected AbstractState getNewDragHandleState() {
		return getContext().getStateFactory().createDragHandleState(
				getContext());
	}
	final protected AbstractState getNewInitState() {
		return getContext().getStateFactory().createInitState(getContext());
	}
	final protected AbstractState getNewMovingState() {
		return getContext().getStateFactory().createMovingState(getContext());
	}
	final protected Item getItemByCoord(Position p) {
		return getContext().getItemByPosition(p);
	}
	final protected void adjustSelections() {
		getContext().doAdjustSelections();
	}
	final protected void discardSelectionArea() {
		getContext().doDiscardSelectionArea();
	}
	
	final protected ItemHandler getCurrentHandle() {
		return getContext().getCurrentHandle();
	}
	final protected void setCurrentHandle(ItemHandler h) {
		getContext().setCurrentHandle(h);
	}
	
	final protected boolean isOnUnselectedItem(Position p) {
		return getContext().isOnUnselectedItem(p);
	}
	
	final protected void clearSelection() {
		getContext().clearSelection();
	}
	final protected void addToSelection(Item i) {
		getContext().doAddToSelection(i);
	}
	final protected void setMoveCursor() {
		getContext().doSetMoveCursor();
	}
	final protected boolean isOnItemHandle(Position p) {
		return getContext().isOnItemHandle(p);
	}
	
	final protected ItemHandler getItemHandlerByPosition(Position p) {
		return getContext().getItemHandlerByPosition(p);
	}
	final protected void setItemHandleCursor(Cursor cur) {
		getContext().doSetItemeHandleCursor(cur);
	}
	protected final boolean isOnSelectedItem(Position p) {
		return getContext().isOnSelectedItem(p);
	}
	final protected boolean isOnEmptyArea(Position p) {
		return getContext().isOnEmptyArea(p);
	}
	final protected void initSelectionArea(Position p) {
		getContext().doInitSelectionArea(p);
	}
	final protected void setDefaultCursor() {
		getContext().doSetDefaultCursor();
	}
	
	final protected void moveSelectedShapes(Position p) {
		getContext().doMoveSelectedItems(p);
	}
	
	final protected void endMoveSelectedShapes(Position p) {
		getContext().endMoveSelectedItems(p);
	}
}
