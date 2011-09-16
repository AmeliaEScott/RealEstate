package me.killgoblen.RealEstate.utils;

import java.util.HashMap;

import org.bukkit.entity.Player;

import me.killgoblen.RealEstate.RealEstate;

public class NpcChatManager {
	
	public RealEstate plugin;
	HashMap<String, Chat> map = new HashMap<String, Chat>();
	
	public NpcChatManager(RealEstate plugin){
		this.plugin = plugin;
	}
	
	public boolean isTalkingTo(Player player, String id){
		if(map.containsKey(player.getName())){
			if(map.get(player.getName()).id == id)
				return true;
		}
		return false;
	}
	
	public boolean isTalking(Player player){
		return map.containsKey(player.getName());
	}
	
	public void handleChat(Player player, String id, String msg){
		if(!map.containsKey(player.getName())){
			map.put(player.getName(), new Chat(player, id));
			return;
		}
		map.get(player.getName()).handleChat(player, msg);
	}

	private class Chat{
		
		private Question question;
		private String name;
		private String id;
		
		
		public Chat(Player player, String id){
			question = Question.GREET;
			name = player.getName();
			this.id = id;
			player.sendMessage("Hello, " + name + "! I am the real estate agent for " + plugin.getData().getCity(id) + ".");
			player.sendMessage("Would you like to $buy a lot, or would you like some $help?");
		}
		
		public void handleChat(Player player, String s){
			String[] msg = s.split(" ");
			msg[0] = msg[0].replaceFirst("\\$", "");
			System.out.print(msg[0]);
			if (question == Question.GREET){
				if(msg[0].equalsIgnoreCase("help")){
					player.sendMessage("Type $help or $buy. Duh.");
					return;
				}else if(msg[0].equalsIgnoreCase("buy")){
					question = Question.LOT;
					player.sendMessage("Which lot number would you like to buy? (Start with a $)");
					return;
				}else{
					player.sendMessage("Please give an acceptable response. ($buy, $help)");
					return;
				}
			}else if(question == Question.LOT){
				if(plugin.getData().isInCity(msg[0], plugin.getData().getCity(id))){
					player.sendMessage("You bought lot " + msg[0] + "!");
				}else{
					player.sendMessage("That lot is not in this city, derp-tard! :D");
					return;
				}
			}
		}
		
		
	}
	
	enum Question{
			NONE, GREET, LOT, SURE_BUY, SURE_SELL
	}
}
