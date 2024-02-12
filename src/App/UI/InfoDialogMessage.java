package App.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import App.Application;

public class InfoDialogMessage extends JDialog {

    private final String INFO_TEXT = " The program is a model of an ant colony, in which ants find food together, guided by a pheromone, \r\n" +
    "left by other ants. 2 types of pheromone have been implemented: anthill search pheromone, and food search pheromone\r\n" +
    "\r\n" + //
    "Ants that are in a state of searching for food leave behind them an anthill search pheromone, which\r\n" +
"Ants that have already found food can return to the colony. Ants without food ignore this pheromone.\r\n" +
    "Ants that have already found food leave a foraging pheromone, which is followed by ants without food,\r\n" +
    "They also begin to look for the shortest path to the colony, focusing on the pheromone left by the ants without food.\r\n" +
    "Similarly, they ignore the pheromone that they leave behind.\r\n" +
"The pheromone left by the ants evaporates over time, so that the ants do not move along irrelevant paths.\r\n" +
    "If there is no pheromone in the ant's field of vision, they begin to move in a random direction.\r\n" +
    "Ants that cannot find food or a way home for a long time stop leaving pheromone until they find their target.\r\n" +
    "\r\n" +
"The model can be started and paused by pressing a button. It is possible to change the speed from 1 to 10,\r\n" +
    "And also change the model rendering mode by selecting the required option in the drop-down list.\r\n" +
    "The main parameters of the model can be changed in the Control -> Settings menu.\r\n" +
    " Below are the designations of existing objects";

    private final JTextArea infoTextArea;

    private final ImageIcon imageAnthill, imageFoodSource, imageAntWithoutFood,
                            imageAntWithFood;

    public InfoDialogMessage(Frame owner, String title, boolean modal) {
        super(owner, title, modal);    
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        infoTextArea = new JTextArea();

        final String PICTURE_SOURCE = "App/Util/Picture/";
        imageAnthill = new ImageIcon(Application.class.getClassLoader().getResource(PICTURE_SOURCE + "anthill_screen.png"));
        imageFoodSource = new ImageIcon(Application.class.getClassLoader().getResource(PICTURE_SOURCE + "food_source_screen.png"));
        imageAntWithFood = new ImageIcon(Application.class.getClassLoader().getResource(PICTURE_SOURCE + "ant_with_food.png"));
        imageAntWithoutFood = new ImageIcon(Application.class.getClassLoader().getResource(PICTURE_SOURCE + "ant_without_food.png"));

        infoTextArea.setFont(UIManager.getFont("Label.font"));
        infoTextArea.setText(INFO_TEXT);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        infoTextArea.setEditable(false);
        getContentPane().add(infoTextArea);

        final JPanel imagePanel = new JPanel();
        imagePanel.setBackground(Color.WHITE);
        
        JPanel panel1 = new JPanel();
        panel1.setPreferredSize(new Dimension(10, 0));

        imagePanel.add(getPictureWithLabelName(imageAnthill, "Anthill"));
        imagePanel.add(panel1);
        imagePanel.add(getPictureWithLabelName(imageFoodSource, "Food source"));
        imagePanel.add(panel1);
        imagePanel.add(getPictureWithLabelName(imageAntWithoutFood, "Ant without food"));
        imagePanel.add(getPictureWithLabelName(imageAntWithFood, "Ant with food"));

        getContentPane().add(infoTextArea);
        getContentPane().add(imagePanel);
        
        setLocationRelativeTo(owner);
        pack();
        setResizable(false);
        setVisible(true);
    }

    private JPanel getPictureWithLabelName(ImageIcon imageIcon, String textForImage) {
        final JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BorderLayout());

        final JLabel labelForImage = new JLabel(imageIcon); 
        final JLabel labelForText = new JLabel(textForImage);
        labelForText.setHorizontalAlignment(SwingConstants.CENTER);

        mainPanel.add(labelForText, BorderLayout.NORTH);
        mainPanel.add(labelForImage, BorderLayout.CENTER);

        return mainPanel;
    }
}
