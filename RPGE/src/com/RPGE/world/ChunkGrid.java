package com.RPGE.world;

import com.RPGE.core.Entity;

import java.util.ArrayList;

public class ChunkGrid
{
    int chunk_width, chunk_height;
    int width, height; //Dimensions of the grid (in chunks)
    Chunk[][] chunks;

    public ChunkGrid(int cw, int ch, int w, int h)
    {
        chunk_width = cw;
        chunk_height = ch;
        width = w;
        height = h;
        chunks = new Chunk[w][h];
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                chunks[i][j] = new Chunk(chunk_width, chunk_height);
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public void setData(int x, int y, int[][] data)
    {
        chunks[x][y].setData(data);
    }

    public void setChunks(ArrayList<Chunk> c, ArrayList<int[]> p)
    {
        for (int i = 0; i < c.size(); i++)
        {
            chunks[p.get(i)[0]][p.get(i)[1]] = c.get(i);
        }
    }

    public Chunk getChunk(int cx, int cy)
    {
        return chunks[cx][cy];
    }

    public int getTileData(int tx, int ty)
    {
        tx = Math.max(0, Math.min(chunk_width * width - 1, tx));
        ty = Math.max(0, Math.min(chunk_height * height - 1, ty));
        int cx = tx / chunk_width;
        int cy = ty / chunk_height;
        int ttx = tx % chunk_width;
        int tty = ty % chunk_height;
        return chunks[cx][cy].get(ttx, tty);
    }

    public void addEntity(Entity e)
    {
        int cx = e.getPosX() / chunk_width, cy = e.getPosY() / chunk_height;
        chunks[cx][cy].add(e);
    }

    public void removeEntity(Entity e)
    {
        int cx = e.getPosX() / chunk_width, cy = e.getPosY() / chunk_height;
        chunks[cx][cy].remove(e);
    }

    public void updateEntities()
    {
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
            {
                ArrayList<Entity> remove_list = new ArrayList<>();
                for(Entity e : chunks[i][j].getEntities())
                {
                    int cx = e.getPosX() / chunk_width, cy = e.getPosY() / chunk_height;
                    cx = Math.max(0, Math.min(width - 1, cx));
                    cy = Math.max(0, Math.min(height - 1, cy));
                    if (cx != i || cy != j)
                    {
                        remove_list.add(e);
                        chunks[cx][cy].add(e);
                    }
                }
                chunks[i][j].removeAll(remove_list);
            }
    }
}
