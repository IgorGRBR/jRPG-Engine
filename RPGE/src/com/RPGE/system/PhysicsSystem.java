package com.RPGE.system;

import com.RPGE.core.Entity;
import com.RPGE.core.EntityAPI;
import com.RPGE.world.ChunkGrid;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class PhysicsSystem
{
    public ArrayList<Entity> entities;
    private Queue<PhysicsEvent> events;
    private ChunkGrid chunk_grid;

    //Chunks!
    private int chunk_width, chunk_height;
    private int grid_width, grid_height;
    enum EventType
    {
        MOVE,
        JUMP
    }

    public PhysicsSystem(int cw, int ch, int gw, int gh)
    {
        entities = new ArrayList<>();
        events = new LinkedBlockingQueue<>();

        chunk_width = cw;
        chunk_height = ch;
        grid_width = gw;
        grid_height = gh;
    }

    public void setChunkGrid(ChunkGrid cg) { chunk_grid = cg; }

    public void register(Entity e)
    {
        entities.add(e);
        chunk_grid.addEntity(e);
    }


    public void remove(Entity e)
    {
        entities.remove(e);
        chunk_grid.removeEntity(e);
    }


    public void moveEvent(Entity e, int dx, int dy)
    {
        events.offer(new MoveEvent(e, dx, dy));
    }

    public void jumpEvent(Entity e, int x, int y)
    {
        events.offer(new JumpEvent(e, x, y));
    }

    public int getTile(int tx, int ty)
    {
        return chunk_grid.getTileData(tx, ty);
    }

    public void process(EntityAPI eAPI)
    {
        PhysicsEvent e;
        e = events.poll();
        while(e != null)
        {
            switch (e.type)
            {
            case MOVE:
                MoveEvent me = (MoveEvent)e;
                {
                    int ex = me.entity.getPosX(), ey = me.entity.getPosY();
                    int t_data = getTile(ex + me.delta_x, ey + me.delta_y);
                    if (t_data != 1) eAPI.translateDelta(me.entity, me.delta_x, me.delta_y);
                    if (t_data > 0)
                    {
                        me.entity.levelCollision(eAPI, t_data);
                    }
                }
                break;
            case JUMP:
                {
                    JumpEvent je = (JumpEvent)e;
                    int t_data = getTile(je.x, je.y);
                    if (t_data != 1) eAPI.translate(je.entity, je.x, je.y);
                    if (t_data > 0)
                    {
                        je.entity.levelCollision(eAPI, t_data);
                    }
                }
                break;
            default:
                break;
            }
            e = events.poll();
        }

        chunk_grid.updateEntities();

        for (Entity ent : entities)
        {
            int ex = ent.getPosX(), ey = ent.getPosY();
            int t_data = getTile(ex, ey);
            if (t_data > 0)
            {
                ent.levelCollision(eAPI, t_data);
            }
            int cx = ent.getPosX()/chunk_width, cy = ent.getPosY()/chunk_height;
        }
    }
}

abstract class PhysicsEvent
{
    PhysicsSystem.EventType type;
    PhysicsEvent() {}
}

class MoveEvent extends PhysicsEvent
{
    Entity entity;
    int delta_x, delta_y;
    MoveEvent(Entity e, int dx, int dy)
    {
        type = PhysicsSystem.EventType.MOVE;
        entity = e;
        delta_x = dx;
        delta_y = dy;
    }
}

class JumpEvent extends PhysicsEvent
{
    Entity entity;
    int x, y;
    JumpEvent(Entity e, int ix, int iy)
    {
        type = PhysicsSystem.EventType.JUMP;
        entity = e;
        x = ix;
        y = iy;
    }
}