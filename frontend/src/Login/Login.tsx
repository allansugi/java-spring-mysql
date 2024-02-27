import { Alert, Button, Container, FormControlLabel, FormGroup, IconButton, InputAdornment, Paper, Stack, Switch, TextField, Typography } from "@mui/material";
import React from 'react';
import { Visibility, VisibilityOff } from '@mui/icons-material';
import { useNavigate } from "react-router-dom";

/**
 * The login page includes both login and register.
 * when filling in details don't rely on autofill.
 */
export default function Login() {

    const navigate = useNavigate();

    const [error, setError] = React.useState(false);
    const [isRegister, setIsRegister] = React.useState(false);
    const [showPassword, setShowPassword] = React.useState(false);

    const [email, setEmail] = React.useState("");
    // when login, username can also be an email address
    const [username, setUsername] = React.useState("");
    const [password, setPassword] = React.useState("");

    const handleEmailChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setEmail(e.target.value);
    }

    const handleUsernameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setUsername(e.target.value);
    }

    const handlePasswordChange= (e: React.ChangeEvent<HTMLInputElement>) => {
        setPassword(e.target.value);
    }

    const handleClickVisbility = () => {
        setShowPassword(((show) => !show));
    }

    const handleChangeRegister = () => {
        setIsRegister(true);
    }

    const handleSubmit = async () => {
        if (!isRegister && username.length !== 0 && password.length !== 0) {
            // TODO: use API related to login
            try {
                const response = await fetch("http://localhost:8080/api/user/login", {
                    method: 'POST',
                    mode: 'cors',
                    headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        email: username,
                        password: password
                    })
                });

                if (response.ok) {
                    setError(false);
                    navigate("/home");
                } else {
                    setError(true);
                    return;
                }
            } catch (error) {
                console.log(error);
                return;
            }

            navigate('/home');
        } else if (isRegister && email.length !== 0 && username.length !== 0 && password.length !== 0) {
            // TODO: use API related to register
            try {
                const response = await fetch("http://localhost:8080/api/user/register", {
                    method: 'POST',
                    mode: 'cors',
                    headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        username: username,
                        email: email,
                        password: password
                    })
                });
                console.log(response);
            } catch (error) {
                console.log(error);
                return;
            }

            setEmail("");
            setUsername("");
            setPassword("");
            setIsRegister(false);
            setError(false);
        } else {
            setError(true);
        }
    }

    return (
        <Container maxWidth='sm' sx={{marginTop: 8}}>
            <Paper elevation={6} sx={{marginTop: 2}}>
                <Container sx={{padding: "3rem"}}>
                    <Stack spacing={3}>
                        <Typography variant='h4' textAlign="center">{isRegister ? "Register New Account" : "Login to your account"}</Typography>
                        <Alert severity='warning'>
                            Make sure that you remember your credential information 
                            because there is currently no other way to recover your account.
                        </Alert>
                        {error && <Alert severity='error'>
                            incorrect username, email or password
                        </Alert>}
                        {isRegister && <TextField onChange={handleEmailChange} required variant="outlined" label="Email" />}
                        <TextField onChange={handleUsernameChange} required variant="outlined" label={isRegister ? "Username" : "Username or Email"}/>
                        <TextField
                            onChange={handlePasswordChange}
                            required
                            label="Password"
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
                        <Button onClick={handleSubmit} variant='contained'>{isRegister ? "REGISTER" : "LOGIN"}</Button>
                        {!isRegister && <Typography>No account, create new one <a onClick={handleChangeRegister} href="#">here</a></Typography>}
                    </Stack>
                </Container>
            </Paper>
        </Container>
        
    )
}