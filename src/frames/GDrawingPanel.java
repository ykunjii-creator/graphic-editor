package frames;

import global.GConstants;
import shapes.GRectangle;
import shapes.GShape;
import shapes.GOval;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Vector;

public class GDrawingPanel extends JPanel {

    private GShapeToolBar toolBar;
    public void associateWith(GShapeToolBar toolBar) {
        this.toolBar = toolBar;
    }

    private enum EDrawingState {
        eIdle,
        eDrawing,
        eMoving,
        eResizing,
        eRotating,
    }
    private EDrawingState eDrawingState;

    private BufferedImage bufferImage;
    private Vector<GShape> shapes;
    private GShape currentShape;
    private GShape.EAnchor currentAnchor;

    // 이전 마우스 좌표 (dx, dy 계산용)
    private int prevX, prevY;

    // constructors
    public GDrawingPanel() {
        this.setBackground(Color.WHITE);
        this.eDrawingState = EDrawingState.eIdle;
        this.shapes = new Vector<GShape>();

        MouseHandler mouseHandler = new MouseHandler();
        this.addMouseListener(mouseHandler);
        this.addMouseMotionListener(mouseHandler);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (g != null) {
            g.drawImage(this.bufferImage, 0, 0, null);
        }
    }

    private void initBufferImage() {
        if (this.getWidth() <= 0 || this.getHeight() <= 0) return;
        if (this.bufferImage == null
                || this.bufferImage.getWidth() != this.getWidth()
                || this.bufferImage.getHeight() != this.getHeight()) {
            this.bufferImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D bufferGraphics = this.bufferImage.createGraphics();
            bufferGraphics.setColor(this.getBackground());
            bufferGraphics.fillRect(0, 0, this.getWidth(), this.getHeight());
            bufferGraphics.dispose();
        }
    }

    private void redrawAll(Graphics2D g) {
        g.setColor(this.getBackground());
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(this.getForeground());
        for (GShape shape : this.shapes) {
            shape.draw(g);
        }
    }

    private void startRectangularShape(int x, int y) {
        if (this.eDrawingState == EDrawingState.eIdle) {
            if (this.toolBar.getShapeType() == GConstants.EShapeType.eSelect) {
                // 먼저 선택 해제
                for (GShape shape : this.shapes) {
                    shape.setSelected(false);
                }
                for (GShape shape : this.shapes) {
                    GShape.EAnchor eAnchor = shape.onShape(x, y);
                    if (eAnchor != null) {
                        shape.setSelected(true);
                        this.currentShape = shape;
                        this.currentAnchor = eAnchor;
                        if (eAnchor == GShape.EAnchor.eRotate) {
                            eDrawingState = EDrawingState.eRotating;
                        } else if (eAnchor == GShape.EAnchor.eMove) {
                            eDrawingState = EDrawingState.eMoving;
                        } else {
                            eDrawingState = EDrawingState.eResizing;
                        }
                        break;
                    }
                }
            } else {
                if (this.toolBar.getShapeType() == GConstants.EShapeType.eOval) {
                    this.currentShape = new GOval(x, y, x, y);
                } else if (this.toolBar.getShapeType() == GConstants.EShapeType.eRectangle) {
                    this.currentShape = new GRectangle(x, y, x, y);
                }
                eDrawingState = EDrawingState.eDrawing;
            }

            if (this.eDrawingState == EDrawingState.eIdle) return;

            initBufferImage();
            this.prevX = x;
            this.prevY = y;
        }
    }

    private void keepRectangularShape(int x, int y) {
        if (this.eDrawingState != EDrawingState.eIdle) {
            int dx = x - prevX;
            int dy = y - prevY;

            Graphics2D bufferGraphics = this.bufferImage.createGraphics();

            if (this.eDrawingState == EDrawingState.eDrawing) {
                this.currentShape.setLocation1(x, y);
                redrawAll(bufferGraphics);
                this.currentShape.draw(bufferGraphics);
            } else if (this.eDrawingState == EDrawingState.eMoving) {
                this.currentShape.move(dx, dy);
                redrawAll(bufferGraphics);
            } else if (this.eDrawingState == EDrawingState.eResizing) {
                this.currentShape.resize(this.currentAnchor, dx, dy);
                redrawAll(bufferGraphics);
            } else if (this.eDrawingState == EDrawingState.eRotating) {
                this.currentShape.rotate(x, y);
                redrawAll(bufferGraphics);
            }

            bufferGraphics.dispose();
            repaint();

            this.prevX = x;
            this.prevY = y;
        }
    }

    private void finishRectangularShape(int x, int y) {
        if (this.eDrawingState != EDrawingState.eIdle) {
            if (this.eDrawingState == EDrawingState.eDrawing) {
                this.shapes.add(this.currentShape);
            }
            this.eDrawingState = EDrawingState.eIdle;
            this.currentShape = null;
            this.currentAnchor = null;
        }
    }

    private class MouseHandler implements MouseListener, MouseMotionListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == 1) {
                if (e.getClickCount() == 1) {
                    mouseLButton1Clicked(e);
                } else if (e.getClickCount() == 2) {
                    mouseLButton2Clicked(e);
                }
            }
        }

        private void mouseLButton1Clicked(MouseEvent e) {}
        private void mouseLButton2Clicked(MouseEvent e) {}

        @Override
        public void mousePressed(MouseEvent e) {
            startRectangularShape(e.getX(), e.getY());
        }
        @Override
        public void mouseDragged(MouseEvent e) {
            keepRectangularShape(e.getX(), e.getY());
        }
        @Override
        public void mouseReleased(MouseEvent e) {
            finishRectangularShape(e.getX(), e.getY());
        }

        @Override public void mouseMoved(MouseEvent e) {}
        @Override public void mouseEntered(MouseEvent e) {}
        @Override public void mouseExited(MouseEvent e) {}
    }
}