package com.RPGE.gui;

import com.RPGE.core.GUIAPI;

import java.util.ArrayList;

public class GUILayer
{
    ArrayList<GUIElement> elements;

    public GUILayer()
    {
        elements = new ArrayList<>();
    }

    public void add(GUIElement e)
    {
        elements.add(e);
    }

    public void remove(GUIElement e)
    {
        elements.remove(e);
    }

    public void draw(GUIAPI guiapi)
    {
        for (GUIElement e : elements)
        {
            if (e.visible) e.draw(guiapi);
        }
    }
}
