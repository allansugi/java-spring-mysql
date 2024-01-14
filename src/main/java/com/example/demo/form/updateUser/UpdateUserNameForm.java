package com.example.demo.form.updateUser;

public class UpdateUserNameForm extends UpdateUserForm{
    private final String username;
    public UpdateUserNameForm(String id, String username) {
        super(id);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
