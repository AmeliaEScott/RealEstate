package me.killgoblen.RealEstate.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class DataTable {
	
	Logger log = Bukkit.getServer().getLogger();
	HashMap<String, HashMap<String, HashMap<String, String>>> map;
	
	@SuppressWarnings("unchecked")
	public DataTable(File file){
		try {
			FileInputStream fin = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fin);
			Object obj = in.readObject();
			if (obj instanceof HashMap<?, ?>){
				try{
					map = (HashMap<String, HashMap<String, HashMap<String, String>>>) obj;
				}catch (ClassCastException e){
					log.severe("Data file " + file.getPath() + " is messed up!");
				}
			}else{
				log.severe("Data file " + file.getPath() + " is messed up!");
			}

		} catch (FileNotFoundException e) {
			log.warning("File " + file.getPath() + " could not be found.");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if (map == null)
			map = new HashMap<String, HashMap<String, HashMap<String, String>>>();
		if(!map.containsKey("NPC"))
			map.put("NPC", new HashMap<String, HashMap<String, String>>());
		if(!map.containsKey("CITY"))
			map.put("CITY", new HashMap<String, HashMap<String, String>>());
		if(!map.containsKey("LOT"))
			map.put("LOT", new HashMap<String, HashMap<String, String>>());
	}
	
	public boolean hasId(String id){
		return map.get("NPC").containsKey(id);
	}
	
	public boolean hasCity(String city){
		return map.get("CITY").containsKey(city);
	}
	
	public void removeNpc(String id){
		if(hasId(id)){
			map.get("NPC").remove(id);
		}
	}
	
	public DataTable(String file){
		File file2 = new File(file);
		if(!file2.exists()){
			try {
				file2.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		map = new HashMap<String, HashMap<String, HashMap<String, String>>>();
		if(!map.containsKey("NPC"))
			map.put("NPC", new HashMap<String, HashMap<String, String>>());
		if(!map.containsKey("CITY"))
			map.put("CITY", new HashMap<String, HashMap<String, String>>());
		if(!map.containsKey("LOT"))
			map.put("LOT", new HashMap<String, HashMap<String, String>>());
	}
	
	public String[] getIds(){
		String[] array = new String[map.get("NPC").size()];
		array = map.get("NPC").keySet().toArray(array);
		return array;
		
	}
	
	public Location getLocation(String id){
		HashMap<String, HashMap<String, String>> map2 = map.get("NPC");
		Location loc = new Location((Bukkit.getServer().getWorld(map2.get(id).get("world"))), 
				Double.parseDouble(map2.get(id).get("x")), Double.parseDouble(map2.get(id).get("y")), Double.parseDouble(map2.get(id).get("z")), 
				Float.parseFloat(map2.get(id).get("yaw")), Float.parseFloat(map2.get(id).get("pitch")));
		return loc;
	}
	
	public void setLocation(String id, Location loc){
		HashMap<String, String> map2 = map.get("NPC").get(id);
		map2.put("world", loc.getWorld().getName());
		map2.put("x", Double.toString(loc.getX()));
		map2.put("y", Double.toString(loc.getY()));
		map2.put("z", Double.toString(loc.getZ()));
		map2.put("yaw", Float.toString(loc.getYaw()));
		map2.put("pitch", Float.toString(loc.getPitch()));
	}
	
	public String getCity(String id){
		return map.get("NPC").get(id).get("city");
	}
	
	public void setCity(String id, String city){
		map.get("NPC").get(id).put("city", city);
	}
	
	public String getName(String id){
		return map.get("NPC").get(id).get("name");
	}
	
	public void setName(String id, String name){
		map.get("NPC").get(id).put("name", name);
	}
	
	public void newNpc(String id, String name, Location loc, String city){
		//id = "NPC:" + id;
		map.get("NPC").put(id, new HashMap<String, String>());
		setLocation(id, loc);
		setCity(id, city);
		setName(id, name);
	}
	
	public void save(String file){
		try{
			File file2 = new File(file);
			if(!file2.exists())
				file2.createNewFile();
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
			out.writeObject((Object) map);
			out.flush();
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void newCity(String city){
		map.get("CITY").put(city, new HashMap<String, String>());
	}
	
	public boolean addLot(String city, String lot, int initprice, int rent, String world){
		if(!map.get("CITY").containsKey(city))
			return false;
		map.get("CITY").get(city).put(lot, "");
		map.get("LOT").put(lot, new HashMap<String, String>());
		map.get("LOT").get(lot).put("originalInit", Integer.toString(initprice));
		map.get("LOT").get(lot).put("originalRent", Integer.toString(rent));
		map.get("LOT").get(lot).put("init", Integer.toString(initprice));
		map.get("LOT").get(lot).put("rent", Integer.toString(rent));
		map.get("LOT").get(lot).put("city", city);
		map.get("LOT").get(lot).put("owner", "");
		map.get("LOT").get(lot).put("world", world);
		return true;
	}
	
	public boolean isInCity(String lot, String city){
		if(!map.get("LOT").containsKey(lot))
			return false;
		String c = map.get("LOT").get(lot).get("city");
		return c.equals(city);
	}
	
	/*public boolean addLot(String city, String lot, String initprice, String rent){
		
		String initpriceS = initprice;
		String rentS = rent;
		String price = initpriceS + ":" + rentS;
		String cityS = "City:" + city;
		map.get(cityS).put(lot, price);
		return true;
	}*/
	
	public int getInitPrice(String city, String lot){
		if(!map.get("LOT").containsKey(lot))
			return -1;
		String init = map.get("LOT").get(lot).get("init");
		return Integer.parseInt(init);
	}
	
	public int getRent(String city, String lot){
		if(!map.get("LOT").containsKey(lot))
			return -1;
		String s = map.get("LOT").get(lot).get("rent");
		return Integer.parseInt(s);
	}
	
	public boolean isAvailable(String lot){
		return true;
	}
	
	public boolean setOwner(String lot, String player){
		if (!map.get("LOT").containsKey(lot)){
			return false;
		}else{
			map.get("LOT").get(lot).put("owner", player);
			return true;
		}
	}
	
	public String getOwner(String lot){
		if(!map.get("LOT").containsKey(lot)){
			return "";
		}else{
			return map.get("LOT").get(lot).get("owner");
		}
	}
	
	public boolean lotExists(String lot){
		return map.get("LOT").containsKey(lot);
	}
	
	public String getWorld(String lot){
		if (!lotExists(lot)){
			return "";
		}else{
			return map.get("LOT").get(lot).get("world");
		}
	}

}
