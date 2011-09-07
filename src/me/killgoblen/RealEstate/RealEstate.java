package me.killgoblen.RealEstate;

import java.io.File;

import me.killgoblen.RealEstate.listeners.RealEstateCommandListener;
import me.killgoblen.RealEstate.listeners.RealEstateEntityListener;
import me.killgoblen.RealEstate.listeners.RealEstatePlayerListener;
import me.killgoblen.RealEstate.utils.DataTable;

import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.martin.bukkit.npclib.NPCManager;

public class RealEstate extends JavaPlugin{
	
	public DataTable data;
	public NPCManager manager;
	
	public void onEnable(){
		PluginManager pm = getServer().getPluginManager();
		RealEstatePlayerListener pl = new RealEstatePlayerListener(this);
		RealEstateCommandListener cmd = new RealEstateCommandListener(this);
		RealEstateEntityListener ent = new RealEstateEntityListener(this);
		pm.registerEvent(Event.Type.PLAYER_INTERACT, pl, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_INTERACT_ENTITY, pl, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_MOVE, pl, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.ENTITY_TARGET, ent, Event.Priority.Normal, this);
		getCommand("RealEstate").setExecutor(cmd);
		try{
			new File("plugins/RealEstate").mkdir();
			File file = new File("plugins/RealEstate/RealEstate.dat");
			if(!file.exists()){
				data = new DataTable(file.getPath());
			}else{
				data = new DataTable(file);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		manager = new NPCManager(this);
		for (String id : data.getIds()){
			System.out.print(id);
			manager.spawnNPC(data.getName(id), data.getLocation(id), id);
		}
	}
	
	public void onDisable(){
		data.save("plugins/RealEstate/RealEstate.dat");
	}
	
	public DataTable getData(){
		return data;
	}
	
	public NPCManager getManager(){
		return manager;
	}


}
