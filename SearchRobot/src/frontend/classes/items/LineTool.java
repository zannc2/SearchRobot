package frontend.classes.items;

import helper.Position;
import helper.Size;

import java.util.List;

import frontend.classes.view.Field;
import frontend.interfaces.Item;


public class LineTool extends AbstractTool {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3403869222008259766L;


	public LineTool(Field field) {
		super(field);
	}

	private Item i;
	

	@Override
	public void mouseDown(Position p) {
		this.i = new Line(p);
		getField().addItem(this.i);
		//System.out.println("Line Created and added to View");
	}

	@Override
	public void mouseDrag(Position p) {
		int actualX = i.getPosition().getOriginX();
		int actualY = i.getPosition().getOriginY();
		
		i.setSize(new Size(p.getOriginX() - actualX, p.getOriginY() -  actualY));
//		System.out.println("X-Pos: " + actualX + " YPos: " + actualY + " height: " + i.getSize().getHeight() + "Width: " + i.getSize().getWidth());
	}

	@Override
	public void mouseUp(Position p) {
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
			else
			{
				mouseDrag(p);
			}
		}
	}
		
	@Override
	public void mouseOver(Position p) {
		// Not needed
	}

}
