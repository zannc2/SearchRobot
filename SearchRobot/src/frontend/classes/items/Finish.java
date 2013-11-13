package frontend.classes.items;

import frontend.interfaces.ItemHandler;
import helper.Position;
import helper.Size;
import helper.Vector;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class Finish extends AbstractItem {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9221569467727804276L;
	private Position position;
	private Size size = new Size(20, 20);
	
	private List<ItemHandler> itemHandler = new ArrayList<ItemHandler>();
	
	public Finish(Position p) {
		this.position = p;
		

		notifyItemChangedListeners();
		//TODO create handler
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(1));
		g2.fillRect(this.position.getOriginX(), this.position.getOriginY(), 
				this.size.getWidth()/4, this.size.getHeight()/4);
		g2.drawRect(this.position.getOriginX()+this.size.getWidth()/4, 
				this.position.getOriginY(), this.size.getWidth()/4, 
				this.size.getHeight()/4);
		g2.fillRect(this.position.getOriginX() +this.size.getWidth()/2, this.position.getOriginY(), 
				this.size.getWidth()/4, this.size.getHeight()/4);
		g2.drawRect(this.position.getOriginX()+(this.size.getWidth()/4 * 3), 
				this.position.getOriginY(), this.size.getWidth()/4, 
				this.size.getHeight()/4);
		
		g2.drawRect(this.position.getOriginX(), this.position.getOriginY() + this.size.getHeight()/4, 
				this.size.getWidth()/4, this.size.getHeight()/4);
		g2.fillRect(this.position.getOriginX()+this.size.getWidth()/4, 
				this.position.getOriginY() + this.size.getHeight()/4, this.size.getWidth()/4, 
				this.size.getHeight()/4);
		g2.drawRect(this.position.getOriginX() +this.size.getWidth()/2, this.position.getOriginY() + this.size.getHeight()/4, 
				this.size.getWidth()/4, this.size.getHeight()/4);
		g2.fillRect(this.position.getOriginX()+(this.size.getWidth()/4 * 3), 
				this.position.getOriginY() + this.size.getHeight()/4, this.size.getWidth()/4, 
				this.size.getHeight()/4);
		
		g2.fillRect(this.position.getOriginX(), this.position.getOriginY() + this.size.getHeight()/2, 
				this.size.getWidth()/4, this.size.getHeight()/4);
		g2.drawRect(this.position.getOriginX()+this.size.getWidth()/4, 
				this.position.getOriginY() + this.size.getHeight()/2, this.size.getWidth()/4, 
				this.size.getHeight()/4);
		g2.fillRect(this.position.getOriginX() +this.size.getWidth()/2, this.position.getOriginY() + this.size.getHeight()/2, 
				this.size.getWidth()/4, this.size.getHeight()/4);
		g2.drawRect(this.position.getOriginX()+(this.size.getWidth()/4 * 3), 
				this.position.getOriginY() + this.size.getHeight()/2, this.size.getWidth()/4, 
				this.size.getHeight()/4);
		
		g2.drawRect(this.position.getOriginX(), this.position.getOriginY() + this.size.getHeight()/4 * 3, 
				this.size.getWidth()/4, this.size.getHeight()/4);
		g2.fillRect(this.position.getOriginX()+this.size.getWidth()/4, 
				this.position.getOriginY() + this.size.getHeight()/4 * 3, this.size.getWidth()/4, 
				this.size.getHeight()/4);
		g2.drawRect(this.position.getOriginX() +this.size.getWidth()/2, this.position.getOriginY() + this.size.getHeight()/4 * 3, 
				this.size.getWidth()/4, this.size.getHeight()/4);
		g2.fillRect(this.position.getOriginX()+(this.size.getWidth()/4 * 3), 
				this.position.getOriginY() + this.size.getHeight()/4 * 3, this.size.getWidth()/4, 
				this.size.getHeight()/4);
		
		g2.drawRect(this.position.getOriginX(), this.position.getOriginY(), 
				this.size.getWidth(), this.size.getHeight());
	}

	@Override
	public void setPosition(Position position) {
		this.position = position;
		notifyItemChangedListeners();
	}

	@Override
	public void move(Vector delta) {
		/* origin cordinates*/
		int x = this.position.getOriginX();
		int y = this.position.getOriginY();
		
		/* Move */
		x = x + delta.getXComponent();
		y = y + delta.getYComponent();
		
		this.position = new Position(x, y);
		
		notifyItemChangedListeners();

		//TODO
		/* set handles */
	}

	@Override
	public boolean contains(Position p) {
		boolean containsX = false;
		boolean containsY = false;
		// test x Coordinate
		if(p.getOriginX() >= this.position.getOriginX() && 
				p.getOriginX() <= (this.position.getOriginX() + this.size.getWidth()))
			containsX = true;
			
		//test y Coordinate
		if(p.getOriginY() >= this.position.getOriginY() &&
				p.getOriginY() <= (this.position.getOriginY() + this.size.getHeight()))
			containsY = true;
		if(containsX && containsY) return true;
		
		return false;
	}

	@Override
	public List<ItemHandler> getItemHandler() {
		return this.itemHandler;
	}
	
	@Override
	public Position getPosition() {
		return this.position;
	}

	@Override
	public void setSize(Size size) {
		this.size = size;
		notifyItemChangedListeners();
	}

	@Override
	public Size getSize() {
		return this.size;
	}

	@Override
	public boolean contains(Position p, int epsilon) {
		return contains(p);
	}

}
