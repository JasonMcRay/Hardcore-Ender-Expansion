package chylex.hee.block;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import net.minecraft.block.BlockEndPortal;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import chylex.hee.entity.technical.EntityTechnicalVoidPortal;
import chylex.hee.game.save.SaveData;
import chylex.hee.game.save.types.global.WorldFile;
import chylex.hee.item.ItemPortalToken;
import chylex.hee.item.block.ItemBlockWithSubtypes.IBlockSubtypes;
import chylex.hee.system.abstractions.Meta;
import chylex.hee.system.abstractions.Pos;
import chylex.hee.system.abstractions.entity.EntitySelector;
import chylex.hee.system.collections.CollectionUtil;
import chylex.hee.tileentity.TileEntityVoidPortal;
import chylex.hee.world.end.EndTerritory;
import chylex.hee.world.util.BoundingBox;
import chylex.hee.world.util.EntityPortalStatus;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockVoidPortal extends BlockEndPortal implements IBlockSubtypes{
	public static Optional<EntityTechnicalVoidPortal> getData(World world, int x, int y, int z){
		return CollectionUtil.get(EntitySelector.type(world, EntityTechnicalVoidPortal.class, AxisAlignedBB.getBoundingBox(x-4.5D, y-1D, z-4.5D, x+5.5D, y+1D, z+5.5D)), 0);
	}
	
	private final EntityPortalStatus portalStatus = new EntityPortalStatus();
	
	public BlockVoidPortal(){
		super(Material.portal);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta){
		return new TileEntityVoidPortal();
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity){
		if (entity.posY <= y+0.05D && entity instanceof EntityPlayerMP){
			Pos pos = Pos.at(x, y, z);
			int meta = pos.getMetadata(world);
			EntityPlayerMP player = (EntityPlayerMP)entity;
			
			if (portalStatus.onTouch(player)){
				if (meta == Meta.voidPortalReturn){ // TODO update current territory spawn if it was different
					Pos voidPortal = SaveData.global(WorldFile.class).getVoidPortalPos();
					if (voidPortal == null)return;
					
					player.mountEntity(null);
					player.setPositionAndUpdate(voidPortal.getX()+0.5D, voidPortal.getY()+1D, voidPortal.getZ()+0.5D);
				}
				else if (meta == Meta.voidPortalTravel){
					ItemStack tokenIS = getData(world, x, y, z).map(data -> data.getActiveToken()).orElse(null);
					if (tokenIS == null)return;
					
					EndTerritory territory = ItemPortalToken.getTerritory(tokenIS);
					if (territory == null || !territory.canGenerate())return;
					
					ItemPortalToken.generateTerritory(tokenIS, world).ifPresent(targetPos -> {
						player.mountEntity(null);
						
						if (EntitySelector.players(world, new BoundingBox(targetPos, targetPos).toAABB()).isEmpty()){
							player.setPositionAndUpdate(targetPos.getX()+0.5D, targetPos.getY()+1D, targetPos.getZ()+0.5D);
						}
						else{
							player.setPositionAndUpdate(targetPos.getX()-0.7D+world.rand.nextDouble()*2.4D, targetPos.getY()+1D, targetPos.getZ()-0.7D+world.rand.nextDouble()*2.4D);
						}
					});
				}
			}
		}
	}
	
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB checkAABB, List list, Entity entity){
		AxisAlignedBB collisionBox = AxisAlignedBB.getBoundingBox(x, y, z, x+1D, y+0.025D, z+1D);
		if (checkAABB.intersectsWith(collisionBox))list.add(collisionBox);
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z){}
	
	@Override
	public String getUnlocalizedName(ItemStack is){
		return is.getItemDamage() == Meta.voidPortalReturn ? "tile.voidPortal.return" : is.getItemDamage() == Meta.voidPortalTravel ? "tile.voidPortal.travel" : "tile.voidPortal.disabled";
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player){
		return new ItemStack(this, 1, Pos.at(x, y, z).getMetadata(world));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list){
		list.add(new ItemStack(item, 1, Meta.voidPortalTravel));
		list.add(new ItemStack(item, 1, Meta.voidPortalReturn));
		list.add(new ItemStack(item, 1, Meta.voidPortalDisabled));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand){}
}
