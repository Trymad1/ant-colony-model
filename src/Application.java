import java.awt.Dimension;

import Model.World;
import UI.UserInterface;

public class Application {

    private static final int USER_INTERFACE_WIDTH = 570;
    private static final int USER_INTERFACE_HEIGHT = 520;

    private static final int WORLD_WIDTH = 100;
    private static final int WORLD_HEIGHT = 100;

    public static void main(String[] args) throws Exception {
        
        final UserInterface userInterface = 
            new UserInterface(new Dimension(USER_INTERFACE_WIDTH,USER_INTERFACE_HEIGHT)); 

        final World world = new World(new Dimension(WORLD_WIDTH,WORLD_HEIGHT));
        userInterface.setWorldForDisplay(world);
            
        new Controller(userInterface, world);
    }
}





