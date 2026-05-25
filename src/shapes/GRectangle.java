package shapes;

import java.awt.*;

public class GRectangle extends GShape {

    public GRectangle(int x0, int y0, int x1, int y1) {
        super(x0, y0, x1, y1);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        int left = getLeft(), top = getTop();
        int w = getRight() - left;
        int h = getBottom() - top;
        g.drawRect(left, top, w, h);
        if (this.selected) drawAnchors(g);
    }
}