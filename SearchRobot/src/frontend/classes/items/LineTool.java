package frontend.classes.items;

import helper.Position;
import helper.Size;
import frontend.interfaces.Item;
import frontend.interfaces.Tool;
import frontend.interfaces.View;


public class LineTool implements Tool {

	private Item i;
	private View view;
	
	public LineTool(View view) {
		this.view = view;
	}

	@Override
	public void mouseDown(Position p) {
		this.i = new Line(p);
		getView().addItem(this.i);
		//System.out.println("Line Created and added to View");
	}

	@Override
	public void mouseDrag(Position p) {
		int actualX = i.getPosition().getOriginX();
		int actualY = i.getPosition().getOriginY();
		
		i.setSize(new Size(p.getOriginX() - actualX, p.getOriginY() -  actualY));
		System.out.println("X-Pos: " + actualX + " YPos: " + actualY + " height: " + i.getSize().getHeight() + "Width: " + i.getSize().getWidth());
	}

	@Override
	public void mouseUp(Position p) {
		mouseDrag(p);
	}

	public View getView() {
		return view;
	}	
}
