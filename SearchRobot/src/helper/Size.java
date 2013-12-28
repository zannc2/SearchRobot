package helper;

import java.awt.Rectangle;
import java.io.Serializable;


public class Size implements Serializable {

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
	
	public static Rectangle getAWTRectangle(Size size, Position position) {
		Rectangle r = null;
		int width = size.getWidth();
		int height = size.getHeight();
		if (width >= 0) {
			if (height >= 0) {
				
				if(width > height)
				{
					r = new Rectangle(position.getOriginX(), position
							.getOriginY(), width, width);
				}
				else
				{
					r = new Rectangle(position.getOriginX(), position
							.getOriginY(), height, height);
				}
				
			} else {
				// width >= 0 && height < 0
				if(Math.abs(width) > Math.abs(height))
				{
					r = new Rectangle(position.getOriginX(), position
							.getOriginY() - width, width, width);
				}
				else
				{
					r = new Rectangle(position.getOriginX(), position
							.getOriginY() + height, -height, -height);
				}
			}
		} else {
			if (height >= 0) {
				if(Math.abs(width) > Math.abs(height))
				{
					r = new Rectangle(position.getOriginX() + width, position
						.getOriginY(), -width, -width);
				}
				else
				{
					r = new Rectangle(position.getOriginX() - height, position
							.getOriginY(), height, height);
				}
			} else {
				// width < 0 && height < 0
				if(Math.abs(width) > Math.abs(height))
				{
				r = new Rectangle(position.getOriginX() + width, position
						.getOriginY()
						+ width, -width, -width);
				}
				else
				{
					r = new Rectangle(position.getOriginX() + height, position
							.getOriginY()
							+ height, -height, -height);
				}
			}
		}
		return r;
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
