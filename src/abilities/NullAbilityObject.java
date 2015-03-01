package abilities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class NullAbilityObject extends AbilityObject {

	private int countDown;
	
	public NullAbilityObject( int xPos, int yPos)
			throws SlickException {
		super(new Image("data/thrusterFlame.png"), xPos, yPos);
		
		this.countDown= 20;
		
		System.out.println( "Null ability object generated at position" + xPos + "," + yPos);
		
	}

	
	
	@Override
	public boolean shouldRemove() {
		return countDown<0;
	}

	@Override
	public void update() {
		countDown -=1;

	}

}