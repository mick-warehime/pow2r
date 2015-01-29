package actors;

import java.util.ArrayList;
import java.util.Properties;

import main.CollisionHandler;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

import actionEngines.EnemyActionEngine;
import commands.DieCommand;
import commands.InputListenerAggregator;
import gameobjects.InteractiveCollideable;
import graphics.ActorGraphics;

public class Enemy extends Actor implements Broadcaster{

	private LemmingBehavior behavior;
	

//	public Enemy(int x, int y, int w, int h, String name, TiledMap map, Properties args ) throws SlickException {
//		super();
//
//		int rectTopLeftX = x*map.getTileWidth();
//		int rectTopLeftY = y*map.getTileHeight(); //These shouldn't be necessary. Fix later
//
//		listener = new GlobalInputListener();
//		
//		Rectangle rect = new Rectangle(rectTopLeftX,rectTopLeftY,32, 32);
//
//		status = new Status(rect);
//		
//		
//		if(args.containsKey("direction")){
//			int dir = Integer.parseInt((String) args.get("direction"));
//			if (dir*status.getDirection('x')<0){//directions don't agree
//				status.setDirection('x',dir);
//			}
//		}
//		graphics = new ActorGraphics("data/dwarf3.png", status);
//
//		
// 	}

	public Enemy(int xPixels, int yPixels, CollisionHandler collisionHandler) throws SlickException {
		super();
		
		Rectangle rect = new Rectangle(xPixels,yPixels,32,32);
		 
		listenerAggregator = new InputListenerAggregator();
				
		status = new Status(rect);
		status.setCollisionHandler(collisionHandler);
		
		graphics = new ActorGraphics("data/dwarf3.png", status);

		listener = new GlobalInputListener();
		
		
		engine = new EnemyActionEngine(listener, status);

		behavior = new LemmingBehavior(status, collisionHandler);

		listenerAggregator.addListener(behavior);
		
	}

	public void update(){
		behavior.determine();
		super.update();
		assert (status != null) : "Error! Collision Handler not incorporated!";
	}

	@Override
	public void onCollisionDo(Class<?> collidingObjectClass, Shape collidingObjectShape) {
		if (collidingObjectClass.equals(Player.class)){
			status.gainEffect(Effect.EFFECT_COLLIDED_WITH_PLAYER, 1);
		}
	}

	@Override
	public ArrayList<Command> onCollisionBroadcast(Class<?> collidingObjectClass, Shape collidingObjectShape) {
		ArrayList<Command> list = new ArrayList<Command>();
		if (collidingObjectClass.equals(Player.class)){
			list.add( new DieCommand());
		}
		return list;
	}
	





}
