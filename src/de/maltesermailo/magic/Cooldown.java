package de.maltesermailo.magic;

import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.maltesermailo.magic.registry.Staff;

public class Cooldown {

	private static Map<Player, Map<ItemStack, Long>> cooldown = new HashMap<>();

	public static void putCooldown(Player p, ItemStack staff, int cooldown) {
		if (!Staff.isStaff(staff)) {
			throw new RuntimeException("This has to be a staff.");
		}

		if (!Cooldown.cooldown.containsKey(p)) {
			Cooldown.cooldown.put(p, new HashMap<ItemStack, Long>());
		}

		Cooldown.cooldown.get(p).put(staff, ManagementFactory.getRuntimeMXBean().getUptime() + (cooldown * 1000));
	}
	
	public static double getRemaining(Player p, ItemStack staff) {
		if (!Staff.isStaff(staff)) {
			throw new RuntimeException("This has to be a staff.");
		}
		
		if (!Cooldown.cooldown.containsKey(p)) {
			return 0;
		}
		
		if (!Cooldown.cooldown.get(p).containsKey(staff)) {
			return 0;
		}
		
		double time = ((Cooldown.cooldown.get(p).get(staff) - ManagementFactory.getRuntimeMXBean().getUptime()) / 1000);
		
		return time;
	}

	public static boolean hasCooldown(Player p, ItemStack staff) {
		if (!Staff.isStaff(staff)) {
			throw new RuntimeException("This has to be a staff.");
		}

		if (!Cooldown.cooldown.containsKey(p)) {
			return false;
		}

		if (!Cooldown.cooldown.get(p).containsKey(staff)) {
			return false;
		}
		
		double time = ((Cooldown.cooldown.get(p).get(staff) - ManagementFactory.getRuntimeMXBean().getUptime()) / 1000.0D);
		
		return time > 0;
	}

}
