package com.ms.funcionario.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.funcionario.dto.CadastroDTO;
import com.ms.funcionario.dto.FuncionarioDTO;
import com.ms.funcionario.model.Funcionario;
import com.ms.funcionario.repository.FuncionarioRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Transactional
    public FuncionarioDTO saveFuncionario(FuncionarioDTO funcionarioDTO) {
        boolean cpfExistente = repository.findByCpf(funcionarioDTO.getCpf()).isPresent();
        boolean emailExistente = repository.findByEmail(funcionarioDTO.getEmail()).isPresent();

        if (cpfExistente || emailExistente) {
            throw new IllegalArgumentException("CPF ou E-mail já cadastrados.");
        }

        Funcionario funcionario = mapper.map(funcionarioDTO, Funcionario.class);

        if (funcionario.getAtivo() == null) {
            funcionario.setAtivo(true); 
        }

        Funcionario funcionarioSalvo = repository.save(funcionario);

        CadastroDTO cadastroDTO = new CadastroDTO();
        cadastroDTO.setNome(funcionarioSalvo.getNome());
        cadastroDTO.setEmail(funcionarioSalvo.getEmail());
        cadastroDTO.setPerfil("FUNCIONARIO");

        try {
            String mensagem = new ObjectMapper().writeValueAsString(cadastroDTO);

            rabbitTemplate.convertAndSend("autocadastro", "auth", mensagem);

            System.out.println("foi");
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao serializar DTO para JSON ou enviar para RabbitMQ", e);
        } catch (AmqpException e) {
            throw new RuntimeException("Erro ao enviar mensagem para RabbitMQ", e);
        }

        return mapper.map(funcionarioSalvo, FuncionarioDTO.class);
    }

    @Transactional
    public FuncionarioDTO findByIdAtivo(Long id) {
        Funcionario funcionario = repository.findById(id).filter(Funcionario::getAtivo).orElseThrow(() -> new RuntimeException("Funcionário não encontrado com o ID: " + id));

        return mapper.map(funcionario, FuncionarioDTO.class);
    }

    @Transactional
    public Funcionario findById(Long id) {
        Funcionario funcionario = repository.findById(id).orElseThrow(() -> new RuntimeException("Funcionário não encontrado com o ID: " + id));

        if (!funcionario.getAtivo()) {
            throw new RuntimeException("Não é possível atualizar um funcionário inativo");
        }

        return funcionario;
    }

    @Transactional
    public List<FuncionarioDTO> listarFuncionarios() {
        List<Funcionario> ativos = repository.findAllByAtivoTrueOrderByNomeAsc();

        return ativos.stream().map(func -> mapper.map(func, FuncionarioDTO.class)).collect(Collectors.toList());
    }

    @Transactional
    public FuncionarioDTO findByEmail(String email) {
        Funcionario funcionario = repository.findByEmail(email).orElseThrow(() -> new RuntimeException("Funcionário não encontrado com o e-mail: " + email));

        return mapper.map(funcionario, FuncionarioDTO.class);
    }

    @Transactional
    public FuncionarioDTO updateFuncionario(Long id, FuncionarioDTO funcionarioDTO) {
        Funcionario funcionarioExistente = repository.findById(id).orElseThrow(() -> new RuntimeException("Funcionário não encontrado com o ID: " + id));

        funcionarioExistente.setNome(funcionarioDTO.getNome());
        funcionarioExistente.setEmail(funcionarioDTO.getEmail());
        funcionarioExistente.setTelefone(funcionarioDTO.getTelefone());

        try {
            return mapper.map(repository.save(funcionarioExistente), FuncionarioDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar funcionário: " + e.getMessage(), e);
        }
    }

    @Transactional
    public FuncionarioDTO deleteFuncionario(Long id) {
        Funcionario funcionario = repository.findById(id).orElseThrow(() -> new RuntimeException("Funcionário não encontrado com o ID: " + id));

        funcionario.setAtivo(false);

        Funcionario atualizado = repository.save(funcionario);

        return mapper.map(atualizado, FuncionarioDTO.class);
    }
}
