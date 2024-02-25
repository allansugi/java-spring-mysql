import { Dialog, DialogTitle, DialogContent, Stack, TextField, DialogActions, Button, FilledInput, IconButton, InputAdornment, OutlinedInput, Alert } from "@mui/material";
import React from "react";
import { Visibility, VisibilityOff } from "@mui/icons-material";

export default function NewAccountDialog() {

    const [open, setOpen] = React.useState(false);
    const [showPassword, setShowPassword] = React.useState(false);

    const [accountName, setAccountName] = React.useState("");
    const [username, setUsername] = React.useState("");
    const [password, setPassword] = React.useState("");
    const [error, setError] = React.useState(false);

    const handleChangeAccountName = (e: React.ChangeEvent<HTMLInputElement>) => {
        setAccountName(e.target.value);
    }

    const handleChangeUsername = (e: React.ChangeEvent<HTMLInputElement>) => {
        setUsername(e.target.value);
    }

    const handleChangePassword = (e: React.ChangeEvent<HTMLInputElement>) => {
        setPassword(e.target.value);
    }

    const handleClickVisbility = () => {
        setShowPassword(((show) => !show));
    }

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setError(false);
        setOpen(false);
    }

    const handleSubmit = () => {
        console.log(accountName);
        console.log(username);
        console.log(password);
        if (accountName.length === 0 || username.length === 0 || password.length === 0) {
            setError(true);
        } else {
            setError(false);
            setOpen(false);
        }
    };

    return (
        <React.Fragment>
            <Button variant="contained" sx={{padding: "10px"}} onClick={handleClickOpen}>New Account</Button>

            <Dialog open={open} fullWidth={true}>
                <DialogTitle>New Account</DialogTitle>
                <DialogContent>
                {error && <Alert severity="error">Make sure account details are filled</Alert>}
                    <Stack sx={{paddingTop:"20px"}} spacing={2} component="form" justifyContent="center">
                        <TextField
                            error={accountName.length === 0}
                            required
                            onChange={handleChangeAccountName}
                            helperText="e.g: Github, Facebook, Google, etc"
                            label="Account Name"
                        />
                        <TextField
                            error={username.length === 0}
                            label="Username"
                            helperText="Can be email or username"
                            onChange={handleChangeUsername}
                            required 
                        />
                        <TextField
                            required
                            error={password.length === 0}
                            label="Password"
                            onChange={handleChangePassword}
                            type={showPassword ? 'text' : 'password'}
                            InputProps={{
                                endAdornment:
                                    <InputAdornment position="end">
                                        <IconButton onClick={handleClickVisbility}>
                                            {showPassword ? <Visibility /> : <VisibilityOff />}
                                        </IconButton>
                                    </InputAdornment>
                            }} 
                        />
                    </Stack>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose}>Cancel</Button>
                    <Button type="submit" onClick={handleSubmit}>Submit</Button>
                </DialogActions>
            </Dialog>
        </React.Fragment>
    );
}