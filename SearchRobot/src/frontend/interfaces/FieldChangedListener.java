package frontend.interfaces;

import java.io.Serializable;
import java.util.EventListener;

import frontend.impl.view.FieldChangedEvent;

/**
 * Listener for Field FieldChangedEvent, generates an event whenever the state 
 * of the field changes
 * 
 * @author zannc2 & gfells4
 */
public interface FieldChangedListener extends EventListener, Serializable {
	
	/**
	 * Called when something changed in the Field (e.g. a new item, resize....)
	 * 
	 * @param e The event object which contains the source of the event
	 */
	public void fieldChanged(FieldChangedEvent e);
	
}
