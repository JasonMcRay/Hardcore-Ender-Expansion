package chylex.hee.entity.mob;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import chylex.hee.HardcoreEnderExpansion;
import chylex.hee.entity.projectile.EntityProjectileMinerShot;
import chylex.hee.init.ItemList;
import chylex.hee.item.ItemScorchingPickaxe;
import chylex.hee.packets.PacketPipeline;
import chylex.hee.packets.client.C07AddPlayerVelocity;
import chylex.hee.packets.client.C08PlaySound;
import chylex.hee.proxy.ModCommonProxy;
import chylex.hee.system.abstractions.Pos;
import chylex.hee.system.abstractions.Pos.PosMutable;
import chylex.hee.system.abstractions.Vec;
import chylex.hee.system.abstractions.entity.EntityAttributes;
import chylex.hee.system.abstractions.entity.EntityDataWatcher;
import chylex.hee.system.abstractions.entity.EntitySelector;
import chylex.hee.system.collections.CollectionUtil;
import chylex.hee.system.util.MathUtil;

public class EntityMobHauntedMiner extends EntityFlying implements IMob{
	private enum Data{ ATTACK_TYPE }
	
	private static final byte ATTACK_TIMER = 80;
	private static final byte ATTACK_NONE = 0, ATTACK_PROJECTILES = 1, ATTACK_LAVA = 2, ATTACK_BLAST_WAVE = 3;
	
	private EntityDataWatcher entityData;
	private AxisAlignedBB bottomBB = AxisAlignedBB.getBoundingBox(0D, 0D, 0D, 0D, 0D, 0D);
	private EntityLivingBase target;
	private double targetX, targetY, targetZ;
	private byte wanderResetTimer = -120, nextAttackTimer = ATTACK_TIMER, currentAttack = ATTACK_NONE, currentAttackTime;
	
	private Pos attackLavaCurrent;
	private byte attackLavaCounter, attackLavaDone;
	
	public EntityMobHauntedMiner(World world){
		super(world);
		setSize(2.2F, 1.7F);
		isImmuneToFire = true;
		experienceValue = 10;
	}
	
	@Override
	protected void entityInit(){
		super.entityInit();
		entityData = new EntityDataWatcher(this);
		entityData.addByte(Data.ATTACK_TYPE, ATTACK_NONE);
	}
	
	@Override
	protected void applyEntityAttributes(){
		super.applyEntityAttributes();
		EntityAttributes.setValue(this, EntityAttributes.maxHealth, ModCommonProxy.opMobs ? 100D : 85D);
	}
	
