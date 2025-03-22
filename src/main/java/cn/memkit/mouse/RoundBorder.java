package cn.memkit.mouse;

import javax.swing.border.Border;
import java.awt.*;

public class RoundBorder implements Border {


    /**
     * #1890FF  蓝色
     */
    public static final Color PRIMARY_BG = new Color(24, 144, 255);
    /**
     * #FF4D4F  红色
     */
    public static final Color DANGER_BG = new Color(255, 77, 79);
    /*
     *#009688 墨绿色
     */
    public static final Color GREEN_BG = new Color(0, 150, 136);
    /**
     * #F0FFFE 白色
     */
    public static final Color FONT_COLOR = new Color(240, 255, 254);

    /**
     * 透明色
     */
    public static final Color NO_COLOR = new Color(255, 255, 255, 0);

    private Color color;
    private int radius;
    private int thickness;

    public RoundBorder(Color color, int radius, int thickness) {
        this.color = color;
        this.radius = radius;
        this.thickness = thickness;
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(thickness, thickness, thickness, thickness);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color);
        g2.setStroke(new BasicStroke(thickness));
        g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        g2.dispose();
    }
}