package com.RPGE.core;

import java.util.*;

import com.RPGE.asset.AssetManager;
import com.RPGE.asset.TilesetAsset;
import com.RPGE.file.RPGEFile;
import com.RPGE.system.DrawSystem;
import com.RPGE.system.PhysicsSystem;
import com.RPGE.system.StepSystem;
import com.RPGE.world.*;
import org.newdawn.slick.Graphics;

public class WorldScene extends AbstractScene
{
    //Variables
    private String world_name;
    private ArrayList<Entity> entities;
    private Tilemap tilemap;
    private int tile_width, tile_height;
    private ChunkGrid chunk_grid;
    private EntityAPI eAPI;
    private Camera camera;

    //Systems
    private StepSystem step_system;
    private DrawSystem draw_system;
    private PhysicsSystem phys_system;

    //Methods
    public WorldScene(String n, String p, int tw, int th, EntityAPI ea)
    {
        name = n;
        tile_width = tw;
        tile_height = th;
        entities = new ArrayList<>();
        path = p;
        loaded = false;
        world_name = "";
        eAPI = ea;
    }

    @Override
    public void load(AssetManager asset_manager, EntityFactory entity_factory)
    {
        if (loaded) return;
        this.path = path;
        RPGEFile file = new RPGEFile(path, true);

        //Temp state variables
        int chunk_width = 0, chunk_height = 0;
        int chunk_x = 0, chunk_y = 0;

        //Tilesets
        TilesetAsset current_tileset = null;
        TilemapChunk current_tchunk = null;

        //Chunks
        ArrayList<Chunk> temp_chunks = new ArrayList<>();
        ArrayList<int[]> temp_chunks_pos = new ArrayList<>();
        int[] global_dimensions = new int[2];
        HashMap<Integer, ArrayList<TilemapChunk>> chunk_map = new HashMap<>();

        //Layers
        int current_layer_index = 0;
        HashMap<Integer, Integer[]> layer_dimensions = new HashMap<>();
        HashMap<Integer, TilesetAsset> layer_tilesets = new HashMap<>();
        HashMap<Integer, ArrayList<Entity>> layer_entities = new HashMap<>();

        ArrayList<String> line = file.getLine();
        while(line != null)
        {
            if (line.get(0).charAt(0) != '#' || line.get(0).length() > 0)
            switch (line.get(0))
            {
                case "name":
                    world_name = line.get(1);
                    for (int i = 2; i < line.size(); i++)
                    {
                        world_name += " " + line.get(i);
                    }
                    break;
                case "tileset":
                    current_tileset = asset_manager.getTileset(line.get(1));
                    break;
                case "chunksize":
                    chunk_width = Integer.parseInt(line.get(1));
                    chunk_height = Integer.parseInt(line.get(2));
                    break;
                case "layer":
                    current_layer_index = Integer.parseInt(line.get(1));
                    chunk_map.putIfAbsent(current_layer_index,
                            new ArrayList<TilemapChunk>());
                    layer_dimensions.putIfAbsent(current_layer_index,
                             new Integer[]{-1, -1, 0, 0});
                    layer_tilesets.put(current_layer_index, current_tileset);
                    layer_entities.putIfAbsent(current_layer_index, new ArrayList<>());
                    break;
                case "chunk":
                    chunk_x = Integer.parseInt(line.get(1));
                    chunk_y = Integer.parseInt(line.get(2));

                    //Update min/max
                    Integer[] dimensions = layer_dimensions.get(current_layer_index);
                    if (dimensions[0] == -1 && dimensions[1] == -1)
                    {
                        dimensions[0] = chunk_x;
                        dimensions[1] = chunk_y;
                    }
                    dimensions[0] = Math.min(dimensions[0], chunk_x);
                    dimensions[1] = Math.min(dimensions[1], chunk_y);
                    dimensions[2] = Math.max(dimensions[2], chunk_x);
                    dimensions[3] = Math.max(dimensions[3], chunk_y);
                    layer_dimensions.put(current_layer_index, dimensions);

                    //Update global max
                    global_dimensions[0] = Math.max(global_dimensions[0], chunk_x);
                    global_dimensions[1] = Math.max(global_dimensions[1], chunk_y);
                    break;
                case "tile_data":
                    {
                        current_tchunk = new TilemapChunk(chunk_width, chunk_height);
                        current_tchunk.setPos(chunk_x, chunk_y);
                        chunk_map.get(current_layer_index).add(current_tchunk);
                        int[][] temp_data = new int[chunk_width][chunk_height];
                        for (int j = 0; j < chunk_height; j++)
                        {
                            ArrayList<String> subline = file.getLine();
                            for (int i = 0; i < chunk_width; i++)
                            {
                                temp_data[i][j] = Integer.parseInt(subline.get(i));
                            }
                        }
                        current_tchunk.setData(temp_data);
                    }
                    break;
                case "chunk_data":
                    {
                        int[][] temp_data = new int[chunk_width][chunk_height];
                        for (int j = 0; j < chunk_height; j++)
                        {
                            ArrayList<String> subline = file.getLine();
                            for (int i = 0; i < chunk_width; i++)
                            {
                                temp_data[i][j] = Integer.parseInt(subline.get(i));
                            }
                        }
                        Chunk temp_chunk = new Chunk(chunk_width, chunk_height);
                        temp_chunk.setData(temp_data);
                        temp_chunks.add(temp_chunk);
                        temp_chunks_pos.add(new int[]{chunk_x, chunk_y});
                    }
                    break;
                case "entity":
                    Entity e = entity_factory.buildEntity(line.get(1));
                    e.setPosX(Integer.parseInt(line.get(2)), tile_width);
                    e.setPosY(Integer.parseInt(line.get(3)), tile_height);
                    e.worldLoad(eAPI, line.subList(4, line.size()));
                    layer_entities.get(current_layer_index).add(e);
                    entities.add(e);
                    break;
                default:
                    System.out.println("World loading error: unknown token '"
                    + line.get(0) + "'!");
                    break;
            }
            line = file.getLine();
        }

        //After all strings were parsed, time to process them
        HashMap<Integer, TilemapLayer> layer_map = new HashMap<>();
        List<Map.Entry<Integer, ArrayList<TilemapChunk>>> entries =
                new ArrayList<>(chunk_map.entrySet());

        //First, we need to sort them
        Collections.sort(entries,
                new Comparator<Map.Entry<Integer, ArrayList<TilemapChunk>>>()
                {
                    @Override
                    public int compare(Map.Entry<Integer, ArrayList<TilemapChunk>> o1,
                                       Map.Entry<Integer, ArrayList<TilemapChunk>> o2)
                    {
                        return Integer.compare(o1.getKey(), o2.getKey());
                    }
                });
        int index = 0;
        for (Map.Entry<Integer, ArrayList<TilemapChunk>> i : entries)
        {
            Integer[] dimensions = layer_dimensions.get(i.getKey());
            TilemapLayer tl = new TilemapLayer(
                    dimensions[2] - dimensions[0] + 1,
                    dimensions[3] - dimensions[1] + 1,
                    tile_width, tile_height,
                    chunk_width, chunk_height,
                    layer_tilesets.get(i.getKey())
            );
            tl.offsetX(dimensions[0]);
            tl.offsetY(dimensions[1]);
            tl.init();
            for (TilemapChunk tc : i.getValue())
            {
                tl.set(tc.pos_x, tc.pos_y, tc);
            }
            layer_map.put(i.getKey(), tl);

            //Also, add entities to their draw layers
            for (Entity e : layer_entities.get(i.getKey()))
            {
                e.layer = index;
            }

            index++;
        }


        tilemap = new Tilemap(tile_width, tile_height, chunk_width, chunk_height);
        tilemap.setLayers(layer_map);

        chunk_grid = new ChunkGrid(chunk_width, chunk_height,
                global_dimensions[0] + 1, global_dimensions[1] + 1);
        chunk_grid.setChunks(temp_chunks, temp_chunks_pos);


        //Init systems
        draw_system = new DrawSystem(entries.size());
        step_system = new StepSystem();
        phys_system = new PhysicsSystem(chunk_width, chunk_height,
                global_dimensions[0], global_dimensions[1]);
        //Setup systems
        phys_system.setChunkGrid(chunk_grid);
        for (Entity e : entities)
        {
            draw_system.register(e);
            step_system.register(e);
            phys_system.register(e);
        }

        //Setup camera
        camera = new Camera(
                tile_width, tile_height,
                eAPI.screen_tile_count_h, eAPI.screen_tile_count_v,
                (global_dimensions[0]+1) * chunk_width, (global_dimensions[1]+1) * chunk_height
        );

        //DONE(?)
        loaded = true;
    }

    public boolean isLoaded() { return loaded; }

    @Override
    public void init()
    {
        tilemap.compileLayers();
        eAPI.setCamera(camera);
        eAPI.setPhysicsSystem(phys_system);
        for (Entity e : entities)
        {
            e.init(eAPI);
        }
    }

    @Override
    public void step(float delta)
    {
        step_system.process(eAPI);
        phys_system.process(eAPI);
        camera.update();
    }

    @Override
    public void draw(Graphics gfx)
    {
        gfx.translate(-camera.getCornerX(), -camera.getCornerY());
        for (int i = 0; i < draw_system.getLayerCount(); i++)
        {
            tilemap.drawLayer(gfx, i, camera);
            draw_system.draw(eAPI, i);
        }
    }

    public String getName() { return world_name; }
}
