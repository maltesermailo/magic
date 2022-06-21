package de.maltesermailo.magic;

import java.util.Random;

import org.bukkit.plugin.java.JavaPlugin;

import de.maltesermailo.magic.commands.MagicCommand;
import de.maltesermailo.magic.listener.ListenerEntityDamageByEntity;
import de.maltesermailo.magic.listener.ListenerInventoryClick;
import de.maltesermailo.magic.listener.ListenerPlayerInteract;
import de.maltesermailo.magic.listener.ListenerProjectileHit;
import de.maltesermailo.magic.registry.SpellRegistry;
import de.maltesermailo.magic.registry.modifiers.ModifierCasting;
import de.maltesermailo.magic.registry.modifiers.ModifierCooldown;
import de.maltesermailo.magic.registry.modifiers.ModifierDurability;
import de.maltesermailo.magic.registry.modifiers.ModifierStrength;
import de.maltesermailo.magic.registry.spellbases.DamageSpell;
import de.maltesermailo.magic.registry.spellbases.ExplosionSpell;
import de.maltesermailo.magic.registry.spellbases.FireSpell;
import de.maltesermailo.magic.registry.spellbases.GlowSpell;
import de.maltesermailo.magic.registry.spellbases.HealSpell;
import de.maltesermailo.magic.registry.spellbases.LightningSpell;
import de.maltesermailo.magic.registry.spellbases.NoGravitySpell;
import de.maltesermailo.magic.registry.spellbases.TeleportSpell;
import de.maltesermailo.magic.registry.spellbases.ThrowSpell;
import de.maltesermailo.magic.registry.spelltargets.SpellTargetExpl;
import de.maltesermailo.magic.registry.spelltargets.SpellTargetExplSelf;
import de.maltesermailo.magic.registry.spelltargets.SpellTargetProjectile;
import de.maltesermailo.magic.registry.spelltargets.SpellTargetSelf;

public class Magic extends JavaPlugin {
	
	private static Random random;
	
	private static Magic instance;
	
	public static final String prefix = "§8[§6Magie§8] §7";
	
	public static Magic instance() {
		return Magic.instance;
	}
	
	public static Random random() {
		return Magic.random;
	}
	
	private SpellRegistry registry;
	
	@Override
	public void onLoad() {
		Magic.instance = this;
		Magic.random = new Random();
	}
	
	@Override
	public void onEnable() {
		//Commands
		this.getCommand("magic").setExecutor(new MagicCommand());
		
		//Listener
		this.getServer().getPluginManager().registerEvents(new ListenerPlayerInteract(), this);
		this.getServer().getPluginManager().registerEvents(new ListenerEntityDamageByEntity(), this);
		this.getServer().getPluginManager().registerEvents(new ListenerInventoryClick(), this);
		this.getServer().getPluginManager().registerEvents(new ListenerProjectileHit(), this);
		
		//Tasks
		this.getServer().getScheduler().runTaskTimer(this, new TaskActionBar(), 2L, 2L);
		this.getServer().getScheduler().runTaskTimer(this, new TaskSnowball(), 2L, 2L);
		
		//Create Registry
		this.registry = new SpellRegistry();
		
		//Spell Targets
		this.registry.registerSpellTarget(SpellTargetSelf.instance());
		this.registry.registerSpellTarget(SpellTargetExplSelf.instance());
		this.registry.registerSpellTarget(SpellTargetProjectile.instance());
		this.registry.registerSpellTarget(SpellTargetExpl.instance());
		
		//Spell Bases
		this.registry.registerSpellBase(HealSpell.instance());
		this.registry.registerSpellBase(NoGravitySpell.instance());
		this.registry.registerSpellBase(FireSpell.instance());
		this.registry.registerSpellBase(DamageSpell.instance());
		this.registry.registerSpellBase(LightningSpell.instance());
		this.registry.registerSpellBase(TeleportSpell.instance());
		this.registry.registerSpellBase(ThrowSpell.instance());
		this.registry.registerSpellBase(ExplosionSpell.instance());
		this.registry.registerSpellBase(GlowSpell.instance());
		
		//Spell Modifiers
		this.registry.registerSpellModifier(ModifierCasting.instance());
		this.registry.registerSpellModifier(ModifierCooldown.instance());
		this.registry.registerSpellModifier(ModifierDurability.instance());
		this.registry.registerSpellModifier(ModifierStrength.instance());
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public SpellRegistry getRegistry() {
		return this.registry;
	}

}
