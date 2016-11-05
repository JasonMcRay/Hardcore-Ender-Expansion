package chylex.hee.mechanics.compendium.content.objects;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import javax.annotation.Nonnull;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import chylex.hee.init.ItemList;
import chylex.hee.item.ItemSpawnEggs;
import chylex.hee.system.util.ItemUtil;

public class ObjectMob implements IObjectHolder<Class<? extends EntityLiving>>{
	private static ItemStack getMobEgg(Class<? extends EntityLiving> mobClass){
		ItemStack is = null;
		String name = "Unknown Mob";
		int spawnEggDamage = ItemSpawnEggs.getDamageForMob(mobClass);
		
		if (spawnEggDamage != -1){
			is = new ItemStack(ItemList.spawn_eggs, 1, spawnEggDamage);
			name = ItemSpawnEggs.getMobName(mobClass);
		}
		else{
			OptionalInt dmg = IntStream.range(0, 256).filter(id -> EntityList.getClassFromID(id) == mobClass).findAny();
			
			if (dmg.isPresent())is = new ItemStack(Items.spawn_egg, 1, dmg.getAsInt());
			name = (String)EntityList.classToStringMapping.get(mobClass);
		}
		
		if (is == null)is = new ItemStack(Blocks.bedrock);
		
		ItemUtil.setName(is, name);
		return is;
	}
	
	private static Class<? extends EntityLiving> getMobClass(ItemStack is){
		if (is.getItem() == ItemList.spawn_eggs)return ItemSpawnEggs.getMobFromDamage(is.getItemDamage());
		else if (is.getItem() == Items.spawn_egg)return (Class<? extends EntityLiving>)EntityList.IDtoClassMapping.get(is.getItemDamage());
		else return null;
	}
	
	private final Class<? extends EntityLiving> mobClass;
	private final ItemStack displayIS;
	
	public ObjectMob(Class<? extends EntityLiving> mobClass){
		this.mobClass = mobClass;
		this.displayIS = getMobEgg(mobClass);
	}
	
	@Override
	public ItemStack getDisplayItemStack(){
		return displayIS;
	}

	@Override
	public Class<? extends EntityLiving> getUnderlyingObject(){
		return mobClass;
	}
	
	@Override
	public boolean checkEquality(@Nonnull Object obj){
		return obj == mobClass || obj.getClass() == mobClass; // accepts both class and the entity itself
	}
	
	@Override
	public boolean checkEquality(ItemStack is){
		return getMobClass(is) == mobClass;
	}
}
