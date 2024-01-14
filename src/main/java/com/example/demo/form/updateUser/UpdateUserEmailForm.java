package com.example.demo.form.updateUser;

public class UpdateUserEmailForm extends UpdateUserForm{
    private final String email;
    public UpdateUserEmailForm(String id, String email) {
        super(id);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
