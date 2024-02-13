package com.tddninja.model;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class UserView {

    private TextField id;
    private TextField name;
    private TextField email;
    private Button delete;

    public UserView() {
    }

    public UserView(TextField id, TextField name, TextField email, Button delete) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.delete = delete;
    }

    public TextField getId() {
        return id;
    }

    public void setId(TextField id) {
        this.id = id;
    }

    public TextField getName() {
        return name;
    }

    public void setName(TextField name) {
        this.name = name;
    }

    public TextField getEmail() {
        return email;
    }

    public void setEmail(TextField email) {
        this.email = email;
    }

    public Button getDelete() {
        return delete;
    }

    public void setDelete(Button delete) {
        this.delete = delete;
    }
}
