import  {MainLayout}  from './shared/components/main-layout';
import  {UserContextProvider}  from './shared/contexts/UserContext';
import { Outlet } from 'react-router-dom';
import { createTheme } from '@mui/material';
import { ThemeProvider } from '@emotion/react';
import { defaultThemeOptions } from './themes';
import Home from './pages/home';

const customTheme = createTheme(defaultThemeOptions);

function App() {
  return (
    <UserContextProvider>
      <ThemeProvider theme={customTheme}>
        <MainLayout>
          <Outlet />
        </MainLayout>
      </ThemeProvider>
    </UserContextProvider>
  );
}

export default App;