package frontend.impl.items;

import java.util.ArrayList;
import java.util.List;

import frontend.impl.view.ItemChangedEvent;
import frontend.interfaces.Item;
import frontend.interfaces.ItemChangedListener;

/**
 * Implements the common methods and variables of all Items
 * @author zannc2 & gfels4
 *
 */
public abstract class AbstractItem implements Item {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6766229224617420521L;
	
	private List<ItemChangedListener> listeners = new ArrayList<ItemChangedListener>();
	

	@Override
	public void addItemChangedListener(ItemChangedListener listener) {
		this.listeners.add(listener);
	}

	@Override
	public boolean removeItemChangedListener(ItemChangedListener listener) {
		return this.listeners.remove(listener);
	}
	
	/**
	 * Notifies all registered shape changed listeners. Called by subclasses
	 * upon changes of a shape.
	 */
	protected void notifyItemChangedListeners() {
		ItemChangedEvent e = new ItemChangedEvent(this);
		for (ItemChangedListener l : this.listeners) {
			l.itemChanged(e);
		}
	}

}
