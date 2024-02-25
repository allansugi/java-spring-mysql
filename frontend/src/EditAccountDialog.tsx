import React from "react";
import { AccountResult } from "./types/Account";
import { Alert, Button, Dialog, DialogActions, DialogContent, DialogTitle, FilledInput, FormControl, IconButton, InputAdornment, InputLabel, OutlinedInput, Stack, TableCell, TableRow, TextField } from "@mui/material";
import { IconMenu } from "./IconMenu";
import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';

/**
 * Table Row Clickable for edit account
 * @param props accountResult
 * @returns TSX element
 */
export function EditAccountDialog({props}: {props: AccountResult}) {
    const { id, accountname, username, password } = props;

    const [open, setOpen] = React.useState(false);
    const [showPassword, setShowPassword] = React.useState(false);

    const [Username, setUsername] = React.useState(username);
    const [Password, setPassword] = React.useState(password);

    const [error, setError] = React.useState(false);

    const handleClickVisbility = () => {
        setShowPassword((show) => !show);
    }

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setError(false);
        setOpen(false);
    };

    const handleUsernameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setUsername(e.target.value);
    }

    const handlePasswordChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setPassword(e.target.value);
    }

    const handleSubmit = () => {
        console.log("Account Name: " + accountname);
        console.log("Username: " + Username);
        console.log("Password: " + Password);

        if (Username.length === 0 || Password.length === 0) {
            setError(true);
        } else {
            setError(false);
            setOpen(false);
        }
    }

    return (
        <React.Fragment>
            {/* replace with table row */}
            <TableRow key={id} sx={{ cursor: 'pointer' }}>
                <TableCell component="th" scope="row" onClick={handleClickOpen}>
                  {accountname}
                </TableCell>
                <TableCell  onClick={handleClickOpen}>{username}</TableCell>
                <TableCell onClick={handleClickOpen}>
                  {'*'.repeat(password.length)}
                </TableCell>
                <TableCell>
                  <IconMenu account={{accountname: accountname, username: username, password: password}}/>
                </TableCell>
            </TableRow>

            {/* may use form for dialog */}
            <Dialog open={open} fullWidth={true}>
                <DialogTitle>Edit Account</DialogTitle>
                <DialogContent>
                    {error && <Alert severity="error">Make sure account details are filled</Alert>}
                    <Stack sx={{paddingTop:"20px"}} spacing={2} component="form" justifyContent="center">
                        <TextField
                            disabled
                            label="Account Name"
                            defaultValue={accountname}
                        />
                        <TextField 
                            label="Username"
                            defaultValue={username}
                            onChange={handleUsernameChange}
                            required 
                        />
                        {/* need to choose between outlined input or textfield */}
                        <TextField
                            required
                            label="Password"
                            type={showPassword ? 'text' : 'password'}
                            defaultValue={password}
                            onChange={handlePasswordChange}
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