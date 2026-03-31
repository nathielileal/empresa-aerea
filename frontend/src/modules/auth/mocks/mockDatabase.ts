import { Cliente } from '../../cliente/models/ClienteTypes';
import { Funcionario } from '../../funcionario/models/FuncionarioTypes';

export type MockUser = (Cliente | Funcionario) & { password: string };

export const mockDatabase: Record<string, MockUser> = {
  "cliente@example.com": {
    id: "user-123",
    nome: "João Silva",
    email: "cliente@example.com",
    role: "client",
    password: "senha123",
    cpf: "123.456.789-00",
    cep: "80000-000",
    endereco: "Rua Exemplo, 123",
    cidade: "Curitiba",
    estado: "PR",
    saldoMilhas: 11000
  },
  "funcionario@empresa.com": {
    id: "user-456",
    nome: "Maria Souza",
    email: "funcionario@empresa.com",
    role: "employee",
    password: "senha456",
    cpf: "123.456.789-00",
    telefone: "11111111"
  },
  "func_pre@gmail.com": {
    id: "user-789",
    nome: "Maria Souza",
    email: "func_pre@gmail.com",
    role: "employee",
    password: "TADS",
    cpf: "907.692.810-01",
    telefone: "11111111"
  }
};
