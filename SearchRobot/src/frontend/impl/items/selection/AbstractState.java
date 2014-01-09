package frontend.impl.items.selection;

import frontend.interfaces.Item;
import frontend.interfaces.ItemHandler;
import helper.Position;

import java.awt.Cursor;
import java.io.Serializable;

/**
 * The base state for the state classes of the selection tool. The selection
 * tool is the context.
 * @author zannc2 & gfells4
 *
 */
public abstract class AbstractState implements Serializable {
	
	private static final long serialVersionUID = -7801388581385239555L;
	
	private SelectionTool myContext;
	
	/**
	 * Constructor which defines the context of the state. 
	 * @param context the SelectionTool
	 */
	protected AbstractState(SelectionTool context) {
		this.myContext = context;
	}
	
	/**
	 * Handles the mouse down event on this state.
	 * @param p Position of the mouse. 
	 */
	final void mouseDown(Position p) {
		mouseDownEvent(p);
	}
	
	/**
	 * Handles the mouse drag event on this state.
	 * @param p Position of the mouse.
	 */
	final void mouseDrag(Position p) {
		mouseDragEvent(p);
	}
	
	/**
	 * Handles the mouse up event on this state.
	 * @param p Position of the mouse.
	 */
	final void mouseUp(Position p) {
		mouseUpEvent(p);
	}
	
	/**
	 * handles the mouse over event on this state.
	 * @param p Position of the mouse. 
	 */
	final void mouseOver(Position p) {
		mouseOverEvent(p);
	}
	
	/**
	 * Handles the mouse-down event if overwritten in subclasses. The method in
	 * this base class is empty.
	 * @param p Position of the mouse. 
	 */
	protected void mouseDownEvent(Position p) {
		// Default implementation, intentionally left empty.
	}
	
	/**
	 * Handles the mouse-drag event if overwritten in subclasses. The method in
	 * this base class is empty.
	 * @param p Position of the mouse. 
	 */
	protected void mouseDragEvent(Position p) {
		// Default implementation, intentionally left empty.
	}
	
	/**
	 * Handles the mouse-over event if overwritten in subclasses. The method in
	 * this base class is empty.
	 * @param p Position of the mouse. 
	 */
	protected void mouseOverEvent(Position p) {
		// Default implementation, intentionally left empty.
	}
	
	/**
	 * Handles the mouse-up event if overwritten in subclasses. The method in
	 * this base class is empty.
	 * @param p Position of the mouse. 
	 */
	protected void mouseUpEvent(Position p) {
		// Default implementation, intentionally left empty.
	}
	
	/**
	 * Sets the new state of the selection tool.
	 * @param newState the new state
	 */
	final protected void setToolState(AbstractState newState) {
		getContext().setToolState(newState);
	}
	
	/**
	 * Returns the context
	 * @return the SelectionTool
	 */
	final protected SelectionTool getContext() {
		return this.myContext;
	}
	
	/**
	 * Helper method that returns the state needed while user is dragging on
	 * area.
	 * @return DragArea State
	 */
	final protected AbstractState getNewDragAreaState() {
		return getContext().getStateFactory().createDragAreaState(getContext());
	}
	
	/**
	 * Helper method that returns the state needed while user is dragging on
	 * a ItemHandler.
	 * @return DragHandle State
	 */
	final protected AbstractState getNewDragHandleState() {
		return getContext().getStateFactory().createDragHandleState(
				getContext());
	}
	
	/**
	 * Helper method that returns the state needed while user starts selection.
	 * @return Init State
	 */
	final protected AbstractState getNewInitState() {
		return getContext().getStateFactory().createInitState(getContext());
	}
	
	/**
	 * Helper method that returns the state needed while user is moving on
	 * the selection.
	 * @return Moving State
	 */
	final protected AbstractState getNewMovingState() {
		return getContext().getStateFactory().createMovingState(getContext());
	}
	
