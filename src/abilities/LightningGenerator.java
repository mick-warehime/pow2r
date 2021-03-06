package abilities;

import interfaces.Broadcaster;
import interfaces.ObjectCreator;

import java.io.IOException;
import java.util.ArrayList;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

import actors.Player;

public class LightningGenerator extends AbilityObject implements Broadcaster, ObjectCreator {

	private int countDown;
	private boolean shouldRemove;

	private float[] startPt;
	private float[] facingDirection;
	private ArrayList<Object> objsToCreate;
	public LightningGenerator(float[] startPt, float[] facingDirection) throws SlickException, IOException {

		this.startPt = startPt;
		this.facingDirection = facingDirection;		

		countDown = 60;

		canCollide = false;
		shouldRemove = false;

		generateShape();
		objsToCreate = new ArrayList<Object>();
	}


	public void generateShape(){

		// create a triangule to test for the presence of enemies
		// if an enemy is in this triangle create a lightning object

		float triBase = 100;
		float triHeight = 200;
		
		float[] tangent = new float[] {facingDirection[0], facingDirection[1]};
		float[] normal = new float[] {facingDirection[1], -facingDirection[0]};

		float x1 = startPt[0];
		float y1 = startPt[1];

		float x2 = x1+tangent[0]*triHeight-triBase*normal[0]/2;
		float y2 = y1+tangent[1]*triHeight-triBase*normal[1]/2;

		float x3 = x1+tangent[0]*triHeight+triBase*normal[0]/2;
		float y3 = y1+tangent[1]*triHeight+triBase*normal[1]/2;


		shape = new Polygon();
		((Polygon) shape).addPoint(x1,y1);
		((Polygon) shape).addPoint(x2,y2);
		((Polygon) shape).addPoint(x3,y3);

	}


	@Override
	public boolean shouldRemove() {		
		return shouldRemove;
	}

	@Override
	public void update() {

		countDown -=1;

		if (countDown<0){
			shouldRemove = true;
		}

	}



	@Override
	public void onCollisionDo(Class<?> collidingObjectClass,
			Shape collidingObjectShape) {
		
		if(!(collidingObjectClass.equals(Player.class))){			
			try {	
				float endX = (float) (collidingObjectShape.getX() + collidingObjectShape.getWidth()*.5);
				float endY = (float) (collidingObjectShape.getY() + collidingObjectShape.getHeight()*.5);
						
				objsToCreate.add(new LightningAbilityObject(startPt,
						new float[] {endX,endY}));
			
			} catch (SlickException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	public void render(Graphics g, int offsetX, int offsetY){}


	@Override
	public ArrayList<Command> onCollisionBroadcast(
			Class<?> collidingObjectClass, Shape collidingObjectShape) {
		ArrayList<Command> output = new ArrayList<Command>();
		return output;
	}


	public boolean hasObjects(){
		return !objsToCreate.isEmpty();
	}

	public ArrayList<Object> popObjects() {
		@SuppressWarnings("unchecked")
		ArrayList<Object> output = (ArrayList<Object>) objsToCreate.clone();
		objsToCreate.clear();
		shouldRemove = true;
		return output;
	}


	@Override
	public Shape getInteractionRange() {
		return shape;
	}


	@Override
	public void onRemoveDo() {
		if (!this.objsToCreate.isEmpty()){
			objsToCreate = null;
		}
		
	}

}
