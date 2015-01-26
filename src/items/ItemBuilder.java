package items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemBuilder {

	private List<Map<String,String>> itemMaps;
	private HashMap<String,ArrayList<Integer>> itemTypeMap = new HashMap<String, ArrayList<Integer>>();
	private ArrayList<String> itemTypes = new ArrayList<String>();

//	public static final int TYPEARMOR = 1;
	
	
	
	public ItemBuilder(List<Map<String,String>> itemMaps){

		this.itemMaps = itemMaps;

		initializeBuilder();
		
		
		// item types [armor, weapons, books, misc]
		
		
	}

	

	public Item newItem(){
		// build a random item
		int itemIndex =  (int)(Math.random()*itemMaps.size());
		
		return buildItem(itemIndex);
	}


	public Item newItem(String typeOrName){

		int itemIndex = -1;

		// get a random item of the input type
		if(itemTypes.contains(typeOrName)){

			ArrayList<Integer> index = itemTypeMap.get(typeOrName);

			int randomNum = (int)(Math.random()*index.size());
			itemIndex = index.get(randomNum);

		}
		// or get an item by name
		else{
			for (int i = 0; i < itemMaps.size(); i++){

				// get the indices of all items with type t
				if(typeOrName.equalsIgnoreCase(itemMaps.get(i).get("name"))){
					itemIndex = i;
				}

			}

		}		
		
		return buildItem(itemIndex);
		
	}

	private Item buildItem(int i){
		
		
		// builds the ith item in the vector list
		Map<String,String> itm = itemMaps.get(i);
		
		if(itm.get("itemType").equalsIgnoreCase("armor")){
			Item item = new Armor(itm);
			return item;
		}else if(itm.get("itemType").equalsIgnoreCase("weapon")){
			Item item = new Weapon(itm);
			return item;
		}
		else if(itm.get("itemType").equalsIgnoreCase("misc")){
			Item item = new Misc(itm);
			return item;
		}
		else if (itm.get("itemType").equalsIgnoreCase("book")){
			Item item = new Book(itm);
			return item;
		}
			
		return null;
		
	}

	// this finds the indices of all the items of all the various types
		// too allow for faster searching when a new item is needed
		private void initializeBuilder(){
			// loop over all items and pull out the item types
			
			ArrayList<Integer> defaultLookup = new ArrayList<Integer>();

			defaultLookup.add(0); 
			defaultLookup.add(itemMaps.size());

			// create a list of the itemTypes
			for (Map<String,String> m : itemMaps ){

				String type = m.get("itemType");

				if(!itemTypes.contains(type)){
					itemTypes.add(type);
				}
			}

			// create a look-up table for the item type
			// create a list of the itemTypes
			for (String type : itemTypes){
				ArrayList<Integer> index = new ArrayList<Integer>();

				for (int i = 0; i < itemMaps.size(); i++){

					// get the indices of all items with type t
					if(type.equalsIgnoreCase(itemMaps.get(i).get("itemType"))){
						index.add(i);
					}

				}
				itemTypeMap.put(type,index);
			}


		}


}
