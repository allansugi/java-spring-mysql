import { Box, Typography } from "@mui/material";
import FeatureCard from "./FeatureCard";

export default function Home() {
    return (
        <>
            <Typography variant="h2" sx={{textAlign: "center"}}>Features</Typography>
            <Box sx={{display: "flex", flexWrap: "wrap", alignContent: "center", alignItems: "center"}}>
                {/* put card elements here */}
                <FeatureCard />
                <FeatureCard />
                <FeatureCard />
                <FeatureCard />
            </Box>
        </>
    )
}