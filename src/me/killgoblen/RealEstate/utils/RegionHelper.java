package me.killgoblen.RealEstate.utils;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import me.killgoblen.RealEstate.RealEstate;

import org.bukkit.World;
import org.bukkit.plugin.Plugin;

public class RegionHelper {
	
	RealEstate plugin;
	WorldGuardPlugin worldGuard;
	
	public RegionHelper(RealEstate plugin){
		this.plugin = plugin;
		if(getWorldGuard() != null){
			worldGuard = getWorldGuard();
		}
	}
	
	public WorldGuardPlugin getWorldGuard(){
		Plugin wg = plugin.getServer().getPluginManager().getPlugin("WorldGuard");
		if (wg == null || !(wg instanceof WorldGuardPlugin)){
			System.out.print("SOMETHING WENT WRONG!!! PANIC TIME!!!");
			return null;
		}
		return (WorldGuardPlugin) wg;
	}
	
	public boolean setMembers(String regionID, World world, String[] players){
		ProtectedRegion region = getWorldGuard().getGlobalRegionManager().get(world).getRegion(regionID);
		if(region == null)
			return false;
		DefaultDomain domain = new DefaultDomain();
		for(String name : players){
			domain.addPlayer(name);
		}
		region.setMembers(domain);
		return true;
	}
	
	
}
