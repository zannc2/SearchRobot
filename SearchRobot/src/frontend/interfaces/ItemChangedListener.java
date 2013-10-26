package frontend.interfaces;

import java.util.EventListener;

import frontend.classes.view.ItemChangedEvent;

public interface ItemChangedListener extends EventListener {

	// TODO
	public void itemChanged(ItemChangedEvent e);
	
}
