package de.maltesermailo.magic.effects;

import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class ParticlePacket {

	private ParticleData data;
	private Vector vector;

	public ParticlePacket(ParticleData data, Vector vector) {
		this.data = data;
		this.vector = vector;
	}

	public void send(Player target) {
		((CraftPlayer) target).getHandle().playerConnection.sendPacket(this.data.createPacket(this.vector.getX(), this.vector.getY(), this.vector.getZ()));
	}

	public ParticleData getData() {
		return this.data;
	}

	public Vector getVector() {
		return this.vector;
	}

}
