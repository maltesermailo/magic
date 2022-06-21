package de.maltesermailo.magic.registry.spelltargets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.bukkit.Color;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;

import de.maltesermailo.magic.effects.ParticleData;
import de.maltesermailo.magic.effects.visuals.EffectLine;
import de.maltesermailo.magic.registry.SpellModifier;
import de.maltesermailo.magic.registry.SpellModifierList;
import de.maltesermailo.magic.registry.SpellTarget;

public class SpellTargetExpl extends SpellTarget {

    private static SpellTargetExpl instance;
    
    private static Map<Snowball, Consumer<LivingEntity>> balls;
	
	static {
		SpellTargetExpl.instance = new SpellTargetExpl();
		SpellTargetExpl.balls = new HashMap<>();
	}
	
	public static SpellTargetExpl instance() {
		return SpellTargetExpl.instance;
	}
	
	public static void acceptSnowball(Snowball ball, LivingEntity target) {
		if (SpellTargetExpl.balls.containsKey(ball)) {
			Consumer<LivingEntity> action = SpellTargetExpl.balls.get(ball);
			SpellTargetExpl.balls.remove(ball);
			
			action.accept(target);
		}
	}
	
	private SpellTargetExpl() {
		super("expl", "Umkreisverzaubern", 
				"Der Zaubert wirkt auf alle im getroffenem Umkreis.");
	}

	@Override
	public void execute(Player caster, SpellModifierList modifiers, Consumer<List<LivingEntity>> action) {
        Snowball snowball = (Snowball) caster.getLocation().getWorld().spawnEntity(caster.getEyeLocation(), EntityType.SNOWBALL);
		
		snowball.setShooter(caster);
		snowball.setVelocity(caster.getEyeLocation().getDirection().multiply(1.5D));
		
		EffectLine effect = new EffectLine(6, caster.getEyeLocation().getDirection().multiply(1.5D), 0.5F, ParticleData.coloredPotion(Color.RED));
		effect.calculate();
		effect.play(caster.getLocation());
		
		SpellTargetExpl.balls.put(snowball, (entity) -> {
			
            List<LivingEntity> nearby = new ArrayList<>();
			
			for (Entity e : (entity != null ? entity : snowball).getNearbyEntities(10.0D, 10.0D, 10.0D)) {
				if (e instanceof LivingEntity) {
					nearby.add((LivingEntity) e);
				}
			}
			
			action.accept(nearby);
		});
	}

	@Override
	public List<SpellModifier> getValidModifiers() {
		return Arrays.asList();
	}

}
