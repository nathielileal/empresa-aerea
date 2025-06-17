package com.ms.funcionario.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.funcionario.dto.FuncionarioDTO;
import com.ms.funcionario.services.FuncionarioService;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FuncionarioListener {

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = "funcionario.create")
    public void receberCreate(String funcionarioJson) {
        try {
            FuncionarioDTO funcionarioDTO = objectMapper.readValue(funcionarioJson, FuncionarioDTO.class);

            funcionarioService.saveFuncionario(funcionarioDTO);
        } catch (IllegalArgumentException e) {
            System.err.println("Erro de validação ao criar funcionário: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro ao criar funcionário: " + e.getMessage());

            throw new AmqpRejectAndDontRequeueException("Erro na criação do funcionário", e);
        }
    }

    @RabbitListener(queues = "funcionario.update")
    public String receberUpdate(String funcionarioJson) {
        try {
            FuncionarioDTO funcionarioDTO = objectMapper.readValue(funcionarioJson, FuncionarioDTO.class);

            if (funcionarioDTO.getCodigo() == null) {
                return "{\"erro\":\"ID do funcionário é obrigatório para atualização.\"}";
            }

            FuncionarioDTO funcionarioAtualizado = funcionarioService.updateFuncionario(funcionarioDTO.getCodigo(), funcionarioDTO);

            return objectMapper.writeValueAsString(funcionarioAtualizado);
        } catch (Exception e) {
            System.err.println("Erro ao atualizar funcionário: " + e.getMessage());

            throw new AmqpRejectAndDontRequeueException("Erro na atualização do funcionário", e);
        }
    }

    @RabbitListener(queues = "funcionario.delete")
    public String receberDelete(String id) {
        try {
            Long funcionarioId = Long.parseLong(id);

            funcionarioService.deleteFuncionario(funcionarioId);

            return "{\"status\":\"Funcionário deletado com sucesso.\"}";
        } catch (NumberFormatException e) {
            return "{\"erro\":\"ID inválido.\"}";
        } catch (Exception e) {
            System.err.println("Erro ao deletar funcionário: " + e.getMessage());

            throw new AmqpRejectAndDontRequeueException("Erro na exclusão do funcionário", e);
        }
    }

}
