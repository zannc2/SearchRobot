package robot.classes.strategies;

import frontend.classes.view.Field;
import helper.Direction;
import helper.Position;
import helper.Size;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import robot.classes.FieldMatrix;
import robot.classes.RobotController;
import robot.interfaces.Strategy;

public class Strategy_Z implements Strategy{
	private Size fieldSize;
	private int EPSILON = 10;
	// field values
	private final int UNKNOWN = 0;
	private final int ITEM = 1;
	private final int FINISH = 2;
	private final int FREE = 3;

	private Field field;
	
	RobotController robotController;
	
	private FieldMatrix foundMatrix;
	
	public Strategy_Z(RobotController robotController, Size fieldSize, Field field) {
		this.robotController = robotController;
		this.fieldSize = fieldSize;
		this.field = field;
	}
	
	@Override
	public List<Position> computePath() {	
		System.out.println("Strategy_Z computePath()");
		
		this.foundMatrix = robotController.getFoundMatrix();
		
		List<Position> movePath = new ArrayList<>();
		
		// finish not found yet
		Position nextP = computeNextPosition();
		System.out.println("computeNextPosition: " + nextP);
		System.out.println("robotPosition: " + this.field.getRobotPosition());
		movePath = computePathToNextPosition(nextP);
		
		return movePath;
	}

