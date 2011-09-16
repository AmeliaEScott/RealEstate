package me.killgoblen.RealEstate.listeners;

import me.killgoblen.RealEstate.RealEstate;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;

public class RealEstatePlayerListener extends PlayerListener{
	
	RealEstate plugin;
		
	public RealEstatePlayerListener(RealEstate plugin){
		this.plugin = plugin;
	}
	
	public void onPlayerChat(PlayerChatEvent event){
		Player player = event.getPlayer();
		if(plugin.getChat().isTalking(player) && event.getMessage().startsWith("$")){
			event.setCancelled(true);
			plugin.getChat().handleChat(player, "blarghlol", event.getMessage());
		}
	}

}
