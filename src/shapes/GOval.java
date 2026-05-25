
package shapes;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class GOval extends GShape {
  public GOval() {
    this.shape = new Ellipse2D.Double();
  }

  @Override
  public void draw(Graphics2D g) {
    Graphics2D g2d = (Graphics2D) g.create();
    g.setColor(Color.BLACK);
    g.drawOval(x0,y0,x1-x0,y1-y0);
  }
}
