package Aug_15_SpaceStation.spaceStation.models.astronauts;

import Aug_15_SpaceStation.spaceStation.models.bags.Bag;

public interface Astronaut {
    String getName();

    double getOxygen();

    boolean canBreath();

    Bag getBag();

    void breath();
}
