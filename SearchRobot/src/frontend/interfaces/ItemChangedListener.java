package frontend.interfaces;

import java.io.Serializable;
import java.util.EventListener;

import frontend.impl.view.ItemChangedEvent;

/**
 * Listener for ItemChangedEvent
 * @author zannc2 & gfells4
 *
 */
public interface ItemChangedListener extends EventListener, Serializable {

	/**
	 * Called when something changed in the item (e.g. resized, moved....)
	 * 
	 * @param e The event object which contains the source of the event
	 */
	public void itemChanged(ItemChangedEvent e);
	
}
