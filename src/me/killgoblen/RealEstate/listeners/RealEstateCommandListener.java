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
			int init = getIntFromString(args[3]);
			int rent = getIntFromString(args[4]);
			if(init == -1 || rent == -1){
				player.sendMessage("Invalid number.");
				return true;
			}
			if(init == -2 || rent == -2){
				player.sendMessage("No negatives!");
				return true;
			}
			
				
			if(!(plugin.getData().addLot(args[1], args[2], init, rent, player.getLocation().getWorld().getName())))
				player.sendMessage("Yo make a citeh first man");
			player.sendMessage("Lot added! " + init + " " + rent);
		}else if (args.length == 2 && args[0].equalsIgnoreCase("removeAgent")){
			if (plugin.getData().hasId(args[1])){
				plugin.getData().removeNpc(args[1]);
				return true;
			}else{
				sender.sendMessage("No npc with that name found!");
				return true;
			}
		}else if(args.length == 3 && args[0].equalsIgnoreCase("setMember")){
			String[] array = new String[1];
			array[0] = args[2];
			plugin.getRegionHelper().setMembers(args[1], player.getWorld(), array);
		}
		return false;
	}
	
	private int getIntFromString(String number){
		int i;
		try{
			i = Integer.parseInt(number);
		}catch (NumberFormatException e){
			return -1;
		}
		
		if (i < 0)
			return -2;
		return i;
	}

}
