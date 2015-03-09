package commands;

import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.command.Command;


//Listens to command inputs from generic providers
public class CommandProviderAggregator {


	private ArrayList <CommandProvider> providers; 	
	private ArrayList<Command> currentActionCommands ;

	public CommandProviderAggregator() {
		providers = new ArrayList <CommandProvider>();
		this.currentActionCommands = new ArrayList<Command>();



	}

	public void addProvider( CommandProvider provider){
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
		@SuppressWarnings("unchecked")
		ArrayList<Command> output = (ArrayList<Command>) currentActionCommands.clone();
		currentActionCommands.clear();
		
		
		return output;
	}


}
