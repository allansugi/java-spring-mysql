import { AppBar, Box, Button, Container, IconButton, Toolbar } from "@mui/material";
import SecurityIcon from '@mui/icons-material/Security';
import { useNavigate } from "react-router-dom";

export function NavBar() {

  const navigate = useNavigate();

  const navigateLogin = () => {
    navigate('/login');
  }

  const navigateHome = () => {
    navigate('/home')
  }

  const navigatePasswordVault = () => {
    navigate('/passwords');
  }

  return (
      <AppBar position="static">
        <Container>
          <Toolbar>
            <Box sx={{flexGrow: 1}}>
              <IconButton color='inherit' onClick={navigateHome}>
                <SecurityIcon />InfoKey
              </IconButton>
              
              <Button color="inherit" onClick={navigatePasswordVault}>Password Vault</Button>
              <Button color="inherit">Notes Vault</Button>
              <Button color="inherit">Generator</Button>
            </Box>
            <Box>
              <Button color="inherit" onClick={navigateLogin}>Login</Button>
            </Box>
          </Toolbar>
        </Container>
      </AppBar>
    
  )
}