	@Override
	protected void updateEntityActionState(){
		if (!worldObj.isRemote && worldObj.difficultySetting == EnumDifficulty.PEACEFUL)setDead();
		
		despawnEntity();
		if (dead)return;
		
		if (target == null){
			if (--wanderResetTimer < -120 || rand.nextInt(300) == 0 || (motionX == 0D && motionZ == 0D && rand.nextInt(20) == 0)){
				wanderResetTimer = 0;
				PosMutable mpos = new PosMutable();
				
				for(int attempt = 0; attempt < 32; attempt++){
					mpos.set(this).move(rand.nextInt(14)-rand.nextInt(14), 0, rand.nextInt(14)-rand.nextInt(14));
					
					if (mpos.isAir(worldObj)){
						while(mpos.moveDown().isAir(worldObj) && Math.abs(posY-mpos.y) < 10);
						if (Math.abs(posY-mpos.y) >= 10)continue;
					}
					else{
						while(!mpos.moveUp().isAir(worldObj) && Math.abs(posY-mpos.y) < 10);
						if (Math.abs(posY-mpos.y) >= 10)continue;
					}
					
					targetX = mpos.x+rand.nextDouble();
					targetY = mpos.y+rand.nextDouble()*0.2D+3D;
					targetZ = mpos.z+rand.nextDouble();
					wanderResetTimer += 40;
					break;
				}
				
				wanderResetTimer += rand.nextInt(40)+20;
			}
			
			if (rand.nextInt(50) == 0){
				List<Entity> entities = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(32D, 16D, 32D));
				
				if (!entities.isEmpty()){
					Entity temp = entities.get(rand.nextInt(entities.size()));
					
					if (temp instanceof EntityPlayer){
						if (rand.nextInt(6) == 0){
							InventoryPlayer inv = ((EntityPlayer)temp).inventory;
							int foundMiningStuff = 0;
							
							for(int a = 0; a < inv.mainInventory.length; a += rand.nextInt(3)+1){
								ItemStack is = inv.mainInventory[a];
								if (is == null)continue;
								
								Item item = is.getItem();
								if (item == ItemList.scorching_pickaxe || item instanceof ItemPickaxe)foundMiningStuff += 4;
								else if (item == Items.iron_ingot || item == Items.gold_ingot || item == Items.diamond || item == Items.redstone || (item == Items.dye && is.getItemDamage() == 4) ||
										 item == Items.emerald || item == Items.coal || item == ItemList.end_powder || item == ItemList.igneous_rock || item == ItemList.instability_orb ||
										 item == ItemList.stardust)foundMiningStuff += 1+(is.stackSize>>3);
								else if (item instanceof ItemBlock && ItemScorchingPickaxe.isBlockOre(((ItemBlock)item).field_150939_a))foundMiningStuff += 1+(is.stackSize>>3);
							}
							
							if (foundMiningStuff >= 13+rand.nextInt(6))target = (EntityPlayer)temp;
						}
					}
					else if (temp instanceof EntityLivingBase && !(temp instanceof EntityEnderman) && !temp.isImmuneToFire()){
						target = (EntityLivingBase)temp;
					}
				}
			}
		}
		else{
			targetX = target.posX;
			targetZ = target.posZ;
			targetY = target.posY+2D;
			
			if (!worldObj.isRemote){
				if (currentAttack != ATTACK_NONE){
					boolean hasFinished = false;
					++currentAttackTime;
					
					switch(currentAttack){
						case ATTACK_PROJECTILES:
							if (currentAttackTime == 50){
								Vec3 look = getLookVec();
								
								look.rotateAroundY(MathUtil.toRad(36F));
								worldObj.spawnEntityInWorld(new EntityProjectileMinerShot(worldObj, this, posX+look.xCoord*1.5D, posY+0.7D, posZ+look.zCoord*1.5D, target));
								look.rotateAroundY(MathUtil.toRad(-72F));
								worldObj.spawnEntityInWorld(new EntityProjectileMinerShot(worldObj, this, posX+look.xCoord*1.5D, posY+0.7D, posZ+look.zCoord*1.5D, target));
								hasFinished = true;
								
								PacketPipeline.sendToAllAround(this, 64D, new C08PlaySound(C08PlaySound.SPAWN_FIREBALL, posX, posY, posZ, 2F, 1.8F));
							}
							
							break;
							
						case ATTACK_LAVA:
							if (currentAttackTime % 8 == 0){
								currentAttackTime -= 8;
								
								if (attackLavaCounter == 0){
									PosMutable mpos = new PosMutable();
									
									for(int attempt = 0; attempt < 64; attempt++){
										mpos.set(this).move(rand.nextInt(5)-rand.nextInt(5), 4, rand.nextInt(5)-rand.nextInt(5));
										
										for(int yAttempt = 0; yAttempt < 7; yAttempt++){
											if (mpos.isAir(worldObj) && mpos.getDown().getBlock(worldObj).isOpaqueCube()){
												attackLavaCurrent = mpos.offset(0, -2, 0);
												attackLavaCounter = 1;
												attempt = 65;
												break;
											}
											else mpos.moveDown();
										}
									}
								}
								else{
									PosMutable mpos = new PosMutable();
									
									for(int px = -1; px <= 1; px++){
										for(int pz = -1; pz <= 1; pz++){
											if (px == 0 && pz == 0)continue;
											mpos.set(attackLavaCurrent).move(px, attackLavaCounter-1, pz);
											
											Block block = mpos.getBlock(worldObj);
											
											if (block == Blocks.flowing_lava || block == Blocks.lava)continue;
											else if (!MathUtil.floatEquals(block.getBlockHardness(worldObj, mpos.x, mpos.y, mpos.z), -1F)){
												mpos.breakBlock(worldObj, false);
											}
										}
									}
									
									mpos.set(attackLavaCurrent).move(0, attackLavaCounter-1, 0);
									
									mpos.setBlock(worldObj, Blocks.flowing_lava);
									for(int a = 0; a < 5; a++)Blocks.flowing_lava.updateTick(worldObj, mpos.x, mpos.y, mpos.z, rand);
									
									if (++attackLavaCounter == 6){
										if (++attackLavaDone >= 4){
											attackLavaDone = 0;
											hasFinished = true;
										}
										
										attackLavaCounter = 0;
										attackLavaCurrent = null;
									}
								}
							}
							
							break;
							
						case ATTACK_BLAST_WAVE:
							if (currentAttackTime == 30){
								for(Entity entity:(List<Entity>)worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(12D, 4D, 12D).offset(0D, -2D, 0D))){
									double dist = MathUtil.distance(entity.posX-posX, entity.posZ-posZ);
									if (dist > 12D)continue;
									
									Vec vec = Vec.between(this, entity).normalized().multiplied(0.4D+(12D-dist)*0.2D);
									
									entity.attackEntityFrom(DamageSource.causeMobDamage(this), 13F);
									if (entity instanceof EntityPlayer)PacketPipeline.sendToPlayer((EntityPlayer)entity, new C07AddPlayerVelocity(vec.x, 0.4D, vec.z));
									
									entity.motionX += vec.x;
									entity.motionY += 0.4D;
									entity.motionZ += vec.z;
								}
								
								PacketPipeline.sendToAllAround(this, 24D, new C08PlaySound(C08PlaySound.HAUNTEDMINER_ATTACK_BLAST, posX, posY, posZ, 1.5F, 1F));
								PosMutable mpos = new PosMutable();
								
								for(int attempt = 0; attempt < 90; attempt++){
									mpos.set(this).move(rand.nextInt(21)-10, -1, rand.nextInt(21)-10);
									if (MathUtil.distance(mpos.x-posX, mpos.z-posZ) > 10D)continue;
									
									for(int yAttempt = 0; yAttempt < 4; yAttempt++){
										if (mpos.isAir(worldObj) && !mpos.getDown().isAir(worldObj)){
											mpos.setBlock(worldObj, Blocks.fire);
											break;
										}
										else mpos.moveDown();
									}
								}
								
								hasFinished = true;
							}
							
							break;
							
						default: hasFinished = true;
					}
					
					if (hasFinished || currentAttackTime > 120){
						currentAttack = ATTACK_NONE;
						nextAttackTimer = (byte)(ATTACK_TIMER-5*worldObj.difficultySetting.getDifficultyId());
						currentAttackTime = 0;
						entityData.setByte(Data.ATTACK_TYPE, ATTACK_NONE);
					}
				}
				else if (--nextAttackTimer <= 0){
					currentAttack = (MathUtil.distance(target.posX-posX, target.posZ-posZ) < 7.5D && rand.nextInt(3) != 0) || rand.nextInt(6) == 0 ? ATTACK_BLAST_WAVE : (rand.nextInt(4) != 0 ? ATTACK_PROJECTILES : ATTACK_LAVA);
					entityData.setByte(Data.ATTACK_TYPE, currentAttack);
				}
			}
			
