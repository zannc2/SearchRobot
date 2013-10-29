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

	@Override
	public void mouseDown(Position p) {
		this.i = new Finish(p);
		getView().addItem(this.i);
	}

	@Override
	public void mouseDrag(Position p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseUp(Position p) {
		// TODO Auto-generated method stub
		
	}
	
	private View getView() {
		return this.v;
	}
}