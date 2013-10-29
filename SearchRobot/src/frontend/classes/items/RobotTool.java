package frontend.classes.items;

import helper.Position;
import frontend.interfaces.Item;
import frontend.interfaces.Tool;
import frontend.interfaces.View;

public class RobotTool implements Tool {

	private Item i;
	private View v;

	public RobotTool(View v) {
		this.v = v;
	}

	public void mouseDown(Position p) {
		this.i = new Robot(p);
		getView().addItem(this.i);
		System.out.println("Robot Created and added to View");
	}

	public void mouseDrag(Position p) {

	}

	public void mouseUp(Position p) {

	}	
	
	private View getView() {
		return this.v;
	}
}
