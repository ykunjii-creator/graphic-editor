import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GMainFrame extends JFrame {
	// components
	private GMenuBar menuBar;
	private GShapeToolBar toolBar;
	private GDrawingPanel drawingPanel;
	// associations
	// ...

	public GMainFrame() {
		// attributes
		this.setLocation(200, 200);
		this.setSize(600, 400);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		// components
		this.menuBar = new GMenuBar();
		this.setJMenuBar(menuBar);

		this.setLayout(new BorderLayout());

		this.toolBar = new GShapeToolBar();
		this.add(toolBar, BorderLayout.NORTH);

		this.drawingPanel = new GDrawingPanel();
		this.add(drawingPanel, BorderLayout.CENTER);
	}

	private class ToButtonActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() instanceof JButton) {
				JButton button = (JButton) e.getSource();
				String command = button.getText();
				switch (command) {
					case "rectangle":
						// 도형 그리기 모드로 전환
						break;
					case "oval":
						// 도형 그리기 모드로 전환
						break;
				}
			}
		}
	}
}

