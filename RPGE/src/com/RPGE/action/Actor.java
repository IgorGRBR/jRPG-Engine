package com.RPGE.action;

import com.RPGE.core.ActorAPI;

import java.util.HashMap;

public abstract class Actor
{
    protected int x, y; // x and y positions on the world grid (in pixels)
    protected boolean active, visible, removed; // event activity flags
    int layer; // layer actor will be drawn in
    public Actor(boolean a, boolean v)
    {
        active = a;
        visible = v;
        removed = false;
        x = 0;
        y = 0;
    }
    public int getLayer() { return layer; }
    public final void setLayer(int l) { layer = l; }

    //Events
    public abstract void init(ActorAPI eAPI);
    public abstract void sceneLoad(ActorAPI eAPI, HashMap<String, String> args);
    public abstract void step(ActorAPI eAPI);
    public abstract void draw(ActorAPI eAPI);
}
