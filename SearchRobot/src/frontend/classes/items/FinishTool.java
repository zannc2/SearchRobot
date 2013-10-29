package frontend.classes.items;

import helper.Position;
import frontend.interfaces.Item;
import frontend.interfaces.Tool;
import frontend.interfaces.View;

public class FinishTool implements Tool {

	private Item i;
	private View v;

	public FinishTool(View v) {
		this.v = v;
	}

	public void mouseDown(Position p) {
		this.i = new Finish(p);
		getView().addItem(this.i);
		System.out.println("Finish Created and added to View");
	}

	public void mouseDrag(Position p) {

	}

	public void mouseUp(Position p) {

	}	
	
	private View getView() {
		return this.v;
	}
}