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
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import App.Util.Setting;
import App.Util.Setting.SettingBuilder;

public class SettingWindow extends JFrame {

    private final JTextField antQuantField, foodSourceQuantField, 
    foodInSourceQuantField, startFoodValueField, foodConsumptionField;

    private final JButton accept, defaultSetting;

    private Setting setting;

    public SettingWindow(Component parrentComponent, Setting setting) {
        setTitle("Настройки");

        // Используем менеджер компоновки BoxLayout с ориентацией Y_AXIS
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Добавляем метки и поля для ввода значений
        antQuantField = new JTextField(4);
        foodSourceQuantField = new JTextField(4);
        foodInSourceQuantField = new JTextField(4);
        startFoodValueField = new JTextField(4);
        foodConsumptionField = new JTextField(4);

        addLabelAndTextField("Количество муравьев: ", antQuantField);
        addLabelAndTextField("Количество источников еды: ", foodSourceQuantField);
        addLabelAndTextField("Количество еды в источнике: ", foodInSourceQuantField);
        addLabelAndTextField("Стартовое значение еды: ", startFoodValueField);
        addLabelAndTextField("Трата еды на 1 муравья: ", foodConsumptionField);

        setSetting(setting);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        accept = new JButton("Принять");
        defaultSetting = new JButton("По умолчанию");

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
                setSetting(setting);
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
                builder.append("Количество муравьев должно быть больше 0 \n");
            }

            final int foodSourceQuant = Integer.valueOf(foodSourceQuantField.getText());
            if (foodSourceQuant < 1) {
                builder.append("Количество источников еды должно быть больше 0 \n");
            }

            final int foodInSourceQuant = Integer.valueOf(foodInSourceQuantField.getText());
            if (foodInSourceQuant < 1) {
                builder.append("Количество еды в источнике должно быть больше 0 \n");
            }

            final float startFoodValue = Float.valueOf(startFoodValueField.getText());
            if (startFoodValue < 0f) {
                builder.append("Количество стартовой еды не может быть отрицательным \n");
            }

            final float foodConsumption = Float.valueOf(foodConsumptionField.getText());
            if (foodConsumption < 0f) {
                builder.append("Потребление еды не может быть отрицательным \n");
            }

        } catch (NumberFormatException e) {
            builder.append("В полях могут содержаться только цифры \n" );
        }

        if (builder.length() > 0) {
            JOptionPane.showMessageDialog(this, builder.toString());
            return false;
        }
        JOptionPane.showMessageDialog(this, "Настройки будут применены к следующей модели мира");
        this.setVisible(false);
        return true;
    }

    public Setting getSetting() {
        return setting;
    }
}

