package frontend.impl.items;

import frontend.impl.view.Field;
import frontend.interfaces.ItemHandler;
import helper.Direction;
import helper.Position;
import helper.Size;
import helper.Vector;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

public class Robot extends AbstractItem{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1654749892286238788L;
	
	private Position position;
	private Size size;
	private Direction direction = Direction.WEST;
	
	public int testV;

	private Field field;

	public Robot(Position p, Size s, Field field) {
		this.position = p;
		this.size = s;
		this.field = field;
		
		//TODO create hanlder
	}
	
	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(1));
		BufferedImage robot;
		try {
            //robot = ImageIO.read(new File("src/frontend/impl/resources/draw_robot1.png"));
            robot = ImageIO.read(getClass().getResource("/draw_robot1.png"));
            AffineTransform tx = AffineTransform.getRotateInstance(direction.getDirection(), 5, 5);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
            g2.drawImage(op.filter(robot, null), this.position.getOriginX(), this.position.getOriginY(), null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }		
	}

	@Override
	public void setPosition(Position p) {
		if(p.getOriginX()>=0 && p.getOriginY() >= 0 && p.getOriginX() <= field.getFieldSize().getWidth()-10 && p.getOriginY() 
				<= field.getFieldSize().getHeight()-10){
			this.position = p;
		}
		notifyItemChangedListeners();
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
		return isCloseToLine(p, 2);
	}
	
	/**
	 * calculations if Position is in line
	 * @param p Position
	 * @param epsilon 
	 * @return
	 */
	private boolean isCloseToLine(Position p, int epsilon) {
		boolean rval = false;
		for(int i = this.position.getOriginX(); i<position.getOriginX() + 10; i++)
		{
			for(int j = this.position.getOriginY(); j < this.position.getOriginY()+10; j++)
			{
				if(p.getOriginX() == i && p.getOriginY() == j) rval = true;
			}
		}
		return rval;
	}

	@Override
	public List<ItemHandler> getItemHandler() {
		//not used here
		return null;
	}

	@Override
	public boolean contains(Position p, int epsilon) {
		return contains(p);
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}
}
