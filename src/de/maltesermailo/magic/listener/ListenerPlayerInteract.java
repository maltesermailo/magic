package de.maltesermailo.magic.listener;

import java.util.Arrays;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import de.maltesermailo.magic.Cooldown;
import de.maltesermailo.magic.registry.Spell;
import de.maltesermailo.magic.registry.Staff;
import de.maltesermailo.magic.registry.modifiers.ModifierCooldown;

public class ListenerPlayerInteract implements Listener {
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if (event.getHand() != EquipmentSlot.HAND) {
			return;
		}

		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (event.getItem() != null && Staff.isStaff(event.getItem()) && !Cooldown.hasCooldown(event.getPlayer(), event.getItem())) {
				if (!Cooldown.hasCooldown(event.getPlayer(), event.getItem()) && Staff.isReady(event.getItem())) {
					Spell spell = Staff.getSpell(event.getItem());
					spell.cast(event.getPlayer());
					this.initCooldown(spell, event.getItem(), event.getPlayer());
				}
			}
		}
	}
	
	@EventHandler
	public void onInteractEntity(PlayerInteractAtEntityEvent event) {
		if (event.getHand() != EquipmentSlot.HAND) {
			return;
		}

		ItemStack item = event.getPlayer().getInventory().getItemInMainHand();

		if (item != null && Staff.isReady(item) && !Cooldown.hasCooldown(event.getPlayer(), item)) {
			Entity e = event.getRightClicked();

			if (e != null && e instanceof LivingEntity) {
				Spell spell = Staff.getSpell(item);
				spell.getBase().cast(event.getPlayer(), spell.getModifiers(), Arrays.asList((LivingEntity) e));
				this.initCooldown(spell, item, event.getPlayer());
			}
		}
	}
	
	private void initCooldown(Spell spell, ItemStack item, Player player) {
		if (Staff.getCooldownLength(item) > 0) {
			int cooldown = Staff.getCooldownLength(item) - (spell.getModifiers().getAmount(ModifierCooldown.class) * 5);

			if (cooldown > 0) {
				Cooldown.putCooldown(player, item, cooldown);
			}
		}
	}
	
}
