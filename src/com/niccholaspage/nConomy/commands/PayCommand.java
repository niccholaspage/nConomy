package com.niccholaspage.nConomy.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.niccholaspage.nConomy.nConomy;

public class PayCommand implements CommandExecutor {
	public static nConomy plugin;
	public PayCommand(nConomy instance){
		plugin = instance;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		Player player = (Player) sender;
		if (args.length < 3){
			player.sendMessage(ChatColor.RED + "/ncon pay name amount");
			return false;
		}
		if (plugin.isInt(args[2]) == false){
			player.sendMessage(ChatColor.RED + "/ncon pay name amount");
			return false;			
		}
		Integer amount = Integer.parseInt(args[2]);
		if (plugin.canAddorDelete(amount) == false){
			player.sendMessage(ChatColor.RED + "You cannot pay that amount!");
			return true;
		}
		if (plugin.getPlayerStartsWith(args[1].toLowerCase()) == null){
			player.sendMessage(ChatColor.RED + "That user doesn't exist!");
			return true;
		}
		if (plugin.getMoney(player) < amount){
			player.sendMessage("You do not have enough money!");
			return true;
		}
		Player giveto = plugin.getPlayerStartsWith(args[1].toLowerCase());
		plugin.removeMoney(player, amount);
		plugin.addMoney(giveto, amount);
		player.sendMessage(ChatColor.BLUE + "You paid " + giveto.getName() + " " + amount + " " + plugin.currencyName);
		return true;
	}
}
