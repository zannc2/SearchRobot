package frontend.classes.items;

import helper.Position;
import helper.Size;

import java.awt.Rectangle;
import java.util.List;

import frontend.classes.view.Field;
import frontend.interfaces.Item;

public class CircleTool extends AbstractTool {

	/**
	 * 
	 */
	private static final long serialVersionUID = -994596525815413661L;

	public CircleTool(Field field) {
		super(field);
	}

	private Item i;

	@Override
	public void mouseDown(Position p) {
		this.i = new Circle(p);
		getField().addItem(this.i);
		//System.out.println("Circle Created and added to View");
	}

	@Override
	public void mouseDrag(Position p) 
	{
		int actualX = i.getPosition().getOriginX();
		int actualY = i.getPosition().getOriginY();
		i.setSize(new Size(p.getOriginX() - actualX, p.getOriginY() -  actualY));
	}
	
	private Rectangle getAWTRectangle() {
		Rectangle r = null;
		int width = i.getSize().getWidth();
		int height = i.getSize().getHeight();
		if (width >= 0) {
			if (height >= 0) {
				
				if(width > height)
				{
					r = new Rectangle(i.getPosition().getOriginX(), i.getPosition()
							.getOriginY(), width, width);
				}
				else
				{
					r = new Rectangle(i.getPosition().getOriginX(), i.getPosition()
							.getOriginY(), height, height);
				}
				
			} else {
				// width >= 0 && height < 0
				if(Math.abs(width) > Math.abs(height))
				{
					r = new Rectangle(i.getPosition().getOriginX(), i.getPosition()
							.getOriginY() - width, width, width);
				}
				else
				{
					r = new Rectangle(i.getPosition().getOriginX(), i.getPosition()
							.getOriginY() + height, -height, -height);
				}
			}
		} else {
			if (height >= 0) {
				if(Math.abs(width) > Math.abs(height))
				{
					r = new Rectangle(i.getPosition().getOriginX() + width, i.getPosition()
						.getOriginY(), -width, -width);
				}
				else
				{
					r = new Rectangle(i.getPosition().getOriginX() - height, i.getPosition()
							.getOriginY(), height, height);
				}
			} else {
				// width < 0 && height < 0
				if(Math.abs(width) > Math.abs(height))
				{
				r = new Rectangle(i.getPosition().getOriginX() + width, i.getPosition()
						.getOriginY()
						+ width, -width, -width);
				}
				else
				{
					r = new Rectangle(i.getPosition().getOriginX() + height, i.getPosition()
							.getOriginY()
							+ height, -height, -height);
				}
			}
		}
		return r;
	}

	@Override
	public void mouseUp(Position p) {
		
		Rectangle r = getAWTRectangle();
		i.setPosition(new Position((int)r.getX(), (int)r.getY()));
		i.setSize(new Size((int)r.getWidth(), (int)r.getHeight()));
		
		List<Item> items = field.getItems();
		for(int j = 0; j < items.size(); j++)
		{
			if(items.get(j) instanceof Robot)
			{
				Robot robot = (Robot) items.get(j);
				Position robotP = robot.getPosition();
				Size robotS = robot.getSize();
				if(i.contains(new Position(robotP.getOriginX(), robotP.getOriginY())) ||
						i.contains(new Position(robotP.getOriginX(), robotP.getOriginY() + robotS.getHeight())) ||
						i.contains(new Position(robotP.getOriginX() + robotS.getWidth(), robotP.getOriginY())) ||
						i.contains(new Position(robotP.getOriginX() + robotS.getWidth(), robotP.getOriginY() + robotS.getHeight())) ||
						i.contains(new Position(robotP.getOriginX() + robotS.getWidth()/2, robotP.getOriginY())) ||
						i.contains(new Position(robotP.getOriginX() + robotS.getWidth()/2, robotP.getOriginY() + robotS.getHeight())) ||
						i.contains(new Position(robotP.getOriginX(), robotP.getOriginY() + robotS.getHeight()/2)) ||
						i.contains(new Position(robotP.getOriginX() + robotS.getWidth(), robotP.getOriginY() + robotS.getHeight()/2)))
				{
					field.removeItem(i);
				}
			}
			else if(items.get(j) instanceof Finish)
			{
				Finish finish = (Finish) items.get(j);
				Position finishP = finish.getPosition();
				Size finishS = finish.getSize();
				if(i.contains(new Position(finishP.getOriginX(), finishP.getOriginY())) ||
						i.contains(new Position(finishP.getOriginX(), finishP.getOriginY() + finishS.getHeight())) ||
						i.contains(new Position(finishP.getOriginX() + finishS.getWidth(), finishP.getOriginY())) ||
						i.contains(new Position(finishP.getOriginX() + finishS.getWidth(), finishP.getOriginY() + finishS.getHeight())) ||
						i.contains(new Position(finishP.getOriginX() + finishS.getWidth()/2, finishP.getOriginY())) ||
						i.contains(new Position(finishP.getOriginX() + finishS.getWidth()/2, finishP.getOriginY() + finishS.getHeight())) ||
						i.contains(new Position(finishP.getOriginX(), finishP.getOriginY() + finishS.getHeight()/2)) ||
						i.contains(new Position(finishP.getOriginX() + finishS.getWidth(), finishP.getOriginY() + finishS.getHeight()/2)))
				{
					field.removeItem(i);
				}
			}
		}
	}

	@Override
	public void mouseOver(Position p) {
		//not needed
	}
}
