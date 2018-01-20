package ug.gomoku.gomoku.ui;


import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.UI;

import java.util.Random;

@SpringUI
@Title("Gomoku")
public class GomokuUi extends UI {

    private GridLayout gridLayout;
	private int i,j;
	private Cell cell;
	private String mark;

	@Override
	protected void init(VaadinRequest vaadinRequest) {

	    gridLayout = new GridLayout(10,10);
        mark = "X";

        for (i = 0 ; i < 10; i++){
            for (j = 0 ; j < 10 ; j++) {
                cell = new Cell();
                cell.setX(i);
                cell.setY(j);
                cell.setHeight(50, Unit.PIXELS);
                cell.setWidth(50, Unit.PIXELS);
                cell.addClickListener(event -> {
                    event.getComponent().setCaption(mark);
                    event.getButton().setEnabled(false);
                    if(chechWin(gridToTab(gridLayout),mark)) {
                        gridLayout.setEnabled(false);
                    }

                    randomComputerMove(gridLayout);

                    if(chechWin(gridToTab(gridLayout),"O")) {
                        gridLayout.setEnabled(false);
                    }

                });


                gridLayout.addComponent(cell, i, j);

            }
        }

        gridLayout.setSpacing(false);
        setContent(gridLayout);




	}

	boolean chechWin(String[][] tab,String mark) {

        for (i = 0 ; i < 10; i++) {
            for (j = 0; j < 10; j++) {
                if(j+4 < 10) {
                    if (tab[i][j] == mark && tab[i][j+1] == mark && tab[i][j+2] == mark && tab[i][j+3] == mark && tab[i][j+4] == mark) {
                        return true;
                    }
                }
            }
        }

        for (i = 0 ; i < 10; i++) {
            for (j = 0; j < 10; j++) {
                if(i+4 < 10) {
                    if (tab[i][j] == mark && tab[i+1][j] == mark && tab[i+2][j] == mark && tab[i+3][j] == mark && tab[i+4][j] == mark) {
                        return true;
                    }
                }
            }
        }

        for (i = 0 ; i < 10; i++) {
            for (j = 0; j < 10; j++) {
                if(j+4 < 10 && i+4 < 10) {
                    if (tab[i][j] == mark && tab[i+1][j+1] == mark && tab[i+2][j+2] == mark && tab[i+3][j+3] == mark && tab[i+4][j+4] == mark) {
                        return true;
                    }
                }
            }
        }

        for (i = 0 ; i < 10; i++) {
            for (j = 0; j < 10; j++) {
                if(i+4 < 10 && j-4 >= 0) {
                    if (tab[i][j] == mark && tab[i+1][j-1] == mark && tab[i+2][j-2] == mark && tab[i+3][j-3] == mark && tab[i+4][j-4] == mark) {
                        return true;
                    }
                }
            }
        }


	    return false;
    }

    void move(GridLayout gridLayout,int i, int j) {
	    gridLayout.getComponent(i,j).setCaption("O");
    }

    void randomComputerMove(GridLayout gridLayout) {
        Random randomGenerator = new Random();
	    int i;
	    int j;
	    boolean t = true;
	    while (t == true) {
            i = randomGenerator.nextInt(10);
            j = randomGenerator.nextInt(10);

            if(gridLayout.getComponent(i,j).getCaption() != "X" && gridLayout.getComponent(i,j).getCaption() != "O" ) {
                t = false;
                move(gridLayout,i,j);
                gridLayout.getComponent(i,j).setEnabled(false);
            }
        }


    }

    String[][] gridToTab(GridLayout gridLayout) {
	    String[][] tab = new String[10][10];

        for (i = 0 ; i < 10; i++) {
            for (j = 0; j < 10; j++) {
                if(gridLayout.getComponent(i,j).getCaption() == "X") {
                    tab[i][j] = "X";
                } else if (gridLayout.getComponent(i,j).getCaption() == "O") {
                    tab[i][j] = "O";
                } else {
                    tab[i][j] = "";
                }
            }
        }

        return tab;
    }



}