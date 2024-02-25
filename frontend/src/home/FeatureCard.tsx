import { Button, Card, CardActions, CardContent, CardMedia, Typography } from "@mui/material";

// add props which includes image, title and content
export default function FeatureCard() {
    return (
        <Card sx={{ maxWidth: 345, p: 2, m: 2 }} elevation={6}>
            <CardMedia
            sx={{ height: 140 }}
            image="src/assets/1673316.jpg"
            title="green iguana"
            />
            <CardContent>
            <Typography gutterBottom variant="h5" component="div">
                Password Vault
            </Typography>
            <Typography variant="body2" color="text.secondary">
                Don't remember your password? We got it for you.
                Password Vault allows user to store their account info
                and then copy it securely. Let InfoKey be your second brain.
            </Typography>
            </CardContent>
            <CardActions>
            <Button size="small">Share</Button>
            <Button size="small">Learn More</Button>
            </CardActions>
        </Card>
    );
  }