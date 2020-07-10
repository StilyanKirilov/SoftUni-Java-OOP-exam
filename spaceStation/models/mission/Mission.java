package Aug_15_SpaceStation.spaceStation.models.mission;

import Aug_15_SpaceStation.spaceStation.models.astronauts.Astronaut;
import Aug_15_SpaceStation.spaceStation.models.planets.Planet;

import java.util.Collection;

public interface Mission {
    void explore(Planet planet, Collection<Astronaut> astronauts);
}
