package frontend.interfaces;

import java.awt.Cursor;
import java.util.List;

import frontend.classes.view.Field;


public interface View 
{
	public Tool getTool();
	public void setTool(Tool tool);
	public Field getField();
	public StateFactory getStateFactory();
	public List<Item> getSelection();
	public void addToSelection(Item i);
	void removeFromSelection(Item i);
	void clearSelection();
	public void setCursor(Cursor cur);
	public List<ItemHandler> getSelectionHandles();
}
