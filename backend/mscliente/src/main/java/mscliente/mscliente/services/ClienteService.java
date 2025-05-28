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
            Cliente cliente = mapper.map(clienteDTO, Cliente.class);

            // Associa o cliente no endereço para manter relação bidirecional
            if (cliente.getEndereco() != null) {
                cliente.getEndereco().setCliente(cliente);
            }

            Cliente saved = repository.save(cliente);
            return mapper.map(saved, ClienteDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar cliente: " + e.getMessage(), e);
        }
    }

    public List<Cliente> listarClientes() {
        return repository.findAll();
    }
}
