package frontend.impl.items.selection;

import helper.Position;
import helper.Size;
import helper.Vector;

import java.awt.Cursor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import frontend.impl.items.AbstractTool;
import frontend.impl.view.Field;
import frontend.interfaces.Item;
import frontend.interfaces.ItemHandler;
import frontend.interfaces.StateFactory;

/**
 * This is the class for the selection tool, which creates the selection tool
 * It extends the {@link AbstractTool} class
 * 
 * @author zannc2 & gfels4
 *
 */
public class SelectionTool extends AbstractTool {
	
	private static final long serialVersionUID = 5585838914067568255L;

	/**
	 * Constructor which defines the field where the selection tool belongs to
	 * @param field Field
	 */
	public SelectionTool(Field field) {
		super(field);
		this.factory = field.getView().getStateFactory();
		setToolState(this.factory.createInitState(this));
	}
	
	private StateFactory factory;
	private AbstractState state = null;

	private Item selectionArea;
	
	private Position previousMouseDownPosition;
	public static final Cursor MOVE_CURSOR = new Cursor(Cursor.HAND_CURSOR);

	private ItemHandler currentHandle;
	private Map<Object,Object> originalPositions =new HashMap<Object, Object>();

	@Override
	public void mouseDown(Position p) 
	{
		this.previousMouseDownPosition = p;
		this.state.mouseDown(p);
	}
	
	@Override
	public void mouseDrag(Position p) {		
		this.state.mouseDrag(p);
		this.previousMouseDownPosition = p;
			
	}
	
	@Override
	public void mouseOver(Position p) {
		this.state.mouseOver(p);
	}

	@Override
	public void mouseUp(Position p) {
		this.state.mouseUp(p);
	}
	
	/**
	 * Sets a new State
	 * @param s AbstractState
	 */
	public void setToolState(AbstractState s) {
		this.state = s;
	}
	
	/**
	 * Moves the selection are to a new position
	 * @param p new position
	 */
	public void doSetSelectionAreaTo(Position p) {
		if (this.selectionArea != null) {
			Position origP = this.selectionArea.getPosition();
			Size newSize = new Size(p.getOriginX() - origP.getOriginX(), p.getOriginY() - origP.getOriginY());
			this.selectionArea.setSize(newSize);
		}
	}
	
	/**
	 * returns the state factory
	 * @return StateFactory
	 */
	public StateFactory getStateFactory() {
		return this.factory;
	}
	
	/**
	 * Helper method which gets the Item on the Position asked
	 * @param p Position asked
	 * @return Item on this position
	 */
	public Item getItemByPosition(Position p) {
		// iterate over reverse stacking order
		for (int i = getItems().size() - 1; i >= 0; i--) {
			Item item = getItems().get(i);
			if (item.contains(p)) {
				return item;
			}
		}
		return null;
	}
	
	/**
	 * returns the List of all selected items
	 * @return List of selected items
	 */
	public List<Item> getSelection() {
		return getField().getView().getSelection();
	}
	
	/**
	 * Adds every non-selected shape fully contained within the selection area
	 */
	public void doAdjustSelections() {
		for (Item i : getItems()) {
			if (i != this.selectionArea) { 
				if (isItemInSeletionArea(i)) {
					if (!getSelection().contains(i)) {
						getField().getView().addToSelection(i);
					}
				} else {
					if (getSelection().contains(i)) {
						getField().getView().removeFromSelection(i);
					}
				}
			}
		}
	}
	
	/**
	 * removes the selection area from the field
	 */
	public void doDiscardSelectionArea() {
		if (this.selectionArea != null) {
			getField().removeItem(this.selectionArea);
		}
		this.selectionArea = null;
	}
	
	/**
	 * helper method to check if the item is contained in the selection area
	 * @param i asked Item
	 * @return true if the item is contained in the selection area
	 */
	private boolean isItemInSeletionArea(Item i) {
		if (this.selectionArea != null) {
			Position itemPosition = i.getPosition();
			Size itemSize = i.getSize();
			Position startPoint = new Position(itemPosition.getOriginX(),
					itemPosition.getOriginY());
			Position endPoint = new Position(itemPosition.getOriginX() + itemSize.getWidth(),
					itemPosition.getOriginY() + itemSize.getHeight());
			boolean c1 = this.selectionArea.contains(startPoint);
			boolean c2 = this.selectionArea.contains(endPoint);
			return c1 && c2;
		} else {
			return false;
		}
	}
	
