package com.RPGE.asset;

import com.RPGE.exception.RPGEException;

import java.util.ArrayList;



public class AssetCollection<T extends AbstractAsset>
{
    private ArrayList<T> items;

    public AssetCollection()
    {
        items = new ArrayList<T>();
    }

    public void add(T i)
    {
        items.add(i);
    }

    public void remove(T i)
    {
        items.remove(i);
    }

    public void remove(int i)
    {
        items.remove(i);
    }

    public T find(String name) throws RPGEException {
        for (T i : items)
        {
            if (((AbstractAsset)i).name.equals(name))
                return i;
        }
        throw new RPGEException("Asset with name " + name + " was not found!");
    }

    public T get(int i)
    {
        return items.get(i);
    }
}
