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

        userInterface.getStartButton().addActionListener(e -> {
            try {
                startButtonPressed(e);
            } catch (InterruptedException ee) {
                
            }
        });
        userInterface.getDeveloperInfo().addActionListener(this::aboutDeveloperPressed);
        userInterface.getAppInfo().addActionListener(this::aboutAppPressed);
    }

    private void startButtonPressed(ActionEvent e) throws InterruptedException {
        world.createRandomWorld();
        Runnable task = () -> {
            while (true) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException exc) {
                exc.printStackTrace();
            }
            world.update();
            userInterface.getWorldPanel().repaint(); 
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    private void aboutDeveloperPressed(ActionEvent e) {
        JOptionPane.showMessageDialog(userInterface, "Информация о разработчике //TODO");
    }

    private void aboutAppPressed(ActionEvent e) {
        JOptionPane.showMessageDialog(userInterface, "Информация о приложении //TODO");
    }


}
