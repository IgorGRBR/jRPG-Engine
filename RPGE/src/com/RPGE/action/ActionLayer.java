package com.RPGE.action;

import com.RPGE.core.ActorAPI;

import java.util.ArrayList;

public class ActionLayer
{
    public ArrayList<Actor> actors;

    public ActionLayer()
    {
        actors = new ArrayList<>();
    }

    public void add(Actor a)
    {
        actors.add(a);
    }

    public void remove(Actor a)
    {
        actors.remove(a);
    }

    public void draw(ActorAPI aAPI)
    {
        for (Actor a : actors)
        {
            if (a.visible) a.draw(aAPI);
        }
    }
}
