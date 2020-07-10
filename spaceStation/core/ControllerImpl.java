package Aug_15_SpaceStation.spaceStation.core;

import Aug_15_SpaceStation.spaceStation.models.astronauts.Astronaut;
import Aug_15_SpaceStation.spaceStation.models.astronauts.Biologist;
import Aug_15_SpaceStation.spaceStation.models.astronauts.Geodesist;
import Aug_15_SpaceStation.spaceStation.models.astronauts.Meteorologist;
import Aug_15_SpaceStation.spaceStation.models.mission.Mission;
import Aug_15_SpaceStation.spaceStation.models.mission.MissionImpl;
import Aug_15_SpaceStation.spaceStation.models.planets.Planet;
import Aug_15_SpaceStation.spaceStation.models.planets.PlanetImpl;
import Aug_15_SpaceStation.spaceStation.repositories.AstronautRepository;
import Aug_15_SpaceStation.spaceStation.repositories.PlanetRepository;
import Aug_15_SpaceStation.spaceStation.repositories.Repository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static Aug_15_SpaceStation.spaceStation.common.ConstantMessages.*;
import static Aug_15_SpaceStation.spaceStation.common.ExceptionMessages.*;

public class ControllerImpl implements Controller {
    private Repository<Astronaut> astronautRepository;
    private Repository<Planet> planetRepository;
    private Mission mission;
    private int planetsExplored;

    public ControllerImpl() {
        this.astronautRepository = new AstronautRepository();
        this.planetRepository = new PlanetRepository();
        this.mission = new MissionImpl();
    }


    @Override
    public String addAstronaut(String type, String astronautName) {
        Astronaut astronaut;
        switch (type) {
            case "Geodesist":
                astronaut = new Geodesist(astronautName);
                break;
            case "Biologist":
                astronaut = new Biologist(astronautName);
                break;
            case "Meteorologist":
                astronaut = new Meteorologist(astronautName);
                break;
            default:
                throw new IllegalArgumentException(ASTRONAUT_INVALID_TYPE);
        }

        this.astronautRepository.add(astronaut);

        return String.format(ASTRONAUT_ADDED, type, astronautName);
    }

    @Override
    public String addPlanet(String planetName, String... items) {
        Planet planet = new PlanetImpl(planetName, items);
        this.planetRepository.add(planet);
        return String.format(PLANET_ADDED, planetName);
    }

    @Override
    public String retireAstronaut(String astronautName) {
        Astronaut astronaut = this.astronautRepository.findByName(astronautName);
        if (astronaut != null) {
            this.astronautRepository.remove(astronaut);
            return String.format(ASTRONAUT_RETIRED, astronautName);
        }
        throw new IllegalArgumentException(String.format(ASTRONAUT_DOES_NOT_EXIST, astronautName));
    }

    @Override
    public String explorePlanet(String planetName) {
        Planet planet = this.planetRepository.findByName(planetName);
        List<Astronaut> models = this.astronautRepository.getModels()
                .stream().filter(a -> a.getOxygen() > 60)
                .collect(Collectors.toList());

        if (models.size() > 0) {
            long deadAstr = getAstronautsWithZeroOxygen(models);
            this.mission.explore(planet, models);

            this.planetsExplored++;

            return String.format(PLANET_EXPLORED, planetName, getAstronautsWithZeroOxygen(models) - deadAstr);

        } else {
            throw new IllegalArgumentException(PLANET_ASTRONAUTS_DOES_NOT_EXISTS);
        }
    }

    private long getAstronautsWithZeroOxygen(Collection<Astronaut> models) {
        return models.stream().filter(a -> !a.canBreath()).count();
    }

    @Override
    public String report() {
        StringBuilder builder = new StringBuilder();

        builder.append(String.format(REPORT_PLANET_EXPLORED, this.planetsExplored)).append(System.lineSeparator());
        if (this.astronautRepository.getModels().size() > 0) {
            builder.append(REPORT_ASTRONAUT_INFO).append(System.lineSeparator());
            for (Astronaut model : this.astronautRepository.getModels()) {
                builder.append(String.format(REPORT_ASTRONAUT_NAME, model.getName()))
                        .append(System.lineSeparator())
                        .append(String.format(REPORT_ASTRONAUT_OXYGEN, model.getOxygen()))
                        .append(System.lineSeparator());
                Collection<String> bagItems = model.getBag().getItems();
                if (bagItems.size() > 0) {
                    String joinedCollection = String.join(REPORT_ASTRONAUT_BAG_ITEMS_DELIMITER, model.getBag().getItems());
                    builder.append(String.format(REPORT_ASTRONAUT_BAG_ITEMS, joinedCollection)).append(System.lineSeparator());
                } else {
                    builder.append(String.format(REPORT_ASTRONAUT_BAG_ITEMS, "none")).append(System.lineSeparator());
                }
            }
        }

        return builder.toString().trim();
    }
}
