package com.RPGE.world;

import com.RPGE.core.Entity;

import java.util.ArrayList;
import java.util.List;

public class Chunk
{
    int[][] chunk_data;
    private ArrayList<Entity> entities;

    public Chunk(int w, int h)
    {
        chunk_data = new int[w][h];
        for (int i = 0; i < w; i++)
            for (int j = 0; j < h; j++)
                chunk_data[i][j] = 0;
        entities = new ArrayList<>();
    }

    public void add(Entity e)
    {
        entities.add(e);
    }

    public void remove(Entity e)
    {
        entities.remove(e);
    }

    public void removeAll(List<Entity> el) { entities.removeAll(el); }

    public ArrayList<Entity> getEntities()
    {
        return entities;
    }

    public void setData(int[][] data)
    {
        chunk_data = data;
    }

    public int get(int x, int y)
    {
        return chunk_data[x][y];
    }

    public void set(int x, int y, int value)
    {
        chunk_data[x][y] = value;
    }
}
