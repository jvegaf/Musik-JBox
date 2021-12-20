package me.jvegaf.musikbox.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import me.jvegaf.musikbox.models.CellItem;

import java.io.IOException;

public class SideBarItemCell extends ListCell<CellItem> {



    @FXML
    private Label itemTitle;

    @FXML
    private AnchorPane anchorPane;

    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(CellItem item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {

            setGraphic(null);

        } else {
            if (this.mLLoader == null) {
                this.mLLoader = new FXMLLoader(getClass().getResource("/components/SideBarItem.fxml"));
                this.mLLoader.setController(this);

                try {
                    this.mLLoader.load();
                    this.itemTitle.setText(item.getItemTitle());
                } catch (IOException e) {
                    System.out.println("error en itemCell");
                    e.printStackTrace();
                }
            }
        }

        setGraphic(anchorPane);
    }
}
