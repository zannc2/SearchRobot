package frontend.interfaces;

import java.io.Serializable;
import java.util.EventListener;

import frontend.classes.view.FieldChangedEvent;

/**
 * Listener for Field FieldChangedEvent
 * @author zannc2 & gfells4
 *
 */
public interface FieldChangedListener extends EventListener, Serializable {
	
	public void fieldChanged(FieldChangedEvent e);
	
}
