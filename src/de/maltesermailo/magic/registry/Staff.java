package de.maltesermailo.magic.registry;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.maltesermailo.magic.Magic;
import net.minecraft.server.v1_10_R1.NBTTagCompound;
import net.minecraft.server.v1_10_R1.NBTTagList;

/**
 * NBT-Format:
 * Magic:{Base:"systemName",Target:"systemName",Modifiers:[{name:"systemName",amount:0},{name:"systemName",amount:1}],MaxModifiers:5,Cooldown:10}
 *
 */
public class Staff {
	
	public static void updateStaffDisplay(ItemStack staff) {
		if (!Staff.isStaff(staff)) {
			throw new RuntimeException("The item has to be a staff!");
		}
		
		SpellBase base = null;
		SpellTarget target = null;
		
		if (Staff.isBaseSet(staff)) {
			base = Staff.getBase(staff);
		}
		
		if (Staff.isTargetSet(staff)) {
			target = Staff.getTarget(staff);
		}
		
		ItemMeta meta = staff.getItemMeta();
		meta.setDisplayName("§7[" + (target != null ? target.getDisplayName() : "§8???") + "§7] " + (base != null ? base.getDisplayName() : "§8???"));
		
		List<String> lore = new ArrayList<>();
		lore.add("");
		lore.add("§8=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		lore.add("§7Maximale Modifikationen: §6" + Staff.getMaximalModifications(staff));
		lore.add("§7Cooldown: §6" + Staff.getCooldownLength(staff));
		lore.add("§7");
		
		if (base != null && target != null) {
			lore.add("§7Modifikationen: ");
			
			SpellModifierList list = Staff.getModifiers(staff);
			
			if (list.asArrayList().size() == 0) {
				lore.add("§7  §7§oKeine");
			} else {
				for (SpellModifier modifier : list) {
					lore.add("§7- " + modifier.getDisplayName());
				}
			}
		} else {
			lore.add("§7§lDer Stab ist noch nicht fertig!");
		}
		
		lore.add("§8=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		
		meta.setLore(lore);
		staff.setItemMeta(meta);
	}
	
	public static boolean isReady(ItemStack staff) {
		if (!Staff.isStaff(staff)) {
			return false;
		}
		
		return Staff.isBaseSet(staff) && Staff.isTargetSet(staff);
	}
	
	public static boolean isStaff(ItemStack staff) {
		net.minecraft.server.v1_10_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(staff);
		
		NBTTagCompound nbtTag = null;
		
		if (nmsItem.getTag() != null) {
			nbtTag = nmsItem.getTag();
			
			return nbtTag.hasKey("Magic");
		}
		
		return false;
	}
	
	public static boolean isBaseSet(ItemStack staff) {
		if (!Staff.isStaff(staff)) {
			throw new RuntimeException("The item has to be a staff!");
		}
		
		net.minecraft.server.v1_10_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(staff);
		NBTTagCompound magicTag = nmsItem.getTag().getCompound("Magic");
		
		String base = magicTag.getString("Base");
		return !base.isEmpty() && Magic.instance().getRegistry().getSpellBase(base) != null;
	}

	public static boolean isTargetSet(ItemStack staff) {
		if (!Staff.isStaff(staff)) {
			throw new RuntimeException("The item has to be a staff!");
		}
		
		net.minecraft.server.v1_10_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(staff);
		NBTTagCompound magicTag = nmsItem.getTag().getCompound("Magic");
		
		String target = magicTag.getString("Target");
		return !target.isEmpty() && Magic.instance().getRegistry().getSpellTarget(target) != null;
	}
	
	public static boolean isApplyable(ItemStack staff, SpellModifier modifier) {
		if (!Staff.isStaff(staff)) {
			throw new RuntimeException("The item has to be a staff!");
		}
		
		if (!Staff.isTargetSet(staff) || !Staff.isBaseSet(staff)) {
			throw new RuntimeException("The staff has to be built!");
		}

		List<SpellModifier> validModifiers = new ArrayList<>();
		validModifiers.addAll(Staff.getBase(staff).getValidModifiers());
		validModifiers.addAll(Staff.getTarget(staff).getValidModifiers());
		
		if (!validModifiers.contains(modifier)) {
			return false;
		}
		
		net.minecraft.server.v1_10_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(staff);

		NBTTagCompound nbtTag = nmsItem.getTag();
		NBTTagCompound magicTag = nbtTag.getCompound("Magic");
		
		int maxModifiers = Staff.getMaximalModifications(staff);
		
		NBTTagList modifiersTag = magicTag.getList("Modifiers", 10);
		
		if (modifiersTag.size() >= maxModifiers) {
			return false;
		}
		
		if (modifiersTag.isEmpty()) {
			return true;
		} else {
			NBTTagCompound modifierTag = null;
			int i = Staff.checkContains(modifiersTag, modifier);

			if (i != -1) {
				modifierTag = modifiersTag.get(i);

				if (modifierTag.getInt("amount") < modifier.getMaximumAmount()) {
					return true;
				} else {
					return false;
				}
			} else {
				return true;
			}
		}
	}
	
	public static int getCooldownLength(ItemStack staff) {
		if (!Staff.isStaff(staff)) {
			throw new RuntimeException("The item has to be a staff!");
		}
		
		return CraftItemStack.asNMSCopy(staff).getTag().getCompound("Magic").getInt("Cooldown");
	}
	
	public static int getMaximalModifications(ItemStack staff) {
		if (!Staff.isStaff(staff)) {
			throw new RuntimeException("The item has to be a staff!");
		}
		
		return CraftItemStack.asNMSCopy(staff).getTag().getCompound("Magic").getInt("MaxModifiers");
	}
	
	public static ItemStack newStaff(Material type, int maxModifiers, int cooldown) {
		net.minecraft.server.v1_10_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(new ItemStack(type));
		
		NBTTagCompound nbtTag;

		if (nmsItem.getTag() != null) {
			nbtTag = nmsItem.getTag();
		} else {
			nbtTag = new NBTTagCompound();
		}

		NBTTagCompound magicTag = new NBTTagCompound();
		magicTag.setInt("MaxModifiers", maxModifiers);
		magicTag.setInt("Cooldown", cooldown);
		magicTag.setString("Base", "");
		magicTag.setString("Target", "");
		
		NBTTagList modifierList = new NBTTagList();
		
		// setup, cause 'typeId' in NBTTagList
		modifierList.add(new NBTTagCompound());
		modifierList.remove(0);
		
		magicTag.set("Modifiers", modifierList);
		
		nbtTag.set("Magic", magicTag);
		nmsItem.setTag(nbtTag);
		return CraftItemStack.asBukkitCopy(nmsItem);
	}

	public static ItemStack initBase(ItemStack staff, SpellBase spell) {
		if (!Staff.isStaff(staff)) {
			throw new RuntimeException("The item has to be a staff!");
		}
		
		if (Staff.isBaseSet(staff)) {
			throw new RuntimeException("The spell base is already set!");
		}
		
		net.minecraft.server.v1_10_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(staff);
		NBTTagCompound nbtTag = nmsItem.getTag();
		NBTTagCompound magicTag = nbtTag.getCompound("Magic");
		
		magicTag.setString("Base", spell.getSystemName());
		nbtTag.set("Magic", magicTag);
		nmsItem.setTag(nbtTag);
		return CraftItemStack.asBukkitCopy(nmsItem);
	}

	public static ItemStack initTarget(ItemStack staff, SpellTarget target) {
		if (!Staff.isStaff(staff)) {
			throw new RuntimeException("The item has to be a staff!");
		}
		
		if (Staff.isTargetSet(staff)) {
			throw new RuntimeException("The spell target is already set!");
		}
		
		net.minecraft.server.v1_10_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(staff);
		NBTTagCompound nbtTag = nmsItem.getTag();
		NBTTagCompound magicTag = nbtTag.getCompound("Magic");
		
		magicTag.setString("Target", target.getSystemName());
		nbtTag.set("Magic", magicTag);
		nmsItem.setTag(nbtTag);
		return CraftItemStack.asBukkitCopy(nmsItem);
	}

	/**
	 * @author Nightishaman
	 */
	public static ItemStack addModifier(ItemStack staff, SpellModifier modifier) {
		if (!Staff.isStaff(staff)) {
			throw new RuntimeException("The item has to be a staff!");
		}
		
		if (!Staff.isTargetSet(staff) || !Staff.isBaseSet(staff)) {
			throw new RuntimeException("The staff has to be built!");
		}

		net.minecraft.server.v1_10_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(staff);

		NBTTagCompound nbtTag = nmsItem.getTag();
		NBTTagCompound magicTag = nbtTag.getCompound("Magic");
		
		NBTTagList modifiersTag = magicTag.getList("Modifiers", 10);
		
		if (!Staff.isApplyable(staff, modifier)) {
			throw new RuntimeException("Maximal modifier count reached!");
		}
		
		if (modifiersTag.isEmpty()) {
			NBTTagCompound modifierTag = new NBTTagCompound();
			modifierTag.setString("name", modifier.getSystemName());
			modifierTag.setInt("amount", 1);

			modifiersTag.add(modifierTag);
		} else {
			NBTTagCompound modifierTag = null;
			int i = Staff.checkContains(modifiersTag, modifier);

			if (i != -1) {
				modifierTag = modifiersTag.get(i);
				modifierTag.setInt("amount", modifierTag.getInt("amount") + 1);
				modifiersTag.a(i, modifierTag);
			} else {
				modifierTag = new NBTTagCompound();
				modifierTag.setString("name", modifier.getSystemName());
				modifierTag.setInt("amount", 1);
				modifiersTag.add(modifierTag);
			}
		}

		magicTag.set("Modifiers", modifiersTag);
		nbtTag.set("Magic", magicTag);
		nmsItem.setTag(nbtTag);
		
		return CraftItemStack.asBukkitCopy(nmsItem);
	}

	public static SpellBase getBase(ItemStack staff) {
		if (!Staff.isStaff(staff)) {
			throw new RuntimeException("The item has to be a staff!");
		}
		
		if (!Staff.isBaseSet(staff)) {
			throw new RuntimeException("The spell base is not set!");
		}

		return Magic.instance().getRegistry().getSpellBase(CraftItemStack.asNMSCopy(staff).getTag().getCompound("Magic").getString("Base"));
	}

	public static SpellTarget getTarget(ItemStack staff) {
		if (!Staff.isStaff(staff)) {
			throw new RuntimeException("The item has to be a staff!");
		}
		
		if (!Staff.isTargetSet(staff)) {
			throw new RuntimeException("The spell target is not set!");
		}

		return Magic.instance().getRegistry().getSpellTarget(CraftItemStack.asNMSCopy(staff).getTag().getCompound("Magic").getString("Target"));
	}

	public static SpellModifierList getModifiers(ItemStack staff) {
		if (!Staff.isStaff(staff)) {
			throw new RuntimeException("The item has to be a staff!");
		}
		
		if (!Staff.isBaseSet(staff) || !Staff.isTargetSet(staff)) {
			throw new RuntimeException("The spell is not ready yet!");
		}

		net.minecraft.server.v1_10_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(staff);

		NBTTagCompound magicTag = nmsItem.getTag().getCompound("Magic");
		NBTTagList nbtList = magicTag.getList("Modifiers", 10);
		
		SpellModifierList modifiers = new SpellModifierList();
		
		for (int i = 0; i < nbtList.size(); ++i) {
			NBTTagCompound tag = nbtList.get(i);
			SpellModifier modifier = Magic.instance().getRegistry().getSpellModifier(tag.getString("name"));
			
			for (int j = 0; j < tag.getInt("amount"); j++) {
				modifiers.addModifier(modifier);
			}
		}

		return modifiers;
	}

	public static Spell getSpell(ItemStack staff) {
		SpellBase base = Staff.getBase(staff);
		SpellTarget target = Staff.getTarget(staff);
		SpellModifierList modifiers = Staff.getModifiers(staff);
		
		return new Spell(base, target, modifiers);
	}

	/**
	 * @author Nightishaman
	 */
	private static int checkContains(NBTTagList modifiersTag, SpellModifier modifier) {
		for (int i = 0; i < modifiersTag.size(); ++i) {
			if (modifiersTag.get(i).getString("name").equals(modifier.getSystemName())) {
				return i;
			}
		}

		return -1;
	}

}
