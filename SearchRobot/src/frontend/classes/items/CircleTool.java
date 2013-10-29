package frontend.classes.items;

import helper.Position;
import helper.Size;
import frontend.interfaces.Item;
import frontend.interfaces.Tool;
import frontend.interfaces.View;

public class CircleTool implements Tool {

	private Item i;
	private View v;

	public CircleTool(View v) {
		this.v = v;
	}

	@Override
	public void mouseDown(Position p) {
		this.i = new Circle(p);
		getView().addItem(this.i);
		//System.out.println("Circle Created and added to View");
	}

	@Override
	public void mouseDrag(Position p) {
//		int newX = i.getPosition().getOriginX();
//		int newY = i.getPosition().getOriginY();
//		
//
//		int xsize = p.getOriginX()-i.getPosition().getOriginX();
//		int ysize = p.getOriginY()-i.getPosition().getOriginY();
//		
//		if(xsize <0) {
//			newX = newX + xsize;
//			xsize = -xsize;
//		}
//		
//		if(ysize < 0) {
//			newY = newY + ysize;
//			ysize = -ysize;
//		}
//		
//		if(xsize > ysize)
//		{
//			i.setSize(new Size(xsize, xsize));
//		}
//		else
//		{
//			i.setSize(new Size(ysize, ysize));
//		}
//		
//		i.setPosition(new Position(newX, newY));

		int actualX = i.getPosition().getOriginX();
		int actualY = i.getPosition().getOriginY();
		
		i.setSize(new Size(p.getOriginX() - actualX, p.getOriginY() -  actualY));
		
		//System.out.println("X-Pos: " + i.getPosition().getOriginX() + " YPos: " + i.getPosition().getOriginY() + " height: " + i.getSize().getHeight() + "Width: " + i.getSize().getWidth());
	}

	@Override
	public void mouseUp(Position p) {
		mouseDrag(p);
	}	
	
	private View getView() {
		return this.v;
	}
}
