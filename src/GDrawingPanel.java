import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Vector;

public class GDrawingPanel extends JPanel {
	private boolean isDrawing; // 처음에는 놀고 있는 상태. 속성임
	private BufferedImage bufferImage;
	private EDrawingState eDrawingState;
	private GShape currentShape;
	private Vector<GShape> shapes;

	private enum EDrawingState { // 해야할 일
		eIdle,
		eDrawing,
		eMoving,
		eResizing,
		eSharing
	}

	public GDrawingPanel() {
		this.setBackground(Color.WHITE);
		//this.isDrawing = false; // no 그림
		this.eDrawingState=EDrawingState.eIdle;

		this.shapes = new Vector<GShape>();
		MouseHandler mouseHandler = new MouseHandler();
		this.addMouseListener(mouseHandler);
		this.addMouseMotionListener(mouseHandler);

		if (this.getWidth() <= 0 || this.getHeight() <= 0) {
			return;
		}

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

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D panelGraphics = (Graphics2D) g;
		for (GShape shape : shapes) {
			panelGraphics.dispose();
		}
	}
	private void startRectangularShape(int x, int y) {
		this.currentShape = new GShape(x, y, x, y); // 초기좌표
		this.currentShape.setLocation0(x, y);

		if (this.getWidth() <= 0 || this.getHeight() <= 0) {
			return;
		}
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
	private void finishRectangularShape(int x, int y) {
		this.currentShape.setLocation1(x, y);
		Graphics2D bufferGraphics = this.bufferImage.createGraphics();
		bufferGraphics.setColor(this.getBackground());
		bufferGraphics.fillRect(0, 0, this.getWidth(), this.getHeight());
		bufferGraphics.setColor(Color.BLACK);
		for(GShape shape : this.shapes) {
			shape.draw(bufferGraphics);
		}
		this.currentShape.draw(bufferGraphics);// 연습장 그림도구를 주고 그리라고한다.
		bufferGraphics.dispose();
		Graphics2D panelGraphics = (Graphics2D) this.getGraphics();
		if (panelGraphics != null) {
			panelGraphics.drawImage(this.bufferImage, 0, 0, null);
			panelGraphics.dispose();
		}
	}
	private void addShape() {
		this.shapes.add(this.currentShape);
	}

	private class MouseHandler implements MouseListener, MouseMotionListener {
		@Override
		public void mousePressed(MouseEvent e) {
			if (eDrawingState == EDrawingState.eIdle) {
				startRectangularShape(e.getX(), e.getY());
				eDrawingState = EDrawingState.eDrawing;
			}
		}
		@Override
		public void mouseDragged(MouseEvent e) {
			if (eDrawingState == EDrawingState.eDrawing) {
				finishRectangularShape(e.getX(), e.getY());
			}
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			if (eDrawingState == EDrawingState.eDrawing) {
				finishRectangularShape(e.getX(), e.getY());
				addShape();
				eDrawingState = EDrawingState.eIdle;
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
//			if(e.getButton() == 1) { //1번 버튼이 왼쪽 버튼
//			} else if(e.getClickCount() == 1) { //single click
//				mouseLButton1Clicked(e);
//			} else if (e.getClickCount() == 2) { // right click
//				mouseLButton2Clicked(e);
//			}
//			if (e.getClickCount() == 1) {
//				startRectangularShape(e.getX(), e.getY());
//			} else if (e.getClickCount() == 2) {
//				finishRectangularShape(e.getX(), e.getY());
//			}
		}


		@Override
		public void mouseMoved(MouseEvent e) {
//			if (eDrawingState == EDrawingState.eIdle) {
//				finishRectangularShape(e.getX(), e.getY());
//				eDrawingState = EDrawingState.eDrawing;
//			}
		}

		private void mouseLButton1Clicked(MouseEvent e) {
//			if (eDrawingState == EDrawingState.eDrawing) {
//				startRectangularShape(e.getX(), e.getY());
//			}
		}
		private void mouseLButton2Clicked(MouseEvent e) {

		}
		@Override
		public void mouseEntered(MouseEvent e) {
		}
		@Override
		public void mouseExited(MouseEvent e) {
		}

	}

	public class GShape{
		private int x0, y0, x1, y1;

		public GShape(int x0, int y0, int x1, int y1) {
			this.x0 = x0;
			this.y0 = y0;
			this.x1 = x1;
			this.y1 = y1;
		}

		public void setLocation0(int x0, int y0) {
			this.x0 = x0;
			this.y0 = y0;
		}
		public void setLocation1(int x1, int y1) {
			this.x1 = x1;
			this.y1 = y1;
		}

		public void draw(Graphics2D g) {
			g.setColor(Color.BLACK);
			g.drawRect(x0, y0, x1 - x0, y1 - y0);
		}
	}
}


