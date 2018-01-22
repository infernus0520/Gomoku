package ug.gomoku.gomoku.ui;


import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.Random;

@SpringUI
@Title("Gomoku")
public class GomokuUi extends UI {

    private GridLayout gridLayout;
    private Cell cell;
    private String mark;
    private Button resetButton;
    private HorizontalLayout horizontalLayout;

    //Wartosci poszczegolnych kombinacji
    private final int glue = 1;
    private final int two = 10;
    private final int three = 100;
    private final int four = 500;
    private final int five = 100000;
    private final int openThree = 1000;
    private final int openFour = 2500;
    private final int threatTwo = -15;
    private final int threatThree = -300;
    private final int threatFour = -1000;
    private final int threatOpenThree = -2000;
    private final int threatOpenFour = -5000;


    @Override
    protected void init(VaadinRequest vaadinRequest) {

        resetButton = new Button("Nowa Gra");
        horizontalLayout = new HorizontalLayout();
        gridLayout = new GridLayout(10, 10);
        mark = "X";

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                cell = new Cell();
                cell.setX(i);
                cell.setY(j);
                cell.setHeight(50, Unit.PIXELS);
                cell.setWidth(50, Unit.PIXELS);
                cell.addClickListener(event -> {
                    if (checkContinue(gridLayout)) {
                        event.getComponent().setCaption(mark);
                        event.getButton().setEnabled(false);
                        event.getButton().setStyleName(ValoTheme.BUTTON_FRIENDLY);
                        if (chechWin(gridToTab(gridLayout), mark)) {
                            gridLayout.setEnabled(false);
                            Notification.show("Wygrales!",
                                    Notification.Type.HUMANIZED_MESSAGE);
                        }

                        if (checkContinue(gridLayout)) {
                            //randomComputerMove(gridLayout);
                            smartComputerMove(gridLayout);
                            if (chechWin(gridToTab(gridLayout), "O")) {
                                gridLayout.setEnabled(false);
                                Notification.show("Przegrales!",
                                        Notification.Type.HUMANIZED_MESSAGE);
                            }
                        } else {
                            Notification.show("Remis!",
                                    Notification.Type.HUMANIZED_MESSAGE);
                        }
                    } else {
                        Notification.show("Remis!",
                                Notification.Type.HUMANIZED_MESSAGE);
                    }

                });
                gridLayout.addComponent(cell, i, j);
            }
        }

        resetButton.addClickListener(event -> {
            reset(gridLayout);
        });

        gridLayout.setSpacing(false);
        horizontalLayout.addComponents(gridLayout, resetButton);
        setContent(horizontalLayout);


    }

    boolean chechWin(String[][] tab, String mark) {

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (j + 4 < 10) {
                    if (tab[i][j] == mark && tab[i][j + 1] == mark && tab[i][j + 2] == mark && tab[i][j + 3] == mark && tab[i][j + 4] == mark) {
                        return true;
                    }
                }
            }
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (i + 4 < 10) {
                    if (tab[i][j] == mark && tab[i + 1][j] == mark && tab[i + 2][j] == mark && tab[i + 3][j] == mark && tab[i + 4][j] == mark) {
                        return true;
                    }
                }
            }
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (j + 4 < 10 && i + 4 < 10) {
                    if (tab[i][j] == mark && tab[i + 1][j + 1] == mark && tab[i + 2][j + 2] == mark && tab[i + 3][j + 3] == mark && tab[i + 4][j + 4] == mark) {
                        return true;
                    }
                }
            }
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (i + 4 < 10 && j - 4 >= 0) {
                    if (tab[i][j] == mark && tab[i + 1][j - 1] == mark && tab[i + 2][j - 2] == mark && tab[i + 3][j - 3] == mark && tab[i + 4][j - 4] == mark) {
                        return true;
                    }
                }
            }
        }


        return false;
    }

    void move(int i, int j) {
        gridLayout.getComponent(i, j).setCaption("O");
        gridLayout.getComponent(i, j).setStyleName(ValoTheme.BUTTON_DANGER);
        gridLayout.getComponent(i, j).setEnabled(false);
    }

    void randomComputerMove(GridLayout gridLayout) {
        Random randomGenerator = new Random();
        int i;
        int j;
        boolean t = true;
        while (t == true) {
            i = randomGenerator.nextInt(10);
            j = randomGenerator.nextInt(10);

            if (gridLayout.getComponent(i, j).getCaption() != "X" && gridLayout.getComponent(i, j).getCaption() != "O") {
                t = false;
                move(i, j);
            }
        }


    }

    String[][] gridToTab(GridLayout gridLayout) {
        String[][] tab = new String[10][10];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (gridLayout.getComponent(i, j).getCaption() == "X") {
                    tab[i][j] = "X";
                } else if (gridLayout.getComponent(i, j).getCaption() == "O") {
                    tab[i][j] = "O";
                } else {
                    tab[i][j] = "";
                }
            }
        }

        return tab;
    }


    void reset(GridLayout gridLayout) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                gridLayout.getComponent(i, j).setCaption(null);
                gridLayout.getComponent(i, j).setEnabled(true);
                gridLayout.getComponent(i, j).removeStyleName(ValoTheme.BUTTON_DANGER);
                gridLayout.getComponent(i, j).removeStyleName(ValoTheme.BUTTON_FRIENDLY);
                gridLayout.setEnabled(true);
            }
        }
    }

    boolean checkContinue(GridLayout gridLayout) {

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (gridLayout.getComponent(i, j).getCaption() == null) {
                    return true;
                }

            }
        }

        return false;
    }


    void smartComputerMove(GridLayout grid) {
        int bestValX = 0;
        int bestValY = 0;
        int bestEvaluation = -1;
        int currentEvaluation;

        for (int g = 0; g < 10; g++) {
            for (int h = 0; h < 10; h++) {
                if (gridLayout.getComponent(g, h).getCaption() != "X" && gridLayout.getComponent(g, h).getCaption() != "O") {
                    currentEvaluation = fitnessFunction(grid, g, h);
                    if (currentEvaluation > bestEvaluation) {
                        bestEvaluation = currentEvaluation;
                        bestValX = g;
                        bestValY = h;
                    }
                }
            }
        }

        move(bestValX, bestValY);


    }

    int fitnessFunction(GridLayout gl,int k, int l) {
        int val = 0;
        String[][] tab = gridToTab(gl);
        tab[k][l] = "O";

        //Wartosci poziome
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if(i+4 < 10) {

                    //podstawowe uklady
                    if (tab[i][j] == "O" && tab[i + 1][j] == "O") {
                        val += two;
                    }
                    if (tab[i][j] == "O" && tab[i + 1][j] == "O" && tab[i + 2][j] == "O") {
                        val += three;
                    }
                    if (tab[i][j] == "O" && tab[i + 1][j] == "O" && tab[i + 2][j] == "O" && tab[i + 3][j] == "O") {
                        val += four;
                    }
                    if (tab[i][j] == "O" && tab[i + 1][j] == "O" && tab[i + 2][j] == "O" && tab[i + 3][j] == "O" && tab[i + 4][j] == "O") {
                        val += five;
                    }


                    // tzw 'klejenie sie do gry'
                    if ((tab[i][j] == "X" && tab[i + 1][j] == "O") || ((tab[i][j] == "O" && tab[i+1][j] == "X")))   {
                        val += glue;
                    }

                    //open3
                    if (tab[i][j] == "" && tab[i + 1][j] == "O" && tab[i + 2][j] == "O" && tab[i + 3][j] == "O" && tab[i + 4][j] == "") {
                        val += openThree;
                    }

                    //open4
                    if(i+5 < 10) {
                        if (tab[i][j] == "" && tab[i + 1][j] == "O" && tab[i + 2][j] == "O" && tab[i + 3][j] == "O" && tab[i + 4][j] == "O" && tab[i + 5][j] == "") {
                            val += openFour;
                        }
                    }

                }
            }
        }

        //Wartosci Pionowe
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if(j+4 < 10) {
                    if (tab[i][j] == "O" && tab[i][j+1] == "O") {
                        val += two;
                    }
                    if (tab[i][j] == "O" && tab[i][j+1] == "O" && tab[i][j+2] == "O") {
                        val += three;
                    }
                    if (tab[i][j] == "O" && tab[i][j+1] == "O" && tab[i][j+2] == "O" && tab[i][j+3] == "O") {
                        val += four;
                    }
                    if (tab[i][j] == "O" && tab[i][j+1] == "O" && tab[i][j+2] == "O" && tab[i][j+3] == "O" && tab[i][j+4] == "O") {
                        val += five;
                    }

                    // tzw 'klejenie sie do gry'
                    if (((tab[i][j] == "X" && tab[i][j+1] == "O")) || ((tab[i][j] == "O" && tab[i][j+1] == "X"))) {
                        val += glue;
                    }

                    //open3
                    if (tab[i][j] == "" && tab[i][j+1] == "O" && tab[i][j+2] == "O" && tab[i][j+3] == "O" && tab[i][j+4] == "") {
                        val += openThree;
                    }

                    //open4
                    if(j+5 < 10) {
                        if (tab[i][j] == "" && tab[i][j+1] == "O" && tab[i][j+2] == "O" && tab[i][j+3] == "O" && tab[i][j+4] == "O" && tab[i][j+5] == "") {
                            val += openFour;
                        }
                    }

                }
            }
        }

        //Skosy1
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if(j + 4 < 10 && i + 4 < 10) {
                    if (tab[i][j] == "O" && tab[i+1][j+1] == "O") {
                        val += two;
                    }
                    if (tab[i][j] == "O" && tab[i+1][j+1] == "O" && tab[i+2][j+2] == "O") {
                        val += three;
                    }
                    if (tab[i][j] == "O" && tab[i+1][j+1] == "O" && tab[i+2][j+2] == "O" && tab[i+3][j+3] == "O") {
                        val += four;
                    }
                    if (tab[i][j] == "O" && tab[i+1][j+1] == "O" && tab[i+2][j+2] == "O" && tab[i+3][j+3] == "O" && tab[i+4][j+4] == "O") {
                        val += five;
                    }

                    // tzw 'klejenie sie do gry'
                    if (((tab[i][j] == "X" && tab[i+1][j+1] == "O")) || ((tab[i][j] == "O" && tab[i+1][j+1] == "X"))) {
                        val += glue;
                    }

                    //open3
                    if (tab[i][j] == "" && tab[i+1][j+1] == "O" && tab[i+2][j+2] == "O" && tab[i+3][j+3] == "O" && tab[i+4][j+4] == "") {
                        val += openThree;
                    }

                    //open4
                    if(j+5 < 10) {
                        if (tab[i][j] == "" && tab[i+1][j+1] == "O" && tab[i+2][j+2] == "O" && tab[i+3][j+3] == "O" && tab[i+4][j+4] == "O" && tab[i+5][j+5] == "") {
                            val += openFour;
                        }
                    }

                }
            }
        }




        //Skosy2
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if(i + 4 < 10 && j - 4 >= 0) {
                    if (tab[i][j] == "O" && tab[i+1][j-1] == "O") {
                        val += two;
                    }
                    if (tab[i][j] == "O" && tab[i+1][j-1] == "O" && tab[i+2][j-2] == "O") {
                        val += three;
                    }
                    if (tab[i][j] == "O" && tab[i+1][j-1] == "O" && tab[i+2][j-2] == "O" && tab[i+3][j-3] == "O") {
                        val += four;
                    }
                    if (tab[i][j] == "O" && tab[i+1][j-1] == "O" && tab[i+2][j-2] == "O" && tab[i+3][j-3] == "O" && tab[i+4][j-4] == "O") {
                        val += five;
                    }

                    // tzw 'klejenie sie do gry'
                    if (((tab[i][j] == "X" && tab[i+1][j-1] == "O")) || ((tab[i][j] == "O" && tab[i+1][j-1] == "X"))) {
                        val += glue;
                    }

                    //open3
                    if (tab[i][j] == "" && tab[i+1][j-1] == "O" && tab[i+2][j-2] == "O" && tab[i+3][j-3] == "O" && tab[i+4][j-4] == "") {
                        val += openThree;
                    }

                    //open4
                    if(i+5 < 10 && j-5 >= 0) {
                        if (tab[i][j] == "" && tab[i+1][j-1] == "O" && tab[i+2][j-2] == "O" && tab[i+3][j-3] == "O" && tab[i+4][j-4] == "O" && tab[i+5][j-5] == "") {
                            val += openFour;
                        }
                    }

                }
            }
        }



        return val;
    }
}