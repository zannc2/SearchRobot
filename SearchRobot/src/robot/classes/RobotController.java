package robot.classes;

import helper.Position;
import helper.Size;
import helper.Vector;

public class RobotController {
	private Size fieldSize;
	private Position robotPosition;
	private Vector direction;
	private Position eyePosition;
	
	public void startRobotSearch(Size fieldSize, Position p, Size roboterSize, Vector direction) {
		this.fieldSize = fieldSize;
		this.robotPosition = p;
		this.direction = direction;
		
		this.eyePosition = new Position(p.getOriginX() + roboterSize.getWidth(),
				p.getOriginY() + roboterSize.getHeight()/2);
		
		scanField();
	}
	
	private void scanField() {
		System.out.println("scan Field");
		
		// draw eye
		
	}

}
