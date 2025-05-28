package mscliente.mscliente.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mscliente.mscliente.DTO.ClienteDTO;
import mscliente.mscliente.model.Cliente;
import mscliente.mscliente.repository.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;
    @Autowired
    private ModelMapper mapper;

    public ClienteDTO saveCliente(ClienteDTO clienteDTO) {
        try {
            return mapper.map(repository.save(mapper.map(clienteDTO, Cliente.class)), ClienteDTO.class);
        } catch (Exception e) {
            // Log e rethrow — melhor para debug
            throw new RuntimeException("Erro ao salvar cliente: " + e.getMessage(), e);
        }
    }

    public List<Cliente> listarClientes() {
        return repository.findAll();
    }
}
