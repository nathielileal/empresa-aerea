package mscliente.mscliente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import mscliente.mscliente.DTO.ClienteDTO;
import mscliente.mscliente.model.Cliente;

import org.modelmapper.ModelMapper;

@SpringBootApplication
public class MsclienteApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsclienteApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
	
		mapper.typeMap(ClienteDTO.class, Cliente.class).addMappings(map -> {
			map.map(ClienteDTO::getEndereco, Cliente::setEndereco);
		});
	
		mapper.typeMap(Cliente.class, ClienteDTO.class).addMappings(map -> {
			map.map(Cliente::getEndereco, ClienteDTO::setEndereco);
		});
	
		return mapper;
	}
}
