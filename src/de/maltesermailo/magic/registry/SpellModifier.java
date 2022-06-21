package de.maltesermailo.magic.registry;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_10_R1.NBTTagCompound;

public class SpellModifier {
	
	private int amount;
	
	private String systemName;
	private String displayName;
	
	private List<String> lore;
	
	protected SpellModifier(String systemName, String displayName, int amount, String...lore) {
		this.systemName = systemName;
		this.displayName = "§a" + displayName;
		
		this.amount = amount; 
		
		List<String> loreList = new ArrayList<>();
		
		for (String line : lore) {
			loreList.add("§7§o" + line);
		}
		
		this.lore = loreList;
	}
	
	public int getMaximumAmount() {
		return this.amount;
	}
	
	public String getSystemName() {
		return this.systemName;
	}
	
	public String getDisplayName() {
		return this.displayName;
	}
	
	public List<String> getLore() {
		return this.lore;
	}
	
	public ItemStack initItem(ItemStack item) {
		net.minecraft.server.v1_10_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound nbtTag = nmsItem.getTag();
		
		if (nbtTag == null) {
			nbtTag = new NBTTagCompound();
		}
		
		nbtTag.setString("SpellModifier", this.systemName);
		nmsItem.setTag(nbtTag);
		return CraftItemStack.asBukkitCopy(nmsItem);
	}

}
