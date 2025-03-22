package cn.memkit.mouse;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * @description: 快捷button
 * @author: jee
 */
public class ButtonBuilder {

    private static JButton DEFAULT_BUTTON = new JButton();
    /**
     * #1890FF
     */
    public static final Color PRIMARY_BG = new Color(24, 144, 255);
    /**
     * #FF4D4F
     */
    public static final Color DANGER_BG = new Color(255, 77, 79);
    /*
     * #009688
     */
    public static final Color GREEN_BG = new Color(0, 150, 136);
    /**
     * #F0FFFE
     */
    public static final Color FONT_COLOR = new Color(240, 255, 254);

    /**
     *
     */
    public static final Color NO_COLOR = new Color(255, 255, 255, 0);

    public static JButton primaryButton(String text){
        JButton button = new JButton(text);
        Border roundBorder = new RoundBorder(RoundBorder.PRIMARY_BG, 10, 1);
        button.setBorder(roundBorder);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(50, 25)); // Set preferred size
        button.setBackground(RoundBorder.PRIMARY_BG);
        button.setForeground(RoundBorder.PRIMARY_BG);
        return button;
    }

}

