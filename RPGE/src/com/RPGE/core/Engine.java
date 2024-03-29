package com.RPGE.core;

import java.io.File;
import java.util.ArrayList;

import com.RPGE.action.Actor;
import com.RPGE.action.ActorFactory;
import com.RPGE.asset.AssetManager;
import com.RPGE.asset.TilesetAsset;
import com.RPGE.exception.RPGEException;
import com.RPGE.gui.GUIController;
import com.RPGE.gui.GUIElement;
import com.RPGE.gui.GUIElementFactory;
import com.RPGE.gui.elements.BoxElement;
import com.RPGE.gui.elements.IconElement;
import com.RPGE.gui.elements.TextBoxElement;
import org.newdawn.slick.*;

public class Engine
{
    private AppGameContainer app;
    private EngineGame e_game;
    private boolean initialized, loaded;
    private boolean tilesize_set;
    InputState input_state;
    EntityFactory entity_factory;
    GUIElementFactory ge_factory;
    ActorFactory actor_factory;
    int tile_width, tile_height; //Size of a single tile
    int res_width, res_height; //Resolution (in tiles)
    float gfx_scale; //Coeff to scale graphics with

    private AssetManager asset_manager;

    ArrayList<String> world_scenes;
    ArrayList<String> action_scenes;
    ArrayList<String> gui_layout_paths;

    public Engine()
    {
        loaded = false;
        initialized = false;
        tilesize_set = false;
        asset_manager = new AssetManager();
        world_scenes = new ArrayList<>();
        action_scenes = new ArrayList<>();
        input_state = new InputState();
        entity_factory = new EntityFactory();
        ge_factory = new GUIElementFactory();
        gui_layout_paths = new ArrayList<>();
        actor_factory = new ActorFactory();
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
        world_scenes.add(path);
    }

    public void addActionScene(String path)
    {
        action_scenes.add(path);
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

    public void addActorClass(Class<? extends Actor> cla)
    {
        try
        {
            actor_factory.bind(cla);
        }
        catch (RPGEException e)
        {
            e.printStackTrace();
        }
    }

    public void addGUIElementClass(Class<? extends GUIElement> cla)
    {
        try
        {
            ge_factory.bind(cla);
        }
        catch (RPGEException e)
        {
            e.printStackTrace();
        }
    }

    public void addGUILayout(String path)
    {
        gui_layout_paths.add(path);
    }
}

class EngineGame extends BasicGame
{
    private Engine engine;

    //Graphics stuff
    Image gfx_img, gui_img;
    Graphics gfx, gui_gfx;

    //APIs
    EntityAPI eAPI;
    GUIAPI gAPI;
    ActorAPI aAPI;

    //Controllers
    SceneManager scene_manager;
    GUIController gui_controller;

    public EngineGame()
    {
        super("RPGE game");
    }
    public EngineGame(Engine e)
    {
        super("RPGE game");
        engine = e;
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
        gui_img = new Image(engine.tile_width * engine.res_width,
                engine.tile_height*engine.res_height);
        gfx = gfx_img.getGraphics();
        gui_gfx = gui_img.getGraphics();
        gfx.setBackground(new Color(255,255,255));
        gui_gfx.setBackground(new Color(255,255,255));

        //Load input
        engine.input_state.setInputProvider(container.getInput());

        //Bind basic inputs
        engine.input_state.bind("up", Input.KEY_UP);
        engine.input_state.bind("down", Input.KEY_DOWN);
        engine.input_state.bind("left", Input.KEY_LEFT);
        engine.input_state.bind("right", Input.KEY_RIGHT);
        engine.input_state.bind("a", Input.KEY_C);
        engine.input_state.bind("b", Input.KEY_X);
        engine.input_state.bind("c", Input.KEY_Z);

        //Initialize default GUI elements
        try
        {
            engine.ge_factory.bind(BoxElement.class);
            engine.ge_factory.bind(TextBoxElement.class);
            engine.ge_factory.bind(IconElement.class);
        }
        catch (RPGEException e)
        {
            e.printStackTrace();
        }

        //Create APIs
        eAPI = new EntityAPI();
        gAPI = new GUIAPI(engine.tile_width, engine.tile_height,
                gfx_img.getWidth(), gfx_img.getHeight());
        aAPI = new ActorAPI(gfx_img.getWidth(), gfx_img.getHeight());

        //Setup controllers
        scene_manager = new SceneManager(engine.entity_factory, engine.actor_factory);
        gui_controller = new GUIController(gAPI, engine.ge_factory);

        //Initialize Entity API
        eAPI.setScreenSize(engine.res_width, engine.res_height);
        eAPI.setTileDimensions(engine.tile_width, engine.tile_height);
        eAPI.setAssetManager(engine.assetManager());
        eAPI.setGFX(gfx);
        eAPI.setInputState(engine.input_state);
        eAPI.setWorldController(scene_manager);
        eAPI.setGUIController(gui_controller);

        //Initialize GUI API
        gAPI.setGFX(gui_gfx);
        gAPI.setAssetManager(engine.assetManager());
        gAPI.setInputState(engine.input_state);
        gAPI.setGUIController(gui_controller);
        eAPI.setGUIAPI(gAPI);

        //Initialize Actor API
        aAPI.setGFX(gfx);
        aAPI.setAssetManager(engine.assetManager());
        aAPI.setInputState(engine.input_state);
        aAPI.setGUIController(gui_controller);
        aAPI.setGUIAPI(gAPI);
        aAPI.setSceneManager(scene_manager);

        //Load all layouts
        gui_controller.load(engine.gui_layout_paths);

        //Load queued levels
        scene_manager.addWorlds(engine.world_scenes, eAPI.tile_width, eAPI.tile_height, eAPI);
        scene_manager.addActionScenes(engine.action_scenes, aAPI);
        scene_manager.load();

        //Initialize first scene in the list
        scene_manager.setCurrentWorld(0);
        scene_manager.switchToWorld();
        scene_manager.init();
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException
    {
        engine.input_state.update();
        scene_manager.step(delta);
        gui_controller.step();
    }

    public void render(GameContainer container, Graphics g) throws SlickException
    {
        scene_manager.draw(gfx);
        gui_controller.draw();

        gfx.setColor(Color.black);
        gui_gfx.setColor(Color.black);
        gfx.flush();
        gui_gfx.flush();
        gfx_img.draw(0, 0,engine.gfx_scale);
        gui_img.draw(0, 0,engine.gfx_scale);
    }
}
