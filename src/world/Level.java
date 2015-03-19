package world;

import gameobjects.BasicObject;
import gameobjects.Door;
import gameobjects.Stairs;
import interfaces.Broadcaster;
import interfaces.Collider;
import interfaces.ObjectCreator;
import interfaces.Removeable;
import interfaces.Updater;
import items.Item;
import items.ItemBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;

import actors.Actor;
import actors.Enemy;
import actors.Player;
import collisions.ContextualCollisions;
import collisions.PhysicalCollisions;



/* Top level management of all game objects. */

public class Level {


	private int startX;
	private int startY;

	private int heightInTiles;
	private int widthInTiles;

	private SectorMap sectorMap;
	private int numXSectors = 1;
	private int numYSectors = 1;

	private int[][] map;

	private ArrayList<Actor> actors;
	private ArrayList<Broadcaster> broadcasters;
	private ArrayList<BasicObject> basicObjects;
	private ArrayList<Updater> updaters;
	private ArrayList<ObjectCreator> creators;
	private ArrayList<Collider> colliders;

	private ArrayList<Shape> walls;
	private ArrayList<Shape> doors;
	private ArrayList<Shape> floors;
	private ArrayList<Shape> halls;

	private PhysicalCollisions physicalCollisions;
	private ArrayList<Stairs> stairsUp;
	private ArrayList<Stairs> stairsDown;
	private ContextualCollisions contextualCollisions;





	public Level(ItemBuilder itemBuilder, int width, int height, Player player) throws SlickException {


		this.widthInTiles = width;
		this.heightInTiles = height;
		
		this.sectorMap = 
				new SectorMap(World.TILE_WIDTH*widthInTiles*LevelBuilder.SCALING,
						World.TILE_HEIGHT*heightInTiles*LevelBuilder.SCALING,
						numXSectors,
						numYSectors);

		buildNewLevel(itemBuilder, player);

		

		physicalCollisions = new PhysicalCollisions(sectorMap,walls);
		contextualCollisions = new ContextualCollisions(sectorMap);
		
		for (Collider col : colliders){
			col.assignPhysicalCollisions(physicalCollisions);
		}
		
		
		for (Actor dude : actors){
			dude.assignContextualCollisions(contextualCollisions);;
		}
		

	};

	private void buildNewLevel(ItemBuilder itemBuilder, Player player) throws SlickException{

		this.actors = new ArrayList<Actor>(); 
		this.broadcasters = new ArrayList<Broadcaster>(); 
		this.basicObjects = new ArrayList<BasicObject>();
		this.updaters = new ArrayList<Updater>();
		this.creators = new ArrayList<ObjectCreator>();
		this.colliders = new ArrayList<Collider>();
		this.stairsUp = new ArrayList<Stairs>();
		this.stairsDown = new ArrayList<Stairs>();
		// build a new Level
		LevelBuilder levelBuilder = new LevelBuilder(widthInTiles,heightInTiles);

		// store the shapes for doors/walls/floors
		walls = levelBuilder.getWalls();
		doors = levelBuilder.getDoors();
		floors = levelBuilder.getFloors();
		halls = levelBuilder.getHalls();

		map = levelBuilder.getMap();



		// poop out the starting position
		int[] startPosition = levelBuilder.getStartingPosition();
		startX = startPosition[0];
		startY = startPosition[1];

		// build items using the levelbuilder to get the random locations
		for(int[] itemLoc : levelBuilder.randomLocationsAllRooms(0.75,3)){
			Item item = itemBuilder.newItem(itemLoc[0],itemLoc[1]);
			addObjectOld(item);
			addObject(item,(int)item.getShape().getX(),(int)item.getShape().getY());
		}

		for(int[] enemyLoc : levelBuilder.randomLocationsAllRooms(1,2)){
			Enemy enemy = new Enemy(enemyLoc[0],enemyLoc[1],this,player);
			addObjectOld(enemy);
			addObject(enemy,(int) enemy.getX(), (int) enemy.getY());
		}

		// build two sets of stairs to the next world
		for(int[] stairLoc : levelBuilder.randomLocationsStartRoom()){
			Stairs stairs = new Stairs(stairLoc[0],stairLoc[1],false);
			stairsUp.add(stairs);
			addObjectOld(stairs);
		}
		for(int[] stairLoc : levelBuilder.randomLocationsStartRoom()){
			Stairs stairs = new Stairs(stairLoc[0],stairLoc[1],true);
			stairsDown.add(stairs);
			addObjectOld(stairs);
		}


		for(Shape doorShape : doors){
			Door door = new Door(doorShape,  actors);
			addObjectOld(door);
			addObject(door,(int) doorShape.getX(), (int) doorShape.getY());
			
			
		}


	}




