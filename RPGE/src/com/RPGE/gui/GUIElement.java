package com.RPGE.gui;

import com.RPGE.core.GUIAPI;

import java.util.HashMap;

public abstract class GUIElement
{
    public int x, y; //x and y positions of the element (int pixels)
    private int layer; //layer index of current element
    private boolean active, removed;
    public boolean visible;
    public String id;
    public GUIElement parent;

    public GUIElement(boolean a, boolean v)
    {
        x = 0;
        y = 0;
        layer = 0;
        active = a;
        visible = v;
        removed = false;
        id = "";
    }
    public int getLayer() { return layer; }

    void setLayer(int l) { layer = l; }
    void remove() { removed = true; }
    boolean isRemoved() { return removed; }

    public abstract void init(GUIAPI guiapi);
    public abstract void onGUILoad(GUIAPI guiapi, HashMap<String, String> args);
    public abstract void step(GUIAPI guiapi);
    public abstract void draw(GUIAPI guiapi);
    public abstract void destroy(GUIAPI guiapi);
}
