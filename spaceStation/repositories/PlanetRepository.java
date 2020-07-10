package Aug_15_SpaceStation.spaceStation.repositories;

import Aug_15_SpaceStation.spaceStation.models.planets.Planet;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class PlanetRepository implements Repository<Planet> {
    private Map<String, Planet> planets;

    public PlanetRepository() {
        this.planets = new LinkedHashMap<>();
    }

    @Override
    public Collection<Planet> getModels() {
        return null;
    }

    @Override
    public void add(Planet model) {
        if (!planets.containsKey(model.getName())) {
            this.planets.put(model.getName(), model);
        }
    }

    @Override
    public boolean remove(Planet model) {
        return this.planets.remove(model.getName(), model);
    }

    @Override
    public Planet findByName(String name) {
        return this.planets.get(name);
    }
}
