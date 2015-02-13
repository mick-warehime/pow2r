package commands;

import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.command.Command;


//Listens to command inputs from generic providers
public class InputListenerAggregator {


	private ArrayList <CommandProvider> providers; 	
	private ArrayList<Command> currentActionCommands ;

	public InputListenerAggregator() {
		providers = new ArrayList <CommandProvider>();
		this.currentActionCommands = new ArrayList<Command>();



	}

	public void addListener( CommandProvider provider){
		providers.add(provider);
	}
	
	
	public void removeListenersOfClass(Class<?> providerClass){
		for (Iterator<CommandProvider> iterator = providers.iterator(); iterator.hasNext();){
			CommandProvider provider2 = iterator.next();
			Class<? extends CommandProvider> className = provider2.getClass();
			
			if (className.equals(providerClass)){
				iterator.remove();
			}
		}
	}
	

	
	//For external inputs such as elevator collisions
	//or being hit by enemies
	private void receiveExternalInputs(){

		for (CommandProvider provider: providers){
			currentActionCommands.addAll(provider.getCommands());
		}
	}

	
	public ArrayList<Command> popCurrentActionCommands(){
		receiveExternalInputs();
		ArrayList<Command> output = currentActionCommands;
		currentActionCommands = new ArrayList<Command>();
		return output;
	}


}
