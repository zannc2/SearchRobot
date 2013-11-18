package robot.classes;

import helper.Direction;
import helper.Position;
import helper.Size;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import frontend.classes.view.Field;

public class RobotController implements Runnable {
	private final int MOVE_SPEED = 10;
	//size of steps
	private final int EPSILON = 10;
	// field values
	private final int UNKNOWN = 0;
	private final int ITEM = 1;
	private final int FINISH = 2;
	private final int FREE = 3;
	
	private Field field;
	private Size fieldSize;
	private FieldMatrix fieldCopy;
	private FieldMatrix foundMatrix;
	private Size robotSize;
	private Thread thread = null;
	
	private boolean foundFinish = false;
	

	public RobotController(Field f) {
		this.field = f;
		this.fieldSize = f.getFieldSize();
		this.robotSize = f.getRobotSize();
		this.fieldCopy = new FieldMatrix(this.fieldSize, this.robotSize, f);
		this.foundMatrix = new FieldMatrix(this.fieldSize, this.robotSize);
	}


	public synchronized void start(){
		if (thread == null){
			thread = new Thread(this);
			thread.start();
		}
	}

	public synchronized void stop(){
		if (thread != null)
			thread = null;
	}

	private void scanField() {
		// The position of the robot eye(s)
		Position eyePosition;
		// direction of the robot
		Direction d = field.getRobotDirection();
		// max and min angle of the robots view
		double minDegree, maxDegree;
		Position robotPosition = this.field.getRobotPosition();
		if(d == Direction.NORTH)
		{
			eyePosition = new Position(robotPosition.getOriginX() + robotSize.getWidth()/2,
					robotPosition.getOriginY()-1);
			minDegree = -180;
			maxDegree = 0;
		}
		else if(d == Direction.EAST)
		{
			eyePosition = new Position(robotPosition.getOriginX() + robotSize.getWidth(),
					robotPosition.getOriginY() + robotSize.getHeight()/2);	
			minDegree = -90;
			maxDegree = 90;
		}
		else if(d == Direction.SOUTH)
		{
			eyePosition = new Position(robotPosition.getOriginX() + robotSize.getWidth()/2,
					robotPosition.getOriginY() + robotSize.getHeight());	
			minDegree = 0;
			maxDegree = 180;
		}
		else
		{
			eyePosition = new Position(robotPosition.getOriginX()-1,
					robotPosition.getOriginY() + robotSize.getHeight()/2);
			minDegree = 90;
			maxDegree = 270;

		}

		// degrees Between 0 and 180
		for(double i = minDegree; i <= maxDegree; i=i+0.35) {
			boolean whileB = true;
			int factor = 1;
			double x = Math.cos(Math.toRadians(i)) * this.EPSILON;
			double y = Math.sin(Math.toRadians(i)) * this.EPSILON;

			while(whileB){


				Position p = new Position((int)(eyePosition.getOriginX() + (factor * x)), (int)(eyePosition.getOriginY() + (factor * y)));
				if(p.getOriginX() >= this.fieldSize.getWidth() || 
						p.getOriginY() >= this.fieldSize.getHeight() || 
						p.getOriginX() < 0 || 
						p.getOriginY() < 0) {
					break;
				}
				else{
					Position pixelP = new Position((p.getOriginX()/10)*10, (p.getOriginY()/10)*10);
					
					//check if not allready checked
					int foundMatrixFound = this.foundMatrix.contains(new Position(pixelP.getOriginX()/10, pixelP.getOriginY()/10));
					if(foundMatrixFound == this.ITEM || foundMatrixFound == this.FINISH) whileB = false;
					if(foundMatrixFound == 0){					
						int found = this.fieldCopy.contains(new Position(pixelP.getOriginX()/10, pixelP.getOriginY()/10));
						if(found == this.ITEM) { // 1 = Item
//							field.addItem(new Pixel(pixelP, Color.red));
							whileB = false;
							//fill foundMatrix
							this.foundMatrix.set(new Position((pixelP.getOriginX())/10, (pixelP.getOriginY())/10), this.ITEM);
						}
						else if(found == this.FINISH){ // 2 = Finish
//							field.addItem(new Pixel(pixelP, Color.yellow));
							whileB = false;
							//fill foundMatrix
							this.foundMatrix.set(new Position(pixelP.getOriginX()/10, pixelP.getOriginY()/10), this.FINISH);
							foundFinish = true;
						}
						else { // If the position is free
//							field.addItem(new Pixel(pixelP, Color.green));
	
							//fill foundMatrix
							this.foundMatrix.set(new Position(pixelP.getOriginX()/10, pixelP.getOriginY()/10), this.FREE);
						}
					}
				}
				//								try {
				//									Thread.sleep(1);
				//								} catch (InterruptedException e) {
				//									// TODO Auto-generated catch block
				//									e.printStackTrace();
				//								}
				factor++;
			}

		}
//		System.out.println("DONE");
		//foundMatrix.printArray();
	}

