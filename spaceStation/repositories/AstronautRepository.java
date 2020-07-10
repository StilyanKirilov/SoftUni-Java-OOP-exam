package Aug_15_SpaceStation.spaceStation.repositories;

import Aug_15_SpaceStation.spaceStation.models.astronauts.Astronaut;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class AstronautRepository implements Repository<Astronaut> {
    Map<String, Astronaut> astronauts;

    public AstronautRepository() {
        this.astronauts = new LinkedHashMap<>();
    }


    @Override
    public Collection<Astronaut> getModels() {

        return this.astronauts.values();

    }

    @Override
    public void add(Astronaut model) {
        if (!this.astronauts.containsKey(model.getName())) {
            this.astronauts.put(model.getName(), model);
        }
    }

    @Override
    public boolean remove(Astronaut model) {
        return this.astronauts.remove(model.getName(), model);
    }

    @Override
    public Astronaut findByName(String name) {
        return this.astronauts.get(name);
    }
}
