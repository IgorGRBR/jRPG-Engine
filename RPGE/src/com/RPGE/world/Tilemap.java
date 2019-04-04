package com.RPGE.world;

import java.util.*;

import com.RPGE.asset.TilesetAsset;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Tilemap
{
    //private TilesetAsset tileset;
    private int tile_width, tile_height;
    public ArrayList<TilemapLayer> layers;
    private int size_x, size_y; //Size (in chunks)
    private int chunk_width, chunk_height; //Size of a single chunk (...in tiles)

    public Tilemap(int tw, int th, int cw, int ch)
    {
        tile_width = tw;
        tile_height = th;
        chunk_width = cw;
        chunk_height = ch;
        //tileset = ts;
        layers = new ArrayList<>();
    }

    public void load(ArrayList<ArrayList<String>> input)
    {
        //TODO: Decide the fate of this thing
        //Some shit related to loading:
        //TilemapChunk tc = new TilemapChunk(chunksize_x, chunksize_y);
        //tc.offset_x = x*chunksize_x;
        //tc.offset_y = y*chunksize_y;
    }

    public void setLayers(HashMap<Integer, TilemapLayer> l_list)
    {
        List<Map.Entry<Integer, TilemapLayer>> entries = new ArrayList<>(l_list.entrySet());
        Collections.sort(entries,
                new Comparator<Map.Entry<Integer, TilemapLayer>>()
                {
                    @Override
                    public int compare(Map.Entry<Integer, TilemapLayer> o1,
                                       Map.Entry<Integer, TilemapLayer> o2)
                    {
                        return Integer.compare(o1.getKey(), o2.getKey());
                    }
                });
        for (int i = 0; i < entries.size(); i++)
        {
            layers.add(entries.get(i).getValue());
        }
    }

    public void compileLayers()
    {
        try
        {
            Image image = new Image(tile_width * chunk_width, tile_height * chunk_height);
            Graphics gfx = image.getGraphics();
            //Graphics gfx = new Graphics(tile_width * chunk_width,
             //       tile_height * chunk_height);
            //Graphics.setCurrent(gfx);
            for (TilemapLayer tl : layers)
            {
                tl.compileChunks(gfx);
            }
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }

    }

    public void draw(Graphics gfx, Camera cam)
    {
        for (TilemapLayer tl : layers)
        {
            tl.draw(gfx, cam);
        }
    }

    public void drawLayer(Graphics gfx, int l, Camera cam)
    {
        layers.get(l).draw(gfx, cam);
    }
}
