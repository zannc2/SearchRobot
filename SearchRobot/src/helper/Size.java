package helper;

import java.io.Serializable;

public class Size implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -1272203908120963516L;

	private int width;

	private int height;
	
	public Size(int width, int height) {
		super();
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Size other = (Size) obj;
		if (width != other.width)
			return false;
		if (height != other.height)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Size[" + this.width + " x " + this.height + "]";
	}
}
