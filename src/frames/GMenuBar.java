package frames;

import javax.swing.*;

public class GMenuBar extends JMenuBar {
    // components
    private menus.GFileMenu fileMenu;
    // associations

    public GMenuBar() {
        this.fileMenu = new menus.GFileMenu();
        this.add(this.fileMenu);
    }
}
