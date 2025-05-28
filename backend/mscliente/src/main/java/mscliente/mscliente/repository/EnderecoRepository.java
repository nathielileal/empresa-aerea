package mscliente.mscliente.repository;

import org.springframework.stereotype.Repository;

import mscliente.mscliente.model.Endereco;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}

