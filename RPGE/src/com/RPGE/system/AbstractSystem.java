package com.RPGE.system;

import com.RPGE.core.Entity;

public abstract class AbstractSystem
{
    AbstractSystem()
    {

    }

    protected abstract void register(Entity e);
    protected abstract void remove(Entity e);

    protected abstract void process(); //TODO: add api object here(?)
}
