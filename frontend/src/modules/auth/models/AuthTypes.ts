import { Cliente } from "../../cliente/models/ClienteTypes";
import { Funcionario } from "../../funcionario/models/FuncionarioTypes";

export interface LoginFormData {
  email: string;
  senha: string;
}

export interface User {
  id?: string;
  nome: string;
  email: string;
  tipo?: UserProfile;
}
export enum UserProfile {
  CLIENTE = "CLIENTE",
  FUNCIONARIO = "FUNCIONARIO"
}
export type AuthUser = User | Cliente | Funcionario;

export interface AuthResponse {
  access_token: string;
  tokenType: string;
  tipo: string;
  usuario: AuthUser;
}

export interface RegisterFormData {
  cpf: string;
  nome: string;
  email: string;
  cep: string;
  saldoMilhas: number
}

export interface RegisterResponse {
  user: Cliente;
  temporaryPassword: string;
}