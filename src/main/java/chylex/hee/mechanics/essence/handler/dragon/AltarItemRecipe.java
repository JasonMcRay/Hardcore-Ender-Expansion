package chylex.hee.mechanics.essence.handler.dragon;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import chylex.hee.entity.item.EntityItemAltar;
import chylex.hee.system.util.ItemUtil;
import net.minecraft.world.EnumDifficulty;

public class AltarItemRecipe{
	private static final NBTTagCompound emptyTag = new NBTTagCompound();
	
	public final ItemStack input, output;
	public static short costPeaceful;
	public static short costEasy;
	public static short costNormal;
	public static short costHard;

	public AltarItemRecipe(ItemStack input, ItemStack output, int costPeaceful, int costEasy, int costNormal, int costHard){
		this.input = input;
		this.output = output;
		this.costPeaceful = (short) costPeaceful;
		this.costEasy = (short) costEasy;
		this.costNormal = (short) costNormal;
		this.costHard = (short) costHard;

		System.out.println("Cost Peaceful is " + getCost(EnumDifficulty.PEACEFUL));
		ItemUtil.getTagRoot(input,true);
	}

	public static int getCost(EnumDifficulty diff) {
		if (diff == EnumDifficulty.PEACEFUL) {
			return costPeaceful;
		} else if (diff == EnumDifficulty.EASY){
			return costEasy;
		} else if (diff == EnumDifficulty.NORMAL){
			return costNormal;
		} else {
			return costHard;
		}
	}

	/*
	public static int getCost() {
		if (!worldObj.isRemote && worldObj.difficultySetting == EnumDifficulty.PEACEFUL) {
			return 60;
		} else {
			return 10;
		}
	}
	*/
	/**
	 * Checks if the ItemStacks have the same item, damage and NBT (except for Altar status NBT).
	 */
	public boolean isApplicable(ItemStack is){
		if (input.getItem() == is.getItem() && input.getItemDamage() == is.getItemDamage()){
			NBTTagCompound nbt = is.hasTagCompound() ? (NBTTagCompound)is.getTagCompound().copy() : emptyTag;
			
			nbt.removeTag("HEE_transform");
			nbt.removeTag("HEE_enchant");
			nbt.removeTag("HEE_repair");
			
			return nbt.equals(emptyTag);
		}
		else return false;
	}
	
	public void doTransaction(EntityItem item){
		ItemStack is = output.copy();
		is.stackSize = 1;
		item.setEntityItemStack(is);
		if (item instanceof EntityItemAltar)((EntityItemAltar)item).setSparkling();
	}
	
	@Override
	public String toString(){
		return "{ input: "+input.toString()+", output: "+output.toString()+", costPeaceful: "+costPeaceful+", costEasy: "+costEasy+", costNormal: "+costNormal+", costHard: "+costHard+ "}";
	}
}
