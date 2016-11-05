package chylex.hee.gui;
import gnu.trove.iterator.TObjectIntIterator;
import gnu.trove.map.hash.TObjectIntHashMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import chylex.hee.gui.helpers.ContainerHelper;
import chylex.hee.gui.helpers.IContainerEventHandler;
import chylex.hee.gui.slots.SlotEnhancementsSubject;
import chylex.hee.gui.slots.SlotShowCase;
import chylex.hee.mechanics.enhancements.EnhancementData;
import chylex.hee.mechanics.enhancements.EnhancementData.EnhancementInfo;
import chylex.hee.mechanics.enhancements.EnhancementIngredient;
import chylex.hee.mechanics.enhancements.EnhancementList;
import chylex.hee.mechanics.enhancements.EnhancementRegistry;
import chylex.hee.mechanics.enhancements.IEnhanceableTile;

public class ContainerEndPowderEnhancements extends Container implements IContainerEventHandler{
	private final EntityPlayer owner;
	public final IInventory containerInv;
	public final IEnhanceableTile enhanceableTile;
	
	public ContainerEndPowderEnhancements(InventoryPlayer inv, IEnhanceableTile tileOptional){
		containerInv = new InventoryBasic("", false, 1);
		this.enhanceableTile = tileOptional;
		
		if (isEnhancingTile()){
			addSlotToContainer(new SlotShowCase(containerInv, 0, 80, 8));
			containerInv.setInventorySlotContents(0, IEnhanceableTile.createItemStack(tileOptional));
		}
		else{
			addSlotToContainer(new SlotEnhancementsSubject(containerInv, 0, 80, 8));
		}
		
		ContainerHelper.addPlayerInventorySlots(this, inv, 0, 28);
		owner = inv.player;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player){
		return true;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotId){
		return ContainerHelper.transferStack(this, this::mergeItemStack, containerInv.getSizeInventory(), slotId); // TODO test
	}
	
	@Override
	public void onContainerClosed(EntityPlayer player){
		super.onContainerClosed(player);
		if (!isEnhancingTile() && containerInv.getStackInSlot(0) != null)player.dropPlayerItemWithRandomChoice(containerInv.getStackInSlot(0), false);
	}
	
	@Override
	public void onEvent(EntityPlayer player, int eventID){
		List<EnhancementInfo> info = listEnhancementInfo();
		if (eventID >= 0 && eventID < info.size())tryUpgradeEnhancement(info.get(eventID)); // same order as enums
	}
	
	public boolean isEnhancingTile(){
		return enhanceableTile != null;
	}
	
	public int getStackSize(){
		return getSlot(0).getStack().stackSize;
	}
	
	public EnhancementList getEnhancements(){
		return EnhancementRegistry.getEnhancementList(getSlot(0).getStack());
	}
	
	public List<EnhancementInfo> listEnhancementInfo(){
		return EnhancementRegistry.listEnhancementInfo(getSlot(0).getStack().getItem());
	}
	
	private TObjectIntHashMap<EnhancementIngredient> getIngredientMap(final EnhancementData<?>.EnhancementInfo info, final int level){
		TObjectIntHashMap<EnhancementIngredient> ingredientMap = new TObjectIntHashMap<>(4);
		info.getIngredients(level, getStackSize()).forEach(ingredient -> ingredientMap.put(ingredient, ingredient.getAmount(level, getStackSize())));
		return ingredientMap;
	}
	
	public Collection<EnhancementIngredient> getMissingUpgradeIngredients(final EnhancementData<?>.EnhancementInfo info){
		TObjectIntHashMap<EnhancementIngredient> left = getIngredientMap(info, getEnhancements().get(info.getEnhancement())+1);
		
		Arrays.stream(owner.inventory.mainInventory).filter(is -> is != null).map(is -> is.copy()).forEach(is -> {
			for(TObjectIntIterator<EnhancementIngredient> iter = left.iterator(); iter.hasNext();){
				iter.advance();
				
				if (iter.key().selector.isValid(is)){
					int newValue = Math.max(0, iter.value()-is.stackSize);
					is.stackSize = Math.max(0, is.stackSize-iter.value());
					
					if (newValue == 0)iter.remove();
					else iter.setValue(newValue);
				}
			}
		});
		
		return left.keySet();
	}
	
	public boolean tryUpgradeEnhancement(final EnhancementData<?>.EnhancementInfo info){
		EnhancementList list = getEnhancements();
		
		if (list.get(info.getEnhancement()) >= info.getMaxLevel())return false;
		if (!getMissingUpgradeIngredients(info).isEmpty())return false;
		
		EnhancementList enhancements = getEnhancements();
		TObjectIntHashMap<EnhancementIngredient> left = getIngredientMap(info, enhancements.get(info.getEnhancement())+1);
		
		for(int slot = 0; slot < owner.inventory.mainInventory.length; slot++){
			ItemStack is = owner.inventory.mainInventory[slot];
			if (is == null)continue;
			
			for(TObjectIntIterator<EnhancementIngredient> iter = left.iterator(); iter.hasNext();){
				iter.advance();
				
				if (iter.key().selector.isValid(is)){
					int newValue = Math.max(0, iter.value()-is.stackSize);
					if ((is.stackSize -= iter.value()) <= 0)owner.inventory.mainInventory[slot] = null;
					
					if (newValue == 0)iter.remove();
					else iter.setValue(newValue);
				}
			}
		}
		
		enhancements.upgrade(info.getEnhancement());
		if (isEnhancingTile())enhanceableTile.getEnhancements().upgrade(info.getEnhancement());
		
		return true;
	}
}
