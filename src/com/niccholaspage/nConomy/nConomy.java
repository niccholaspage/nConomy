package com.niccholaspage.nConomy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

import com.niccholaspage.nConomy.commands.*;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class nConomy extends JavaPlugin {
	public PermissionHandler Permissions;
	public static Bank bank = new Bank();
    @Override
	public void onDisable() {
		System.out.println("nConomy Disabled");
		
	}
    @Override
	public void onEnable() {
       //Get the infomation from the yml file.
        PluginDescriptionFile pdfFile = this.getDescription();
        //Setup Permissions
        setupPermissions();
        //Register commands
        registerCommands();
        //Read Config
        readConfig();
        //Print that the plugin has been enabled!
        System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
	}
    public static Bank getBank(){
    	return bank;
    }
    private void registerCommands(){
    	CommandHandler commandHandler = new CommandHandler(this);
    	getCommand("ncon").setExecutor(commandHandler);
    	commandHandler.registerExecutor("pay", new PayCommand(this));
    }
    private void setupPermissions(){
        Plugin test = this.getServer().getPluginManager().getPlugin("Permissions");

        if (this.Permissions == null) {
            if (test != null) {
                this.Permissions = ((Permissions)test).getHandler();
            } else {
            	System.out.println("Permissions not detected, Everyone can use all the commands.");
            }
        }
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
    	bank.itemID = _config.getInt("nConomy.item", 266);
    	bank.value = _config.getInt("nConomy.value", 5);
    	bank.currencyName = _config.getString("nConomy.currencyname", "bucks");
    }
}
