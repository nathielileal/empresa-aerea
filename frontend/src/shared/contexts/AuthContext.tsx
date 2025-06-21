import { createContext, useContext, ReactNode, useState, useEffect } from 'react';
import { authService } from '../../modules/auth/services/authService';
import { AuthUser } from '../../modules/auth/models/AuthTypes';



interface AuthContextType {
  user: AuthUser | null;
  isAuthenticated: boolean;
  isLoading: boolean; // Adicione isso;
  login: (user: AuthUser, access_token: string) => void;
  logout: () => void;
  refreshUser: () => Promise<void>;
  updateUser: (updatedUser: Partial<AuthUser>) => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [user, setUser] = useState<AuthUser | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const initializeAuth = async () => {
      try {

        const userData = await authService.getCurrentUser();
        setUser(userData);
      } catch {
        localStorage.removeItem('access_token');
        localStorage.removeItem('user');
      } finally {
        setIsLoading(false); // Sempre define como false quando termina
      }
    };

    initializeAuth();
  }, []);

  const refreshUser = async () => {
    try {
      const updatedUser = await authService.getCurrentUser();
      setUser(updatedUser);
      localStorage.setItem('user', JSON.stringify(updatedUser));
    } catch (error) {
      console.error('Erro ao atualizar usuário', error);
      logout(); // opcional: fazer logout se der erro
    }
  };

  const login = (userData: AuthUser, access_token: string) => {
    setUser(userData);
    localStorage.setItem('access_token', access_token);
    localStorage.setItem('user', JSON.stringify(userData));  // salva usuário
  };


  const logout = () => {
    setUser(null);
    localStorage.removeItem('access_token');
  };

  const updateUser = (updatedFields: Partial<AuthUser>) => {
    if (!user) {
      throw new Error('No user is currently logged in to update.');
    }
    setUser({ ...user, ...updatedFields });
  };
  return (
    <AuthContext.Provider value={{
      user,
      isAuthenticated: !!user,
      isLoading, // Adicione isso
      login,
      refreshUser,
      logout,
      updateUser,
    }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) throw new Error('useAuth must be used within AuthProvider');
  return context;
};