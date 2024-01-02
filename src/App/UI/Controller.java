package App.UI;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import App.Model.World;
import App.Util.WorldPaintMode;

public class Controller {

    public final UserInterface userInterface;
    public final World world;
    private final Thread worldThread;
    public boolean isRunning;

    public Controller(UserInterface ui, World world) {
        this.userInterface = ui;
        this.world = world; 
        isRunning = false;

        userInterface.getStartButton().addActionListener(this::startButtonPressed);
        userInterface.getDeveloperInfo().addActionListener(this::aboutDeveloperPressed);
        userInterface.getAppInfo().addActionListener(this::aboutAppPressed);

        worldThread = new Thread(new Controller.WorldThread());
        worldThread.setDaemon(true);
        worldThread.start();
    }
    
    private synchronized void startButtonPressed(ActionEvent e) {
        if(!world.isCreated()) world.createRandomWorld();

        if(!isRunning) {    
            isRunning = true;
        } else {
            isRunning = false;
        };
    }

    private void aboutDeveloperPressed(ActionEvent e) {
        JOptionPane.showMessageDialog(userInterface, "Информация о разработчике //TODO");
    }

    private void aboutAppPressed(ActionEvent e) {
        JOptionPane.showMessageDialog(userInterface, "Информация о приложении //TODO");
    }

    private class WorldThread implements Runnable {

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                final WorldPaintMode selectedPaintMode = 
                (WorldPaintMode) userInterface.getPaintModeComboBox().getSelectedItem();
                userInterface.getWorldPanel().setWorldPaintMode(selectedPaintMode);
                userInterface.getWorldPanel().repaint();

                if (isRunning) {

                    final int choosedSpeed = (int) userInterface.getSpeedChooser().getValue();
                    final int delay = 1000 / (int) Math.pow(choosedSpeed, 3);
                    
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    world.update();
                }
            }
        }
    }

}
