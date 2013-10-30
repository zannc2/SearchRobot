package frontend.classes.items;

import frontend.interfaces.ItemHandler;
import helper.Position;
import helper.Size;
import helper.Vector;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;


public class Circle extends AbstractItem {
	
	private Position position;
	private Size size;
	
	private List<ItemHandler> itemHandler = new ArrayList<ItemHandler>();

	public Circle(Position p) {
		this.position = p;
		this.size = new Size(1, 1);
		//TODO greate Handler
	}
	
	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(1));
//		g2.fillOval(this.position.getOriginX(), this.position.getOriginY(), 
//				this.size.getWidth(), this.size.getHeight());
		g2.fill(new Ellipse2D.Double(this.position.getOriginX(), this.position.getOriginY(), 
				this.size.getWidth(), this.size.getHeight()));
		System.out.println("Zeichne Kreis, size: " + this.size);
	}

	@Override
	public void setPosition(Position position) {
		this.position = position;
		notifyItemChangedListeners();
	}

	@Override
	public boolean contains(Position p) {
		return new Ellipse2D.Double(this.position.getOriginX(), this.position.getOriginX(),
				this.size.getWidth(), this.size.getHeight()).contains(p.getAWTPoint());
		
	}

	@Override
	public List<ItemHandler> getItemHandler() {
		return this.itemHandler;	
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
//		Coord c = new Coord(this.bb.getX0(), this.bb.getY0());
//		shapeHandles.get(0).setPosition(c);
//
//		Coord end = new Coord(this.bb.getX0() + this.bb.getWidth(), 
//				this.bb.getY0() + this.bb.getHeight());
//		shapeHandles.get(1).setPosition(end);
	}

	@Override
	public Position getPosition() {
		return this.position;
	}

	@Override
	public void setSize(Size size) {
		System.out.println("Circle set Size, new size: " + size + " old size: " + this.size);
		//Circle can not be oval -> size.height = size_weight
		Size newSize = null;
//		if(this.size.getWidth() == size.getWidth()) {
//			newSize = new Size(size.getHeight(), size.getHeight());
//		}
//		else if(this.size.getHeight() == size.getHeight()) {
//			newSize = new Size(size.getWidth(), size.getWidth());
//		}
//		else {
//			int heightDif = Math.abs(this.size.getHeight() - size.getHeight());
//			System.out.println("heightDif: " + heightDif);
//			int widthDif = Math.abs(this.size.getWidth() - size.getWidth());
//			System.out.println("widthDif: " + widthDif);
//			if(heightDif > widthDif){
//				newSize = new Size(size.getHeight(), size.getHeight());
//			}
//			else {
//				newSize = new Size(size.getWidth(), size.getWidth());
//			}
//		}
		
		newSize = size;
		
		this.size = newSize;
		System.out.println("Circle set size: " + this.size);

		notifyItemChangedListeners();
	}

	@Override
	public Size getSize() {
		return this.size;
	}

}