	private List<Position> computePathToNextPosition(Position pathFinish) {
		List<Position> movePath = new ArrayList<>();
		
		Position oldP = this.field.getRobotPosition();
		
		int deltaX = pathFinish.getOriginX() - oldP.getOriginX();
		int deltaY = pathFinish.getOriginY() - oldP.getOriginY();
		System.out.println("deltaX: " + deltaX + " deltaY: " + deltaY);
		
		double angle = Math.toDegrees(Math.atan(((double)deltaY)/((double)deltaX)));
		
		System.out.println("winkel: " + angle);
		
		Direction direction;
		// detect direction
		if(angle > 315 && angle <= 45) direction = Direction.EAST;
		else if(angle > 45 && angle <= 135) direction = Direction.NORTH;
		else if(angle > 135 && angle <= 225) direction = Direction.WEST;
		else direction = Direction.SOUTH;
		
		System.out.println("direction: " + direction);
		
		return movePath;
	}

//	private List<Position> computePathToNextPosition(Position pathFinish) {
//		List<Position> movePath = new ArrayList<>();
//		
//		Position oldP = this.field.getRobotPosition();
//		
//		int deltaX = pathFinish.getOriginX() - oldP.getOriginX();
//		int deltaY = pathFinish.getOriginY() - oldP.getOriginY();
//		System.out.println("deltaX: " + deltaX + " deltaY: " + deltaY);
//		
//		double angle = Math.toDegrees(Math.atan(((double)deltaY)/((double)deltaX)));
//		
//		System.out.println("winkel: " + angle);
//		
//		double x = this.EPSILON / Math.tan(Math.toRadians(angle));
//		double y = Math.tan(Math.toRadians(angle)) * this.EPSILON;
//		
//		if(deltaX < 0 && deltaY < 0){
//			if(x >= this.EPSILON) {
//				x = -this.EPSILON;
//				y = -y;
//			}
//			else {
//				y = -this.EPSILON;
//				x = -x;
//			}
//		}
//		else if(deltaX >= 0 && deltaY < 0) {
//			if(x <= -this.EPSILON) x = this.EPSILON;
//			else {
//				y = -this.EPSILON;
//				x = -x;
//			}
//		}
//		else if(deltaX < 0 && deltaY >= 0){
//			if(x <= -this.EPSILON) {
//				x = -this.EPSILON;
//				y = -y;
//			}
//			else y = this.EPSILON;
//		}
//		else {
//			//deltaX && deltaY >=0
//			if(x >= this.EPSILON) x = this.EPSILON;
//			else y = this.EPSILON;
//		}
//		
//		System.out.println("x: " + x + " y: " + y);
//		
//
//		int newX = (int) (oldP.getOriginX() + x);
//		int newY = (int) (oldP.getOriginY() + y);
//		Position nextP = new Position(newX, newY);
//		
//		Direction itemAround = null;
//		boolean free = true;
//		while(free) {
//			int foundMatrixPosition = this.foundMatrix.contains(new Position(newX/this.EPSILON, newY/this.EPSILON));
//			
//			//Position is not free, there is a item
//			if(foundMatrixPosition == this.ITEM) {
//				//check direction
//				if(angle > 315 && angle < 45 || itemAround == Direction.EAST) {
//					//check if east Position of oldP is free
//					Position eastP = new Position(oldP.getOriginX() + this.EPSILON, oldP.getOriginY());
//					if(this.foundMatrix.contains(new Position(eastP.getOriginX()/this.EPSILON, eastP.getOriginY()/this.EPSILON)) == this.FREE) {
//						movePath.add(eastP);
//						oldP = eastP;
//						itemAround = Direction.EAST;
//						
//						//compute next Position
//						newX = (int) (oldP.getOriginX() + x);
//						newY = (int) (oldP.getOriginY() + y);
//						nextP = new Position(newX, newY);
//					}
//					//east Position is unknown, scan again
//					else if(foundMatrixPosition == this.UNKNOWN) return movePath;
//					else{
//						itemAround=Direction.SOUTH;
//					}
//				}
//				else if(angle > 45 && angle < 135 || itemAround == Direction.NORTH) {				
//					//check if north Position of oldP is free
//					Position northP = new Position(oldP.getOriginX(), oldP.getOriginY() - this.EPSILON);
//					if(this.foundMatrix.contains(new Position(northP.getOriginX()/this.EPSILON, northP.getOriginY()/this.EPSILON)) == this.FREE) {
//						movePath.add(northP);
//						oldP = northP;
//						itemAround = Direction.NORTH;
//						
//						//compute next Position
//						newX = (int) (oldP.getOriginX() + x);
//						newY = (int) (oldP.getOriginY() + y);
//						nextP = new Position(newX, newY);
//					}
//					//north Position is unknown, scan again
//					else if(foundMatrixPosition == this.UNKNOWN) return movePath;
//					else{
//						itemAround=Direction.EAST;
//					}
//				}
//				else if(angle > 135 && angle < 225 || itemAround == Direction.WEST) {
//					//check if west Position of oldP is free
//					Position westP = new Position(oldP.getOriginX() - this.EPSILON, oldP.getOriginY());
//					if(this.foundMatrix.contains(new Position(westP.getOriginX()/this.EPSILON, westP.getOriginY()/this.EPSILON)) == this.FREE) {
//						movePath.add(westP);
//						oldP = westP;
//						itemAround = Direction.WEST;
//						
//						//compute next Position
//						newX = (int) (oldP.getOriginX() + x);
//						newY = (int) (oldP.getOriginY() + y);
//						nextP = new Position(newX, newY);
//					}
//					//west Position is unknown, scan again
//					else if(foundMatrixPosition == this.UNKNOWN) return movePath;
//					else itemAround = Direction.NORTH;
//				}
//				else {				
//					//check if south Position of oldP is free
//					Position southP = new Position(oldP.getOriginX(), oldP.getOriginY() + this.EPSILON);
//					if(this.foundMatrix.contains(new Position(southP.getOriginX()/this.EPSILON, southP.getOriginY()/this.EPSILON)) == this.FREE) {
//						movePath.add(southP);
//						oldP = southP;
//						itemAround = Direction.SOUTH;
//						
//						//compute next Position
//						newX = (int) (oldP.getOriginX() + x);
//						newY = (int) (oldP.getOriginY() + y);
//						nextP = new Position(newX, newY);
//					}
//					//south Position is unknown, scan again
//					else if(foundMatrixPosition == this.UNKNOWN) return movePath;
//					else itemAround = Direction.WEST;
//				}
//			}
//			//Position is not free, it is unknown, scan again
//			else if(foundMatrixPosition == this.UNKNOWN) return movePath;
//			//Position is free
//			else {
//				movePath.add(nextP);
//				oldP = nextP;
//				
//				//compute next Position
//				newX = (int) (oldP.getOriginX() + x);
//				newY = (int) (oldP.getOriginY() + y);
//				nextP = new Position(newX, newY);
//			}
////			free= false;
//			
//			//Test if Path finalFosition is nextP
//			if((newX/this.EPSILON - pathFinish.getOriginX()/this.EPSILON) > -this.EPSILON && (newX/this.EPSILON - pathFinish.getOriginX()/this.EPSILON) < this.EPSILON
//					 && (newY/this.EPSILON - pathFinish.getOriginY()/this.EPSILON) > -this.EPSILON && (newY/this.EPSILON - pathFinish.getOriginY()/this.EPSILON) < this.EPSILON) free = false;
//		}
//		
//		
//		
//		return movePath;
////		return computePathToFinish(oldP);
//	}


