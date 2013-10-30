package frontend.interfaces;

import java.util.EventListener;

import frontend.classes.view.FieldChangedEvent;
import frontend.classes.view.ItemChangedEvent;

public interface FieldChangedListener extends EventListener {

	public void fieldChanged(FieldChangedEvent e);
	
}
