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

	public void mouseDown(Position p) {
		this.i = new Circle(p);
		getView().addItem(this.i);
		//System.out.println("Circle Created and added to View");
	}

	public void mouseDrag(Position p) {
		int newX = i.getPosition().getOriginX();
		int newY = i.getPosition().getOriginY();
		

		int xsize = Math.abs(p.getOriginX()-i.getPosition().getOriginX());
		int ysize = Math.abs(p.getOriginY()-i.getPosition().getOriginY());
		
		if(xsize > ysize)
		{
			i.setSize(new Size(xsize, xsize));
		}
		else
		{
			i.setSize(new Size(ysize, ysize));
		}
		
		i.setPosition(new Position(newX, newY));
		
		//System.out.println("X-Pos: " + i.getPosition().getOriginX() + " YPos: " + i.getPosition().getOriginY() + " height: " + i.getSize().getHeight() + "Width: " + i.getSize().getWidth());
	}

	public void mouseUp(Position p) {
		
		mouseDrag(p);
	}	
	
	private View getView() {
		return this.v;
	}
}
