package com.tddninja.controller;

import com.tddninja.model.DBConnUtil;
import com.tddninja.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;

import java.util.List;

public class MainController {

    @FXML
    private TextField inputName;

    @FXML
    private TextField inputEmail;

    @FXML
    private TableView<User> userTable;

    @FXML
    public TableColumn<User, Long> id;

    @FXML
    public TableColumn<User, String> name;

    @FXML
    public TableColumn<User, String> email;

    @FXML
    protected void onLoadUsers() {
        Session session = DBConnUtil.getSessionFactory().openSession();
        session.beginTransaction();

        List<User> users = session.createQuery("from User", User.class).list();

        session.getTransaction().commit();
        session.close();

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));

        ObservableList<User> usersData = FXCollections.observableArrayList(users);
        userTable.setItems(usersData);
    }

    public void onAddUser() {
        Session session = DBConnUtil.getSessionFactory().openSession();
        session.beginTransaction();

        User newUser = new User(inputName.getText(), inputEmail.getText());
        session.persist(newUser);
        session.getTransaction().commit();
        session.close();

        onLoadUsers();
    }
}