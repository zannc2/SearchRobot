package frontend.interfaces;

import java.awt.Cursor;
import java.util.List;

import frontend.impl.view.Field;

/**
 * A view manages all Items and Tools
 * @author zannc2 & gfells4
 *
 */
public interface View 
{
	/**
	 * returns the current Tool
	 * @return current Tool
	 */
	public Tool getTool();
	
	/**
	 * sets a new tool to current tool
	 * @param tool new toll
	 */
	public void setTool(Tool tool);
	
	/**
	 * returns the coresponding field
	 * @return Field
	 */
	public Field getField();
	
	/**
	 * returns the correspondig stateFactory
	 * @return StateFactory
	 */
	public StateFactory getStateFactory();
	
	/**
	 * returns all selected Items
	 * @return List of selected Items
	 */
	public List<Item> getSelection();
	
	/**
	 * add Item to selected Items
	 * @param i Item
	 */
	public void addToSelection(Item i);
	
	/**
	 * remove Item from selected Items
	 * @param i
	 */
	public void removeFromSelection(Item i);
	
	/**
	 * removes all selected Items
	 */
	public void clearSelection();
	
	/**
	 * set displayed cursor
	 * @param cur new cursor
	 */
	public void setCursor(Cursor cur);
	
	/**
	 * returns Handler of the selected Items
	 * @return all selected Hanlders
	 */
	public List<ItemHandler> getSelectionHandles();
}
