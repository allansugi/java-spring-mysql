import { IconButton, Snackbar, Menu, MenuItem } from "@mui/material";
import React from "react";
import MoreVertIcon from '@mui/icons-material/MoreVert';
import ContentCopyIcon from '@mui/icons-material/ContentCopy';
import DeleteIcon from '@mui/icons-material/Delete';
import CloseIcon from '@mui/icons-material/Close';
import { Account } from "./types/Account";
import { SnackbarMessage } from "./types/SnackBarMessage";

export function IconMenu({ account }: { account: Account }) {
    const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);
    const open = Boolean(anchorEl);

    const [alert, setAlert] = React.useState(false);
    const [alertMessage, setAlertMessage] = React.useState<SnackbarMessage | undefined>(
        undefined,
    );
    const [snackPack, setSnackPack] = React.useState<readonly SnackbarMessage[]>([]);

    const handleCloseAlert = (event: React.SyntheticEvent | Event, reason?: string) => {
        if (reason === 'clickaway') {
            return;
        }
        setAlert(false);
    };

    const action = (
        <React.Fragment>
        <IconButton
            size="small"
            aria-label="close"
            color="inherit"
            onClick={handleCloseAlert}
        >
            <CloseIcon fontSize="small" />
        </IconButton>
        </React.Fragment>
    );

    React.useEffect(() => {
        if (snackPack.length && !alertMessage) {
            // Set a new snack when we don't have an active one
            setAlertMessage({ ...snackPack[0] });
            setSnackPack((prev) => prev.slice(1));
            setAlert(true);
        } else if (snackPack.length && alertMessage && alert) {
            // Close an active snack when a new one is added
            setAlert(false);
        }
    }, [snackPack, alertMessage, alert])

    const handleClick = (event: React.MouseEvent<HTMLButtonElement>) => {
        setAnchorEl(event.currentTarget);
    }

    const handleClickAlert = (message: string) => {
        setSnackPack((prev) => [...prev, { message, key: new Date().getTime() }]);
    }

    const handleClose = () => {
        setAnchorEl(null);
    }

    // only works on browser
    const handleCopyPassword = () => {
        navigator.clipboard.writeText(account.password)
        handleClickAlert("Password Copied")
        handleClose();
    }

    // only works on browser
    const handleCopyUsername = () => {
        navigator.clipboard.writeText(account.username)
        handleClickAlert("Username Copied")
        handleClose();
    }

    const handleDelete = () => {
        handleClickAlert("Account has been Deleted");
        handleClose();
    }

    const handleExited = () => {
        setAlertMessage(undefined);
    };

    return (
        <div>
            <Snackbar
                key={alertMessage ? alertMessage.key : undefined}
                open={alert}
                autoHideDuration={6000}
                TransitionProps={{ onExited: handleExited }}
                onClose={handleCloseAlert}
                message={alertMessage ? alertMessage.message : undefined}
                action={action}
            />

            <IconButton onClick={handleClick}>
                <MoreVertIcon/>
            </IconButton>
            
            <Menu anchorEl={anchorEl} open={open} onClose={handleClose}>
                <MenuItem onClick={handleDelete}>
                    <DeleteIcon />
                    Delete Account
                </MenuItem>
                <MenuItem onClick={handleCopyPassword}>
                    <ContentCopyIcon />
                    Copy Password
                </MenuItem>
                <MenuItem onClick={handleCopyUsername}>
                    <ContentCopyIcon />
                    Copy Username
                </MenuItem>
            </Menu>
        </div>
    );
}