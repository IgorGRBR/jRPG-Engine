package com.RPGE.system;

import com.RPGE.core.Entity;
import com.RPGE.core.EntityAPI;

import java.util.ArrayList;

public class StepSystem
{
    public ArrayList<Entity> entities;
    public StepSystem()
    {
        entities = new ArrayList<>();
    }

    public void register(Entity e)
    {
        entities.add(e);
    }

    
    public void remove(Entity e)
    {
        entities.remove(e);
    }

    
    public void process(EntityAPI eAPI)
    {
        for (Entity e : entities)
        {
            if (e.isActive())
                e.step(eAPI);
            else
                remove(e);
        }
    }
}
