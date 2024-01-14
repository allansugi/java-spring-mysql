package com.example.demo.form.updateUser;

public class UpdateUserPasswordForm extends UpdateUserForm{
    private final String password;

    public UpdateUserPasswordForm(String id, String password) {
        super(id);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
