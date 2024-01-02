package App.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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

import App.Model.World;
import App.Util.EntityParams;
import App.Util.WorldPaintMode;

public class UserInterface extends JFrame {

    private final WorldPanel worldPanel;

    private final JPanel mainPanel, controllPanel, infoWorldPanel, viewWorldPanel,
                         antInfoPanel, colonyInfoPanel, emptyPanel;

    private final JLabel colonyLabel, speedLabel, paintModeLabel;

    private final InfoLabel<Integer> antQuantityInfo, antCollectorsInfo, antSoldierInfo,
                                     antScoutInfo;
    private final InfoLabel<Float> colonyFoodInfo;

    private final JButton startButton;

    private final JSpinner speedChooser;
    
    private final JComboBox<WorldPaintMode> paintModeComboBox;

    private final JMenuBar menuBar;

    private final JMenu info, file;

    private final JMenuItem developerInfo, appInfo, loadModel, 
                            saveModel, exit, reset;

    public UserInterface(Dimension size) {

        setSize(size);
        setLayout(new FlowLayout(FlowLayout.CENTER));
        this.mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel);
        setIconImage(new ImageIcon(getClass().getResource("ant.png")).getImage());
        setTitle("Моделирование жизни в муравейнике");

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
        antCollectorsInfo = new InfoLabel<Integer>("Собирателей:", 0, EntityParams.Colors.ANT_COLLECTOR);
        antSoldierInfo = new InfoLabel<Integer>("Воинов:", 0, EntityParams.Colors.ANT_SOLDIER);
        antScoutInfo = new InfoLabel<Integer>("Разведчиков:", 0, EntityParams.Colors.ANT_SCOUT);
        colonyFoodInfo = new InfoLabel<Float>("Еда:", 0.0f);

        paintModeComboBox = new JComboBox<>();
        paintModeComboBox.addItem(WorldPaintMode.ALL_ENTITIES);
        paintModeComboBox.addItem(WorldPaintMode.ONLY_CREATURE);
        paintModeComboBox.addItem(WorldPaintMode.ONLY_OBJECTS);
        paintModeComboBox.addItem(WorldPaintMode.PHEROMONE_TO_ANTHILL);
        paintModeComboBox.addItem(WorldPaintMode.PHEROMONE_TO_TARGET);

        menuBar = new JMenuBar();

        info = new JMenu("Справка");
        file = new JMenu("Файл");
    
        developerInfo = new JMenuItem("Разработчик");
        appInfo = new JMenuItem("О приложении");

        loadModel = new JMenuItem("Загрузить");
        saveModel = new JMenuItem("Сохранить");
        reset = new JMenuItem("Сбросить");
        exit = new JMenuItem("Выход");
        
        infoWorldPanel.setLayout(new BoxLayout(infoWorldPanel, BoxLayout.Y_AXIS));
        antInfoPanel.setLayout(new BoxLayout(antInfoPanel, BoxLayout.Y_AXIS));
        controllPanel.setLayout(new FlowLayout());
        viewWorldPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        calculateAndSetComponentsPrefferedSize(size);
        addComponentsInFrame(this);
        
        worldPanel.setBackground(Color.WHITE);
        worldPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        setResizable(true);
        pack();
        setVisible(true);
    }
    
    private void calculateAndSetComponentsPrefferedSize(final Dimension frameSize) {
        final Dimension viewWorldPanelDimension = 
            new Dimension((int) (frameSize.width / 1.15), 
                          (int) (frameSize.height / 1.6));

        final Dimension worldPanelDimension = 
            // new Dimension((int) (viewWorldPanelDimension.width / 1.6), 
            //               (int) (viewWorldPanelDimension.height / 1.05));
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
        antCollectorsInfo.setPreferredSize(antLabelDimension);
        antCollectorsInfo.setMaximumSize(antLabelDimension);
        antSoldierInfo.setPreferredSize(antLabelDimension);
        antSoldierInfo.setMaximumSize(antLabelDimension);
        antScoutInfo.setPreferredSize(antLabelDimension);
        antScoutInfo.setMaximumSize(antLabelDimension);
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

        file.add(loadModel);
        file.add(saveModel);
        file.add(reset);
        file.addSeparator();
        file.add(exit);
        menuBar.add(file);

        setJMenuBar(menuBar);
        info.add(developerInfo);
        info.add(appInfo);
        menuBar.add(info);

        antInfoPanel.add(antQuantityInfo);
        
        antInfoPanel.add(emptyPanel);
        antInfoPanel.add(antCollectorsInfo);
        antInfoPanel.add(antSoldierInfo);
        antInfoPanel.add(antScoutInfo);

        colonyInfoPanel.add(colonyLabel);
        colonyInfoPanel.add(colonyFoodInfo);

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
    
    public JMenuItem getDeveloperInfo() {
        return developerInfo;
    }

    public JMenuItem getAppInfo() {
        return appInfo;
    }

    public void setWorldForDisplay(World world) {
        worldPanel.setWorld(world);
    }

    public JButton getStartButton() {
        return startButton;
    }

    public JSpinner getSpeedChooser() {
        return speedChooser;
    }

    public JMenuItem getSaveModel() {
        return saveModel;
    }

    public JMenuItem getLoadModel() {
        return loadModel;
    }

    public JMenuItem getExit() {
        return exit;
    }

    public JMenuItem getReset() {
        return reset;
    }

    public WorldPanel getWorldPanel() {
        return worldPanel;
    }

    public JComboBox<WorldPaintMode> getPaintModeComboBox() {
        return paintModeComboBox;
    }
}
