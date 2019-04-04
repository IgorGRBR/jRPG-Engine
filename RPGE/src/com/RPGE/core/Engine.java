package com.RPGE.core;

import java.io.File;
import java.util.ArrayList;

import com.RPGE.asset.AssetManager;
import com.RPGE.asset.ImageAsset;
import com.RPGE.asset.TilesetAsset;
import com.RPGE.exception.RPGEException;
import org.newdawn.slick.*;

public class Engine
{
    private AppGameContainer app;
    private EngineGame e_game;
    private boolean initialized, loaded;
    private boolean tilesize_set;
    InputState input_state;
    EntityFactory entity_factory;
    EntityAPI eAPI;

    int tile_width, tile_height; //Size of a single tile
    int res_width, res_height; //Resolution (in tiles)
    float gfx_scale; //Coeff to scale graphics with

    private AssetManager asset_manager;

    ArrayList<WorldScene> world_scenes;

    public Engine()
    {
        loaded = false;
        initialized = false;
        tilesize_set = false;
        asset_manager = new AssetManager();
        world_scenes = new ArrayList<>();
        input_state = new InputState();
        entity_factory = new EntityFactory();
        eAPI = new EntityAPI();
    }

    public void init()
    {
        System.out.println("Hello World!");

        e_game = new EngineGame(this);

        initialized = true;
    }

    public void run()
    {
        loaded = true;

        try
        {
            app = new AppGameContainer(e_game);
            app.setDisplayMode((int)Math.floor(tile_width * res_width * gfx_scale),
                    (int)Math.floor(tile_height * res_height * gfx_scale), false);
            //app.setTargetFrameRate(60); //Uuh, no delta times

            app.setMaximumLogicUpdateInterval(1000/60); // Max. 16 miliseconds can pass!
            app.setMinimumLogicUpdateInterval(1000/60);

            app.start();
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }
    }

    public void setSysLibPath(String natives)
    {
        System.setProperty("java.library.path", "lib");
        System.setProperty("org.lwjgl.librarypath", new File(natives).getAbsolutePath());
        System.setProperty("net.java.games.input.librarypath", new File(natives).getAbsolutePath());
    }

    public AssetManager assetManager()
    {
        return asset_manager;
    }

    public void setTileSize(int width, int height)
    {
        if (!loaded && !tilesize_set)
        {
            tile_width = width;
            tile_height = height;
            asset_manager.setTileSize(tile_width, tile_height);
            tilesize_set = true;
        }
    }

    public void addLevel(String path)
    {
        WorldScene ws = new WorldScene("", path, tile_width, tile_height, eAPI);
        world_scenes.add(ws);
    }

    public void bindKey(String name, int key)
    {
        if (initialized)
        {
            input_state.bind(name, key);
        }
    }

    public void setResolution(int x_tiles, int y_tiles, float scale)
    {
        if (!loaded)
        {
            res_width = x_tiles;
            res_height = y_tiles;
            gfx_scale = scale;
        }
    }

    public void addEntityClass(Class<? extends Entity> cla)
    {
        try
        {
            entity_factory.bind(cla);
        }
        catch (RPGEException e)
        {
            e.printStackTrace();
        }
    }
}

class EngineGame extends BasicGame
{
    private Engine engine;

    //Graphics stuff
    Image gfx_img;
    Graphics gfx;

    //Entity API
    EntityAPI eAPI;

    //Scenes stuff;
    WorldController world_controller;

    public EngineGame()
    {
        super("RPGE game");
    }
    public EngineGame(Engine e)
    {
        super("RPGE game");
        engine = e;
        eAPI = engine.eAPI;
    }

    //Testing vars
    //public ImageAsset testImg;
    public TilesetAsset testTile;
    @Override
    public void init(GameContainer container) throws SlickException
    {
        //Setup global drawing surface
        gfx_img = new Image(engine.tile_width * engine.res_width,
                engine.tile_height*engine.res_height);
        gfx = gfx_img.getGraphics();
        gfx.setBackground(new Color(255,255,255));

        //Load input
        engine.input_state.setInputProvider(container.getInput());

        //Setup world controller
        world_controller = new WorldController(engine.world_scenes, engine.entity_factory);

        //Bind basic inputs
        engine.input_state.bind("up", Input.KEY_UP);
        engine.input_state.bind("down", Input.KEY_DOWN);
        engine.input_state.bind("left", Input.KEY_LEFT);
        engine.input_state.bind("right", Input.KEY_RIGHT);
        engine.input_state.bind("a", Input.KEY_C);
        engine.input_state.bind("b", Input.KEY_X);
        engine.input_state.bind("c", Input.KEY_Z);

        //Initialize Entity API
        eAPI.setScreenSize(engine.res_width, engine.res_height);
        eAPI.setTileDimensions(engine.tile_width, engine.tile_height);
        eAPI.setAssetManager(engine.assetManager());
        eAPI.setGFX(gfx);
        eAPI.setInputState(engine.input_state);
        eAPI.setWorldController(world_controller);


        //Load queued levels
        world_controller.load(engine.assetManager());

        //Testing stuff
        //testImg = engine.assetManager().getImage("plr");

        //Initialize first scene in the list
        world_controller.setCurrentWorld(0);
        world_controller.init();
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException
    {
        engine.input_state.update();
        world_controller.step(delta);
    }

    public void render(GameContainer container, Graphics g) throws SlickException
    {
        world_controller.draw(gfx);

        gfx.flush();
        gfx_img.draw(0, 0,engine.gfx_scale);
    }
}
