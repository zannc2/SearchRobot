package frontend.impl.items;

import frontend.impl.view.Field;
import frontend.interfaces.ItemHandler;
import helper.Position;
import helper.Size;
import helper.Vector;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

public class Finish extends AbstractItem {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8323053597903637271L;
	
	private Position position;
	private Size size = new Size(20, 20);
	
	private Field field;
	
	public Finish(Position p, Field field) {
		this.position = p;
		this.field = field;
		

		notifyItemChangedListeners();
		//TODO create handler
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(1));
		Image finish;
		try {
            //finish = ImageIO.read(new File("src/frontend/impl/resources/finish20x20.png"));
            finish = ImageIO.read(getClass().getResource("/finish20x20.png"));
            g2.drawImage(finish, this.position.getOriginX(), this.position.getOriginY(), null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
	}

	@Override
	public void setPosition(Position p) {
		if(p.getOriginX()>=0 && p.getOriginY() >= 0 && p.getOriginX() <= field.getFieldSize().getWidth()-20 && p.getOriginY() 
				<= field.getFieldSize().getHeight()-20){
			this.position = p;
		}
		
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
		
		setPosition(new Position(x, y));
		
		notifyItemChangedListeners();

		//TODO
		/* set handles */
	}

	@Override
	public boolean contains(Position p) {
		return contains(p, 0);
	}

	@Override
	public List<ItemHandler> getItemHandler() {
		//not used here
		return null;
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

}
