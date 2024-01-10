package App.UI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import App.Model.World;
import App.Model.Entity.Ant.Anthill;
import App.Util.Setting;
import App.Util.WorldPaintMode;
import App.Util.Setting.SettingBuilder;

public class Controller {

    public final UserInterface userInterface;
    private final SettingWindow settingWindow;
    public World world;
    private final Thread worldThread;
    private boolean isRunning;
    private boolean isReset;
    

    public Controller(UserInterface ui, World world) {
        this.userInterface = ui;
        this.world = world;
        this.settingWindow = new SettingWindow(userInterface, SettingBuilder.createDefaultSetting()); 
        isRunning = false;
        isReset = false;

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
            userInterface.getStartButton().setText("Стоп");
        } else {
            isRunning = false;
            userInterface.getStartButton().setText("Старт");
        };
    }

    private void aboutDeveloperPressed(ActionEvent e) {
        JOptionPane.showMessageDialog(userInterface, "Информация о разработчике //TODO");
    }

    private void aboutAppPressed(ActionEvent e) {
        JOptionPane.showMessageDialog(userInterface, "Информация о приложении //TODO");
    }

    private void resetPressed(ActionEvent e) {
        isReset = true;
        isRunning = false;
        userInterface.getStartButton().setText("Старт");
    }

    
    private void settingPressed(ActionEvent e) {
        if (!settingWindow.isVisible()) {
            settingWindow.setVisible(true);
        } else {
            settingWindow.setVisible(false);
        }
    }

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
                userInterface.getWorldPanel().repaint();

                if (isRunning) {

                    final int choosedSpeed = (int) userInterface.getSpeedChooser().getValue();
                    final int delay = 1000 / (int) Math.pow(choosedSpeed, 3);
                    
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    userInterface.setAntQuant(world.getAntQuant());
                    userInterface.setAntWithFood(world.getAntWithFood());
                    userInterface.setAntWithoutFood(world.getAntWithoutFood());
                    userInterface.setAnthillFood(world.getAnthillFoodQuant());

                    Anthill anthill = (Anthill) world.getAnthill().values().toArray()[0];
                    userInterface.setFoodConsumption(anthill.getFoodConsumption());
                    world.update();
                }
            }
        }
    }

}
