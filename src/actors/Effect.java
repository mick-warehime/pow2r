package actors;

import interfaces.Removeable;

public class Effect implements Removeable {

	public static final int EFFECT_X_COLLISION = 0;
	public static final int EFFECT_Y_COLLISION = 1;
	public static final int EFFECT_COLLIDED_WITH_PLAYER = 2;
	public static final int EFFECT_WINDED = 3;
	public static final int EFFECT_INTERACTING = 5;
	public static final int EFFECT_CASTING_ABILITY = 6;
	public static final int EFFECT_RUNNING = 7;
	public static final int EFFECT_WALKING = 8;
	public static final int EFFECT_AGRO = 10;
	public static final int EFFECT_DYING = 11;
	public static final int EFFECT_CHASING = 12;
	public static final int EFFECT_AGROED = 13;
	public static final int EFFECT_LOSTHP = 14;
	public static final int EFFECT_GAINEDHP = 15;

	public static final int[] EFFECTS_PREVENTING_ACTION = new int[]
		{EFFECT_INTERACTING,EFFECT_CASTING_ABILITY};
	
	
	public static final int[] EFFECTS_PREVENTING_MOVEMENT = new int[]
			{EFFECT_WINDED};
	
	public static final int[] EFFECTS_AMBULATING = new int[]
			{EFFECT_WALKING,EFFECT_RUNNING};
	
	
	
	
	public int name;
	public int timer;

	public Effect(int name, int duration){
		this.name = name;
		this.timer = duration;
	}

	//Count down to effect end
	public void countDown(){
		timer -=1;
		
	}

	@Override
	public boolean shouldRemove() {
		// TODO Auto-generated method stub
		return (timer <= 0);
	}

	@Override
	public void onRemoveDo() {
		
	}


	



}
