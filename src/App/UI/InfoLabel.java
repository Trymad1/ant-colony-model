package App.UI;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoLabel <T extends Number> extends JPanel {

    private T value;
    private JLabel textLabel;
    private JLabel valueLabel;
    
    public InfoLabel(String text, T value) {
        super();
        setLayout(new BorderLayout());
        this.textLabel = new JLabel(text);
        this.valueLabel = new JLabel(String.valueOf(value));
        add(textLabel, BorderLayout.WEST);
        add(valueLabel, BorderLayout.EAST);
    }

    public InfoLabel(String text, T value, Color color) {
        this(text, value);
        textLabel.setForeground(color);
        valueLabel.setForeground(color);
    }

    public void setValue(T value) {
        this.value = value;
        System.out.println(this.value);
        valueLabel.setText(String.valueOf(value));
    }

    public T getValue() {
        return value;
    }
}
