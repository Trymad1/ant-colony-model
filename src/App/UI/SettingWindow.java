package App.UI;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import App.Model.Setting;
import App.Model.Setting.SettingBuilder;

/**
*Window with model settings
*/
public class SettingWindow extends JFrame {

    private final JTextField antQuantField, foodSourceQuantField, 
    foodInSourceQuantField, startFoodValueField, foodConsumptionField;

    private final JButton accept, defaultSetting;

    private Setting setting;

    public SettingWindow(Component parrentComponent, Setting setting) {
        setTitle("Settings");

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        antQuantField = new JTextField(4);
        foodSourceQuantField = new JTextField(4);
        foodInSourceQuantField = new JTextField(4);
        startFoodValueField = new JTextField(4);
        foodConsumptionField = new JTextField(4);

        addLabelAndTextField("Number of ants: ", antQuantField);
        addLabelAndTextField("Quantity of food sources: ", foodSourceQuantField);
        addLabelAndTextField("Quantity of food in source: ", foodInSourceQuantField);
        addLabelAndTextField("Start food value: ", startFoodValueField);
        addLabelAndTextField("Spending food per 1 ant: ", foodConsumptionField);

        setSetting(setting);

        final JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        accept = new JButton("Accept");
        defaultSetting = new JButton("Default");

        accept.addActionListener(e -> {
            if (this.validation()) {
                setSetting(this.getNewSetting());
            }
        });
        defaultSetting.addActionListener(e -> setSetting(SettingBuilder.createDefaultSetting()));

        panel.add(defaultSetting);
        panel.add(accept);


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setSetting(getSetting());
                setVisible(false);
            }
        });

        setLocationRelativeTo(parrentComponent);
        add(panel);
        setResizable(false);
        pack();
    }

    public void setSetting(Setting setting) {

        this.setting = setting;
        antQuantField.setText(String.valueOf(this.setting.getAntQuant()));
        foodSourceQuantField.setText(String.valueOf(this.setting.getFoodSourceQuant()));
        foodInSourceQuantField.setText(String.valueOf(this.setting.getFoodInSourceQuant()));
        startFoodValueField.setText(String.valueOf(this.setting.getStartFoodValue()));
        foodConsumptionField.setText(String.valueOf(this.setting.getFoodConsumption()));
    }

    
    private void addLabelAndTextField(String labelText, JTextField field) {
        final JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); 
        final JLabel label = new JLabel(labelText);

        panel.add(label);
        panel.add(field);
        add(panel); 
    }

    private Setting getNewSetting() {
        return Setting.builder()
        .setAntQuant(Integer.valueOf(antQuantField.getText()))
        .setFoodSourceQuant(Integer.valueOf(foodSourceQuantField.getText()))
        .setFoodInSourceQuant(Integer.valueOf(foodInSourceQuantField.getText()))
        .setStartFoodValue(Float.valueOf(startFoodValueField.getText()))
        .setFoodConsumption(Float.valueOf(foodConsumptionField.getText()))
        .build();
    }

    private boolean validation() {
        final StringBuilder builder = new StringBuilder();
        try {
            final int antQuant = Integer.valueOf(antQuantField.getText());
            if (antQuant < 1) {
                builder.append("The number of ants must be greater than 0 \n");
            }

            final int foodSourceQuant = Integer.valueOf(foodSourceQuantField.getText());
            if (foodSourceQuant < 1) {
                builder.append("The number of food sources must be greater than 0 \n");
            }

            final int foodInSourceQuant = Integer.valueOf(foodInSourceQuantField.getText());
            if (foodInSourceQuant < 1) {
                builder.append("The amount of food in the source must be greater than 0 \n");
            }

            final float startFoodValue = Float.valueOf(startFoodValueField.getText());
            if (startFoodValue < 0f) {
                builder.append("The amount of starting food cannot be negative \n");
            }

            final float foodConsumption = Float.valueOf(foodConsumptionField.getText());
            if (foodConsumption < 0f) {
                builder.append("Food consumption cannot be negative \n");
            }

        } catch (NumberFormatException e) {
            builder.append("Fields can only contain numbers \n" );
        }

        if (builder.length() > 0) {
            JOptionPane.showMessageDialog(this, builder.toString());
            return false;
        }
        JOptionPane.showMessageDialog(this, "The settings will be applied to the next world model");
        this.setVisible(false);
        return true;
    }

    public Setting getSetting() {
        return setting;
    }
}

