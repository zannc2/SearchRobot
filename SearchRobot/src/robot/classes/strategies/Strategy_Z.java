package robot.classes.strategies;

import frontend.classes.view.Field;
import helper.Position;
import helper.Size;

import java.util.ArrayList;
import java.util.List;

import robot.classes.FieldMatrix;
import robot.classes.RobotController;

public class Strategy_Z {
	private Size fieldSize;
	private int EPSILON = 10;
	// field values
	private final int UNKNOWN = 0;
	private final int ITEM = 1;
	private final int FINISH = 2;
	private final int FREE = 3;

	private Field field;
	
	private FieldMatrix foundMatrix;
	
	private RobotController robotController;
	
	public Strategy_Z(RobotController robotController, FieldMatrix foundMatrix, Size fieldSize, Field field) {
		this.robotController = robotController;
		this.foundMatrix = foundMatrix;
		this.fieldSize = fieldSize;
		this.field = field;
	}
	
	public List<Position> computePath() {	
		
		List<Position> movePath = new ArrayList<>();
		
		//check if finish is found
		for(int i = 0; i < this.fieldSize.getWidth()/this.EPSILON; i++){
			for(int j = 0; j < this.fieldSize.getHeight()/this.EPSILON; j++) {
				if(this.foundMatrix.contains(new Position(i, j)) == this.FINISH)
				{ 
					Position finishP = new Position(i,j);
					return movePath = this.robotController.computePathToFinish(finishP);
				}
			}
		}
		
		// finish not found yet
		Position nextP = computeNextPosition();
		movePath = computePathToNextPosition(nextP);
		
		return movePath;
	}


