package de.maltesermailo.magic.listener;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import de.maltesermailo.magic.Magic;
import de.maltesermailo.magic.registry.SpellModifier;
import de.maltesermailo.magic.registry.Staff;
import net.minecraft.server.v1_10_R1.NBTTagCompound;

public class ListenerInventoryClick implements Listener {
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if (event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR
				&& event.getWhoClicked().getItemOnCursor() != null && event.getWhoClicked().getItemOnCursor().getType() != Material.AIR) {
			
			if (Staff.isStaff(event.getCurrentItem())) {
				ItemStack staff = event.getCurrentItem();
				ItemStack spellPart = event.getWhoClicked().getItemOnCursor();
				ItemStack clone = staff.clone();
				
				if (staff.getAmount() > 1) {
					int amount = staff.getAmount() - 1;
					staff.setAmount(1);
					clone.setAmount(amount);
				} else {
					clone = null;
				}
				
				net.minecraft.server.v1_10_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(spellPart);
				NBTTagCompound nbtTag = nmsItem.getTag();
				
				if (nbtTag == null) {
					return;
				}
				
				boolean cancel = false;
				
				if (nbtTag.hasKey("SpellBase") && !Staff.isBaseSet(staff)) {
					staff = Staff.initBase(staff, Magic.instance().getRegistry().getSpellBase(nbtTag.getString("SpellBase")));
					cancel = true;
				} else if (nbtTag.hasKey("SpellTarget") && !Staff.isTargetSet(staff)) {
					staff = Staff.initTarget(staff, Magic.instance().getRegistry().getSpellTarget(nbtTag.getString("SpellTarget")));
					cancel = true;
				} else if (nbtTag.hasKey("SpellModifier")) {
					SpellModifier modifier = Magic.instance().getRegistry().getSpellModifier(nbtTag.getString("SpellModifier"));
					
					if (Staff.isReady(staff) && Staff.isApplyable(staff, modifier)) {
						staff = Staff.addModifier(staff, modifier);
						cancel = true;
					}
				}
				
				if (cancel) {
					Staff.updateStaffDisplay(staff);
					
					if (event.getInventory().getSize() == 5) { // Muss man machen, weil die Inventarschnittstelle von Bukkit bodenlos Scheisse ist!
						event.getWhoClicked().getInventory().setItem(event.getSlot(), staff);
					} else {
						event.getInventory().setItem(event.getSlot(), staff);
					}
					
					if (spellPart.getAmount() > 1) {
						spellPart.setAmount(spellPart.getAmount() - 1);
						event.getWhoClicked().setItemOnCursor(spellPart);
					} else {
						event.getWhoClicked().setItemOnCursor(null);
					}
					
					if (clone != null) {
						event.getWhoClicked().getInventory().addItem(clone);
					}
					
					event.setCancelled(true);
				}
			}
		}
	}
	
}
