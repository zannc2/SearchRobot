package frontend.interfaces;

import java.util.List;

public interface View 
{
	public Tool getTool();
	public void setTool(Tool tool);
	public void addItem(Item i);
	public boolean removeItem(Item i);
	public List<Item> getItems();
}
