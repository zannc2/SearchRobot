package robot.classes;

import helper.Direction;
import helper.Position;
import helper.Size;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import robot.classes.strategies.Strategy_G;
import robot.classes.strategies.Strategy_Z;
import frontend.classes.SearchRobotEditor;
import frontend.classes.view.Field;

public class RobotController implements Runnable {
	private final int MOVE_SPEED;
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
	private boolean foundFinish;
	private Position finish;
	private boolean unreachable, isFinished;
	public boolean isFinished() {
		return isFinished;
	}


	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}


	private SearchRobotEditor editor;


	public RobotController(SearchRobotEditor editor, Field f, int robotSpeed) {
		this.field = f;
		this.fieldSize = f.getFieldSize();
		this.robotSize = f.getRobotSize();
		this.fieldCopy = new FieldMatrix(this.fieldSize, this.robotSize, f);
		this.foundMatrix = new FieldMatrix(this.fieldSize, this.robotSize);
		this.MOVE_SPEED = robotSpeed;
		this.editor = editor;
	}


	public synchronized void start(){
		if (thread == null){
			thread = new Thread(this);
			thread.start();
		}
	}

	@SuppressWarnings("deprecation")
	public synchronized void stop(){
		if (thread != null)
			thread = null;	
	}

	private void scanField() {
		this.foundMatrix.set(new Position(this.field.getRobotPosition().getOriginX()/10, this.field.getRobotPosition().getOriginY()/10), this.FREE);
//		field.addItem(new Pixel(new Position(this.field.getRobotPosition().getOriginX(), this.field.getRobotPosition().getOriginY()), Color.green));
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
//							field.addItem(new Pixel(pixelP, Color.RED));
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
							finish = new Position(pixelP.getOriginX(), pixelP.getOriginY());
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


		while(thread != null)
		{
			this.field.setRobotDirection(Direction.NORTH);
			scanField();
			this.field.setRobotDirection(Direction.WEST);
			scanField();
			this.field.setRobotDirection(Direction.SOUTH);
			scanField();
			this.field.setRobotDirection(Direction.EAST);
			scanField();

			//compute next position (list of Positions for move)

			//Strategy zannc2
			//			Strategy_Z strategy_z = new Strategy_Z(this, this.foundMatrix, this.fieldSize, this.field);
			//			List<Position> movePath = strategy_z.computePath();


			//Strategy gfells4
			Strategy_G strategy_g = new Strategy_G(this, this.foundMatrix, this.fieldSize, this.field);
			List<Position> movePath;
			if(!foundFinish)
			{
				movePath = strategy_g.computePath();
			}
			else
			{
				movePath = strategy_g.computePathToFinish();
				//			List<Position> movePath = strategy_g.computeFinishPath();
			}
			//move to new Position with given movePath
			move(movePath);

			{// calculation of time for scanning, just for testing
				long thisRound = System.currentTimeMillis();
				float timeSinceLastRound = ((float)(thisRound-lastRound)/1000f);
				lastRound = thisRound;
				//System.out.println(timeSinceLastRound);
			}

			if(isUnreachable()) 
			{
				JOptionPane.showMessageDialog(null, "Das Ziel kann leider nicht angesteuert werden!");
				editor.stopSearch();
				System.out.println("Can not reach the Finish!");
			}
			else if (isFinished())
			{
				JOptionPane.showMessageDialog(null, "Der Roboter hat das Ziel erreicht!");
				editor.stopSearch();
				System.out.println("Can not reach the Finish!");
			}
				
			//			this.foundFinish = true;
			//if(this.foundFinish)	stop();
		}
	}

	private void move(List<Position> pl) {
		Position lastP = new Position(field.getRobotPosition().getOriginX(), field.getRobotPosition().getOriginY());
		Iterator<Position> i = pl.listIterator();
		while(i.hasNext())
		{
			Position p = i.next();
			int startX, endX, startY, endY;
			// move east
			if(p.getOriginX() > lastP.getOriginX())
			{
				this.field.setRobotDirection(Direction.EAST);
				for(int j = 0; j < 10; j++)
				{
					{
						this.field.setRobotPosition(new Position(lastP.getOriginX()*10+j+1, lastP.getOriginY()*10));
						try { Thread.sleep(MOVE_SPEED); } 
						catch (InterruptedException e) { e.printStackTrace(); }
					}
				}
			}
			// move west
			else if(p.getOriginX() < lastP.getOriginX())
			{
				this.field.setRobotDirection(Direction.WEST);
				for(int j = 0; j < 10; j++)
				{
					{
						this.field.setRobotPosition(new Position(lastP.getOriginX()*10-1-j, lastP.getOriginY()*10));
						try { Thread.sleep(MOVE_SPEED); } 
						catch (InterruptedException e) { e.printStackTrace(); }
					}
				}
			}
			//move south
			else if(p.getOriginY() > lastP.getOriginY())
			{
				this.field.setRobotDirection(Direction.SOUTH);
				for(int j = 0; j < 10; j++)
				{
					{
						this.field.setRobotPosition(new Position(lastP.getOriginX()*10, lastP.getOriginY()*10+j+1));
						try { Thread.sleep(MOVE_SPEED); } 
						catch (InterruptedException e) { e.printStackTrace(); }
					}
				}
			}
			else // move north
			{
				this.field.setRobotDirection(Direction.NORTH);
				for(int j = 0; j < 10; j++)
				{
					{
						this.field.setRobotPosition(new Position(lastP.getOriginX()*10, lastP.getOriginY()*10-1-j));
						try { Thread.sleep(MOVE_SPEED); } 
						catch (InterruptedException e) { e.printStackTrace(); }
					}
				}
			}
			lastP = p;
		}
	}
	//	private void move(List<Position> pl) {
	//		// bsp
	//		Position lastP = this.field.getRobotPosition();
	//		Iterator<Position> i = pl.listIterator();
	//		while(i.hasNext())
	//		{
	//			Position p = i.next();
	//			// move east
	//			if(p.getOriginX() > lastP.getOriginX())
	//			{
	//				this.field.setRobotDirection(Direction.EAST);
	//				int step = 1;
	//				while(step < 11)
	//				{
	//					this.field.setRobotPosition(new Position(lastP.getOriginX()+step, lastP.getOriginY()));
	//					step++;
	//					try { Thread.sleep(MOVE_SPEED); } 
	//					catch (InterruptedException e) { e.printStackTrace(); }
	//				}
	//			}
	//			// move west
	//			else if(p.getOriginX() < lastP.getOriginX())
	//			{
	//				this.field.setRobotDirection(Direction.WEST);
	//				int step = 1;
	//				while(step < 11)
	//				{
	//					this.field.setRobotPosition(new Position(lastP.getOriginX()-step, lastP.getOriginY()));
	//					step++;
	//					try { Thread.sleep(MOVE_SPEED); } 
	//					catch (InterruptedException e) { e.printStackTrace(); }
	//				}
	//			}
	//			//move south
	//			else if(p.getOriginY() > lastP.getOriginY())
	//			{
	//				this.field.setRobotDirection(Direction.SOUTH);
	//				int step = 1;
	//				while(step < 11)
	//				{
	//					this.field.setRobotPosition(new Position(lastP.getOriginX(), lastP.getOriginY()+step));
	//					step++;
	//					try { Thread.sleep(MOVE_SPEED); } 
	//					catch (InterruptedException e) { e.printStackTrace(); }
	//				}
	//			}
	//			else // move north
	//			{
	//				this.field.setRobotDirection(Direction.NORTH);
	//				int step = 1;
	//				while(step < 11)
	//				{
	//					this.field.setRobotPosition(new Position(lastP.getOriginX(), lastP.getOriginY()-step));
	//					step++;
	//					try { Thread.sleep(MOVE_SPEED); } 
	//					catch (InterruptedException e) { e.printStackTrace(); }
	//				}
	//			}
	//			lastP = p;
	//		}
	//	}




	public List<Position> computePathToFinish(Position finishP) {
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

				movePath.add(new Position((int)newX, (int)newY));
				if(newY <= (finishP.getOriginY()*this.EPSILON) && newX <= (finishP.getOriginX()*this.EPSILON)) repeat = false;
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


	public boolean isUnreachable() {
		return unreachable;
	}


	public void setUnreachable(boolean unreachable) {
		this.unreachable = unreachable;
	}
}
