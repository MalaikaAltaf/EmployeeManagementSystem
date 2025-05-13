package utils;

import javax.swing.*;
import java.awt.*;

public class ThemeManager {

    public enum Theme {
        LIGHT, DARK
    }

    private static Color lightBackground = Color.WHITE;
    private static Color lightForeground = Color.BLACK;
    private static Color lightButtonBackground = new Color(128, 0, 128);
    private static Color lightButtonForeground = Color.WHITE;

    private static Color darkBackground = new Color(45, 45, 45);
    private static Color darkForeground = Color.WHITE;
    private static Color darkButtonBackground = new Color(70, 130, 180);
    private static Color darkButtonForeground = Color.WHITE;

    private static Theme currentTheme = Theme.LIGHT;

    public static void setTheme(Theme theme) {
        currentTheme = theme;
    }

    public static Theme getTheme() {
        return currentTheme;
    }

    public static void applyTheme(Component component) {
        if (component instanceof JFrame) {
            JFrame frame = (JFrame) component;
            applyThemeToContainer(frame.getContentPane());
            frame.getContentPane().setBackground(getBackgroundColor());
        } else if (component instanceof JPanel) {
            applyThemeToContainer((JPanel) component);
        } else if (component instanceof JButton) {
            applyThemeToButton((JButton) component);
        } else if (component instanceof JLabel) {
            applyThemeToLabel((JLabel) component);
        } else if (component instanceof JComboBox) {
            applyThemeToComboBox((JComboBox<?>) component);
        }
        component.repaint();
    }

    private static void applyThemeToContainer(Container container) {
        container.setBackground(getBackgroundColor());
        for (Component comp : container.getComponents()) {
            if (comp instanceof JPanel) {
                applyThemeToContainer((JPanel) comp);
            } else if (comp instanceof JButton) {
                applyThemeToButton((JButton) comp);
            } else if (comp instanceof JLabel) {
                applyThemeToLabel((JLabel) comp);
            } else if (comp instanceof JComboBox) {
                applyThemeToComboBox((JComboBox<?>) comp);
            } else if (comp instanceof JScrollPane) {
                JScrollPane scrollPane = (JScrollPane) comp;
                applyThemeToContainer((JPanel) scrollPane.getViewport().getView());
            }
            comp.repaint();
        }
    }

    private static void applyThemeToButton(JButton button) {
        if (currentTheme == Theme.DARK) {
            button.setBackground(darkButtonBackground);
            button.setForeground(darkButtonForeground);
        } else {
            button.setBackground(lightButtonBackground);
            button.setForeground(lightButtonForeground);
        }
        button.setFocusPainted(false);
    }

    private static void applyThemeToLabel(JLabel label) {
        label.setForeground(getForegroundColor());
    }

    private static void applyThemeToComboBox(JComboBox<?> comboBox) {
        comboBox.setBackground(getBackgroundColor());
        comboBox.setForeground(getForegroundColor());
    }

    private static Color getBackgroundColor() {
        return currentTheme == Theme.DARK ? darkBackground : lightBackground;
    }

    private static Color getForegroundColor() {
        return currentTheme == Theme.DARK ? darkForeground : lightForeground;
    }
}
