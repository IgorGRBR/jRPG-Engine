package com.RPGE.asset;

public abstract class AbstractAsset
{
    protected boolean loaded;
    protected String path;
    protected String name;

    AbstractAsset()
    {
        loaded = false;
        name = "";
    }

    AbstractAsset(String n, String p)
    {
        loaded = false;
        path = p;
        name = n;
    }

    protected abstract void load();
}