	public void removeFromAllLists(Object obj){
		removeFromList(obj,actors);
		removeFromList(obj,basicObjects);
		removeFromList(obj,broadcasters);
		removeFromList(obj,walls);
		removeFromList(obj,updaters);
		removeFromList(obj,creators);
		removeFromList(obj,colliders);
	}

	private void removeFromList(Object obj, ArrayList<?> list){
		if (list.contains(obj)){
			list.remove(obj);
		}
	}

	protected void update() throws SlickException, IOException{


		contextualCollisions.update();
		
		for( Sector sector:  sectorMap.getActiveSectors()){
			sector.update();
			
			
			for (Collider collider : sector.popNewColliders()){
				collider.assignPhysicalCollisions(physicalCollisions);
				collider.assignContextualCollisions(contextualCollisions);
			}
			
		}
		
		

		checkStairs();
	}

	public void resetStairs(){
		for(Stairs stairs : stairsUp){
			stairs.setClimbed(false);
		}
		for(Stairs stairs : stairsDown){
			stairs.setClimbed(false);
		}
	}

	public int checkStairs(){
		for (BasicObject basic : basicObjects){
			if (basic instanceof Stairs){
				int stairClimb = ((Stairs) basic).climbed();
				if(stairClimb!=0){
					return stairClimb;
				}
			}
		}
		return 0;
	}

	public ArrayList<Stairs> getStairsUp(){
		return stairsUp;
	}
	public ArrayList<Stairs> getStairsDown(){
		return stairsDown;
	}
	public void removeStairsUp(){
		stairsUp = new ArrayList<Stairs>();

		HashSet<Object> toRemove = new HashSet<Object>();

		for(BasicObject obj: basicObjects){
			if(obj instanceof Stairs){
				if( !((Stairs) obj).isStairsDown()){
					toRemove.add(obj);
				}
			}
		}

		for (Object obj : toRemove){
			removeFromAllLists(obj);
		}

	}
	public void removeStairsDown(){
		stairsDown = new ArrayList<Stairs>();

		HashSet<Object> toRemove = new HashSet<Object>();

		for(BasicObject obj: basicObjects){
			if(obj instanceof Stairs){
				if( ((Stairs) obj).isStairsDown()){
					toRemove.add(obj);
				}
			}
		}

		for (Object obj : toRemove){
			removeFromAllLists(obj);
		}
	}


	

	public void render(Graphics g, int offsetX, int offsetY){		

		for (Sector sector : sectorMap.getActiveSectors()){
			sector.render(g, offsetX, offsetY);
		}
		
		

	}


	public ArrayList<Shape> getWalls(){
		return walls;
	}

	public ArrayList<Shape> getClosedDoors(){
		ArrayList<Shape> closedDoors = new ArrayList<Shape>();
		for(BasicObject obj: basicObjects){
			if(obj instanceof Door)
				if(!((Door) obj).isOpen()){
					closedDoors.add(obj.getShape());	
				}

		}
		return closedDoors;
	}
	public ArrayList<Shape> getFloors(){
		return floors;
	}
	public ArrayList<Shape> getHalls(){
		return halls;
	}
	public ArrayList<Actor> getActors(){
		return actors;
	}
	public ArrayList<Broadcaster> getBroadcasters(){
		return broadcasters;	
	}
	public ArrayList<BasicObject> getBasicObjects(){
		return basicObjects;
	}
	public int getStartX(){
		return startX;
	}
	public int getStartY(){
		return startY;
	}
	public int getHeightInTiles(){
		return heightInTiles;	
	}
	public int getWidthInTiles(){
		return widthInTiles;
	}

	public int[][] getMap(){
		return map;
	}

	
	public void addObject(Object obj, int xPos, int yPos) throws SlickException {
		sectorMap.placeObjectInSector(obj, xPos, yPos);
		
		
		if (obj instanceof Collider){
			((Collider) obj).assignPhysicalCollisions(physicalCollisions);
		}
		
		

	}

	public void addObjectOld(Object obj) throws SlickException {
		if (obj instanceof Actor){
			actors.add((Actor) obj);
		}
		if (obj instanceof BasicObject){
			basicObjects.add((BasicObject) obj);
		}
		if (obj instanceof Broadcaster){
			broadcasters.add((Broadcaster) obj);
		}
		if (obj instanceof Updater){
			updaters.add((Updater) obj);
		}
		if (obj instanceof ObjectCreator){
			creators.add((ObjectCreator) obj);
		}
		if (obj instanceof Collider){
			this.colliders.add((Collider) obj);
			((Collider) obj).assignPhysicalCollisions(physicalCollisions);
		}

	}



	public void assignToItems(CurrentLevelData currentLevelData) {
		for (BasicObject obj : basicObjects){
			if (obj instanceof Item){
				((Item)obj).setCurrentLevelData(currentLevelData);
			}
		}

	}

	






}


