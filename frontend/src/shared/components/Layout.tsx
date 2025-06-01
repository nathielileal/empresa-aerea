import React from "react";
import { Outlet, useNavigate } from "react-router-dom";
import {
  AppBar,
  Box,
  CssBaseline,
  Divider,
  Drawer,
  IconButton,
  List,
  ListItem,
  ListItemButton,
  ListItemIcon,
  ListItemText,
  Toolbar,
  Typography,
} from "@mui/material";
import HomeIcon from "@mui/icons-material/Home";
import MenuIcon from "@mui/icons-material/Menu";
import FlightIcon from "@mui/icons-material/Flight";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import AttachMoneyIcon from "@mui/icons-material/AttachMoney";
import LogoutIcon from "@mui/icons-material/Logout";
import FlightTakeoffIcon from '@mui/icons-material/FlightTakeoff';
import { useAuth } from "../contexts/AuthContext";

const drawerWidth = 240;

interface LayoutProps {
  tipo: string;
}

export default function Layout({ tipo }: LayoutProps) {
  
  const [mobileOpen, setMobileOpen] = React.useState(false);
  const navigate = useNavigate();

  const handleDrawerToggle = () => {
    setMobileOpen(!mobileOpen);
  };

  const { logout } = useAuth();

  const menuClientItens = [
    { text: "Página inicial", icon: <HomeIcon />, path: "initial-page" },
    { text: "Perfil", icon: <AccountCircleIcon />, path: "meu-perfil" },
    { text: "Reservar Voo", icon: <FlightIcon />, path: "reservar" },
    { text: "Milhas", icon: <AttachMoneyIcon />, path: "milhas" },
    { text: "Checkin", icon: <FlightTakeoffIcon />, path: "checkin" }
  ];

  const menuFuncionarios = [
    { text: "Página inicial", icon: <HomeIcon />, path: "initial-page" },
    { text: "Funcionários", icon: <AccountCircleIcon />, path: "/funcionario/crud" },
    { text: "Voos", icon: <FlightIcon />, path: "/funcionario/voos" },
  ];

  const menuItens = tipo === "CLIENTE" ? menuClientItens : menuFuncionarios;

  const drawer = (
    <Box display="flex" flexDirection="column" height="100%">
      <Box>
        <Toolbar>
          <Typography variant="h6">✈️</Typography>
        </Toolbar>
        <Divider />
        <List>
          {menuItens.map((item) => (
            <ListItem key={item.text} disablePadding>
              <ListItemButton onClick={() => navigate(item.path)}>
                <ListItemIcon>{item.icon}</ListItemIcon>
                <ListItemText primary={item.text} />
              </ListItemButton>
            </ListItem>
          ))}
        </List>
      </Box>
      <Box mt="auto">
        <Divider />
        <List>
          <ListItem disablePadding>
            <ListItemButton onClick={logout}>
              <ListItemIcon>
                <LogoutIcon />
              </ListItemIcon>
              <ListItemText primary="Sair" />
            </ListItemButton>
          </ListItem>
        </List>
      </Box>
    </Box>
  );

  return (
    <Box sx={{ display: "flex" }}>
      <CssBaseline />
      <AppBar
        position="fixed"
        sx={{
          width: { sm: `calc(100% - ${drawerWidth}px)` },
          ml: { sm: `${drawerWidth}px` },
        }}
      >
        <Toolbar>
          <IconButton
            color="inherit"
            aria-label="abrir menu"
            edge="start"
            onClick={handleDrawerToggle}
            sx={{ mr: 2, display: { sm: "none" } }}
          >
            <MenuIcon />
          </IconButton>
          <Typography variant="h6" noWrap component="div">
            Área do {tipo == 'client' ? 'cliente' : 'funcionário'}
          </Typography>
        </Toolbar>
      </AppBar>

      <Box
        component="nav"
        sx={{ width: { sm: drawerWidth }, flexShrink: { sm: 0 } }}
        aria-label="menu lateral"
      >
        <Drawer
          variant="temporary"
          open={mobileOpen}
          onClose={handleDrawerToggle}
          ModalProps={{ keepMounted: true }}
          sx={{
            display: { xs: "block", sm: "none" },
            "& .MuiDrawer-paper": { boxSizing: "border-box", width: drawerWidth },
          }}
        >
          {drawer}
        </Drawer>
        <Drawer
          variant="permanent"
          sx={{
            display: { xs: "none", sm: "block" },
            "& .MuiDrawer-paper": { boxSizing: "border-box", width: drawerWidth },
          }}
          open
        >
          {drawer}
        </Drawer>
      </Box>

      <Box
        component="main"
        sx={{
          flexGrow: 1,
          p: 3,
          width: { sm: `calc(100% - ${drawerWidth}px)` },
          minHeight: "100vh",
          backgroundColor: "#f5f5f5",
        }}
      >
        <Toolbar />
        <Outlet />
      </Box>
    </Box>
  );
}
