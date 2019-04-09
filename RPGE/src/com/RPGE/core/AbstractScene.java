package com.RPGE.core;

import com.RPGE.asset.AssetManager;
import org.newdawn.slick.Graphics;

public abstract class AbstractScene
{
    public String name;
    protected boolean loaded;
    protected String path;

    public AbstractScene()
    {

    };

    public String getName() { return name; }

    public abstract void load();
    public abstract void init(); //Initialize objects here
    public abstract void step(float delta);
    public abstract void draw(Graphics gfx);
}
