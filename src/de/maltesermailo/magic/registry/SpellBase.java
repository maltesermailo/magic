package de.maltesermailo.magic.registry;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_10_R1.NBTTagCompound;

public abstract class SpellBase {
	
	private String systemName;
	private String displayName;
	
	private List<String> lore;
	
	protected SpellBase(String systemName, String displayName, String...lore) {
		this.systemName = systemName;
		this.displayName = "§3" + displayName;
		
		List<String> loreList = new ArrayList<>();
		
		for (String line : lore) {
			loreList.add("§7§o" + line);
		}
		
		this.lore = loreList;
	}	
	
	/**
	 * List of target parts:
	 * - Self: the list includes only the caster
	 * - Projectile: the list includes only the hit target
	 * - ExplSelf: the list includes the caster and all living entities around him
	 * - ExplProjectile: the list includes the hit target and all living entities around it
	 * (- Random: the list includes x random entities in the field of view)
	 * (- Global: the list includes all entities in the arena)
	 */
	public abstract void cast(Player caster, SpellModifierList modifiers, List<LivingEntity> targets);
	
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
		
		nbtTag.setString("SpellBase", this.systemName);
		nmsItem.setTag(nbtTag);
		return CraftItemStack.asBukkitCopy(nmsItem);
	}
	
}
