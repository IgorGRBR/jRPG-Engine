package com.RPGE.gui;

import java.util.ArrayList;
import java.util.ListIterator;

public class GUILayout
{
    String name;

    ArrayList<GUILayer> layers;
    ArrayList<GUIElement> elements;

    public GUILayout(ArrayList<GUILayer> l, ArrayList<GUIElement> e)
    {
        layers = l;
        elements = e;
    }

    public void init(GUIAPI guiapi)
    {
        for (GUIElement e : elements)
        {
            e.init(guiapi);
        }
    }

    public void step(GUIAPI guiapi)
    {
        for (GUIElement e : elements)
        {
            e.step(guiapi);
        }
    }

    public void draw(GUIAPI guiapi)
    {
        for (GUILayer l : layers)
        {
            l.draw(guiapi);
        }
    }

    public void addElement(GUIElement e)
    {
        elements.add(e);
        layers.get(e.getLayer()).add(e);
    }

    public void removeElement(GUIElement e)
    {
        elements.remove(e);
        layers.get(e.getLayer()).remove(e);
    }
}
