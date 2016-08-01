package info.aenterprise.recipeTree.tree.generic;

import info.aenterprise.recipeTree.util.Log;

public abstract class NodeData<T>
{
    protected T data;
    private int x, y, width;

    public NodeData(T stack) {
        this.data = stack;
        this.width = 40;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
		if (width != this.width)
			Log.info("Changing node with from " + this.width + " to " + width);
		this.width = width;
    }

	public T getData() {
		return data;
	}
}