	/**
	 * Returns the first Item on this position. 
	 * @param p Position asked.
	 * @return Item
	 */
	final protected Item getItemByCoord(Position p) {
		return getContext().getItemByPosition(p);
	}
	
	/**
	 * Resizes the selection area by calling corresponding method on the state
	 * context.
	 * @param p the current position of the mouse
	 */
	final protected void setSelectionAreaTo(Position p) {
		getContext().doSetSelectionAreaTo(p);
	}
	
	/**
	 * Adds every non-selected shape fully contained within the selection area
	 * by calling corresponding method on the state context.
	 */
	final protected void adjustSelections() {
		getContext().doAdjustSelections();
	}
	
	/**
	 * Discards the selection area by calling corresponding method on the state
	 * context.
	 */
	final protected void discardSelectionArea() {
		getContext().doDiscardSelectionArea();
	}
	
	/**
	 * Helper method that removes all items from selection
	 */
	final protected void clearSelection() {
		getContext().clearSelection();
	}
	
	/**
	 * Helper method that adds a Item to the selection
	 * @param i Item to add to selection
	 */
	final protected void addToSelection(Item i) {
		getContext().doAddToSelection(i);
	}
	
	/**
	 * Returns the current item handler the user is dragging on.
	 * @return the ItemHandler
	 */
	final protected ItemHandler getCurrentHandle() {
		return getContext().getCurrentHandle();
	}
	
	/**
	 * Registers the current item handle with the context.
	 * @param h ItemHandler
	 */
	final protected void setCurrentHandle(ItemHandler h) {
		getContext().setCurrentHandle(h);
	}
	
	/**
	 * Helper method to check if Position is on a unselected Item.
	 * @param p asked Position
	 * @return true if Position is on a unselected Item
	 */
	final protected boolean isOnUnselectedItem(Position p) {
		return getContext().isOnUnselectedItem(p);
	}
	
	
	/**
	 * Helper method to check if position is on a item handler
	 * @param p asked position
	 * @return true if position is on a item handler
	 */
	final protected boolean isOnItemHandle(Position p) {
		return getContext().isOnItemHandle(p);
	}
	
	/**
	 * Returns the item handler on this position.
	 * @param p asked position
	 * @return handler on the asket position
	 */
	final protected ItemHandler getItemHandlerByPosition(Position p) {
		return getContext().getItemHandlerByPosition(p);
	}
	
	/**
	 * helper method to check if position is on a selected item
	 * @param p asked position
	 * @return true if the position is on a selected item
	 */
	protected final boolean isOnSelectedItem(Position p) {
		return getContext().isOnSelectedItem(p);
	}
	
	/**
	 * helper method to check if position is on empty area 
	 * @param p asked position
	 * @return true if position is on empty area
	 */
	final protected boolean isOnEmptyArea(Position p) {
		return getContext().isOnEmptyArea(p);
	}
	
	/**
	 * Initializes the selection area by calling corresponding method on the
	 * state context.
	 * @param p Position of the mouse
	 */
	final protected void initSelectionArea(Position p) {
		getContext().doInitSelectionArea(p);
	}
	
	/**
	 * helper method to set the cursor to defaul
	 */
	final protected void setDefaultCursor() {
		getContext().doSetDefaultCursor();
	}

	/**
	 * helper method to set the cursor to move
	 */
	final protected void setMoveCursor() {
		getContext().doSetMoveCursor();
	}
	
	/**
	 * helper method to set the cursor defined by the item handler
	 * @param cur
	 */
	final protected void setItemHandleCursor(Cursor cur) {
		getContext().doSetItemHandleCursor(cur);
	}
	
	/**
	 * helper method that moves the selected items
	 * @param p Position of the mouse
	 */
	final protected void moveSelectedShapes(Position p) {
		getContext().doMoveSelectedItems(p);
	}
	
	/**
	 * helper method of finishing the move of selected items
	 * It checks if the new item position is available
	 * @param p Position of the mouse
	 */
	final protected void endMoveSelectedShapes(Position p) {
		getContext().endMoveSelectedItems(p);
	}
}

