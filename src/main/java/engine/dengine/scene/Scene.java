package engine.dengine.scene;

import engine.dengine.ecs.Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Scene
{
    private List<Entity> entities;
    private boolean initialized;

    public Scene ()
    {
        initialized = false;
        entities = new ArrayList<>();
    }

    public void update (float deltaTime)
    {
        if (!initialized) init();

        for (Entity entity : entities)
            entity.update(deltaTime);
    }

    public void init ()
    {
        initialized = true;

        for (Entity entity : entities)
            entity.init();
    }

    public void addEntity (Entity entity)
    {
        if (!entities.contains(entity)) entities.add(entity);
    }

    public void removeEntity (Entity entity)
    {
        entities.remove(entity);
    }

    public List<Entity> getEntities ()
    {
        return Collections.unmodifiableList(entities);
    }

    public void dispose ()
    {
        for (Entity entity : entities)
            entity.dispose();
    }
}
