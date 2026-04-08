import java.awt.BorderLayout;
import javax.swing.*;

public class GMainFrame extends JFrame { // 얘는 JFrame을 통해 확장하고 import 해야 JDK에서 가져옴
    // attributes
    // private int size;

    // component 자식은 특수하고 자기가 만들어야하고 등록해야하고.
    private GMenuBar menuBar; // 자식이름이라 마음대로 줘도 되는 거임
    private GToolBar toolBar;
    private GDrawingPanel drawingPanel;


    // association
    // private GDirectory directory; 여기다 다른 친구의 주소를 연결해줘야함

    // constructor
    public GMainFrame() { // JFrame은 우리가 코드를 따로 안 짜도 자동으로 그려지는데 우리는 몸만 채움.
        super("GMainFrame");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create aggregation 자식 만들기
        this.menuBar = new GMenuBar();
        this.setJMenuBar(menuBar); // 등록을 하는 것. GMainFrame의 자식으로. menuBar만 set으로 사용함.

        // drawingPanel 먼저 생성
        this.drawingPanel = new GDrawingPanel();

        // toolBar 생성 및 drawingPanel에 연결
        this.toolBar = new GToolBar(drawingPanel);


        this.add(toolBar, BorderLayout.NORTH);
        this.add(drawingPanel, BorderLayout.CENTER);

        this.setVisible(true);
    }

    // member function

}