	@Override
	public void run() {
		long lastRound = System.currentTimeMillis();
		
		//first scan
		this.field.setRobotDirection(Direction.NORTH);
		scanField();
		this.field.setRobotDirection(Direction.WEST);
		scanField();
		this.field.setRobotDirection(Direction.SOUTH);
		scanField();
		this.field.setRobotDirection(Direction.EAST);
		
		while(thread != null)
		{
			scanField();

			// fill a list of positions, for testing move
//			List<Position> pl = new ArrayList<>();
//			{
//
//				Position now = new Position(field.getRobotPosition().getOriginX() + 10, 
//						field.getRobotPosition().getOriginY());
//				int fill = 0;
//				pl.add(now);
//				while(fill < 10)
//				{
//					now = new Position(now.getOriginX(), 
//							now.getOriginY()+10);
//					pl.add(now);
//					now = new Position(now.getOriginX()+10, 
//							now.getOriginY());
//					pl.add(now);
//					now = new Position(now.getOriginX()+10, 
//							now.getOriginY());
//					pl.add(now);
//					fill++;
//				}
//			}
//			move(pl);
			//compute next position (list of Positions for move)
			List<Position> movePath = computePath();

			//move to new Position with given movePath
			move(movePath);
			
			{// calculation of time for scanning, just for testing
				long thisRound = System.currentTimeMillis();
				float timeSinceLastRound = ((float)(thisRound-lastRound)/1000f);
				lastRound = thisRound;
				System.out.println(timeSinceLastRound);
			}
			
//			this.foundFinish = true;
			if(this.foundFinish)	stop();
		}
	}

	private void move(List<Position> pl) {
		// bsp
		Direction d = this.field.getRobotDirection();
		Position lastP = this.field.getRobotPosition();
		for(Position p: pl)
		{			
			// move east
			if(p.getOriginX() > lastP.getOriginX())
			{
				this.field.setRobotDirection(Direction.EAST);
				int step = 1;
				while(step < 11)
				{
					this.field.setRobotPosition(new Position(lastP.getOriginX()+step, lastP.getOriginY()));
					step++;
					try { Thread.sleep(MOVE_SPEED); } 
					catch (InterruptedException e) { e.printStackTrace(); }
				}
			}
			// move west
			else if(p.getOriginX() < lastP.getOriginX())
			{
				this.field.setRobotDirection(Direction.WEST);
				int step = 1;
				while(step < 11)
				{
					this.field.setRobotPosition(new Position(lastP.getOriginX()-step, lastP.getOriginY()));
					step++;
					try { Thread.sleep(MOVE_SPEED); } 
					catch (InterruptedException e) { e.printStackTrace(); }
				}
			}
			//move south
			else if(p.getOriginY() > lastP.getOriginY())
			{
				this.field.setRobotDirection(Direction.SOUTH);
				int step = 1;
				while(step < 11)
				{
					this.field.setRobotPosition(new Position(lastP.getOriginX(), lastP.getOriginY()+step));
					step++;
					try { Thread.sleep(MOVE_SPEED); } 
					catch (InterruptedException e) { e.printStackTrace(); }
				}
			}
			else // move north
			{
				this.field.setRobotDirection(Direction.NORTH);
				int step = 1;
				while(step < 11)
				{
					this.field.setRobotPosition(new Position(lastP.getOriginX(), lastP.getOriginY()-step));
					step++;
					try { Thread.sleep(MOVE_SPEED); } 
					catch (InterruptedException e) { e.printStackTrace(); }
				}
			}
			lastP = p;
		}
	}

