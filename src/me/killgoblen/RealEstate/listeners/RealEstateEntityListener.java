package me.killgoblen.RealEstate.listeners;

import me.killgoblen.RealEstate.RealEstate;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.martin.bukkit.npclib.NpcEntityTargetEvent;
import org.martin.bukkit.npclib.NpcEntityTargetEvent.NpcTargetReason;

public class RealEstateEntityListener extends EntityListener{
	
	RealEstate plugin;
	
	public RealEstateEntityListener(RealEstate plugin){
		this.plugin = plugin;
	}
	
	public void onEntityTarget(EntityTargetEvent event){
		if(event instanceof NpcEntityTargetEvent){
			NpcEntityTargetEvent nevent = (NpcEntityTargetEvent) event;
			if(nevent.getNpcReason() == NpcTargetReason.NPC_RIGHTCLICKED){
				Player player = (Player)nevent.getTarget();
				String id = plugin.getManager().getNPCIdFromEntity(nevent.getEntity());
				if(!plugin.getChat().isTalkingTo(player, id)){
					plugin.getChat().handleChat(player, "null", id);
				}
				
			}
		}
	}
	
	public void chat(Player player, String id){
		
	}
	

}
