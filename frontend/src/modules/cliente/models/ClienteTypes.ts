import { User } from "../../auth/models/AuthTypes";

export interface Cliente extends User {
  cpf: string;
  endereco: Endereco;
  saldoMilhas: number;
}

export interface Endereco{
  cep: string;
  uf: string;
  cidade: string;
  bairro: string;
  rua: string;
  numero: string;
  complemento: string;
}