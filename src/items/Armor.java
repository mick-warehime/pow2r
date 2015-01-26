package items;

import java.util.ArrayList;
import java.util.Map;

import org.newdawn.slick.Image;

public class Armor extends Item{


	private Integer AC;
	private String material;
	private String worn;

	public Armor(Map<String, String> itm) {
		
		super(itm);
		
		
		this.AC = (int) Float.parseFloat(itm.get("ac"));
		this.worn = itm.get("worn");
		this.material = itm.get("material");
		
	}


	public Integer getAC(){
		return AC;
	}
	
	public String getMaterial(){
		return material;
	}
	
	public String getWorn(){
		return worn;
	}
	
	public void equip(){
		// add AC remove item from person
	}
	
	public void unequip(){
	}
	
	public void use(){
	}

	

}