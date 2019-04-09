package com.RPGE.asset;


import org.newdawn.slick.Graphics;

public abstract class DrawableAsset extends AbstractAsset
{
    protected int width, height;
    public DrawableAsset()
    {
        super();
    }

    public DrawableAsset(String n, String p)
    {
        super(n, p);
    }

    protected abstract void draw(int x, int y);
    protected abstract void drawCentered(int x, int y);
    protected abstract void drawToContext(int x, int y, Graphics gfx);
    protected abstract void drawToContextCentered(int x, int y, Graphics gfx);
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}
