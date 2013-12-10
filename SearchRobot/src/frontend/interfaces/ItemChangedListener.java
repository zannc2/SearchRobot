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

	public void itemChanged(ItemChangedEvent e);
	
}
