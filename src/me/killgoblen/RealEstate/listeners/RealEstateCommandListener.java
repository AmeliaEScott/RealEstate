package me.killgoblen.RealEstate.listeners;

import me.killgoblen.RealEstate.RealEstate;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RealEstateCommandListener implements CommandExecutor{
	
	public RealEstate plugin;
	
	public RealEstateCommandListener(RealEstate plugin){
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		Player player = (Player) sender;
		if(args.length == 4 && sender instanceof Player && args[0].equalsIgnoreCase("agent")){
			plugin.getManager().spawnNPC(args[2], player.getLocation(), args[1]);
			plugin.getData().newNpc(args[1], args[2], player.getLocation(), args[3]);
			player.sendMessage(plugin.getData().getCity(args[1]));
			return true;
		}else if(args.length == 2 && args[0].equalsIgnoreCase("addcity")){
			plugin.getData().newCity(args[1]);
			return true;
		}else if(args.length == 5 && args[0].equalsIgnoreCase("addLot")){
			int init;
			int rent;
			try{
				init = Integer.parseInt(args[3]);
				rent = Integer.parseInt(args[4]);
			}catch (NumberFormatException e){
				player.sendMessage("Invalid number somewhere. Figure it out on your own.");
				return false;
			}
			if(init < 0 || rent < 0){
				player.sendMessage("No negatives!");
				return false;
			}
				
			if(!(plugin.getData().addLot(args[1], args[2], init, rent)))
				player.sendMessage("Yo make a citeh first man");
			player.sendMessage("Lot added! " + init + " " + rent);
		}
		return false;
	}

}
