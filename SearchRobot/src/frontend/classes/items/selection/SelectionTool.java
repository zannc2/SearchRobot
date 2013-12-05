package frontend.classes.items.selection;

import helper.Position;
import helper.Size;
import helper.Vector;

import java.awt.Cursor;
import java.util.List;

import frontend.classes.items.AbstractTool;
import frontend.classes.view.Field;
import frontend.interfaces.Item;
import frontend.interfaces.ItemHandler;
import frontend.interfaces.StateFactory;

public class SelectionTool extends AbstractTool {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3784532907310734414L;

	public SelectionTool(Field field) {
		super(field);
		this.factory = field.getView().getStateFactory();
		setToolState(this.factory.createInitState(this));
//		System.out.println("create selection tool");
	}
	
	private StateFactory factory;
	private SelectionToolState state = null;

	private Item selectionArea;
	Position lastP;
	
	private Position previousMouseDownPosition;
	public static final Cursor MOVE_CURSOR = new Cursor(Cursor.HAND_CURSOR);

	private ItemHandler currentHandle;

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
	
	void setToolState(SelectionToolState s) {
		this.state = s;
	}
	
	final void doSetSelectionAreaTo(Position p) {
		if (this.selectionArea != null) {
			Position origP = this.selectionArea.getPosition();
			Size newSize = new Size(p.getOriginX() - origP.getOriginX(), p.getOriginY() - origP.getOriginY());
			this.selectionArea.setSize(newSize);
		}
	}
	
	StateFactory getStateFactory() {
		return this.factory;
	}
	
	final Item getItemByPosition(Position p) {
		// iterate over reverse stacking order
		for (int i = getItems().size() - 1; i >= 0; i--) {
			Item item = getItems().get(i);
			if (item.contains(p)) {
				return item;
			}
		}
		return null;
	}
	
	List<Item> getSelection() {
		return getField().getView().getSelection();
	}
	
	final void doAdjustSelections() {
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
	final void doDiscardSelectionArea() {
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
	
	final ItemHandler getCurrentHandle() {
		return this.currentHandle;
	}
	
	final void setCurrentHandle(ItemHandler h) {
		this.currentHandle = h;
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
	
	final Position getPreviousMouseDragPosition() {
		return this.previousMouseDownPosition;
	}
	private Vector previousDelta;

	public void doMoveSelectedShapes(Position p) {
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
	
	public void doEndMoveSelectedShapes(Position p){
		System.out.println("doEndMoveSelectedShapes");
		Position cPrevious = getPreviousMouseDragPosition();
		if (cPrevious == null) {
			cPrevious = p;
		}
		Vector delta = this.previousDelta;
		for (Item i : getSelection()) {
			if(getField().checkMoveItem(i, delta)) i.move(delta);
			else{
				Vector removeDelta = new Vector(-delta.getXComponent(), -delta.getYComponent());
//				System.out.println("removeDelta: [" + removeDelta.getXComponent() + " ," + removeDelta.getYComponent() + "]");
				System.out.println("delta: [" + delta.getXComponent() + " ," + delta.getYComponent() + "]");
				
				//detect shortest direction if removeDelta is null
				if(removeDelta.getXComponent() == 0 && removeDelta.getYComponent() == 0) {
					int stepEast = 0, stepSouth = 0, stepWest = 0, stepNorth = 0;
					Vector east = new Vector(1, 0);
					Vector south = new Vector(0, 1);
					Vector west = new Vector(-1, 0);
					Vector north = new Vector(0,-1);
					
					//detect east
					while(!getField().checkMoveItem(i, east)) {
						i.move(east);
						stepEast++;
					}
					for(int j = stepEast; stepEast >= 0; stepEast--){
						i.move(west);
					}
					
					//detect south
					while(!getField().checkMoveItem(i, south)) {
						i.move(south);
						stepSouth++;
					}
					for(int j = stepSouth; stepSouth >= 0; stepSouth--){
						i.move(north);
					}
					
					//detect west
					while(!getField().checkMoveItem(i, west)) {
						i.move(west);
						stepWest++;
					}
					for(int j = stepWest; stepWest >= 0; stepWest--){
						i.move(east);
					}
					
					//detect north
					while(!getField().checkMoveItem(i, north)) {
						i.move(north);
						stepNorth++;
					}
					for(int j = stepNorth; stepNorth >= 0; stepNorth--){
						i.move(south);
					}
					
					//set Vector vor minimum Step Direction
					if(stepEast <= stepSouth && stepEast <= stepWest && stepEast <= stepNorth) removeDelta = east;
					else if(stepSouth <= stepEast && stepSouth <= stepWest && stepSouth <= stepNorth) removeDelta = south;
					else if(stepWest <= stepEast && stepWest <= stepSouth && stepWest <= stepNorth) removeDelta = south;
					else if(stepNorth <= stepEast && stepNorth <= stepSouth && stepNorth <= stepWest) removeDelta = south;
					
				}
				while(!getField().checkMoveItem(i, removeDelta)) {
					i.move(removeDelta);
				}
				i.move(removeDelta);
			}
		}
	}
	

}