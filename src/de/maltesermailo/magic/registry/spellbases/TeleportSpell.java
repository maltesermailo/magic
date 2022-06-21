package de.maltesermailo.magic.registry.spellbases;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.maltesermailo.magic.Magic;
import de.maltesermailo.magic.registry.SpellBase;
import de.maltesermailo.magic.registry.SpellModifier;
import de.maltesermailo.magic.registry.SpellModifierList;
import de.maltesermailo.magic.registry.modifiers.ModifierCasting;
import de.maltesermailo.magic.registry.modifiers.ModifierStrength;

public class TeleportSpell extends SpellBase {

    private static TeleportSpell instance;
	
	static {
		TeleportSpell.instance = new TeleportSpell();
	}
	
	public static TeleportSpell instance() {
		return TeleportSpell.instance;
	}
	
	
	private TeleportSpell() {
		super("teleport", "Teleportation",
				"Teleportiert das Ziel an die angegebene Position(nur du selbst)");
	}

	@Override
	public void cast(Player caster, SpellModifierList modifiers, List<LivingEntity> targets) {
		int castingTime = 3 - (modifiers.getAmount(ModifierCasting.class));
		int distance = (modifiers.getAmount(ModifierStrength.class) * 2) + 10;
		
		if (castingTime < 0) {
			castingTime = 0;
		}
		
		PotionEffect potionEffect = new PotionEffect(PotionEffectType.BLINDNESS, castingTime * 20, 1, false, false);
		
		caster.addPotionEffect(potionEffect);
		caster.getLocation().getWorld().playEffect(caster.getLocation(), Effect.ENDER_SIGNAL, 0, 6);
		
		Location loc = caster.getTargetBlock((Set<Material>)null, distance).getLocation().add(0, 1, 0);
		
		Bukkit.getScheduler().runTaskLater(Magic.instance(), () -> {
			
			caster.teleport(loc);
			caster.getLocation().getWorld().playEffect(caster.getLocation(), Effect.ENDER_SIGNAL, 0, 6);
			
		}, castingTime * 20);
	}

	@Override
	public List<SpellModifier> getValidModifiers() {
		return Arrays.asList(ModifierStrength.instance());
	}

}
