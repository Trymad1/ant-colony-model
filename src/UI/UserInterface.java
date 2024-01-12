package UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import Model.World;
import Util.Enum.WorldPaintMode;
import Util.Static.EntityParams;

/**
 * Содержит исключительно UI пользователя
 */
public class UserInterface extends JFrame {

    private final WorldPanel worldPanel;

    private final JPanel mainPanel, controllPanel, infoWorldPanel, viewWorldPanel,
                         antInfoPanel, colonyInfoPanel, emptyPanel;

    private final JLabel colonyLabel, speedLabel, paintModeLabel;

    private final InfoLabel<Integer> antQuantityInfo, antWithoutFood, antWithFood;

    private final InfoLabel<Float> colonyFoodInfo, foodConsumption;

    private final JButton startButton;

    private final JSpinner speedChooser;
    
    private final JComboBox<WorldPaintMode> paintModeComboBox;

    private final JMenuBar menuBar;

    private final JMenu info, controll;

    private final JMenuItem developerInfo, appInfo, exit, setting, reset;
    

    public UserInterface(Dimension size) {

        setSize(size);
        setLayout(new FlowLayout(FlowLayout.CENTER));
        this.mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel);
        setIconImage(new ImageIcon(getClass().getResource("ant.png")).getImage());
        setTitle("Моделирование жизни в муравейнике");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        worldPanel = new WorldPanel();
        controllPanel = new JPanel();
        infoWorldPanel = new JPanel();
        viewWorldPanel = new JPanel();
        antInfoPanel = new JPanel();
        colonyInfoPanel = new JPanel();
        emptyPanel = new JPanel();

        colonyLabel = new JLabel("Муравейник", JLabel.CENTER);
        speedLabel = new JLabel("Скорость:");
        paintModeLabel = new JLabel("Режим:");

        startButton = new JButton("Старт");
        
        speedChooser = new JSpinner(
            new SpinnerNumberModel(1,1,10,1)); 

        antQuantityInfo = new InfoLabel<Integer>("Всего муравьев:", 0);
        antWithoutFood = new InfoLabel<Integer>("Без еды:", 0, EntityParams.Colors.ANT_COLLECTOR);
        antWithFood = new InfoLabel<Integer>("С едой", 0, EntityParams.Colors.FOOD);
        // antSoldierInfo = new InfoLabel<Integer>("Воинов:", 0, EntityParams.Colors.ANT_SOLDIER);
        // antScoutInfo = new InfoLabel<Integer>("Разведчиков:", 0, EntityParams.Colors.ANT_SCOUT);
        colonyFoodInfo = new InfoLabel<Float>("Еда:", 0.0f);
        foodConsumption = new InfoLabel<Float>("Потребление: ", 0f);

        paintModeComboBox = new JComboBox<>();
        paintModeComboBox.addItem(WorldPaintMode.ALL_ENTITIES);
        paintModeComboBox.addItem(WorldPaintMode.ONLY_CREATURE);
        paintModeComboBox.addItem(WorldPaintMode.ONLY_OBJECTS);
        paintModeComboBox.addItem(WorldPaintMode.PHEROMONE_TO_ANTHILL);
        paintModeComboBox.addItem(WorldPaintMode.PHEROMONE_TO_TARGET);

        menuBar = new JMenuBar();

        info = new JMenu("Справка");
        controll = new JMenu("Управление");
    
        developerInfo = new JMenuItem("Разработчик");
        appInfo = new JMenuItem("О приложении");

        reset = new JMenuItem("Сбросить");
        exit = new JMenuItem("Выход");
        setting = new JMenuItem("Настройки");
        
        infoWorldPanel.setLayout(new BoxLayout(infoWorldPanel, BoxLayout.Y_AXIS));
        antInfoPanel.setLayout(new BoxLayout(antInfoPanel, BoxLayout.Y_AXIS));
        controllPanel.setLayout(new FlowLayout());
        viewWorldPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        calculateAndSetComponentsPrefferedSize(size);
        addComponentsInFrame(this);
        
