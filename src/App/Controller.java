package App;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import App.Model.World;
import App.Model.Entity.Entity;
import App.Model.Entity.Ant.Anthill;
import App.Model.Setting.SettingBuilder;
import App.UI.InfoDialogMessage;
import App.UI.SettingWindow;
import App.UI.UserInterface;
import App.Util.Enum.WorldPaintMode;

/**
*Handling clicks in the UI, managing a second thread, delegating the modeling process
*/
public class Controller {

    public final UserInterface userInterface;
    private final SettingWindow settingWindow;

    private final Thread worldThread;

    private final World world;
    private boolean isRunning;
    private boolean isReset;
    

    public Controller(UserInterface ui, World world) {
        this.userInterface = ui;
        this.world = world;
        this.settingWindow = new SettingWindow(userInterface, SettingBuilder.createDefaultSetting()); 
        isRunning = false;
        isReset = false;

        //Assigning a new ActionListener to all buttons, redirecting clicks to the appropriate methods
        userInterface.getStartButton().addActionListener(this::startButtonPressed);
        userInterface.getDeveloperInfo().addActionListener(this::aboutDeveloperPressed);
        userInterface.getReset().addActionListener(this::resetPressed);
        userInterface.getAppInfo().addActionListener(this::aboutAppPressed);
        userInterface.getSetting().addActionListener(this::settingPressed);

        worldThread = new Thread(new Controller.WorldThread());
        worldThread.start();

    }
    
    private void startButtonPressed(ActionEvent e) {
        if(!world.isCreated()) {
            world.setSetting(settingWindow.getSetting());
            world.createRandomWorld();
        }

        if(!isRunning) {    
            isRunning = true;
            userInterface.getStartButton().setText("Stop");
        } else {
            isRunning = false;
            userInterface.getStartButton().setText("Start");
        }
    }

    private void aboutDeveloperPressed(ActionEvent e) {
        JOptionPane.showMessageDialog(userInterface, "Developed by: Kuznetsov Ruslan Sergeevich\r\n" + //
                "Group: MKN-22-1");
    }

    private void aboutAppPressed(ActionEvent e) {
        new InfoDialogMessage(userInterface, "Information", true);
    }

    private void resetPressed(ActionEvent e) {
        isReset = true;
        isRunning = false;
        userInterface.getStartButton().setText("Start");
    }

    
    private void settingPressed(ActionEvent e) {
        settingWindow.setVisible(!settingWindow.isVisible());
    }

    // A new thread responsible for updating model information and initiating iterations in the world
    private class WorldThread implements Runnable {

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                if (isReset) {
                    world.clearWorld();
                    isReset = false;
                    userInterface.resetInfoLabels();
                    continue;
                }
                final WorldPaintMode selectedPaintMode = 
                (WorldPaintMode) userInterface.getPaintModeComboBox().getSelectedItem();
                userInterface.getWorldPanel().setWorldPaintMode(selectedPaintMode);

                Map<Point, Entity> entityListForDisplay = new HashMap<>();

                try {
                    entityListForDisplay = 
                        userInterface.getWorldPanel().getWorldPaintUtil().getEntityListForDisplay(world);
                } catch (ConcurrentModificationException ignoredBug) {
                    //an exception is thrown during initialization of a new model
                    //does not affect the performance of the program, the probable cause of the problem
                    //that pressing the start button calls the createRandomWorld method
                    //and this thread is trying to access the list of entities
                    //at the same time as another thread fills it. In this case
                    //the problem is solved by adding synchronization blocks.
                }

                userInterface.getWorldPanel().repaint(entityListForDisplay);

                if (isRunning) {

                    final int selectedSpeed = (int) userInterface.getSpeedChooser().getValue();
                    final int delay = 1000 / (int) Math.pow(selectedSpeed, 3);
                    
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                    //Update world data and launch next iteration
                    userInterface.setAntQuant(world.getAntQuant());
                    userInterface.setAntWithFood(world.getAntWithFood());
                    userInterface.setAntWithoutFood(world.getAntWithoutFood());
                    userInterface.setAnthillFood(world.getAnthillFoodQuant());
                    userInterface.setDiedAnts(world.getDiedAnt());

                    Anthill anthill = (Anthill) world.getAnthill().values().toArray()[0];
                    userInterface.setFoodConsumption(anthill.getFoodConsumption());
                    world.update();
                }
            }
        }
    }
}
