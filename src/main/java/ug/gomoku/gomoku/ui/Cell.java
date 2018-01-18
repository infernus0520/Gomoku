package ug.gomoku.gomoku.ui;

import com.vaadin.ui.Button;

/**
 * Created by Lukasz on 18.01.2018.
 */
public class Cell extends Button {
    int x;
    int y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
