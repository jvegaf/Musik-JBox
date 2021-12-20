package me.jvegaf.musikbox.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeView;

import java.net.URL;
import java.util.ResourceBundle;

public class SidebarController implements Initializable {

    @FXML
    private TreeView playlistTree;
    @FXML
    private TreeView libraryTree;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
