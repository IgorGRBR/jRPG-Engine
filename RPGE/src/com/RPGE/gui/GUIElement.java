package com.RPGE.gui;

import java.util.HashMap;

public abstract class GUIElement
{
    private int x, y; //x and y positions of the element (int pixels)
    private int layer; //layer index of current element
    private boolean active, visible, removed;

    public GUIElement(boolean a, boolean v)
    {
        x = 0;
        y = 0;
        layer = 0;
        active = a;
        visible = v;
        removed = false;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getLayer() { return layer; }

    void setX(int ix) { x = ix; }
    void setY(int iy) { y = iy; }
    void setLayer(int l) { layer = l; }
    void remove() { removed = true; }
    boolean isRemoved() { return removed; }

    public abstract void init(GUIAPI guiapi);
    public abstract void onGUILoad(GUIAPI guiapi, HashMap<String, String> args);
    public abstract void step(GUIAPI guiapi);
    public abstract void draw(GUIAPI guiapi);
    public abstract void destroy(GUIAPI guiapi);
}
