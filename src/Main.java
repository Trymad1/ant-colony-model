import java.awt.Dimension;

import App.Model.World;
import App.UI.Controller;
import App.UI.UserInterface;

public class Main {
    public static void main(String[] args) throws Exception {
        final UserInterface userInterface = 
            new UserInterface(new Dimension(570,520));

        final World world = new World(new Dimension(100,100));
        userInterface.setWorldForDisplay(world);

        final Controller controller = new Controller(userInterface, world);
    }
}
