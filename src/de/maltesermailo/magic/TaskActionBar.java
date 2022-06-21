package de.maltesermailo.magic;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.maltesermailo.magic.registry.Staff;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

public class TaskActionBar implements Runnable {

	@Override
	public void run() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			ItemStack staff = player.getInventory().getItemInMainHand();

			if (staff == null || staff.getType() == Material.AIR || !Staff.isStaff(staff) || !Staff.isBaseSet(staff)
					|| !Staff.isTargetSet(staff)) {
				continue;
			}

			double maximumCooldown = Double.valueOf(Staff.getCooldownLength(staff)).doubleValue();
			double cooldown = Cooldown.hasCooldown(player, staff) ? Cooldown.getRemaining(player, staff) : 0;

			BaseComponent toSend = null;

			if (Cooldown.hasCooldown(player, staff)) {
				toSend = new TextComponent("§cCooldown: ");

				double percent = Double.valueOf((cooldown / maximumCooldown) * 100).doubleValue();

				int toShow = Double.valueOf(percent / 10.0D).intValue();

				// Range from 1-10
				for (int i = 0; i < 10; i++) {
					toSend.addExtra((i >= toShow ? "§c" : "§2") + "|");
				}
			} else {
				toSend = new TextComponent(Staff.getBase(staff).getDisplayName());
			}

			player.spigot().sendMessage(ChatMessageType.ACTION_BAR, toSend);
		}
	}

}
