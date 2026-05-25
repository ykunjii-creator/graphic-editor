package shapes;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class GRectangle extends GShape {
	public GRectangle() {
		this.shape = new Rectangle();
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.drawRect(x0,y0,x1-x0,y1-y0);
	}
}
