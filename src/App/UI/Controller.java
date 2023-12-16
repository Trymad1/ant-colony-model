package App.UI;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import App.Model.World;

public class Controller {
    public final UserInterface userInterface;
    public final World world;

    public Controller(UserInterface ui, World world) {
        this.userInterface = ui;
        this.world = world;

        userInterface.getStartButton().addActionListener(this::startButtonPressed);
        userInterface.getDeveloperInfo().addActionListener(this::aboutDeveloperPressed);
        userInterface.getAppInfo().addActionListener(this::aboutAppPressed);
    }

    private void startButtonPressed(ActionEvent e) {
        world.createRandomWorld();
        userInterface.getWorldPanel().repaint();
    }

    private void aboutDeveloperPressed(ActionEvent e) {
        JOptionPane.showMessageDialog(userInterface, "Информация о разработчике //TODO");
    }

    private void aboutAppPressed(ActionEvent e) {
        JOptionPane.showMessageDialog(userInterface, "Информация о приложении //TODO");
    }


}