	private List<Position> computePathToNextPosition(Position pathFinish) {
		List<Position> movePath = new ArrayList<>();
		
		System.out.println("pathFinish: " + pathFinish);
		
		Position oldP = new Position(this.field.getRobotPosition().getOriginX(),
				this.field.getRobotPosition().getOriginY());
		System.out.println("Robot Position: " + oldP);
		
		int deltaX = pathFinish.getOriginX() - oldP.getOriginX();
		int deltaY = pathFinish.getOriginY() - oldP.getOriginY();
		System.out.println("deltaX: " + deltaX + " deltaY: " + deltaY);
		
		double angle = Math.toDegrees(Math.atan(((double)deltaY)/((double)deltaX)));
		
		System.out.println("winkel: " + angle);
		
		double x = this.EPSILON / Math.tan(Math.toRadians(angle));
		double y = Math.tan(Math.toRadians(angle)) * this.EPSILON;
		
		if(deltaX < 0 && deltaY < 0){
			if(x >= this.EPSILON) {
				x = -this.EPSILON;
				y = -y;
			}
			else {
				y = -this.EPSILON;
				x = -x;
			}
		}
		else if(deltaX >= 0 && deltaY < 0) {
			if(x <= -this.EPSILON) x = this.EPSILON;
			else {
				y = -this.EPSILON;
				x = -x;
			}
		}
		else if(deltaX < 0 && deltaY >= 0){
			if(x <= -this.EPSILON) {
				x = -this.EPSILON;
				y = -y;
			}
			else y = this.EPSILON;
		}
		else {
			//deltaX && deltaY >=0
			if(x >= this.EPSILON) x = this.EPSILON;
			else y = this.EPSILON;
		}
		
		System.out.println("x: " + x + " y: " + y);
		

		int newX = (int) (oldP.getOriginX() + x);
		int newY = (int) (oldP.getOriginY() + y);
		Position nextP = new Position(newX, newY);
		
		boolean free = true;
		while(free) {
			System.out.println("nextP: " + nextP);

			int foundMatrixPosition = this.foundMatrix.contains(new Position(newX/this.EPSILON, newY/this.EPSILON));
			
			//Position is not free, there is a item
			if(foundMatrixPosition == this.ITEM) {
				//check direction
				if(angle > 315 && angle < 45) {
					//check if east Position of oldP is free
					Position eastP = new Position(oldP.getOriginX() + this.EPSILON, oldP.getOriginY());
					if(this.foundMatrix.contains(new Position(eastP.getOriginX()/this.EPSILON, eastP.getOriginY()/this.EPSILON)) == this.FREE) {
						movePath.add(eastP);
						oldP = eastP;
					}
					//east Position is unknown, scan again
					else if(foundMatrixPosition == this.UNKNOWN) return movePath;
				}
				else if(angle > 45 && angle < 135) {				
					//check if north Position of oldP is free
					Position northP = new Position(oldP.getOriginX(), oldP.getOriginY() - this.EPSILON);
					if(this.foundMatrix.contains(new Position(northP.getOriginX()/this.EPSILON, northP.getOriginY()/this.EPSILON)) == this.FREE) {
						movePath.add(northP);
						oldP = northP;
					}
					//north Position is unknown, scan again
					else if(foundMatrixPosition == this.UNKNOWN) return movePath;
				}
				else if(angle > 135 && angle < 225) {
					//check if west Position of oldP is free
					Position westP = new Position(oldP.getOriginX() - this.EPSILON, oldP.getOriginY());
					if(this.foundMatrix.contains(new Position(westP.getOriginX()/this.EPSILON, westP.getOriginY()/this.EPSILON)) == this.FREE) {
						movePath.add(westP);
						oldP = westP;
					}
					//west Position is unknown, scan again
					else if(foundMatrixPosition == this.UNKNOWN) return movePath;
				}
				else {				
					//check if south Position of oldP is free
					Position southP = new Position(oldP.getOriginX(), oldP.getOriginY() + this.EPSILON);
					if(this.foundMatrix.contains(new Position(southP.getOriginX()/this.EPSILON, southP.getOriginY()/this.EPSILON)) == this.FREE) {
						movePath.add(southP);
						oldP = southP;
					}
					//south Position is unknown, scan again
					else if(foundMatrixPosition == this.UNKNOWN) return movePath;
				}
			}
			//Position is not free, it is unknown, scan again
			else if(foundMatrixPosition == this.UNKNOWN) return movePath;
			//Position is free
			else {
				movePath.add(nextP);
				oldP = nextP;
			}
			
			//compute next Position
			newX = (int) (oldP.getOriginX() + x);
			newY = (int) (oldP.getOriginY() + y);
			nextP = new Position(newX, newY);
			free= false;
			
			//Test if Path finalFosition is nextP
			if((newX/this.EPSILON - pathFinish.getOriginX()/this.EPSILON) > -this.EPSILON && (newX/this.EPSILON - pathFinish.getOriginX()/this.EPSILON) < this.EPSILON
					 && (newY/this.EPSILON - pathFinish.getOriginY()/this.EPSILON) > -this.EPSILON && (newY/this.EPSILON - pathFinish.getOriginY()/this.EPSILON) < this.EPSILON) free = false;
		}
		
		
		
		return movePath;
//		return computePathToFinish(oldP);
	}


	private Position computeNextPosition() {
		for(int line = 0; line < this.fieldSize.getHeight()/this.EPSILON; line++) {
			for(int row = 0; row < this.fieldSize.getWidth()/this.EPSILON; row++) {
				
				if(this.foundMatrix.contains(new Position(row, line)) == this.FREE) {
					boolean neighbor = checkNeighbors(new Position(row, line));
					if (!neighbor) { 
						return new Position(row, line);
					}
				}
			}
		}
		return null;
	}


