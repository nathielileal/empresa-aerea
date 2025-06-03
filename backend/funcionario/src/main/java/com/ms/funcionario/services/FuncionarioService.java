package com.ms.funcionario.services;

import com.ms.funcionario.dto.FuncionarioDTO;
import com.ms.funcionario.model.Funcionario;
import com.ms.funcionario.repository.FuncionarioRepository;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository repository;
    
    @Autowired
    private ModelMapper mapper;

    public FuncionarioDTO saveFuncionario(FuncionarioDTO funcionarioDTO) {
        boolean cpfExistente = repository.findByCpf(funcionarioDTO.getCpf()).isPresent();
        boolean emailExistente = repository.findByEmail(funcionarioDTO.getEmail()).isPresent();

        if (cpfExistente || emailExistente) {
            throw new IllegalArgumentException("CPF ou E-mail já cadastrados.");
        }

        try {
            Funcionario funcionario = mapper.map(funcionarioDTO, Funcionario.class);
            return mapper.map(repository.save(funcionario), FuncionarioDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar funcionário: " + e.getMessage(), e);
        }
    }

    public FuncionarioDTO findById(Long id) {
        Funcionario funcionario = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com o ID: " + id));
        return mapper.map(funcionario, FuncionarioDTO.class);
    }

    public List<Funcionario> listarFuncionarios() {
        return repository.findAll();
    }

    public FuncionarioDTO findByEmail(String email) {
        Funcionario funcionario = repository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com o e-mail: " + email));
        return mapper.map(funcionario, FuncionarioDTO.class);
    }

    public FuncionarioDTO updateFuncionario(Long id, FuncionarioDTO funcionarioDTO) {
        Funcionario funcionarioExistente = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com o ID: " + id));

        funcionarioExistente.setNome(funcionarioDTO.getNome());
        funcionarioExistente.setEmail(funcionarioDTO.getEmail());
        funcionarioExistente.setTelefone(funcionarioDTO.getTelefone());

        try {
            return mapper.map(repository.save(funcionarioExistente), FuncionarioDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar funcionário: " + e.getMessage(), e);
        }
    }

    public void deleteFuncionario(Long id) {
        Funcionario funcionario = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com o ID: " + id));

        repository.delete(funcionario);
    }

}

