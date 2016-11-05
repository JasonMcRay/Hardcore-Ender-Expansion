package chylex.hee.gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import chylex.hee.system.abstractions.GL;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiLootChest extends GuiContainer{
	private static final ResourceLocation texChest = new ResourceLocation("textures/gui/container/generic_54.png");
	
	private final InventoryPlayer invPlayer;
	private final InventoryLootChest invLootChest;
	private final int inventoryHeight;
	
	public GuiLootChest(InventoryPlayer inventory, InventoryLootChest chest){
		super(new ContainerLootChest(inventory, chest));
		this.invPlayer = inventory;
		this.invLootChest = chest;
		inventoryHeight = chest.getSizeInventory()*2;
		ySize = 114+inventoryHeight;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		fontRendererObj.drawString(invPlayer.player.capabilities.isCreativeMode ? I18n.format("container.lootChest.editing") : I18n.format(invLootChest.getInventoryName()), 8, 6, (64<<16)|(64<<8)|64);
		fontRendererObj.drawString(invPlayer.hasCustomInventoryName() ? invPlayer.getInventoryName() : I18n.format(invPlayer.getInventoryName()), 8, ySize-94, (64<<16)|(64<<8)|64);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTickTime, int mouseX, int mouseY){
		GL.color(1F, 1F, 1F, 1F);
		mc.getTextureManager().bindTexture(texChest);
		
		int x = (width-xSize)>>1, y = (height-ySize)>>1;
		drawTexturedModalRect(x, y, 0, 0, xSize, inventoryHeight+17);
		drawTexturedModalRect(x, y+inventoryHeight+17, 0, 126, xSize, 96);
	}
}
