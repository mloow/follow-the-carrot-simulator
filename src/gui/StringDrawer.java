package gui;

import java.awt.*;

/**
 * Created by marcus on 2015-09-28.
 */
public class StringDrawer {
    private final Graphics graphics;
    private int verticalSpacing = 0;

    public StringDrawer(Graphics graphics) {

        this.graphics = graphics;
    }

    public void setVerticalSpacing(int verticalSpacing) {
        this.verticalSpacing = verticalSpacing;
    }

    public void drawStringsDescending(int startX, int startY, String ... strings) {

        FontMetrics metrics = graphics.getFontMetrics();
        int height = metrics.getHeight();
        int y = startY;
        for(String s : strings) {
            graphics.drawString(s, startX, y);
            y+= height + verticalSpacing;
        }
    }
}
