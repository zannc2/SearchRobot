package robot.interfaces;

import helper.Position;

import java.util.List;

public interface Strategy {
	
	public List<Position> computePath();
	public List<Position> computePathToFinish();

}
