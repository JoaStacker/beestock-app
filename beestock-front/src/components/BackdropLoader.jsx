import {Backdrop, CircularProgress} from "@mui/material";
import {useGlobalContext} from "../context/GlobalContext";

export default function BackdropLoader() {
    const { globalState, updateGlobalState } = useGlobalContext();
    return (
        <Backdrop
            sx={(theme) => ({ color: '#fff', zIndex: theme.zIndex.drawer + 1 })}
            open={globalState.loadingPage}
        >
            <CircularProgress color="inherit" />
        </Backdrop>
    );
}