	private Position computeNextPosition() {		
		Direction robotDirecion = this.field.getRobotDirection();
		double angle;
		if(robotDirecion == Direction.EAST) angle = 0;
		else if(robotDirecion == Direction.NORTH) angle = 270;
		else if(robotDirecion == Direction.WEST) angle = 180;
		else angle = 90;
		int step = this.EPSILON;
		
		Position robotPosition = this.field.getRobotPosition();
		
		while(true) {
			for(double a = angle; a < 360; a = a+0.1) {
				int x = (int) Math.cos(Math.toRadians(angle)) * step;
				int y = (int) Math.sin(Math.toRadians(angle)) * step;
				System.out.println("x: " + x + " y: " + y);
				System.out.println("width: " + this.fieldSize.getWidth() + " height: " + this.fieldSize.getHeight());
				int newX = robotPosition.getOriginX() + x;
				int newY = robotPosition.getOriginY() + y;
				System.out.println("robotX: " + robotPosition.getOriginX() + " robotY: " + robotPosition.getOriginY());
				System.out.println("newX: " + newX + " newY: " + newY);
				if(newX < this.fieldSize.getWidth() && newY < this.fieldSize.getHeight()) {
					Position newP = new Position(newX, newY);
					if(!checkNeighbors(newP)) {
						return newP;
					}
				}
			}
			angle = 0;
			step = step + this.EPSILON;
		}
	}

