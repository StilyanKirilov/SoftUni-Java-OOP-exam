package spaceStation.core;

import spaceStation.common.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class EngineImpl implements Engine {
    private Controller controller;
    private BufferedReader reader;

    public EngineImpl(Controller controller) {
        this.controller = controller;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        while (true) {
            String result = null;
            try {
                result = processInput();

                if (result.equals(Command.Exit.name())) {
                    break;
                }
            } catch (NullPointerException | IllegalArgumentException | IOException e) {
                result = e.getMessage();
            }

            System.out.println(result);
        }
    }

    private String processInput() throws IOException {
        String input = this.reader.readLine();
        String[] tokens = input.split("\\s+");

        Command command = Command.valueOf(tokens[0]);
        String result = null;
        String[] data = Arrays.stream(tokens).skip(1).toArray(String[]::new);

        switch (command) {
            case AddAstronaut:
                result = this.controller.addAstronaut(data[0], data[1]);
                break;
            case AddPlanet:
                String[] items = Arrays.stream(data).skip(1).toArray(String[]::new);
                result = this.controller.addPlanet(data[0], items);
                break;
            case RetireAstronaut:
                result = this.controller.retireAstronaut(data[0]);
                break;
            case ExplorePlanet:
                result = this.controller.explorePlanet(data[0]);
                break;
            case Report:
                result = controller.report();
                break;
            case Exit:
                result = Command.Exit.name();
                break;
        }

        return result;
    }
}
