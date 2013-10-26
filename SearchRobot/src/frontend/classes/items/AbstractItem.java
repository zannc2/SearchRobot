package frontend.classes.items;

import helper.Position;
import helper.Size;

import java.util.ArrayList;
import java.util.List;

import frontend.classes.view.ItemChangedEvent;
import frontend.interfaces.Item;
import frontend.interfaces.ItemChangedListener;
import frontend.interfaces.ItemHandler;

public abstract class AbstractItem implements Item {
	private Size defaultSize = new Size(4, 4); 
	private Position position;
	private Size size;
	
	private List<ItemHandler> itemHandles = new ArrayList<ItemHandler>();
	private List<ItemChangedListener> listeners = new ArrayList<ItemChangedListener>();
	

	
	@Override
	public List<ItemHandler> getItemHandler() {
		return this.itemHandles;
	}

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
