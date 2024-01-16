

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import Model.World;
import Model.Entity.Entity;
import Model.Entity.Ant.Anthill;
import Model.Setting.SettingBuilder;
import UI.SettingWindow;
import UI.UserInterface;
import Util.Enum.WorldPaintMode;

/**
 * Обработка нажатий в UI, управление вторым потоком, делегирующий процесс моделирование
 */
public class Controller {

    public final UserInterface userInterface;
    private final SettingWindow settingWindow;

    private final Thread worldThread;

    private World world;
    private boolean isRunning;
    private boolean isReset;
    

    public Controller(UserInterface ui, World world) {
        this.userInterface = ui;
        this.world = world;
        this.settingWindow = new SettingWindow(userInterface, SettingBuilder.createDefaultSetting()); 
        isRunning = false;
        isReset = false;

        // Присваивание всем кнопкам новый ActionListener, перенаправляющий нажатие на соответствующие методы
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
        JOptionPane.showMessageDialog(userInterface, "Разработал: Кузнецов Руслан Сергеевич\r\n" + //
                "Группа: МКН-22-1");
    }

    private void aboutAppPressed(ActionEvent e) {
        JOptionPane.showMessageDialog(userInterface, "Программа представляет из себя модель муравьиной колонии, в которой муравьи находят пищу сообща, ориентируясь на феромон, \r\n" + //
                "оставляемый другими муравьями. Реализовано 2 вида феромона: Феромон поиска муравейника, и феромон поиска еды\r\n" + //
                "\r\n" + //
                "Муравьи которые находятся в состоянии поиска еды оставляют за собой феромон поиска муравейника, по которому\r\n" + 
                "муравьи, уже нашедшие пищу, могут вернутся в колонию. Муравьи без еды игнорируют данный феромон.\r\n" + 
                "Муравьи, которые уже нашли еду, оставляют феромон поиска пищи, по которому следуют муравьи без еды,\r\n" + 
                "так же начинают искать кратчайший путь к колонии, ориентируясь на феромон, оставляемый муравьями без еды.\r\n" +
                "Аналогично игнорируют феромон, который оставляют сами.\r\n" + 
                "Если в поле зрения муравья нет феромона, начинают двигатся в случайном направлении.\r\n" + 
                "Муравьи, которые долгое время не могут найти пищу или путь домой, перестают оставлять феромон до тех пор, пока не найдут свою цель.\r\n" + 
                "\r\n" + //
                "Модель можно запустить и поставить на паузу нажатием кнопки. Возможно изменить скорость от 1 до 10,\r\n" + 
                "а так же изменить режим отрисовки модели, выбрав необходимой вариант в выпадающем списке.\r\n" + 
                "Основные параметры модели можно изменить в меню Управление -> Настройки.");
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

    // Новый поток, отвечающий за обновление информации о модели и инициаицей итераций в мире
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
                    // выбрасывается исключение во время инициализации новой модели
                    // не влияет на работоспособность программы, вероятная причина проблемы
                    // что нажатие на кнопку старт вызывает метод createRandomWorld
                    // и данный поток пытается получить доступ к списку сущностей
                    // одновременон с тем, как другой поток его заполняет. В данноом случае
                    // проблемма решается добавлением синхронизационных блоков.
                }

                userInterface.getWorldPanel().repaint(entityListForDisplay);

                if (isRunning) {

                    final int choosedSpeed = (int) userInterface.getSpeedChooser().getValue();
                    final int delay = 1000 / (int) Math.pow(choosedSpeed, 3);
                    
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                    // Обновление данных о мире и запуск следующей итерации
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
