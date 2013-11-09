package frontend.interfaces;

import java.io.Serializable;
import java.util.EventListener;

import frontend.classes.view.FieldChangedEvent;

public interface FieldChangedListener extends EventListener, Serializable {

	public void fieldChanged(FieldChangedEvent e);
	
}
