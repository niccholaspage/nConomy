package com.niccholaspage.nConomy;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Bank {
	public int itemID;
	public int value;
	public String currencyName;
	public nConomy plugin;
	
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
	public boolean removeMoney(String name, Integer amount){
    	Player player = plugin.getServer().getPlayer(name);
    	if (player != null){
    	if (!(getMoney(player)%amount == 0)) return false;
    	if (getMoney(player) == 0) return false;
    	ItemStack item = new ItemStack(itemID, amount/value);
    	player.getInventory().removeItem(item);
    	player.updateInventory();
    	}else {
    		plugin.fileHandler.writeLine("removemoney," + name + "," + amount);
    	}
    	return true;
    }
    public boolean setMoney(String name, Integer amount){
    	if (canAddorDelete(amount) == false) return false;
    	Player player = plugin.getServer().getPlayer(name);
    	if (player != null){
    		removeMoney(name, getMoney(player));
    		return addMoney(name, amount);
    	}else {
    		plugin.fileHandler.writeLine("setmoney," + name + "," + amount);
    		return true;
    	}
    }
    @SuppressWarnings("deprecation")
	public boolean addMoney(String name, Integer amount){
    	if (canAddorDelete(amount) == false) return false;
    	ItemStack item = new ItemStack(itemID, amount/value);
    	Player player = plugin.getServer().getPlayer(name);
    	if (player != null){
    	player.getInventory().addItem(item);
    	player.updateInventory();
    	}else {
    		plugin.fileHandler.writeLine("addmoney," + name + "," + amount);
    	}
    	return true;
    }
    public boolean canAddorDelete(Integer amount){
    	return (amount%value == 0);
    }
}