			if (target.isDead || (currentAttack == ATTACK_NONE && getDistanceToEntity(target) > 40D)){
				target = null;
				if (currentAttack != ATTACK_NONE)entityData.setByte(Data.ATTACK_TYPE, currentAttack = ATTACK_NONE);
			}
		}
		
		double speed = 0.075D;
		
		if (target != null){
			double dist = getDistanceToEntity(target);
			
			if (dist > 13D)speed = currentAttack == ATTACK_NONE ? 0.2D : 0.06D;
			else if (dist < 9D)speed = 0D;
		}
		
		Vec vec = Vec.xz(targetX-posX, targetZ-posZ).normalized().multiplied(speed);
		motionX = vec.x;
		motionZ = vec.z;
		if (Math.abs(targetY-posY) > 1D)motionY = (targetY-posY)*0.02D;
		
		if (MathUtil.distance(targetX-posX, targetZ-posZ) > 0.1D)renderYawOffset = rotationYaw = rotationYawHead = -MathUtil.toDeg((float)Math.atan2(targetX-posX, targetZ-posZ));
		else motionX = motionZ = 0D;
	}
	
	@Override
	public void onLivingUpdate(){
		super.onLivingUpdate();
		
		if (worldObj.isRemote){
			for(int a = 0; a < 2; a++)HardcoreEnderExpansion.fx.flame(posX+(rand.nextDouble()-0.5D)*0.2D, posY, posZ+(rand.nextDouble()-0.5D)*0.2D, 0D, -0.05D, 0D, 8);
			
			byte attack = entityData.getByte(Data.ATTACK_TYPE);
			
			if (attack != ATTACK_NONE && !dead){
				rotationYaw = renderYawOffset = rotationYawHead;
				Vec3 look = getLookVec();
				
				look.rotateAroundY(MathUtil.toRad(36F));
				HardcoreEnderExpansion.fx.global("spell", posX+look.xCoord*1.5D+(rand.nextDouble()-0.5D)*0.2D, posY+0.7D, posZ+look.zCoord*1.5D+(rand.nextDouble()-0.5D)*0.2D, 0D, 0D, 0D, 0.9F, 0.6F, 0F);
				look.rotateAroundY(MathUtil.toRad(-72F));
				HardcoreEnderExpansion.fx.global("spell", posX+look.xCoord*1.5D+(rand.nextDouble()-0.5D)*0.2D, posY+0.7D, posZ+look.zCoord*1.5D+(rand.nextDouble()-0.5D)*0.2D, 0D, 0D, 0D, 0.9F, 0.6F, 0F);
				
				++currentAttackTime;
				
				if (attack == ATTACK_BLAST_WAVE){
					if (currentAttackTime == 29){
						for(int flame = 0; flame < 180; flame++)HardcoreEnderExpansion.fx.flame(posX+(rand.nextDouble()-0.5D)*0.2D, posY+height*0.5D, posZ+(rand.nextDouble()-0.5D)*0.2D, (rand.nextDouble()-0.5D)*2D, (rand.nextDouble()-0.5D)*2D, (rand.nextDouble()-0.5D)*2D, 5+rand.nextInt(20));
					}
				}
			}
			else currentAttackTime = 0;
		}
		else if (!dead){
			List<Entity> nearEntities = worldObj.getEntitiesWithinAABBExcludingEntity(this, bottomBB.setBounds(posX-1.65D, posY-3, posZ-1.65D, posX+1.65D, posY, posZ+1.65D));
			
			for(Entity entity:nearEntities){
				if (entity instanceof EntityMobHauntedMiner)continue;
				entity.attackEntityFrom(DamageSource.causeMobDamage(this), 3F);
				entity.setFire(5);
				entity.hurtResistantTime -= 2;
			}
			
			if (currentAttack != ATTACK_NONE)rotationYaw = rotationYawHead;
		}
	}
	
	@Override
	public void setRevengeTarget(EntityLivingBase newTarget){
		if (target == null || (newTarget.getDistanceSqToEntity(this) < target.getDistanceSqToEntity(this) && !(newTarget instanceof EntityMobHauntedMiner))){
			target = newTarget;
			nextAttackTimer = ATTACK_TIMER;
		}
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount){
		boolean damaged = super.attackEntityFrom(source, amount);
		Entity sourceEntity = source.getEntity();
		
		if (damaged && sourceEntity instanceof EntityLivingBase){
			// TODO CausatumUtils.increase(source, CausatumMeters.END_MOB_DAMAGE, amount*0.25F);
			
			if (!(sourceEntity instanceof EntityMobHauntedMiner)){
				target = (EntityLivingBase)sourceEntity;
				nextAttackTimer = 5;
			}
			
			if (rand.nextInt(7) == 0 || (getHealth() <= 0F && rand.nextInt(3) != 0)){
				int maxTargeted = worldObj.difficultySetting.getDifficultyId()-2+rand.nextInt(2);
				List<EntityMobHauntedMiner> nearby = EntitySelector.type(worldObj, EntityMobHauntedMiner.class, boundingBox.expand(48D, 30D, 48D)), viable = new ArrayList<>();
				
				while(!nearby.isEmpty()){
					EntityMobHauntedMiner miner = nearby.remove(rand.nextInt(nearby.size()));
					if (miner == this)continue;
					
					double dist = getDistanceToEntity(miner);
					
					if (miner.target == null && dist < 16D)viable.add(miner);
					else if (miner.target == sourceEntity){
						if (--maxTargeted == 0)break;
					}
				}
				
				if (maxTargeted > 0)CollectionUtil.random(viable, rand).ifPresent(entity -> entity.setRevengeTarget((EntityLivingBase)sourceEntity));
			}
		}
		
		return damaged;
	}
	
	@Override
	public void knockBack(Entity entity, float damage, double xPower, double zPower){}
	
	@Override
	public void dropFewItems(boolean recentlyHit, int looting){
		for(int a = 0; a < rand.nextInt(2+rand.nextInt(2)+looting); a++)dropItem(ItemList.infernium, 1);
	}
	
	@Override
	public String getCommandSenderName(){
		return hasCustomNameTag() ? getCustomNameTag() : StatCollector.translateToLocal("entity.hauntedMiner.name");
	}
}
