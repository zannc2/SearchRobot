package frontend.interfaces;

import java.io.Serializable;
import java.util.EventListener;

import frontend.classes.view.ItemChangedEvent;

public interface ItemChangedListener extends EventListener, Serializable {

	// TODO
	public void itemChanged(ItemChangedEvent e);
	
}
