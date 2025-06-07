package mscliente.mscliente.repository;

import org.springframework.stereotype.Repository;

import mscliente.mscliente.model.Cliente;
import mscliente.mscliente.model.Transacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    List<Transacao> findByCliente(Cliente cliente);
}

