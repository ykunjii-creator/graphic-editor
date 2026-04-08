import javax.swing.*;

public class GToolBar extends JToolBar {
    private JRadioButton rectangleButton;
    private JRadioButton ovalButton;
    private ButtonGroup buttonGroup;

    public GToolBar(GDrawingPanel drawingPanel) {
        this.rectangleButton = new JRadioButton("Rectangle");
        this.ovalButton = new JRadioButton("Oval");

        this.buttonGroup = new ButtonGroup();
        this.buttonGroup.add(rectangleButton);
        this.buttonGroup.add(ovalButton);

        this.add(rectangleButton);
        this.add(ovalButton);
    }


}
