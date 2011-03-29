package com.niccholaspage.nConomy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

import com.niccholaspage.nConomy.commands.*;

public class nConomy extends JavaPlugin {
	public Integer itemID;
	public Integer value;
	public String currencyName;
    @Override
	public void onDisable() {
		System.out.println("nConomy Disabled");
		
	}
    @Override
	public void onEnable() {
       //Get the infomation from the yml file.
        PluginDescriptionFile pdfFile = this.getDescription();
        //Register commands
        registerCommands();
        //Read Config
        readConfig();
        //Print that the plugin has been enabled!
        System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
		
	}
    private void registerCommands(){
    	CommandHandler commandHandler = new CommandHandler(this);
    	getCommand("ncon").setExecutor(commandHandler);
    	commandHandler.registerExecutor("pay", new PayCommand(this));
    }
    public Player getPlayerStartsWith(String startsWith){
    	if (getServer().getOnlinePlayers().length == 0){
    		return null;
    	}
    	for (int i = 0; i < getServer().getOnlinePlayers().length; i++){
    		if (getServer().getOnlinePlayers()[i].getName().toLowerCase().startsWith(startsWith)){
    			return getServer().getOnlinePlayers()[i];
    		}
    	}
    	return null;
    }
    public boolean isInt(String i){
    	try {
    		Integer.parseInt(i);
    		return true;
    	} catch(NumberFormatException nfe){
    		return false;
    	}
    }
    public void readConfig(){
    	File file = new File("plugins/nConomy/");
    	if (!(file.exists())) file.mkdir();
    	file = new File("plugins/nConomy/config.yml");
    	if (!(file.exists())){
  	      try{
	    	    // Create file 
	    	    BufferedWriter out = new BufferedWriter(new FileWriter(file));
	    	    out.write("nConomy:\n");
	    	    out.write("  item: 266\n");
	    	    out.write("  value: 5\n");
	    	    out.write("  currencyname: 'bucks'\n");
	    	    //Close the output stream
	    	    out.close();
	    	    }catch (Exception e){//Catch exception if any
	    	      System.out.println("nConomy could not write the default config file, disabling.");
	    	      getPluginLoader().disablePlugin(this);
	    	    }
    	}
    	Configuration _config = new Configuration(file);
    	_config.load();
    	itemID = _config.getInt("nConomy.item", 266);
    	value = _config.getInt("nConomy.value", 5);
    	currencyName = _config.getString("nConomy.currencyname", "bucks");
    }
    public Integer getMoney(Player player){
    	ItemStack[] items = player.getInventory().getContents();
    	Integer money = 0;
    	for (int i = 0; i < items.length; i++){
    		if (items[i].getType().equals(Material.getMaterial(itemID))){
    			money += items[i].getAmount();
    		}
    	}
    	return money * value;
    }
    @SuppressWarnings("deprecation")
	public boolean removeMoney(Player player, Integer amount){
    	if (!(getMoney(player)%amount == 0)) return false;
    	if (getMoney(player) == 0) return false;
    	ItemStack item = new ItemStack(itemID, amount/value);
    	//if (amount/value == 0) item.setAmount(1);
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
