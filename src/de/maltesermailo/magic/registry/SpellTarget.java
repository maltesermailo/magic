package de.maltesermailo.magic.registry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_10_R1.NBTTagCompound;

public abstract class SpellTarget {
	
	private String systemName;
	private String displayName;
	
	private List<String> lore;
	
	protected SpellTarget(String systemName, String displayName, String... lore) {
		this.systemName = systemName;
		this.displayName = "§c" + displayName;
		
		List<String> loreList = new ArrayList<>();
		
		for (String line : lore) {
			loreList.add("§7§o" + line);
		}
		
		this.lore = loreList;
	}
	
	public abstract void execute(Player caster, SpellModifierList modifiers, Consumer<List<LivingEntity>> action);
	
	public abstract List<SpellModifier> getValidModifiers();
	
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
		
		nbtTag.setString("SpellTarget", this.systemName);
		nmsItem.setTag(nbtTag);
		return CraftItemStack.asBukkitCopy(nmsItem);
	}
	
}
