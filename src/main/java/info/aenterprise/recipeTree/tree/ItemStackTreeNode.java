package info.aenterprise.recipeTree.tree;

import info.aenterprise.recipeTree.tree.generic.TreeNode;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class ItemStackTreeNode extends TreeNode<ItemStack>
{
    public ItemStackTreeNode(ItemStack data)
    {
        super(new ItemStackNodeData(data));
		if (data.getMetadata() == OreDictionary.WILDCARD_VALUE && !data.getHasSubtypes())
			data.setItemDamage(0);
	}


    public ItemStackTreeNode(ItemStackNodeData data)
    {
        super(data);
    }

    public ItemStackTreeNode(ItemStackNodeData data, TreeNode parent)
    {
        super(data, parent);
    }

    @Override
	public void addLeaf(ItemStack data) {
		addNode(new ItemStackTreeNode(data));
    }

	@SideOnly(Side.CLIENT)
	@Override
	public void drawBackGround(GuiScreen gui, int left, int top) {
		gui.drawTexturedModalRect(left + data.getX(), top + data.getY(), 74, 230, 20, 20);
    }

	@SideOnly(Side.CLIENT)
	@Override
	public void drawData(GuiScreen gui) {
		GlStateManager.pushMatrix();
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		RenderHelper.enableGUIStandardItemLighting();
		FontRenderer fontRenderer = gui.mc.fontRendererObj;
		gui.mc.getRenderItem().renderItemAndEffectIntoGUI(null, data.getData(), data.getX() + 2, data.getY() + 2);
		gui.mc.getRenderItem().renderItemOverlayIntoGUI(fontRenderer, data.getData(), data.getX() + 2, data.getY() + 2, null);
		GlStateManager.popMatrix();
	}
}
