package particleTests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Main extends StateBasedGame {

	public Main(String name) {
		super(name);
	}

	public void initStatesList(GameContainer arg0) throws SlickException {
		
		//create the states and add them to the game
		
		addState(new FireballParticleState(1));
		addState(new DoubleHelix(2));
		addState(new Explosion(3));
		addState(new Spiral(4));
		
		enterState(3);
	}
	
	public static void main(String args[]) throws SlickException{
		AppGameContainer app = new AppGameContainer(new Main("Particle Effects"));
		app.setDisplayMode(800, 600, false);
		
		//always render the application, even if we don't focus it
		app.setAlwaysRender(true);
		app.setTargetFrameRate(59);
		app.start();
		
	}

}
