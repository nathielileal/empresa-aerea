package mscliente.mscliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mscliente.mscliente.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