	private boolean checkNeighbors(Position position) {
		int x = position.getOriginX()/10;
		int y = position.getOriginY()/10;
		
//		System.out.println("width: " + this.fieldSize.getWidth() + " height: " + this.fieldSize.getHeight());
//		System.out.println("x: " + x + " y: " + y);
		
		// x on left boarder
		if(x == 0) {
//			System.out.println("x on left border");
			// y on top boarder
			if(y == 0) {
//				System.out.println("y on top border");
				if(this.foundMatrix.contains(new Position(x + 1, y)) == this.UNKNOWN) return false;
				
				else if(this.foundMatrix.contains(new Position(x, y + 1)) == this.UNKNOWN) return false;
				else if(this.foundMatrix.contains(new Position(x + 1, y + 1)) == this.UNKNOWN) return false;
			}
			// y on bottom border
			else if(y == this.fieldSize.getHeight()/this.EPSILON -1){
//				System.out.println("y on bottom border");
				if(this.foundMatrix.contains(new Position(x, y -1)) == this.UNKNOWN) return false;
				else if(this.foundMatrix.contains(new Position(x + 1, y -1)) == this.UNKNOWN) return false;
				
				else if(this.foundMatrix.contains(new Position(x + 1, y)) == this.UNKNOWN) return false;
			}
			// y not on border
			else {
//				System.out.println("y not on border");
				if(this.foundMatrix.contains(new Position(x, y -1)) == this.UNKNOWN) return false;
				else if(this.foundMatrix.contains(new Position(x + 1, y -1)) == this.UNKNOWN) return false;
				
				else if(this.foundMatrix.contains(new Position(x + 1, y)) == this.UNKNOWN) return false;
				
				else if(this.foundMatrix.contains(new Position(x, y + 1)) == this.UNKNOWN) return false;
				else if(this.foundMatrix.contains(new Position(x + 1, y + 1)) == this.UNKNOWN) return false;
			}
		}
		// x on right border
		else if(x == ((this.fieldSize.getWidth()/this.EPSILON)-1)) {
//			System.out.println("x on right border");

			// y on top boarder
			if(y == 0) {				
//				System.out.println("y on top border");
				if(this.foundMatrix.contains(new Position(x -1, y )) == this.UNKNOWN) return false;
				
				else if(this.foundMatrix.contains(new Position(x -1, y +1)) == this.UNKNOWN) return false;
				else if(this.foundMatrix.contains(new Position(x, y + 1)) == this.UNKNOWN) return false;
			}
			// y on bottom border
			else if(y == this.fieldSize.getHeight()/this.EPSILON -1){
//				System.out.println("y on bottom border");
				if(this.foundMatrix.contains(new Position(x -1, y -1)) == this.UNKNOWN) return false;
				else if(this.foundMatrix.contains(new Position(x, y -1)) == this.UNKNOWN) return false;
				
				else if(this.foundMatrix.contains(new Position(x -1, y )) == this.UNKNOWN) return false;
			}
			// y not on border
			else {
//				System.out.println("y not on border");
				if(this.foundMatrix.contains(new Position(x -1, y -1)) == this.UNKNOWN) return false;
				else if(this.foundMatrix.contains(new Position(x, y -1)) == this.UNKNOWN) return false;
				
				else if(this.foundMatrix.contains(new Position(x -1, y )) == this.UNKNOWN) return false;
				
				else if(this.foundMatrix.contains(new Position(x -1, y +1)) == this.UNKNOWN) return false;
				else if(this.foundMatrix.contains(new Position(x, y + 1)) == this.UNKNOWN) return false;
			}
		}
		// x not on border
		else {
//			System.out.println("x not on border");
			// y on top boarder
			if(y == 0) {		
//				System.out.println("y on top border");
				if(this.foundMatrix.contains(new Position(x -1, y )) == this.UNKNOWN) return false;
				else if(this.foundMatrix.contains(new Position(x + 1, y)) == this.UNKNOWN) return false;
				
				else if(this.foundMatrix.contains(new Position(x -1, y +1)) == this.UNKNOWN) return false;
				else if(this.foundMatrix.contains(new Position(x, y + 1)) == this.UNKNOWN) return false;
				else if(this.foundMatrix.contains(new Position(x + 1, y + 1)) == this.UNKNOWN) return false;				
			}
			// y on bottom border
			else if(y == this.fieldSize.getHeight()/this.EPSILON -1){
//				System.out.println("y on bottom border");
				if(this.foundMatrix.contains(new Position(x -1, y -1)) == this.UNKNOWN) return false;
				else if(this.foundMatrix.contains(new Position(x, y -1)) == this.UNKNOWN) return false;
				else if(this.foundMatrix.contains(new Position(x + 1, y -1)) == this.UNKNOWN) return false;
				
				else if(this.foundMatrix.contains(new Position(x -1, y )) == this.UNKNOWN) return false;
				else if(this.foundMatrix.contains(new Position(x + 1, y)) == this.UNKNOWN) return false;				
			}
			// y not on border
			else {
//				System.out.println("y not on border");
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
	
	public List<Position> computePathToFinish(Position finishP) {
		System.out.println("strategy Z: compute Path to finish");
		List<Position> movePath = new LinkedList<>();

		Position robotP = new Position(this.field.getRobotPosition().getOriginX()/this.EPSILON,
				this.field.getRobotPosition().getOriginY()/this.EPSILON);
		//		System.out.println("Finish found: " + finishP);
		//		System.out.println("Robot Position: " + robotP);

		int deltaX = finishP.getOriginX() - robotP.getOriginX();
		int deltaY = finishP.getOriginY() - robotP.getOriginY();
		//		System.out.println("deltaX: " + deltaX + " deltaY: " + deltaY);

		double angle = Math.toDegrees(Math.atan(((double)deltaY)/((double)deltaX)));
		//		System.out.println("winkel: " + angle);

		double x = this.EPSILON / Math.tan(Math.toRadians(angle));
		double y = Math.tan(Math.toRadians(angle)) * this.EPSILON;


		double newX = robotP.getOriginX() *this.EPSILON;
		double newY = robotP.getOriginY() *this.EPSILON;

		boolean repeat = true;

		if(deltaX < 0 && deltaY < 0){
			if(x >= this.EPSILON) {
				x = -this.EPSILON;
				y = -y;
			}
			else {
				y = -this.EPSILON;
				x = -x;
			}
			//			System.out.println("x: " + x + " y: " + y);

			while(repeat) {
				newX = newX + x;
				newY = newY + y;

				Position newP = new Position((int)newX, (int)newY);
				movePath.add(newP);
				System.out.println("newP: " + newP + " finish: " + finishP);
				if((newY - finishP.getOriginY()*this.EPSILON) < 10 && (newY - finishP.getOriginY()*this.EPSILON) > -10
						&& (newX - finishP.getOriginX()*this.EPSILON) < 10 && (newX - finishP.getOriginX()*this.EPSILON) > -10)
					repeat = false;
			}
		}
		else if(deltaX >= 0 && deltaY < 0) {
			if(x <= -this.EPSILON) x = this.EPSILON;
			else {
				y = -this.EPSILON;
				x = -x;
			}
			//			System.out.println("x: " + x + " y: " + y);

			while(repeat) {
				newX = newX + x;
				newY = newY + y;

				movePath.add(new Position((int)newX, (int)newY));
				if(newY <= (finishP.getOriginY()*this.EPSILON) && newX >= (finishP.getOriginX()*this.EPSILON)) repeat = false;
			}
		}
		else if(deltaX < 0 && deltaY >= 0){
			if(x <= -this.EPSILON) {
				x = -this.EPSILON;
				y = -y;
			}
			else y = this.EPSILON;
			//			System.out.println("x: " + x + " y: " + y);

			while(repeat) {
				newX = newX + x;
				newY = newY + y;

				movePath.add(new Position((int)newX, (int)newY));
				if(newY >= (finishP.getOriginY()*this.EPSILON) && newX <= (finishP.getOriginX()*this.EPSILON)) repeat = false;
			}
		}
		else {
			//deltaX && deltaY >=0
			if(x >= this.EPSILON) x = this.EPSILON;
			else y = this.EPSILON;
			//			System.out.println("x: " + x + " y: " + y);

			while(repeat) {
				newX = newX + x;
				newY = newY + y;

				movePath.add(new Position((int)newX, (int)newY));
				if(newY >= (finishP.getOriginY()*this.EPSILON) && newX >= (finishP.getOriginX()*this.EPSILON)) repeat = false;
			}
		}
		//		System.out.println("movePath: " + movePath);
		return movePath;
	}

	@Override
	public List<Position> computePathToFinish() {
		// TODO Auto-generated method stub
		return null;
	}
}