        worldPanel.setBackground(Color.WHITE);
        worldPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        setLocationRelativeTo(null);
        setResizable(false);
        pack();
        setVisible(true);
    }
    
    private void calculateAndSetComponentsPrefferedSize(final Dimension frameSize) {
        final Dimension viewWorldPanelDimension = 
            new Dimension((int) (frameSize.width / 1.15), 
                          (int) (frameSize.height / 1.6));

        final Dimension worldPanelDimension = 
            new Dimension(310,310);

        final Dimension infoWorldPanelDimension = 
            new Dimension((int) (viewWorldPanelDimension.width / 2.9), 
                          (int) (viewWorldPanelDimension.height / 1.05));
        
        final Dimension antInfoPanelDimension = 
            new Dimension((int) (infoWorldPanelDimension.width), 
                          (int) (infoWorldPanelDimension.height / 1.9));

        final Dimension antLabelDimension = 
            new Dimension((int) (antInfoPanelDimension.width), 
                          (int) (antInfoPanelDimension.height / 6));

        final Dimension emptyPanelDimension = 
            new Dimension(antLabelDimension.width, antLabelDimension.height / 2);

        final Dimension colonyPanelDimension = 
            new Dimension((int) (infoWorldPanelDimension.width), 
                          (int) (infoWorldPanelDimension.height / 2.5));
      
        final Dimension controllPanelDimension = 
            new Dimension(viewWorldPanelDimension.width, 
                        (int) (frameSize.height / 17)); 

        final Dimension startButtonDimension = 
            new Dimension((int) (controllPanelDimension.width / 4), 
                          (int) (controllPanelDimension.height / 1.3));
                          
        final Dimension speedChooserDimension = 
            new Dimension((int) (controllPanelDimension.width / 10), 
                          (int) (controllPanelDimension.height / 1.3));

        final Dimension paintModeDimension = 
            new Dimension( (int) (controllPanelDimension.width / 3), 
                           (int) (controllPanelDimension.height / 1.3));

        worldPanel.setPreferredSize(worldPanelDimension);
        viewWorldPanel.setPreferredSize(viewWorldPanelDimension);
        controllPanel.setPreferredSize(controllPanelDimension);
        startButton.setPreferredSize(startButtonDimension);
        speedChooser.setPreferredSize(speedChooserDimension);
        paintModeComboBox.setPreferredSize(paintModeDimension);
        infoWorldPanel.setPreferredSize(infoWorldPanelDimension);
        infoWorldPanel.setPreferredSize(infoWorldPanelDimension);
        antInfoPanel.setPreferredSize(antInfoPanelDimension);
        antInfoPanel.setMaximumSize(antInfoPanelDimension);
        antInfoPanel.setMinimumSize(antInfoPanelDimension);
        antQuantityInfo.setPreferredSize(antLabelDimension);
        antQuantityInfo.setMaximumSize(antLabelDimension);
        antWithoutFood.setPreferredSize(antLabelDimension);
        antWithoutFood.setMaximumSize(antLabelDimension);
        antWithFood.setPreferredSize(antLabelDimension);
        antWithFood.setMaximumSize(antLabelDimension);
        foodConsumption.setPreferredSize(antLabelDimension);
        foodConsumption.setMaximumSize(antLabelDimension);
        emptyPanel.setPreferredSize(emptyPanelDimension);
        emptyPanel.setMaximumSize(emptyPanelDimension);
        colonyInfoPanel.setPreferredSize(colonyPanelDimension);
        colonyInfoPanel.setMaximumSize(colonyPanelDimension);
        colonyInfoPanel.setMinimumSize(colonyPanelDimension);
        colonyLabel.setPreferredSize(antLabelDimension);
        colonyLabel.setMaximumSize(antLabelDimension);
        colonyLabel.setMinimumSize(antLabelDimension);
        colonyFoodInfo.setPreferredSize(new Dimension(antLabelDimension.width - 7, antLabelDimension.height));
    }

    private void addComponentsInFrame(final Frame frame) {

        controll.add(setting);
        controll.add(reset);
        controll.addSeparator();
        controll.add(exit);
        menuBar.add(controll);

        setJMenuBar(menuBar);
        info.add(developerInfo);
        info.add(appInfo);
        menuBar.add(info);

        antInfoPanel.add(antQuantityInfo);
        
        antInfoPanel.add(emptyPanel);
        antInfoPanel.add(antWithoutFood);
        antInfoPanel.add(antWithFood);
        // antInfoPanel.add(antSoldierInfo);
        // antInfoPanel.add(antScoutInfo);

        colonyInfoPanel.add(colonyLabel);
        colonyInfoPanel.add(colonyFoodInfo);
        colonyInfoPanel.add(foodConsumption);

        infoWorldPanel.add(antInfoPanel);
        infoWorldPanel.add(colonyInfoPanel);

        controllPanel.add(paintModeLabel);
        controllPanel.add(paintModeComboBox);
        controllPanel.add(speedLabel);
        controllPanel.add(speedChooser);
        controllPanel.add(startButton);

        viewWorldPanel.add(worldPanel);
        viewWorldPanel.add(infoWorldPanel);
        mainPanel.add(viewWorldPanel);
        mainPanel.add(controllPanel);
    }

    public void resetInfoLabels() {
        antQuantityInfo.setValue(0);
        antWithFood.setValue(0);
        antWithoutFood.setValue(0);
        colonyFoodInfo.setValue(0f);
        foodConsumption.setValue(0f);
    }
    
    public JMenuItem getDeveloperInfo() {
        return developerInfo;
    }

    public JMenuItem getAppInfo() {
        return appInfo;
    }

    public void setWorldForDisplay(World world) {
        worldPanel.setWorld(world);
    }

    public void setAntQuant(int quant) {
        this.antQuantityInfo.setValue(quant);
    }

    public void setAntWithFood(int quant) {
        this.antWithFood.setValue(quant);
    }

    public void setAntWithoutFood(int quant) {
        this.antWithoutFood.setValue(quant);
    }

    public void setAnthillFood(float quant) {
        this.colonyFoodInfo.setValue(quant);
    }

    public void setFoodConsumption(float quant) {
        this.foodConsumption.setValue(quant);
    }

    public JButton getStartButton() {
        return startButton;
    }

    public JSpinner getSpeedChooser() {
        return speedChooser;
    }

    public JMenuItem getExit() {
        return exit;
    }

    public JMenuItem getReset() {
        return reset;
    }
    
    public JMenuItem getSetting() {
        return setting;
    }

    public WorldPanel getWorldPanel() {
        return worldPanel;
    }

    public JComboBox<WorldPaintMode> getPaintModeComboBox() {
        return paintModeComboBox;
    }
}
