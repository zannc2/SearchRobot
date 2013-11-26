package robot.classes.strategies;

import frontend.classes.view.Field;
import helper.Position;
import helper.Size;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import robot.classes.FieldMatrix;
import robot.classes.RobotController;

public class Strategy_G {

	private Size fieldSize;
	// field values
	private final int UNKNOWN = 0;
	private final int ITEM = 1;
	private final int FINISH = 2;

	private Field field;
	private int [][] visited;
	private FieldMatrix foundMatrix;
	private int shortestWay;
	private List<Position> returnList;
	private boolean unknownFieldExist;


	private RobotController robotController;
	private boolean finishAcessible;

	public Strategy_G(RobotController robotController, FieldMatrix foundMatrix, Size fieldSize, Field field) {
		this.foundMatrix = foundMatrix;
		this.fieldSize = fieldSize;
		this.field = field;
		this.robotController = robotController;
	}

	public List<Position> computePath() {
		// get the robot position
		Position robot = new Position(this.field.getRobotPosition().getOriginX()/10, 
				this.field.getRobotPosition().getOriginY()/10);
		// create array to mark the visited positions
		visited = new int[fieldSize.getWidth()/10][fieldSize.getHeight()/10];

		// set the shortest way to max possible
		shortestWay = fieldSize.getHeight()*fieldSize.getWidth();

		// create the list to return
		returnList = new LinkedList<Position>();

		// compute the path, start with the robot position
		computePath(new Position(robot.getOriginX(), robot.getOriginY()), 0);
		
		// if the method found an unknown field
		if(unknownFieldExist)
		{
			Collections.reverse(returnList);
		}
		else // if all reachable fields are discovered -> the finish is unreachable
		{
			this.robotController.setUnreachable(true);
			returnList.clear();
		}
		
		return returnList;
	}

	/**
	 * This is a recursiv method to find the shortest way from the reobot position to its next unknown position
	 * 
	 * @param p the position of the next position (in "pixles/10")
	 * @param depth the depth of this position in the tree
	 */
	private void computePath(Position p, int depth) {
		// if position p was already visited in a less deep recursion OR 
		if ((visited[p.getOriginX()][p.getOriginY()] != 0 && 
				visited[p.getOriginX()][p.getOriginY()] <= depth) || depth > shortestWay)
		{
			return;
		}

		// if the position is visited the first time or the last time was in a deeper recursion
		else
		{
			// set visited in this depth
			visited[p.getOriginX()][p.getOriginY()] = depth;
			// get the value of this position
			int fieldValue = foundMatrix.contains(p);

			// if the value of position p is UNKNOWN & the its the less deepest unknown position till now
			if(fieldValue == UNKNOWN && depth < shortestWay)
			{
				// set unknown as true, its important to find out if there is still an unknown field or not
				unknownFieldExist = true;
				System.out.println(foundMatrix.contains(p));
				// set the shortest way and create a new list from here
				shortestWay = depth;
				returnList.clear();
				return;// returnList;
			}
			// if the value of this position is unknown or its an item -> return without changes
			else if(fieldValue == UNKNOWN || fieldValue == ITEM)
			{
				return;
			}
			else // we go one step deeper
			{
				depth += 1;
				// north
				if(p.getOriginY() > 0)
					computePath(new Position(p.getOriginX(), p.getOriginY()-1), depth);
				// west
				if(p.getOriginX() > 0)
					computePath(new Position(p.getOriginX()-1, p.getOriginY()), depth);
				// south
				if(p.getOriginY() < (fieldSize.getHeight()/10)-1)
					computePath(new Position(p.getOriginX(), p.getOriginY()+1), depth);
				// east
				if(p.getOriginX() < (fieldSize.getWidth()/10)-1)
					computePath(new Position(p.getOriginX()+1, p.getOriginY()), depth);

				if(depth+returnList.size() > shortestWay)
				{
					return;				
				}
				else
				{
					returnList.add(p);
					return;
				}
			}
		}
	}

	public List<Position> computePathToFinish() {
		// get the robot position
		Position robot = new Position(this.field.getRobotPosition().getOriginX()/10, 
				this.field.getRobotPosition().getOriginY()/10);
		// create array to mark the visited positions
		visited = new int[fieldSize.getWidth()/10][fieldSize.getHeight()/10];

		// set the shortest way to max possible
		shortestWay = fieldSize.getHeight()*fieldSize.getWidth();

		// create the list to return
		returnList = new LinkedList<Position>();

		// compute the path, start with the robot position
		computePathToFinish(new Position(robot.getOriginX(), robot.getOriginY()), 0);
		
		// if the method found an unknown field
		if(finishAcessible)
		{
			Collections.reverse(returnList);
			this.robotController.setFinished(true);
		}
		else // if all reachable fields are discovered -> the finish is unreachable
		{
			returnList.clear();
			computePath(new Position(robot.getOriginX(), robot.getOriginY()), 0);
		}
		return returnList;
	}
	
	private void computePathToFinish(Position p, int depth) {
		// if position p was already visited in a less deep recursion OR 
		if ((visited[p.getOriginX()][p.getOriginY()] != 0 && 
				visited[p.getOriginX()][p.getOriginY()] <= depth) || depth > shortestWay)
		{
			return;
		}

		// if the position is visited the first time or the last time was in a deeper recursion
		else
		{
			// set visited in this depth
			visited[p.getOriginX()][p.getOriginY()] = depth;
			// get the value of this position
			int fieldValue = foundMatrix.contains(p);

			// if the value of position p is UNKNOWN & the its the less deepest unknown position till now
			if(fieldValue == FINISH && depth < shortestWay)
			{
				// set unknown as true, its important to find out if there is still an unknown field or not
				finishAcessible = true;
				System.out.println(foundMatrix.contains(p));
				// set the shortest way and create a new list from here
				shortestWay = depth;
				returnList.clear();
				returnList.add(p);
				return;// returnList;
			}
			// if the value of this position is unknown or its an item -> return without changes
			else if(fieldValue == UNKNOWN || fieldValue == FINISH || fieldValue == ITEM)
			{
				return;
			}
			else // we go one step deeper
			{
				depth += 1;
				// north
				if(p.getOriginY() > 0)
					computePathToFinish(new Position(p.getOriginX(), p.getOriginY()-1), depth);
				// west
				if(p.getOriginX() > 0)
					computePathToFinish(new Position(p.getOriginX()-1, p.getOriginY()), depth);
				// south
				if(p.getOriginY() < (fieldSize.getHeight()/10)-1)
					computePathToFinish(new Position(p.getOriginX(), p.getOriginY()+1), depth);
				// east
				if(p.getOriginX() < (fieldSize.getWidth()/10)-1)
					computePathToFinish(new Position(p.getOriginX()+1, p.getOriginY()), depth);
				
				if(depth+returnList.size() > shortestWay+1)
				{
					return;				
				}
				else
				{
					returnList.add(p);
					return;
				}
			}
		}
	}
	
}
