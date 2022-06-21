package de.maltesermailo.magic.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.maltesermailo.magic.Magic;
import de.maltesermailo.magic.registry.SpellBase;
import de.maltesermailo.magic.registry.SpellModifier;
import de.maltesermailo.magic.registry.SpellTarget;
import de.maltesermailo.magic.registry.Staff;

public class MagicCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if(!cs.hasPermission("magic.admin")) {
			cs.sendMessage("§cDu hast keine Permissions!");
			
			return true;
		}
		
		if (cs instanceof Player) {
			Player p = (Player) cs;

			if (args.length == 0) {
				this.sendHelp(p);
			} else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("showspells")) {
					this.showSpells(p);
				} else if (args[0].equalsIgnoreCase("showmods")) {
					this.showMods(p);
				} else if (args[0].equalsIgnoreCase("showtargets")) {
					this.showTargets(p);
				} else if (args[0].equalsIgnoreCase("showstaffs")) {
					this.showStaffs(p);
				} else {
					this.sendHelp(p);
				}
			} else if (args.length == 2) {
				if (args[0].equalsIgnoreCase("addspell")) {
					String name = args[1];

					SpellBase spell = Magic.instance().getRegistry().getSpellBase(name);
					
					if (spell == null) {
						this.showSpells(p);
						return true;
					}
					
					ItemStack display = spell.initItem(new ItemStack(Material.INK_SACK, 1, (short) 12));
					this.setName(display, "§7§lSpruchbasis: " + spell.getDisplayName());
					this.setLore(display, spell.getLore());
					
					spell.initItem(display);
					
					p.getInventory().addItem(display);
				} else if (args[0].equalsIgnoreCase("addmod")) {
					String name = args[1];

					SpellModifier modifier = Magic.instance().getRegistry().getSpellModifier(name);
					
					if (modifier == null) {
						this.showMods(p);
						return true;
					}
					
					List<String> lore = new ArrayList<>();
					lore.add("§7Maximum: §6" + modifier.getMaximumAmount());
					lore.add("§7");
					lore.addAll(modifier.getLore());
					
					ItemStack display = modifier.initItem(new ItemStack(Material.INK_SACK, 1, (short) 10));
					this.setName(display, "§7§lSpruchmodifikation: " + modifier.getDisplayName());
					this.setLore(display, lore);
					
					modifier.initItem(display);
					
					p.getInventory().addItem(display);
				} else if (args[0].equalsIgnoreCase("addtarget")) {
					String name = args[1];

					SpellTarget target = Magic.instance().getRegistry().getSpellTarget(name);
					
					if (target == null) {
						this.showTargets(p);
						return true;
					}
					
					ItemStack display = target.initItem(new ItemStack(Material.INK_SACK, 1, (short) 1));
					this.setName(display, "§7§lSpruchziel: " + target.getDisplayName());
					this.setLore(display, target.getLore());
					
					target.initItem(display);
					
					p.getInventory().addItem(display);
				} else if (args[0].equalsIgnoreCase("addstaff")) {
					String staff = args[1];
					ItemStack item;
					
					if (staff.equalsIgnoreCase("stick")) {
						item = Staff.newStaff(Material.STICK, 5, 8);
						this.setName(item, "§7§lStabbasis: §eHolzstab");
					} else if (staff.equalsIgnoreCase("bone")) {
						item = Staff.newStaff(Material.BONE, 10, 5);
						this.setName(item, "§7§lStabbasis: §eKnochenstab");
					} else if (staff.equalsIgnoreCase("flame")) {
						item = Staff.newStaff(Material.BLAZE_ROD, 20, 3);
						this.setName(item, "§7§lStabbasis: §eFlammenstab");
					} else {
						this.showStaffs(p);
						return true;
					}
					
					this.setLore(item, "§7Maximale Modifikationen: §6" + Staff.getMaximalModifications(item), "§7Cooldown: §6" + Staff.getCooldownLength(item));
					
					p.getInventory().addItem(item);
				} else {
					this.sendHelp(p);
				}
			} else {
				this.sendHelp(p);
			}
		} else {
			cs.sendMessage(Magic.prefix + "Du kannst diesen Befehl nur als §6§lSpieler §r§7ausführen.");
		}

		return true;
	}

	private void showSpells(Player p) {
		p.sendMessage(Magic.prefix + "§7===================§8 [§3Zauber§8] §7===================");

		for (SpellBase spell : Magic.instance().getRegistry().getSpellBases()) {
			p.sendMessage(Magic.prefix + "§7-- " + spell.getDisplayName() + "§7 [§3§l" + spell.getSystemName() + "§7]");
		}
	}

	private void showMods(Player p) {
		p.sendMessage(Magic.prefix + "§7================§8 [§aModifikationen§8] §7================");

		for (SpellModifier modifier : Magic.instance().getRegistry().getSpellModifiers()) {
			p.sendMessage(Magic.prefix + "§7-- " + modifier.getDisplayName() + "§7 [§a§l" + modifier.getSystemName() + "§7]");
		}
	}

	private void showTargets(Player p) {
		p.sendMessage(Magic.prefix + "§7====================§8 [§cZiele§8] §7====================");

		for (SpellTarget target : Magic.instance().getRegistry().getSpellTargets()) {
			p.sendMessage(Magic.prefix + "§7-- " + target.getDisplayName() + "§7 [§c§l" + target.getSystemName() + "§7]");
		}
	}
	
	private void showStaffs(Player p) {
		p.sendMessage(Magic.prefix + "§7===================§8 [§eStäbe§8] §7===================");
		p.sendMessage(Magic.prefix + "§7-- Holzstab [§estick§7]");
		p.sendMessage(Magic.prefix + "§7-- Knochenstab [§ebone§7]");
		p.sendMessage(Magic.prefix + "§7-- Feuerstab [§eflame§7]");
	}
	
	private void sendHelp(Player p) {
		p.sendMessage(Magic.prefix + "§7==================== " + Magic.prefix + "§7====================");
		p.sendMessage(Magic.prefix + "§7/magic addstaff <name> -- Fügt dir einen Zauberstab hinzu.");
		p.sendMessage(Magic.prefix + "§7/magic addspell <name> -- Fügt dir einen Zauber hinzu.");
		p.sendMessage(Magic.prefix + "§7/magic addmod <Name> -- Fügt dir einen Modifizierer hinzu.");
		p.sendMessage(Magic.prefix + "§7/magic addtarget <Name> -- Fügt dir ein Ziel hinzu.");
		p.sendMessage(Magic.prefix + "§7/magic showstaffs -- Zeigt alle Zauberstäbe an.");
		p.sendMessage(Magic.prefix + "§7/magic showspells -- Zeigt alle Zauber an.");
		p.sendMessage(Magic.prefix + "§7/magic showmods -- Zeigt alle Modifizierer an.");
		p.sendMessage(Magic.prefix + "§7/magic showtargets -- Zeigt alle Ziele an.");
	}
	
	private void setName(ItemStack item, String name) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
	}
	
	private void setLore(ItemStack item, List<String> lore) {
		ItemMeta meta = item.getItemMeta();
		meta.setLore(lore);
		item.setItemMeta(meta);
	}
	
	private void setLore(ItemStack item, String... lore) {
		this.setLore(item, Arrays.asList(lore));
	}

}
