package com.RPGE.system;

import com.RPGE.core.Entity;
import com.RPGE.core.EntityAPI;

import java.util.ArrayList;

public class DrawSystem
{
    private int layer_count;

    public ArrayList<ArrayList<Entity>> entity_lists;
    public DrawSystem(int layer_count)
    {
        this.layer_count = layer_count;
        entity_lists = new ArrayList<>();
        for (int i = 0; i < layer_count; i++)
        {
            entity_lists.add(new ArrayList<>());
        }
    }

    
    public void register(Entity e)
    {
        if (e.isVisible())
        {
            entity_lists.get(e.getLayer()).add(e);
        }
    }

    
    public void remove(Entity e)
    {
        entity_lists.get(e.getLayer()).remove(e);
    }

    
    public void process(EntityAPI eAPI)
    {
        for (int i = 0; i < entity_lists.size(); i++)
        {
            draw(eAPI, i);
        }
    }

    public void draw(EntityAPI eAPI, int layer)
    {
        for (Entity e : entity_lists.get(layer))
        {
            e.draw(eAPI);
        }
    }

    public int getLayerCount() { return layer_count; }
}