	private List<Position> computePath() {
		
		List<Position> movePath = new ArrayList<>();
		
		//check if finish is found
		for(int i = 0; i < this.fieldSize.getWidth()/10; i++){
			for(int j = 0; j < this.fieldSize.getHeight()/10; j++) {
				if(this.foundMatrix.contains(new Position(i, j)) == this.FINISH)
				{ 
					Position finishP = new Position(i,j);
					return movePath = computePathToFinish(finishP);
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
			if(x >= 10) {
				x = -this.EPSILON;
				y = -y;
			}
			else {
				y = -this.EPSILON;
				x = -x;
			}
		}
		else if(deltaX >= 0 && deltaY < 0) {
			if(x <= -10) x = this.EPSILON;
			else {
				y = -this.EPSILON;
				x = -x;
			}
		}
		else if(deltaX < 0 && deltaY >= 0){
			if(x <= -10) {
				x = -this.EPSILON;
				y = -y;
			}
			else y = this.EPSILON;
		}
		else {
			//deltaX && deltaY >=0
			if(x >= 10) x = this.EPSILON;
			else y = this.EPSILON;
		}
		
		System.out.println("x: " + x + " y: " + y);
		

		int newX = (int) (oldP.getOriginX() + x);
		int newY = (int) (oldP.getOriginY() + y);
		Position nextP = new Position(newX, newY);
		
		boolean free = true;
		while(free) {
			System.out.println("nextP: " + nextP);

			int foundMatrixPosition = this.foundMatrix.contains(new Position(newX/10, newY/10));
			
			//Position is not free, there is a item
			if(foundMatrixPosition == this.ITEM) {
				//check direction
				if(angle > 315 && angle < 45) {
					//check if east Position of oldP is free
					Position eastP = new Position(oldP.getOriginX() + 10, oldP.getOriginY());
					if(this.foundMatrix.contains(new Position(eastP.getOriginX()/10, eastP.getOriginY()/10)) == this.FREE) {
						movePath.add(eastP);
						oldP = eastP;
					}
					//east Position is unknown, scan again
					else if(foundMatrixPosition == this.UNKNOWN) return movePath;
				}
				else if(angle > 45 && angle < 135) {				
					//check if north Position of oldP is free
					Position northP = new Position(oldP.getOriginX(), oldP.getOriginY() - 10);
					if(this.foundMatrix.contains(new Position(northP.getOriginX()/10, northP.getOriginY()/10)) == this.FREE) {
						movePath.add(northP);
						oldP = northP;
					}
					//north Position is unknown, scan again
					else if(foundMatrixPosition == this.UNKNOWN) return movePath;
				}
				else if(angle > 135 && angle < 225) {
					//check if west Position of oldP is free
					Position westP = new Position(oldP.getOriginX() - 10, oldP.getOriginY());
					if(this.foundMatrix.contains(new Position(westP.getOriginX()/10, westP.getOriginY()/10)) == this.FREE) {
						movePath.add(westP);
						oldP = westP;
					}
					//west Position is unknown, scan again
					else if(foundMatrixPosition == this.UNKNOWN) return movePath;
				}
				else {				
					//check if south Position of oldP is free
					Position southP = new Position(oldP.getOriginX(), oldP.getOriginY() + 10);
					if(this.foundMatrix.contains(new Position(southP.getOriginX()/10, southP.getOriginY()/10)) == this.FREE) {
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
			if((newX/10 - pathFinish.getOriginX()/10) > -10 && (newX/10 - pathFinish.getOriginX()/10) < 10
					 && (newY/10 - pathFinish.getOriginY()/10) > -10 && (newY/10 - pathFinish.getOriginY()/10) < 10) free = false;
		}
		
		
		
		return movePath;
//		return computePathToFinish(oldP);
	}


	private Position computeNextPosition() {
		for(int line = 0; line < this.fieldSize.getHeight()/10; line++) {
			for(int row = 0; row < this.fieldSize.getWidth()/10; row++) {
				
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
			else if(y == this.fieldSize.getHeight()/10 -1){
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
		else if(x == this.fieldSize.getWidth()/10 -1) {

			// y on top boarder
			if(y == 0) {				
				if(this.foundMatrix.contains(new Position(x -1, y )) == this.UNKNOWN) return false;
				
				else if(this.foundMatrix.contains(new Position(x -1, y +1)) == this.UNKNOWN) return false;
				else if(this.foundMatrix.contains(new Position(x, y + 1)) == this.UNKNOWN) return false;
			}
			// y on bottom border
			else if(y == this.fieldSize.getHeight()/10 -1){
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
			else if(y == this.fieldSize.getHeight()/10 -1){
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


	private List<Position> computePathToFinish(Position finishP) {
		List<Position> movePath = new ArrayList<>();

		Position robotP = new Position(this.field.getRobotPosition().getOriginX()/10,
				this.field.getRobotPosition().getOriginY()/10);
//		System.out.println("Finish found: " + finishP);
//		System.out.println("Robot Position: " + robotP);
		
		int deltaX = finishP.getOriginX() - robotP.getOriginX();
		int deltaY = finishP.getOriginY() - robotP.getOriginY();
//		System.out.println("deltaX: " + deltaX + " deltaY: " + deltaY);
		
		double angle = Math.toDegrees(Math.atan(((double)deltaY)/((double)deltaX)));
//		System.out.println("winkel: " + angle);
		
		double x = this.EPSILON / Math.tan(Math.toRadians(angle));
		double y = Math.tan(Math.toRadians(angle)) * this.EPSILON;
		

		double newX = robotP.getOriginX() *10;
		double newY = robotP.getOriginY() *10;
		
		boolean repeat = true;
		
		if(deltaX < 0 && deltaY < 0){
			if(x >= 10) {
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

				movePath.add(new Position((int)newX, (int)newY));
				if(newY <= (finishP.getOriginY()*10) && newX <= (finishP.getOriginX()*10)) repeat = false;
			}
		}
		else if(deltaX >= 0 && deltaY < 0) {
			if(x <= -10) x = this.EPSILON;
			else {
				y = -this.EPSILON;
				x = -x;
			}
//			System.out.println("x: " + x + " y: " + y);
			
			while(repeat) {
				newX = newX + x;
				newY = newY + y;

				movePath.add(new Position((int)newX, (int)newY));
				if(newY <= (finishP.getOriginY()*10) && newX >= (finishP.getOriginX()*10)) repeat = false;
			}
		}
		else if(deltaX < 0 && deltaY >= 0){
			if(x <= -10) {
				x = -this.EPSILON;
				y = -y;
			}
			else y = this.EPSILON;
//			System.out.println("x: " + x + " y: " + y);
			
			while(repeat) {
				newX = newX + x;
				newY = newY + y;

				movePath.add(new Position((int)newX, (int)newY));
				if(newY >= (finishP.getOriginY()*10) && newX <= (finishP.getOriginX()*10)) repeat = false;
			}
		}
		else {
			//deltaX && deltaY >=0
			if(x >= 10) x = this.EPSILON;
			else y = this.EPSILON;
//			System.out.println("x: " + x + " y: " + y);
			
			while(repeat) {
				newX = newX + x;
				newY = newY + y;

				movePath.add(new Position((int)newX, (int)newY));
				if(newY >= (finishP.getOriginY()*10) && newX >= (finishP.getOriginX()*10)) repeat = false;
			}
		}
//		System.out.println("movePath: " + movePath);
		return movePath;
	}
}
