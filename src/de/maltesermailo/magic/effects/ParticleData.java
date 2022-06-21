package de.maltesermailo.magic.effects;

import org.bukkit.Color;

import net.minecraft.server.v1_10_R1.EnumParticle;
import net.minecraft.server.v1_10_R1.PacketPlayOutWorldParticles;

public interface ParticleData {

	public static final ParticleData PARTICLE_FLAME = (x, y, z) -> {
		return ParticleData.simplePacket(EnumParticle.FLAME, x, y, z);
	};

	public static final ParticleData PARTICLE_VILLAGER_HAPPY = (x, y, z) -> {
		return ParticleData.simplePacket(EnumParticle.VILLAGER_HAPPY, x, y, z);
	};

	public static final ParticleData PARTICLE_VILLAGER_ANGRY = (x, y, z) -> {
		return ParticleData.simplePacket(EnumParticle.VILLAGER_ANGRY, x, y, z);
	};

	public static final ParticleData PARTICLE_MAGIC_CRIT = (x, y, z) -> {
		return ParticleData.simplePacket(EnumParticle.CRIT_MAGIC, x, y, z);
	};

	public static final ParticleData PARTICLE_FIREWORK = (x, y, z) -> {
		return ParticleData.simplePacket(EnumParticle.FIREWORKS_SPARK, x, y, z);
	};

	public static final ParticleData PARTICLE_FIREWORK_SMALL_RANDOM = (x, y, z) -> {
		return ParticleData.simplePacket(EnumParticle.FIREWORKS_SPARK, x, y, z);
	};

	public static final ParticleData PARTICLE_EXPLOSION = (x, y, z) -> {
		return ParticleData.simplePacket(EnumParticle.EXPLOSION_LARGE, x, y, z);
	};

	public static final ParticleData PARTICLE_SMOKE_LAGE = (x, y, z) -> {
		return ParticleData.fullPacket(EnumParticle.SMOKE_LARGE, x, y, z, 0f, 0f, 0f, 0.12f, 2);
	};

	public static final ParticleData PARTICLE_HEART = (x, y, z) -> {
		return ParticleData.simplePacket(EnumParticle.HEART, x, y, z);
	};
	
	public static final ParticleData PARTICLE_DEATH_HEART = (x, y, z) -> {
		return ParticleData.simplePacket(EnumParticle.DAMAGE_INDICATOR, x, y, z);
	};

	public static ParticleData coloredPotion(Color color) {
		return new ColoredParticle(EnumParticle.SPELL_MOB, color);
	}

	public static ParticleData coloredDust(Color color) {
		return new ColoredParticle(EnumParticle.REDSTONE, color);
	}

	public static PacketPlayOutWorldParticles simplePacket(EnumParticle particle, double x, double y, double z) {
		return new PacketPlayOutWorldParticles(particle, true, (float) x, (float) y, (float) z, 0.0F, 0.0F, 0.0F, 0.0F,
				1, new int[0]);
	}

	public static PacketPlayOutWorldParticles offsetPacket(EnumParticle particle, double x, double y, double z,
			float offsetX, float offsetY, float offsetZ) {
		return new PacketPlayOutWorldParticles(particle, true, (float) x, (float) y, (float) z, offsetX, offsetY,
				offsetZ, 0.0F, 1, new int[0]);
	}

	public static PacketPlayOutWorldParticles fullPacket(EnumParticle particle, double x, double y, double z,
			float offsetX, float offsetY, float offsetZ, float speed, int particleCount) {

		return new PacketPlayOutWorldParticles(particle, true, (float) x, (float) y, (float) z, offsetX, offsetY,
				offsetZ, speed, particleCount, new int[0]);
	}

	public abstract PacketPlayOutWorldParticles createPacket(double x, double y, double z);

	public static class ColoredParticle implements ParticleData {

		private EnumParticle particle;
		private Color color;

		public ColoredParticle(EnumParticle particle, Color color) {
			this.particle = particle;
			this.color = color;
		}

		@Override
		public PacketPlayOutWorldParticles createPacket(double x, double y, double z) {
			float red = this.color.getRed() / 255.0F;

			if (this.particle == EnumParticle.REDSTONE && red == 0) {
				red = 1.17549435E-38F;
			}

			return new PacketPlayOutWorldParticles(this.particle, true, (float) x, (float) y, (float) z, red,
					this.color.getGreen() / 255.0F, this.color.getBlue() / 255.0F, 1.0F, 0, new int[0]);
		}
	}

}
