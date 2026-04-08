
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;

public class GDrawingPanel extends JPanel {

    private int x0, y0;   // 시작점
    private int x1, y1;   // 끝점(현재 위치 또는 최종 위치)

    private boolean isDrawing = false;   // 현재 그리고 있는 중인지
    private boolean isFinished = false;  // 도형이 완성되었는지

    public GDrawingPanel() {
        super();
        this.setBackground(Color.PINK);

        MouseHandler mouseHandler = new MouseHandler();
        this.addMouseListener(mouseHandler);
        this.addMouseMotionListener(mouseHandler);
    }

    private void startRectangularShape(int x, int y) {
        this.x0 = x;
        this.y0 = y;
        this.x1 = x;
        this.y1 = y;

        this.isDrawing = true;
        this.isFinished = false;

        repaint();
    }

    private void previewRectangularShape(int x, int y) {
        if (isDrawing) {
            this.x1 = x;
            this.y1 = y;
            repaint();
        }
    }

    private void finishRectangularShape(int x, int y) {
        if (isDrawing) {
            this.x1 = x;
            this.y1 = y;

            this.isDrawing = false;
            this.isFinished = true;

            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (isDrawing || isFinished) {
            Graphics2D g2d = (Graphics2D) g;

            int x = Math.min(x0, x1);
            int y = Math.min(y0, y1);
            int width = Math.abs(x1 - x0);
            int height = Math.abs(y1 - y0);

            g2d.drawRect(x, y, width, height);
        }
    }

    private class MouseHandler implements MouseListener, MouseMotionListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 1) {
                // 첫 클릭: 시작점 설정
                startRectangularShape(e.getX(), e.getY());
            } else if (e.getClickCount() == 2) {
                // 더블클릭: 끝점 확정
                finishRectangularShape(e.getX(), e.getY());
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            // Move: 현재 위치까지 미리보기
            previewRectangularShape(e.getX(), e.getY());
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseDragged(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
}