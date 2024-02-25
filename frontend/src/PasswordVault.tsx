import { Button, Container, InputAdornment, Stack, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, TextField, Typography } from "@mui/material";
import SearchIcon from '@mui/icons-material/Search';
import React from "react";
import { AccountResult } from "./types/Account";
import { rows } from "./sample";
import { EditAccountDialog } from "./EditAccountDialog";
import NewAccountDialog from "./NewAccountDialog";

export default function PasswordVault() {
  const [searchResult, setSearchResult] = React.useState<AccountResult[]>(rows);

  const handleSearch = (searchQuery: string) => {
    if (!searchQuery.length) {
      setSearchResult(rows);
    } else {
      setSearchResult([...rows].filter((row) => {
        return row.accountname.toLowerCase().includes(searchQuery.toLowerCase()) || 
              row.username.toLowerCase().includes(searchQuery.toLowerCase())
      }));
    }
  }

  return (
    <TableContainer component={Container} sx={{padding: "20px"}}>
        <Stack spacing={2}>
          <Typography variant="h4">Password Vaults</Typography>
          <Stack direction="row" spacing={2}>
              <TextField
                id="filled-search"
                label="Search field"
                type="search"
                variant="outlined"
                onChange={(e) => handleSearch(e.target.value)}
                InputProps={{
                  startAdornment: (
                    <InputAdornment position="start">
                      <SearchIcon />
                    </InputAdornment>
                  ),
                }}
              />
            <NewAccountDialog />
          </Stack>
        </Stack>
      <Table aria-label="password vault table">
        <TableHead>
          <TableRow>
            <TableCell><h3>ACCOUNT NAME</h3></TableCell>
            <TableCell><h3>USERNAME</h3></TableCell>
            <TableCell><h3>PASSWORD</h3></TableCell>
            <TableCell></TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {searchResult.map((row) => (
            <EditAccountDialog key={row.id} props={{
              id: row.id, 
              accountname: row.accountname, 
              username: row.username, 
              password: row.password}}
            />
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  )
}