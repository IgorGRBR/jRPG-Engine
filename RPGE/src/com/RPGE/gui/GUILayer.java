package com.RPGE.gui;

import java.util.ArrayList;
import java.util.ListIterator;

public class GUILayer
{
    private ArrayList<GUIElement> elements;

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
            e.draw(guiapi);
        }
    }
}