	private boolean checkNeighbors(Position position) {
		int x = position.getOriginX();
		int y = position.getOriginY();
		
		// x on left boarder
		if(x == 0) {
			// y on top boarder
			if(y == 0) {
				if(this.foundMatrix.contains(new Position(x + 1, y)) == this.UNKNOWN) return false;
				
				else if(this.foundMatrix.contains(new Position(x, y + 1)) == this.UNKNOWN) return false;
				else if(this.foundMatrix.contains(new Position(x + 1, y + 1)) == this.UNKNOWN) return false;
			}
			// y on bottom border
			else if(y == this.fieldSize.getHeight()/this.EPSILON -1){
				if(this.foundMatrix.contains(new Position(x, y -1)) == this.UNKNOWN) return false;
				else if(this.foundMatrix.contains(new Position(x + 1, y -1)) == this.UNKNOWN) return false;
				
				else if(this.foundMatrix.contains(new Position(x + 1, y)) == this.UNKNOWN) return false;
			}
			// y not on border
			else {
				if(this.foundMatrix.contains(new Position(x, y -1)) == this.UNKNOWN) return false;
				else if(this.foundMatrix.contains(new Position(x + 1, y -1)) == this.UNKNOWN) return false;
				
				else if(this.foundMatrix.contains(new Position(x + 1, y)) == this.UNKNOWN) return false;
				
				else if(this.foundMatrix.contains(new Position(x, y + 1)) == this.UNKNOWN) return false;
				else if(this.foundMatrix.contains(new Position(x + 1, y + 1)) == this.UNKNOWN) return false;
			}
		}
		// x on right border
		else if(x == this.fieldSize.getWidth()/this.EPSILON -1) {

			// y on top boarder
			if(y == 0) {				
				if(this.foundMatrix.contains(new Position(x -1, y )) == this.UNKNOWN) return false;
				
				else if(this.foundMatrix.contains(new Position(x -1, y +1)) == this.UNKNOWN) return false;
				else if(this.foundMatrix.contains(new Position(x, y + 1)) == this.UNKNOWN) return false;
			}
			// y on bottom border
			else if(y == this.fieldSize.getHeight()/this.EPSILON -1){
				if(this.foundMatrix.contains(new Position(x -1, y -1)) == this.UNKNOWN) return false;
				else if(this.foundMatrix.contains(new Position(x, y -1)) == this.UNKNOWN) return false;
				
				else if(this.foundMatrix.contains(new Position(x -1, y )) == this.UNKNOWN) return false;
			}
			// y not on border
			else {
				if(this.foundMatrix.contains(new Position(x -1, y -1)) == this.UNKNOWN) return false;
				else if(this.foundMatrix.contains(new Position(x, y -1)) == this.UNKNOWN) return false;
				
				else if(this.foundMatrix.contains(new Position(x -1, y )) == this.UNKNOWN) return false;
				
				else if(this.foundMatrix.contains(new Position(x -1, y +1)) == this.UNKNOWN) return false;
				else if(this.foundMatrix.contains(new Position(x, y + 1)) == this.UNKNOWN) return false;
			}
		}
		// x not on border
		else {

			// y on top boarder
			if(y == 0) {				
				if(this.foundMatrix.contains(new Position(x -1, y )) == this.UNKNOWN) return false;
				else if(this.foundMatrix.contains(new Position(x + 1, y)) == this.UNKNOWN) return false;
				
				else if(this.foundMatrix.contains(new Position(x -1, y +1)) == this.UNKNOWN) return false;
				else if(this.foundMatrix.contains(new Position(x, y + 1)) == this.UNKNOWN) return false;
				else if(this.foundMatrix.contains(new Position(x + 1, y + 1)) == this.UNKNOWN) return false;				
			}
			// y on bottom border
			else if(y == this.fieldSize.getHeight()/this.EPSILON -1){
				if(this.foundMatrix.contains(new Position(x -1, y -1)) == this.UNKNOWN) return false;
				else if(this.foundMatrix.contains(new Position(x, y -1)) == this.UNKNOWN) return false;
				else if(this.foundMatrix.contains(new Position(x + 1, y -1)) == this.UNKNOWN) return false;
				
				else if(this.foundMatrix.contains(new Position(x -1, y )) == this.UNKNOWN) return false;
				else if(this.foundMatrix.contains(new Position(x + 1, y)) == this.UNKNOWN) return false;				
			}
			// y not on border
			else {
				if(this.foundMatrix.contains(new Position(x -1, y -1)) == this.UNKNOWN) return false;
				else if(this.foundMatrix.contains(new Position(x, y -1)) == this.UNKNOWN) return false;
				else if(this.foundMatrix.contains(new Position(x + 1, y -1)) == this.UNKNOWN) return false;
				
				else if(this.foundMatrix.contains(new Position(x -1, y )) == this.UNKNOWN) return false;
				else if(this.foundMatrix.contains(new Position(x + 1, y)) == this.UNKNOWN) return false;
				
				else if(this.foundMatrix.contains(new Position(x -1, y +1)) == this.UNKNOWN) return false;
				else if(this.foundMatrix.contains(new Position(x, y + 1)) == this.UNKNOWN) return false;
				else if(this.foundMatrix.contains(new Position(x + 1, y + 1)) == this.UNKNOWN) return false;				
			}
		}
		
		return true;
	}
}
