package me.killgoblen.RealEstate.utils;

import java.util.HashMap;

import org.bukkit.entity.Player;

import me.killgoblen.RealEstate.RealEstate;

public class NpcChatManager {
	
	public RealEstate plugin;
	HashMap<String, String> map = new HashMap<String, String>();
	
	public NpcChatManager(RealEstate plugin){
		this.plugin = plugin;
	}
	
	public boolean isTalkingTo(Player player, String id){
		if(map.containsKey(player.getName())){
			String s = map.get(player.getName().split(":", 2)[0]);
			return s.equals(id);
		}
		return false;
	}
	
	//id:q
	
	public void handleChat(Player player, String msg, String id){
		if(!isTalkingTo(player, id))
			return;
		
		String s = map.get(player.getName()).split(":", 2)[1];
		String city = plugin.getData().getCity(id);
		
		if(s.equals("null")){
			
			player.sendMessage("Hello, " + player.getDisplayName() + "! I am the Real Estate agent for " + city);
			player.sendMessage("Would you like some $help, or do you want to $buy a lot?");
			map.put(player.getName(), id + ":GREET");
		}else if(s.equals("GREET")){
			if(msg.equalsIgnoreCase("$help")){
				player.sendMessage("Type $buy, then, when prompted, the desired lot number.");
			}else if(msg.equalsIgnoreCase("$buy")){
				player.sendMessage("Please type a $ followed by the desired lot number.");
				map.put(player.getName(), id + ":LOT");
			}else{
				player.sendMessage("Please give an acceptable answer.\n$help, $buy");
			}
		}else if(s.equals("LOT")){
			msg = msg.replaceFirst("$", "");
			if(!msg.contains(" ")){
				if(plugin.getData().isInCity(msg, city)){
					player.sendMessage("Are you sure you want to buy lot " + msg + " in " + city + "?");
					player.sendMessage("Initial price: " + plugin.getData().getInitPrice(city, msg) +
							". Weekly rent: " + plugin.getData().getRent(city, msg));
					map.put(player.getName(), id + ":CONFIRMBUY-" + msg);
				}else{
					player.sendMessage("No lot called " + msg + " exists in " + city);
				}
			}else{
				player.sendMessage("No spaces, please.");
			}
		}else if(s.startsWith("CONFIRMBUY")){
			msg = msg.toLowerCase();
			String lot = s.split("-", 2)[1];
			if(msg.equals("$yes")){
				player.sendMessage("You have bought lot " + lot + "!");
			}else if(msg.equals("$no")){
				player.sendMessage("Ok, never mind!");
				map.remove(player.getName());
			}else{
				player.sendMessage("Please give an acceptable answer.\n$yes, $no");
			}
		}
	}

}
