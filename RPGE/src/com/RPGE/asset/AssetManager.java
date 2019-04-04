package com.RPGE.asset;

import com.RPGE.exception.RPGEException;

public class AssetManager
{
    private AssetCollection<ImageAsset> images;
    private AssetCollection<TilesetAsset> tilesets;
    private AssetCollection<SpriteAsset> sprites;
    private int TILE_WIDTH, TILE_HEIGHT;
    private boolean loaded;

    public AssetManager()
    {
        images = new AssetCollection<>();
        tilesets = new AssetCollection<>();
        sprites = new AssetCollection<>();
        loaded = false;

        //Adding dummy assets, so they can be used as placeholders
        //Their usage will result into a exception throw
        images.add(new ImageAsset());
        tilesets.add(new TilesetAsset());
        sprites.add(new SpriteAsset());
    }

    public void setTileSize(int tw, int th)
    {
        if (!loaded)
        {
            TILE_WIDTH = tw;
            TILE_HEIGHT = th;
            loaded = true;
        }
    }

    public void addImage(String name, String path)
    {
        ImageAsset img = new ImageAsset(name, path);

        images.add(img);
    }

    public ImageAsset getImage(String name)
    {
        try
        {
            ImageAsset result = images.find(name);
            if (!result.loaded) result.load();
            return result;
        }
        catch(RPGEException e)
        {
            System.out.println(e);
            return null;
        }
    }

    public void addSprite(String name, String path, int row_count, int col_count)
    {
        SpriteAsset spr = new SpriteAsset(name, path, row_count, col_count);

        sprites.add(spr);
    }

    public SpriteAsset getSprite(String name)
    {
        try
        {
            SpriteAsset result = sprites.find(name);
            if (!result.loaded) result.load();
            return result;
        }
        catch(RPGEException e)
        {
            System.out.println(e);
            return null;
        }
    }

    public void addTileset(String name, String path)
    {
        TilesetAsset tls = new TilesetAsset(name, path, TILE_WIDTH, TILE_HEIGHT);

        tilesets.add(tls);
    }

    public TilesetAsset getTileset(String name)
    {
        try
        {
            TilesetAsset result = tilesets.find(name);
            if (!result.loaded) result.load();
            return result;
        }
        catch(RPGEException e)
        {
            System.out.println(e);
            return null;
        }
    }
}
