package me.killgoblen.RealEstate.listeners;

import me.killgoblen.RealEstate.RealEstate;
import org.bukkit.event.player.PlayerListener;

public class RealEstatePlayerListener extends PlayerListener{
	
	RealEstate plugin;
		
	public RealEstatePlayerListener(RealEstate plugin){
		this.plugin = plugin;
	}

}
