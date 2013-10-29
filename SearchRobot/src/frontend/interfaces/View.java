package frontend.interfaces;

public interface View 
{
	public Tool getTool();
	public void setTool(Tool tool);
	public void addItem(Item i);
	public boolean removeItem(Item i);
}
