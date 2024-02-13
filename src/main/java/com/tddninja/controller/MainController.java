package com.tddninja.controller;

import com.tddninja.model.DBConnUtil;
import com.tddninja.model.User;
import com.tddninja.model.UserView;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainController implements Initializable {

    @FXML
    private Label inputId;

    @FXML
    private TextField inputName;

    @FXML
    private TextField inputEmail;

    @FXML
    private TableView<UserView> userTable;

    @FXML
    public TableColumn<UserView, Long> id;

    @FXML
    public TableColumn<UserView, TextField> name;

    @FXML
    public TableColumn<UserView, TextField> email;

    @FXML
    public TableColumn<UserView, Button> delete;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItem1 = new MenuItem("Option 1");
        MenuItem menuItem2 = new MenuItem("Option 2");
        menuItem1.setOnAction(e -> {
            // Code to execute when menuItem1 is clicked
            System.out.println("Option 1 selected");
        });

        menuItem2.setOnAction(e -> {
            // Code to execute when menuItem2 is clicked
            System.out.println("Option 2 selected");
        });

        contextMenu.getItems().addAll(menuItem1, menuItem2);

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        delete.setCellValueFactory(new PropertyValueFactory<>("delete"));
        userTable.setRowFactory(tv -> {
            TableRow<UserView> row = new TableRow<>();
            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu)
            );
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    UserView rowData = row.getItem();
                    inputId.setText(rowData.getId().getText());
                    inputName.setText(rowData.getName().getText());
                    inputEmail.setText(rowData.getEmail().getText());
                }
            });
            return row;
        });
    }

    @FXML
    protected void onCreateUser() {
        Session session = DBConnUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.persist(new User(inputName.getText(), inputEmail.getText()));
        session.getTransaction().commit();
        session.close();

        onReadUsers();
    }

    @FXML
    protected void onReadUsers() {
        Session session = DBConnUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<User> users = session.createQuery("from User", User.class).list();
        session.getTransaction().commit();
        session.close();
        ObservableList<UserView> userViews = users.stream().map(user -> {
            Button deleteButton = new Button("delete");
            deleteButton.setOnAction(e -> onDeleteUser(user));
            TextField id = new TextField(String.valueOf(user.getId()));
            id.disableProperty().setValue(true);
            id.setStyle("-fx-opacity: 1.0; -fx-border-color: transparent; -fx-background-color: transparent; -fx-border-width: 0; -fx-background-insets: 0;");
            TextField name = new TextField(user.getName());
            name.disableProperty().setValue(true);
            name.setStyle("-fx-opacity: 1.0; -fx-border-color: transparent; -fx-background-color: transparent; -fx-border-width: 0; -fx-background-insets: 0;");
            TextField email = new TextField(user.getEmail());
            email.disableProperty().setValue(true);
            email.setStyle("-fx-opacity: 1.0; -fx-border-color: transparent; -fx-background-color: transparent; -fx-border-width: 0; -fx-background-insets: 0;");
            return new UserView(id, name, email, deleteButton);
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        userTable.setItems(userViews);
    }

    @FXML
    protected void onUpdateUser(ActionEvent actionEvent) {
        Session session = DBConnUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.merge(new User(Long.parseLong(inputId.getText()), inputName.getText(), inputEmail.getText()));
        session.getTransaction().commit();
        session.close();
        onReadUsers();
    }

    private void onDeleteUser(User user) {
        Session session = DBConnUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.remove(user);
        session.getTransaction().commit();
        session.close();
        onReadUsers();
    }

}