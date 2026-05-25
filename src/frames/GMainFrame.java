package frames;

import frames.GMenuBar;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class GMainFrame extends JFrame {
    // components
    private GMenuBar menuBar;
    private frames.GShapeToolBar toolBar;
    private frames.GDrawingPanel drawingPanel;
    // associations
    // ...

    public GMainFrame() {
        // attributes
        // 매직 넘버 관리해야함. 리팩토링
        this.setLocation(200, 200);
        this.setSize(600, 400);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // components
        this.menuBar = new GMenuBar();
        this.setJMenuBar(menuBar);

        this.setLayout(new BorderLayout());

        this.toolBar = new frames.GShapeToolBar();
        this.add(toolBar, BorderLayout.NORTH);

        this.drawingPanel = new frames.GDrawingPanel();
        this.add(drawingPanel, BorderLayout.CENTER);

        this.drawingPanel.associateWith(this.toolBar);
    }

    private class TooButtonActionHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }
}
