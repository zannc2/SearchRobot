package frontend.impl.view;

public class FieldChangedEvent {
	
	protected transient Object  source;

    /**
     * Constructs a prototypical Event.
     *
     * @param    source    The object on which the Event initially occurred.
     * @exception  IllegalArgumentException  if source is null.
     */
    public FieldChangedEvent(Object source) {
        if (source == null)
            throw new IllegalArgumentException("null source");

        this.source = source;
    }
}
