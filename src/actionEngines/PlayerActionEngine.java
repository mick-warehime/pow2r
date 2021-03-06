package actionEngines;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;

import abilities.FireballAbility;
import abilities.LightningAbility;
import abilities.RunAbility;
import actors.Status;

import commands.CommandProviderAggregator;


//Takes in command inputs and implements corresponding actions
public class PlayerActionEngine extends ActorActionEngine {


	public PlayerActionEngine(CommandProviderAggregator listener, Status status,AbilitySlots abilitySlots,ArrayList<Object>objectsToCreate) throws NullPointerException, IndexOutOfBoundsException, SlickException{
		super(listener,status, abilitySlots,objectsToCreate);
		
		this.acceleration = 2;
		this.walkSpeed = 2;
		this.runSpeed = 4;
		
		abilitySlots.setAbility(new RunAbility(), 0);
		abilitySlots.setAbility(new FireballAbility(), 1);
		abilitySlots.setAbility(new LightningAbility(), 2);
		
	}
	
	
	

	
	
	
	
	
	
	
	
	




	








}
