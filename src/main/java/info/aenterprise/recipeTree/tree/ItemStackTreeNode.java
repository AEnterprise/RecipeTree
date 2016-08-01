package info.aenterprise.recipeTree.tree;

import info.aenterprise.recipeTree.tree.generic.TreeNode;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ItemStackTreeNode extends TreeNode<ItemStack>
{
	private static final float[] color = new float[]{0.545F, 0.545F, 0.545F};

    public ItemStackTreeNode(ItemStack data)
    {
        super(new ItemStackNodeData(data));
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
	public void drawBackGround(GuiScreen gui, int left, int top, boolean selected) {
		int x = left + data.getX();
		int y = top + data.getY();
		gui.drawTexturedModalRect(x, y, selected ? 96 : 74, 230, 20, 20);
		if (!subNodes.isEmpty())
		{
			drawLine(x + 10, y + 20, x + 10, y + 30, color);
			for (TreeNode subNode : subNodes)
			{
				int subNodeX = left + subNode.getData().getX();
				int subNodeY = top + subNode.getData().getY();
				drawLine(subNodeX + 10, subNodeY, subNodeX + 10, subNodeY - 6, color);
				drawLine(subNodeX + 10, subNodeY - 6, x + 10, y + 30, color);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void drawData(GuiScreen gui, int left, int top) {
		GlStateManager.pushMatrix();
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		RenderHelper.enableGUIStandardItemLighting();
		FontRenderer fontRenderer = gui.mc.fontRendererObj;
		gui.mc.getRenderItem().renderItemAndEffectIntoGUI(null, data.getData(), data.getX() + 2 + left, data.getY() + top + 2);
		gui.mc.getRenderItem().renderItemOverlayIntoGUI(fontRenderer, data.getData(), data.getX() + 2 + left, data.getY() + top + 2, null);
		GlStateManager.popMatrix();
	}
}
