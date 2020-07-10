package Aug_15_SpaceStation.spaceStation;

import Aug_15_SpaceStation.spaceStation.core.Controller;
import Aug_15_SpaceStation.spaceStation.core.ControllerImpl;
import Aug_15_SpaceStation.spaceStation.core.Engine;
import Aug_15_SpaceStation.spaceStation.core.EngineImpl;

public class Main {
    public static void main(String[] args) {
        Controller controller = new ControllerImpl();
        Engine engine = new EngineImpl(controller);
        engine.run();
    }
}
