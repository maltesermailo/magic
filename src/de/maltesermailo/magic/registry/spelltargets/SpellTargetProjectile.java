package de.maltesermailo.magic.registry.spelltargets;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.bukkit.Color;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;

import de.maltesermailo.magic.effects.ParticleData;
import de.maltesermailo.magic.effects.visuals.EffectLine;
import de.maltesermailo.magic.registry.SpellModifier;
import de.maltesermailo.magic.registry.SpellModifierList;
import de.maltesermailo.magic.registry.SpellTarget;

public class SpellTargetProjectile extends SpellTarget {
	
    private static SpellTargetProjectile instance;
    
    private static Map<Snowball, Consumer<LivingEntity>> balls; // TODO clear map
    
	public static SpellTargetProjectile instance() {
		return SpellTargetProjectile.instance;
	}
	
	static {
		SpellTargetProjectile.instance = new SpellTargetProjectile();
		
		SpellTargetProjectile.balls = new HashMap<>();
	}
	
	public static void acceptSnowball(Snowball ball, LivingEntity target) {
		if (SpellTargetProjectile.balls.containsKey(ball)) {
			Consumer<LivingEntity> action = SpellTargetProjectile.balls.get(ball);
			SpellTargetProjectile.balls.remove(ball);
			
			if (target != null) {
				action.accept(target);
			}
		}
	}
	
	public static Map<Snowball, Consumer<LivingEntity>> getBalls() {
		return Collections.unmodifiableMap(SpellTargetProjectile.balls);
	}
	
	
	public SpellTargetProjectile() {
		super("projectile", "Zielverzaubern", 
				"Der Zauber wirkt auf das Ziel auf das du zielst.");
	}
	
	@Override
	public void execute(Player caster, SpellModifierList modifiers, Consumer<List<LivingEntity>> action) {
		Snowball snowball = (Snowball) caster.getLocation().getWorld().spawnEntity(caster.getEyeLocation(), EntityType.SNOWBALL);
		
		snowball.setShooter(caster);
		snowball.setVelocity(caster.getEyeLocation().getDirection().multiply(1.5D));
		
		EffectLine effect = new EffectLine(6, caster.getEyeLocation().getDirection().multiply(1.5D), 0.5F, ParticleData.coloredPotion(Color.RED));
		effect.calculate();
		effect.play(caster.getLocation());
		
		SpellTargetProjectile.balls.put(snowball, (entity) -> {
			action.accept(Arrays.asList(entity));
		});
	}

	@Override
	public List<SpellModifier> getValidModifiers() {
		return Arrays.asList();
	}
	
}
