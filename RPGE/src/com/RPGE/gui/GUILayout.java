package com.RPGE.gui;

import com.RPGE.core.GUIAPI;
import com.RPGE.exception.RPGEException;

import java.util.ArrayList;

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

    public String getName() { return name; }

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

    public GUIElement findElement(String name) throws RPGEException
    {
        for (GUIElement e : elements)
        {
            if (e.id.equals(name)) return e;
        }
        throw new RPGEException("Element with name " + name + " was not found!");
    }
}
