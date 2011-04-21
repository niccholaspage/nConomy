package com.niccholaspage.nConomy;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Bank {
	public int itemID;
	public int value;
	public String currencyName;
	
    public Integer getMoney(Player player){
    	ItemStack[] items = player.getInventory().getContents();
    	Integer money = 0;
    	for (int i = 0; i < items.length; i++){
    		if (items[i] != null){
    		if (items[i].getType().equals(Material.getMaterial(itemID))){
    			money += items[i].getAmount();
    		}
    		}
    	}
    	return money * value;
    }
    @SuppressWarnings("deprecation")
	public boolean removeMoney(Player player, Integer amount){
    	if (!(getMoney(player)%amount == 0)) return false;
    	if (getMoney(player) == 0) return false;
    	ItemStack item = new ItemStack(itemID, amount/value);
    	player.getInventory().removeItem(item);
    	player.updateInventory();
    	return true;
    }
    public boolean setMoney(Player player, Integer amount){
    	if (canAddorDelete(amount) == false) return false;
    	removeMoney(player, getMoney(player));
    	if (addMoney(player, amount) == false) return false; else return true;
    }
    @SuppressWarnings("deprecation")
	public boolean addMoney(Player player, Integer amount){
    	if (!(amount%value == 0)) return false;
    	ItemStack item = new ItemStack(itemID, amount/value);
    	player.getInventory().addItem(item);
    	player.updateInventory();
    	return true;
    }
    public boolean canAddorDelete(Integer amount){
    	if (amount%value == 0) return true; else return false;
    }
}
