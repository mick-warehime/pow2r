package knowledge;

import java.util.ArrayList;

import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Shape;

import pathfinding.AStarPathFinder;
import pathfinding.LevelMap;
import pathfinding.Path;
import world.Level;
import actors.Enemy;
import actors.Player;

public class Knowledge {

	private Player player;
	private Level level;
	private Enemy self;
	private int searchDistance;

	public Knowledge(Enemy self, Player player, Level level){

		this.player = player;
		this.level = level;
		this.self = self;
		
		this.searchDistance = 15;
		
	}

	public int[] getDirection(){
		
		return new int[] {1,2};
	}
	
	private Path aStarPath(){
		
		// create the astar data
		LevelMap map = new LevelMap(level.getMap());
		AStarPathFinder astar = new AStarPathFinder(map,searchDistance,true);
		
		
		// position of enemy in tiles
		int sx = Math.floorDiv((int) self.getX(), map.getWidthInTiles());
		int sy = Math.floorDiv((int) self.getY(), map.getHeightInTiles());
		
		// position of the player in tiles
		int tx = Math.floorDiv((int) player.getX(), map.getWidthInTiles());
		int ty = Math.floorDiv((int) player.getY(), map.getHeightInTiles());
		
		// try to calculate the astar path from enemy to player
		Path path = astar.findPath(self, sx, sy, tx, ty);
		
		return path;
		
	}

	//Checks if the straight line between two shapes intersects
	// any other collideable shape
	private boolean isPlayerVisible() {
		
		ArrayList<Shape> walls = level.getWalls();

		//Make a line from centers of player and object
		float x1 = self.getShape().getCenterX();
		float y1 = self.getShape().getCenterY();
		float x2 = player.getShape().getCenterX();
		float y2 = player.getShape().getCenterY();

		Line line = new Line(x1, y1, x2, y2);

		//Also check the basic game tiles
		for(Shape wall : walls){
			if(line.intersects(wall)){
				return false;
			}
		}
		
		return true;

	}


	



}
