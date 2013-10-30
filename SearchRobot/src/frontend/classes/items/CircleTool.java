package frontend.classes.items;

import helper.Position;
import helper.Size;
import frontend.classes.view.Field;
import frontend.interfaces.Item;

public class CircleTool extends AbstractTool {

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
	public void mouseDrag(Position p) {
		Position itemP = i.getPosition();
		Size itemS = i.getSize();
		
		System.out.println("mouse Drag, Origin: " + itemP + " Position: " + p + " size " + itemS);
		
		int originX;
		int originY;
		int width;
		int height;
		
		if(itemP.getOriginX() < p.getOriginX() && 
				(itemP.getOriginX() + itemS.getWidth()) > p.getOriginX()){
			// position between item Origin and Item size
			originX = itemP.getOriginX();
			width = p.getOriginX() - originX;
		}
		else if(itemP.getOriginX() > p.getOriginX()){
			// position smaller then Item origin
			width = itemS.getWidth() + itemP.getOriginX() -p.getOriginX();
			originX = p.getOriginX();
		}
		else {
			// normal
			originX = itemP.getOriginX();
			width = p.getOriginX() - itemP.getOriginX();
		}
		
		if(itemP.getOriginY() < p.getOriginY() && 
				(itemP.getOriginY() + itemS.getHeight()) > p.getOriginY()) {
			// Position between Item Origin and Item size
			originY = itemP.getOriginY();
			height = p.getOriginY() - originY;
		}
		else if(itemP.getOriginY() > p.getOriginY()){
			// Position smaler than Item Origin
			height = itemS.getHeight() + itemP.getOriginY() - p.getOriginY();
			originY = p.getOriginY();
		}
		else {
			// normal
			originY = itemP.getOriginY();
			height = p.getOriginY() - itemP.getOriginY();
		}
		
		i.setPosition(new Position(originX, originY));
		i.setSize(new Size(width, height));

		//normal
//		int actualX = i.getPosition().getOriginX();
//		int actualY = i.getPosition().getOriginY();
//		System.out.println("tool, x: " + actualX + " y: " + actualY);
		
//		i.setSize(new Size(p.getOriginX() - actualX, p.getOriginY() -  actualY));
		
		//System.out.println("X-Pos: " + i.getPosition().getOriginX() + " YPos: " + i.getPosition().getOriginY() + " height: " + i.getSize().getHeight() + "Width: " + i.getSize().getWidth());
	}

	@Override
	public void mouseUp(Position p) {
		mouseDrag(p);
	}	
}
