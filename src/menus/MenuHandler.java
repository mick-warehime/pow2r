package menus;

import interfaces.CommandProvider;
import items.Inventory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProviderListener;

import actionEngines.MenuActionEngine;

import commands.CommandProviderAggregator;

import controls.KeyboardInputListener;


/* Applies player input commands to menus, stores them, and plots them.
 * 
 * 
 */
public class MenuHandler {



	private KeyboardInputListener keyboardInputs;
	private MenuInputListener menuInputs;
	private CommandProviderAggregator commandProviderAggregator;
	private MenuActionEngine actionEngine;
	private MenuHandlerData menuHandlerData;


	public MenuHandler(){
		
		
		
		this.keyboardInputs = new KeyboardInputListener();
		this.menuInputs = new MenuInputListener();
		this.commandProviderAggregator = new CommandProviderAggregator();
		commandProviderAggregator.addUniqueProvider(keyboardInputs);
		commandProviderAggregator.addUniqueProvider(menuInputs);

		this.menuHandlerData = new MenuHandlerData();
		
		this.actionEngine = new MenuActionEngine(commandProviderAggregator, menuHandlerData);


	}



	public InputProviderListener getKeyboardListener() {
		return this.keyboardInputs;
	}

	public void update() throws SlickException, IOException {
		actionEngine.update();	
		
	}

	public void renderOpenMenus(Graphics graphics){

		for (Menu menu : menuHandlerData.getAllActiveMenus()){
				menu.render(graphics);
		}
	}
	
	public boolean isMenuActive(){
		Stack <Menu> activeMenus = menuHandlerData.getAllActiveMenus();
		return (!activeMenus.isEmpty());
		
	}
	
	
	//Listens to inputs from menus
	class MenuInputListener implements CommandProvider
	{
		@Override
		public ArrayList<Command> getCommands() {
			Menu menu = menuHandlerData.getTopActiveMenu();
			ArrayList<Command> output = new ArrayList<Command>();
			
			if (menu != null){
				if (menu.isSelectionActivated()){
					Command cmd = menu.getSelectionCommand();
					output.add(cmd);
				}
			}
			
			return output;
		}

		
	}


	public boolean isQuitting() {
		// TODO Auto-generated method stub
		return menuHandlerData.isQuitting();
	}



	public void setPlayerInventory(Inventory inventory) {
		this.menuHandlerData.setPlayerInventory(inventory);
		
	}



	

	
	

}
