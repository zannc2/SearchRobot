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

public class SelectionTool extends AbstractTool {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5585838914067568255L;

	public SelectionTool(Field field) {
		super(field);
		this.factory = field.getView().getStateFactory();
		setToolState(this.factory.createInitState(this));
//		System.out.println("create selection tool");
	}
	
	private StateFactory factory;
	private AbstractState state = null;

	private Item selectionArea;
	Position lastP;
	
	private Position previousMouseDownPosition;
	public static final Cursor MOVE_CURSOR = new Cursor(Cursor.HAND_CURSOR);

	private ItemHandler currentHandle;
	private Vector previousDelta;
	private Map<Object,Object> originalPositions =new HashMap<Object, Object>();

	public void mouseDown(Position p) 
	{
		this.previousMouseDownPosition = p;
		this.state.mouseDown(p);
	}

	public void mouseDrag(Position p) {		
		this.state.mouseDrag(p);
		this.previousMouseDownPosition = p;
			
	}
	
	public void mouseOver(Position p) {
		this.state.mouseOver(p);
	}

	public void mouseUp(Position p) {
		this.state.mouseUp(p);
	}
	
	public void setToolState(AbstractState s) {
		this.state = s;
	}
	
	public void doSetSelectionAreaTo(Position p) {
		if (this.selectionArea != null) {
			Position origP = this.selectionArea.getPosition();
			Size newSize = new Size(p.getOriginX() - origP.getOriginX(), p.getOriginY() - origP.getOriginY());
			this.selectionArea.setSize(newSize);
		}
	}
	
	public StateFactory getStateFactory() {
		return this.factory;
	}
	
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
	
	public List<Item> getSelection() {
		return getField().getView().getSelection();
	}
	
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
	
	public void doDiscardSelectionArea() {
		if (this.selectionArea != null) {
			getField().removeItem(this.selectionArea);
		}
		this.selectionArea = null;
	}
	
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
	
	public ItemHandler getCurrentHandle() {
		return this.currentHandle;
	}
	
	public void setCurrentHandle(ItemHandler h) {
		this.currentHandle = h;
	}

	public void clearSelection() {
		getField().getView().clearSelection();
	}

	public void doAddToSelection(Item i) {
		getField().getView().addToSelection(i);
	}

	public void doSetMoveCursor() {
		setCursor(MOVE_CURSOR);
	}
	
	final private void setCursor(Cursor cur) {
		getField().getView().setCursor(cur);
	}
	final private List<ItemHandler> getSelectionHandles() {
		return getField().getView().getSelectionHandles();
	}

	public boolean isOnItemHandle(Position p) {
		for (ItemHandler h : getSelectionHandles()) {
			if (h.contains(p)) {
				return true;
			}
		}
		return false;
	}

	public ItemHandler getItemHandlerByPosition(Position p) {
		for (ItemHandler h : getSelectionHandles()) {
			if (h.contains(p)) {
				return h;
			}
		}
		return null;
	}

	public void doSetItemeHandleCursor(Cursor cursor) {
		setCursor(cursor);
	}

	public boolean isOnSelectedItem(Position p) {
		for (Item i : getSelection()) {
			if (i.contains(p)) {
				return true;
			}
		}
		return false;
	}

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

	public boolean isOnEmptyArea(Position p) {
		for (Item i : getItems()) {
			if (i.contains(p)) {
				return false;
			}
		}
		return true;
	}

	public void doInitSelectionArea(Position p) {
		this.selectionArea = new SelectionArea(p);
		getField().addItem(this.selectionArea);
	}

	public void doSetDefaultCursor() {
		setCursor(Cursor.getDefaultCursor());
		
	}
	
	public Position getPreviousMouseDragPosition() {
		return this.previousMouseDownPosition;
	}
	
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
		this.previousDelta = delta;
		for (Item i : getSelection()) {
			i.move(delta);
		}
	}
	
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