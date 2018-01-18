package ug.gomoku.gomoku.ui;


import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;

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
                cell.setDescription(""+i+j);
                cell.setHeight(50, Unit.PIXELS);
                cell.setWidth(50, Unit.PIXELS);
                cell.addClickListener(event -> {
                    event.getComponent().setCaption(mark + event.getButton().getDescription());
                    event.getButton().setEnabled(false);
                    if (mark == "X") {
                        mark = "O";
                    } else {
                        mark = "X";
                    }
                });
                gridLayout.addComponent(cell, i, j);

                gridLayout.setEnabled(false);

            }
        }

        gridLayout.setSpacing(false);
        setContent(gridLayout);



	}

}