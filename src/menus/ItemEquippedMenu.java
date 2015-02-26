package menus;

import items.Item;

import java.util.ArrayList;

import commands.MenuCloseCurrentCommand;
import commands.MenuDropItemCommand;
import commands.MenuEquipItemCommand;
import commands.MenuUnequipItemCommand;
import commands.NullCommand;

public class ItemEquippedMenu extends TextMenu {

	
	
	

	public ItemEquippedMenu( int menuRenderX, int menuRenderY, Item item) {
		super(Menu.MENU_ITEM, menuRenderX, menuRenderY);
		
		defineMenuSelections(item);
	}

	
	protected void defineMenuSelections(Item item) {
		selections = new ArrayList<MenuSelection>();

		
		addMenuSelection( new MenuUnequipItemCommand(item), "Unequip Item");
		addMenuSelection( new MenuDropItemCommand(item), "Drop Item");
		addMenuSelection( new MenuCloseCurrentCommand(), "Cancel");


	}


	
	
	

}