	/**
	 * returns the current selected handler
	 * @return ItemHandler
	 */
	public ItemHandler getCurrentHandle() {
		return this.currentHandle;
	}
	
	/**
	 * sets the current selected handler
	 * @param h ItemHandler
	 */
	public void setCurrentHandle(ItemHandler h) {
		this.currentHandle = h;
	}

	/**
	 * removes all Items from selection
	 */
	public void clearSelection() {
		getField().getView().clearSelection();
	}

	/**
	 * adds a new item to selection
	 * @param i new selected item
	 */
	public void doAddToSelection(Item i) {
		getField().getView().addToSelection(i);
	}

	/**
	 * sets the move cursor to current cursor
	 */
	public void doSetMoveCursor() {
		setCursor(MOVE_CURSOR);
	}
	
	final private void setCursor(Cursor cur) {
		getField().getView().setCursor(cur);
	}
	
	final private List<ItemHandler> getSelectionHandles() {
		return getField().getView().getSelectionHandles();
	}

	/**
	 * Helper method to check if position is on an item handler
	 * @param p asked position
	 * @return true if the position is on an item handler
	 */
	public boolean isOnItemHandle(Position p) {
		for (ItemHandler h : getSelectionHandles()) {
			if (h.contains(p)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Helper method that returns the item handler by a given position
	 * @param p asked position
	 * @return item handler on this position
	 */
	public ItemHandler getItemHandlerByPosition(Position p) {
		for (ItemHandler h : getSelectionHandles()) {
			if (h.contains(p)) {
				return h;
			}
		}
		return null;
	}

	/**
	 * Sets the item handler cursor
	 * @param cursor item handler cursor
	 */
	public void doSetItemHandleCursor(Cursor cursor) {
		setCursor(cursor);
	}

	/**
	 * helper method that checks if the position is on a selected item
	 * @param p asked position
	 * @return true if the position is on a selected item
	 */
	public boolean isOnSelectedItem(Position p) {
		for (Item i : getSelection()) {
			if (i.contains(p)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * helper method that checks if the position is on a unselected item
	 * @param p asked position
	 * @return true if the position is on a unselected item
	 */
	public boolean isOnUnselectedItem(Position p) {
		// iterate over reverse stacking order
		for (int i = getItems().size() - 1; i >= 0; i--) {
			Item item = getItems().get(i);
			if (item.contains(p)) {
				if (getSelection().contains(item)) {
					return false;
				} else {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * helper method that checks if the position is on empty area (not on a item)
	 * @param p asked position
	 * @return true if position is on empty area
	 */
	public boolean isOnEmptyArea(Position p) {
		for (Item i : getItems()) {
			if (i.contains(p)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * creates a new selection area at the given position
	 * @param p position of the created selection area
	 */
	public void doInitSelectionArea(Position p) {
		this.selectionArea = new SelectionArea(p);
		getField().addItem(this.selectionArea);
	}

	/**
	 * sets the cursor back to default
	 */
	public void doSetDefaultCursor() {
		setCursor(Cursor.getDefaultCursor());
		
	}
	
	/**
	 * gets the previous position before dragging
	 * @return previous position
	 */
	public Position getPreviousMouseDragPosition() {
		return this.previousMouseDownPosition;
	}
	
	/**
	 * move all selected items to given position by calculating a delta with the previous 
	 * drag position
	 * @param p new position
	 */
	public void doMoveSelectedItems(Position p) {
		
		if(this.originalPositions.isEmpty()) {
			for (Item i : getSelection()) {
				this.originalPositions.put(i, i.getPosition());
			}
		}
		Position cPrevious = getPreviousMouseDragPosition();
		if (cPrevious == null) {
			cPrevious = p;
		}
		Vector delta = new Vector(p.getOriginX() - cPrevious.getOriginX(),
				p.getOriginY() - cPrevious.getOriginY());
		for (Item i : getSelection()) {
			i.move(delta);
		}
	}
	
	/**
	 * ends the moving of the selected items and checks if the new position is free, that 
	 * means circle and line cann't be over the robot or the finish
	 * @param p
	 */
	public void endMoveSelectedItems(Position p){		
		doMoveSelectedItems(p);
		boolean undo = false;
		for (Item i : getSelection()) {
			if(!getField().checkIfPositionFree(i)) undo = true;
		}
		
		if(undo) {
			for (Item i : getSelection()) {
				i.setPosition((Position) this.originalPositions.get(i));
				this.originalPositions.remove(i);
			}
		}
		
	}
	

}