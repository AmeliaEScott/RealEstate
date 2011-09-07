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
	HashMap<String, HashMap<String, String>> map;
	
	@SuppressWarnings("unchecked")
	public DataTable(File file){
		try {
			FileInputStream fin = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fin);
			Object obj = in.readObject();
			if (obj instanceof HashMap<?, ?>){
				try{
					map = (HashMap<String, HashMap<String, String>>) obj;
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
			map = new HashMap<String, HashMap<String, String>>();
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
		map = new HashMap<String, HashMap<String, String>>();
	}
	
	public String[] getIds(){
		String[] array = new String[map.size()];
		array = map.keySet().toArray(array);
		for (int i = 0; i < array.length; i++){
			String s = array[i].split(":", 2)[1];
			array[i] = s;
		}
		return array;
		
	}
	
	public Location getLocation(String id){
		id = "NPC:" + id;
		Location loc = new Location((Bukkit.getServer().getWorld(map.get(id).get("world"))), 
				Double.parseDouble(map.get(id).get("x")), Double.parseDouble(map.get(id).get("y")), Double.parseDouble(map.get(id).get("z")), 
				Float.parseFloat(map.get(id).get("yaw")), Float.parseFloat(map.get(id).get("pitch")));
		return loc;
	}
	
	public void setLocation(String id, Location loc){
		HashMap<String, String> map2 = map.get("NPC:" + id);
		map2.put("world", loc.getWorld().getName());
		map2.put("x", Double.toString(loc.getX()));
		map2.put("y", Double.toString(loc.getY()));
		map2.put("z", Double.toString(loc.getZ()));
		map2.put("yaw", Float.toString(loc.getYaw()));
		map2.put("pitch", Float.toString(loc.getPitch()));
	}
	
	public String getCity(String id){
		return map.get("NPC:" + id).get("city");
	}
	
	public void setCity(String id, String city){
		map.get("NPC:" + id).put("city", city);
	}
	
	public String getName(String id){
		return map.get("NPC:" + id).get("name");
	}
	
	public void setName(String id, String name){
		map.get("NPC:" + id).put("name", name);
	}
	
	public void newNpc(String id, String name, Location loc, String city){
		//id = "NPC:" + id;
		map.put("NPC:" + id, new HashMap<String, String>());
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
		map.put("City:" + city, new HashMap<String, String>());
	}
	
	public boolean addLot(String city, String lot, int initprice, int rent){
		if (!map.containsKey("City:" + city) || map.get("City:" + city) == null)
			return false;String initpriceS = Integer.toString(initprice);
		String rentS = Integer.toString(rent);
		String price = initpriceS + ":" + rentS;
		String cityS = "City:" + city;
		map.get(cityS).put(lot, price);
		return true;
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
		String init = map.get("City:" + city).get(lot).split(":")[0];
		int price = Integer.parseInt(init);
		return price;
	}
	
	public int getRent(String city, String lot){
		String rent = map.get("City:" + city).get(lot).split(":")[1];
		int price = Integer.parseInt(rent);
		return price;
	}

}
