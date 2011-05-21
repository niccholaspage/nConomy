package com.niccholaspage.nConomy;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHandler implements CommandExecutor {
	 public static nConomy plugin;
	 //Thanks for the idea BigBrother (specifically N3X15)
	 private HashMap<String, CommandExecutor> executors = new HashMap<String, CommandExecutor>();
	 private HashMap<String, Boolean> consoleUse = new HashMap<String, Boolean>();
	  public CommandHandler(nConomy instance) {
	        plugin = instance;
	    }
	    public void registerExecutor(String subcmd, CommandExecutor cmd, boolean canUseInConsole) {
	        executors.put(subcmd.toLowerCase(), cmd);
	        consoleUse.put(subcmd.toLowerCase(), canUseInConsole);
	    }
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if (args.length < 1){
			if (sender instanceof Player){
				Player player = (Player)sender;
				if (plugin.Permissions != null)
					if (!(plugin.Permissions.has(player, "nConomy.money"))) return true;
				if (nConomy.getBank().getMoney(player) == 0){
					player.sendMessage(ChatColor.GREEN + "You have no " + nConomy.getBank().currencyName + ".");
			}else{
				player.sendMessage(ChatColor.GREEN + "You have " + nConomy.getBank().getMoney(player) + " " + nConomy.getBank().currencyName + ".");
			}
				return true;
			} else{
				sender.sendMessage("Very funny. The console cannot check its money!");
				return true;
			}
		}
		if (args[0] == "cache"){
			for (String cache : plugin.fileHandler.getCache()){
				sender.sendMessage(cache);
			}
			return true;
		}
		if (!(executors.containsKey(args[0]))) return true;
		if (!(sender instanceof Player)){
			if (!consoleUse.get(args[0])){
				sender.sendMessage("This nConomy command cannot be used in the console!");
				return true;
			}
		}
		executors.get(args[0]).onCommand(sender, cmd, commandLabel, args);
		return true;
	}
}
