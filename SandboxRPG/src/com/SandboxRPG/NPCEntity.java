package com.SandboxRPG;

import com.RPGE.core.Entity;
import com.RPGE.core.EntityAPI;
import com.RPGE.core.IEntity;

import java.util.List;

public class NPCEntity extends Entity implements IEntity
{
     public NPCEntity()
     {
         super(true, true, true);
     }

    @Override
    public void init(EntityAPI eAPI)
    {

    }

    @Override
    public void worldLoad(EntityAPI eAPI, List<String> args)
    {
        System.out.println("NPCEntity.worldLoad");
    }

    @Override
    public void levelCollision(EntityAPI eAPI, int tile_data) {

    }

    @Override
    public void step(EntityAPI eAPI)
    {

    }

    @Override
    public void draw(EntityAPI eAPI)
    {

    }

    public static String getName()
    {
        return "npc";
    }
}
