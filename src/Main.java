import java.awt.Dimension;

import javax.swing.SwingUtilities;

import App.Model.World;
import App.UI.Controller;
import App.UI.UserInterface;
import App.Util.Directional;
import App.Util.Directionals;

public class Main {
    public static void main(String[] args) throws Exception {
        
        SwingUtilities.invokeLater( () -> {
            final UserInterface userInterface = 
                new UserInterface(new Dimension(570,520)); // 520

            final World world = new World(new Dimension(100,100));
                userInterface.setWorldForDisplay(world);
            
            final Controller controller = new Controller(userInterface, world);
        });
    }
}
