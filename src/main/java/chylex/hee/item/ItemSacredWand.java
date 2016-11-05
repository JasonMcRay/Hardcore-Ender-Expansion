package chylex.hee.item;
import java.util.List;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import chylex.hee.entity.GlobalMobData;
import chylex.hee.entity.projectile.EntityProjectileSacredWand;
import chylex.hee.item.base.ItemAbstractEnergyAcceptor;
import chylex.hee.mechanics.wand.WandCore;
import chylex.hee.mechanics.wand.WandType;
import chylex.hee.system.abstractions.nbt.NBT;
import chylex.hee.system.abstractions.nbt.NBTCompound;
import chylex.hee.system.collections.CollectionUtil;
import chylex.hee.system.util.DragonUtil;
import chylex.hee.system.util.MathUtil;
import com.google.common.collect.Multimap;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSacredWand extends ItemAbstractEnergyAcceptor{
	public static boolean attackEntity(ItemStack is, EntityPlayer player, EntityLivingBase entity, EntityProjectileSacredWand projectile){
		NBTCompound tag = NBT.item(is, true);
		
		float damage = WandType.fromItemStack(is).baseDamage;
		
		if (GlobalMobData.isEnderGooTolerant(entity))damage *= 1.5F;
		else if (entity instanceof IMob)damage *= 1.2F;
		
		boolean critical = false, magic = false;
		boolean isMelee = projectile == null;
		EntityLivingBase[] attacked = new EntityLivingBase[]{ entity };
		int knockback = isMelee && player.isSprinting() ? 2 : 0; // double knockback value
		
		// critical
		if (entity.worldObj.getTotalWorldTime()-tag.getLong("latktm") >= 600){
			damage *= 1.2F;
			tag.setLong("latktm", entity.worldObj.getTotalWorldTime());
			critical = true;
		}
		
		// enhancements
		// TODO List<Enum> enhancements = EnhancementHandler.getEnhancements(is);
		boolean hasCapability = false; // TODO enhancements.contains(SacredWandEnhancements.CAPABILITY);
		
		if (hasCapability)damage *= 1.1F;
		
		// cores
		List<WandCore> cores = WandCore.getCores(is);
		
		if (cores.contains(WandCore.DEXTERITY)){
			int max = hasCapability ? 3 : 2;
			List<EntityLivingBase> newAttacked = CollectionUtil.<EntityLivingBase>newList(max+1, entity);
			List<EntityLiving> closest = DragonUtil.getClosestEntities(max, entity, entity.worldObj.getEntitiesWithinAABB(EntityLiving.class, entity.boundingBox.expand(4D, 4D, 4D)));
			
			for(EntityLiving e:closest){
				if (e.getDistanceToEntity(entity) <= (/* TODO enhancements.contains(SacredWandEnhancements.RANGE) ? 4D : */2.5D))newAttacked.add(e);
			}
			
			attacked = newAttacked.toArray(new EntityLivingBase[newAttacked.size()]);
		}
		
		if (cores.contains(WandCore.FORCE))damage *= 1.2F;
		if (cores.contains(WandCore.REPULSION))knockback += isMelee? 1+MathUtil.floor(Math.max(0D, 6D-player.getDistanceToEntity(entity)*0.75D)) : 4;
		
		// no energy
		if (isMelee && is.getItemDamage() == is.getMaxDamage())damage *= 0.1F;
		
		// run
		boolean didHurt = false;
		
		for(EntityLivingBase target:attacked){
			if (target.attackEntityFrom(isMelee ? DamageSource.causePlayerDamage(player) : new EntityDamageSourceIndirect("player", projectile, player), magic ? damage*0.8F : damage)){
				if (knockback > 0){
					double motX = isMelee ? -MathHelper.sin(MathUtil.toRad(player.rotationYaw)) : projectile.motionX;
					double motZ = isMelee ? MathHelper.cos(MathUtil.toRad(player.rotationYaw)) : projectile.motionZ;
					target.addVelocity(motX*knockback*0.25F, 0.1D, motZ*knockback*0.25F);
					
					if (isMelee){
						player.motionX *= 0.6D;
						player.motionZ *= 0.6D;
						player.setSprinting(false);
					}
				}
				
				if (critical)player.onCriticalHit(target);
				player.setLastAttacker(target);
				EnchantmentHelper.func_151385_b(player, target); // OBFUSCATED some kind of damage event (there was another one for thorns, screw that one)
				player.addStat(StatList.damageDealtStat, Math.round(damage*10F));
				
				didHurt = true;
			}
			
			if (magic){
				target.hurtResistantTime = 0;
				target.attackEntityFrom(DamageSource.magic, damage*0.2F);
			}
			
			target.hurtResistantTime = (target.hurtResistantTime*3)/4;
		}
		
		return didHurt;
	}
	
	@Override
	public int getEnergyAccepted(ItemStack is){
		return 7;
	}
	
	@Override
	public int getEnergyUsage(ItemStack is){
		return /* TODO EnhancementHandler.hasEnhancement(is, SacredWandEnhancements.EFFICIENCY) ? 2 : */3;
	}
	
	@Override
	public boolean hitEntity(ItemStack is, EntityLivingBase entity, EntityLivingBase attacker){
		useEnergy(is, attacker);
		return true;
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack is, EntityPlayer player, Entity entity){
		if (!(entity instanceof EntityLivingBase))return false;
		if (!entity.canAttackWithItem() || entity.hitByEntity(player))return true;
		
		if (attackEntity(is, player, (EntityLivingBase)entity, null))useEnergy(is, player);
		return true; // cancel
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player){
		if (is.getItemDamage() == is.getMaxDamage())return is;
		if (!world.isRemote)world.spawnEntityInWorld(new EntityProjectileSacredWand(world, player, is));
		useEnergy(is, player);
		return is;
	}
	
	@Override
	public Multimap getAttributeModifiers(ItemStack is){
		Multimap map = super.getAttributeModifiers(is);
		map.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", 0F, 0));
		return map;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isFull3D(){
		return true;
	}
}
