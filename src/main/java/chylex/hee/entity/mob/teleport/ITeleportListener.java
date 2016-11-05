package chylex.hee.entity.mob.teleport;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import chylex.hee.entity.fx.FXType;
import chylex.hee.packets.AbstractClientPacket;
import chylex.hee.packets.PacketPipeline;
import chylex.hee.packets.client.C12TeleportEntity;
import chylex.hee.packets.client.C21EffectEntity;
import chylex.hee.system.abstractions.Vec;

@FunctionalInterface
public interface ITeleportListener<T extends Entity>{
	void onTeleport(T entity, Vec startPos, Random rand);
	
	public static final ITeleportListener playSound = (entity, startPos, rand) -> {
		entity.worldObj.playSoundEffect(startPos.x, startPos.y, startPos.z, "mob.endermen.portal", 1F, 1F);
		entity.playSound("mob.endermen.portal", 1F, 1F);
	};
	
	public static final ITeleportListener spawnParticlesPrevPos = (entity, startPos, rand) -> {
		PacketPipeline.sendToAllAround(entity.dimension, startPos.x, startPos.y, startPos.z, 64D, new C21EffectEntity(FXType.Entity.SIMPLE_TELEPORT_NOSOUND, startPos.x, startPos.y, startPos.z, entity.width, entity.height));
	};
	
	public static final ITeleportListener skipRenderLerp = (entity, startPos, rand) -> {
		PacketPipeline.sendToAllAround(entity, 128D, new C12TeleportEntity(entity));
	};
	
	public static final ITeleportListener updatePlayerPosition = (entity, startPos, rand) -> {
		if (entity instanceof EntityPlayer)((EntityPlayer)entity).setPositionAndUpdate(entity.posX, entity.posY, entity.posZ);
	};
	
	public static ITeleportListener sendPacket(final IOnTeleportPacketProvider packet){
		return (entity, startPos, rand) -> {
			Vec endPos = Vec.pos(entity);
			Vec middlePos = startPos.interpolated(endPos, 0.5D);
			PacketPipeline.sendToAllAround(entity.dimension, middlePos.x, middlePos.y, middlePos.z, 32D+startPos.distance(endPos), packet.create(startPos, Vec.pos(entity)));
		};
	}
	
	public static interface IOnTeleportPacketProvider{
		AbstractClientPacket create(Vec startPos, Vec endPos);
	}
}