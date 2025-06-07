package mscliente.mscliente.services;

import java.util.List;

import javax.management.RuntimeErrorException;

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
        boolean cpfExistente = repository.findByCpf(clienteDTO.getCpf()).isPresent();
        boolean emailExistente = repository.findByEmail(clienteDTO.getEmail()).isPresent();
    
        if (cpfExistente || emailExistente) {
            throw new IllegalArgumentException("CPF ou e-mail já cadastrados.");
        }
    
        try {
            Cliente cliente = mapper.map(clienteDTO, Cliente.class);
            return mapper.map(repository.save(cliente), ClienteDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar cliente: " + e.getMessage(), e);
        }
    }
    
    public ClienteDTO findById(Long id) {
        Cliente cliente = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o ID: " + id));
        return mapper.map(cliente, ClienteDTO.class);
    }
    

    public List<Cliente> listarClientes() {
        return repository.findAll();
    }

    public ClienteDTO findByEmail(String email) {
        Cliente cliente = repository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o e-mail: " + email));
        if (cliente != null) {
            return mapper.map(cliente, ClienteDTO.class);
        }

        throw new RuntimeException("Cliente não encontrado com o ID: " + email);
    }

}
