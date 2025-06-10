package mscliente.mscliente.services;

import java.time.ZonedDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mscliente.mscliente.DTO.ClienteDTO;
import mscliente.mscliente.DTO.ExtratoDTO;
import mscliente.mscliente.DTO.MilhasDTO;
import mscliente.mscliente.DTO.ReservaCreationResponseDTO;
import mscliente.mscliente.DTO.ReservaOutputDTO;
import mscliente.mscliente.DTO.TransacaoDTO;
import mscliente.mscliente.model.Cliente;
import mscliente.mscliente.model.TipoTransacao;
import mscliente.mscliente.model.Transacao;
import mscliente.mscliente.repository.ClienteRepository;
import mscliente.mscliente.repository.TransacaoRepository;

@Service
public class MilhasService {
        @Autowired
        private ClienteRepository repository;
        @Autowired
        private TransacaoRepository transacaoRepository;
        @Autowired
        private ModelMapper mapper;

        public ClienteDTO comprarMilhas(Long id, MilhasDTO milhas) {
                Cliente cliente = repository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o id: " + id));

                double quantidade = (double) milhas.getQuantidade();
                System.out.println((quantidade));
                Transacao novaTransacao = new Transacao(
                                cliente,
                                null,
                                ZonedDateTime.now(),
                                quantidade,
                                quantidade * 5.0,
                                "COMPRA DE MILHAS",
                                TipoTransacao.ENTRADA);

                transacaoRepository.save(novaTransacao);
                cliente.setSaldo_milhas(cliente.getSaldo_milhas() + quantidade);
                Cliente clienteAtualizado = repository.save(cliente);

                return mapper.map(clienteAtualizado, ClienteDTO.class);
        }

        public ExtratoDTO listarTransacoesCliente(Long clienteId) {
                Cliente cliente = repository.findById(clienteId)
                                .orElseThrow(() -> new RuntimeException(
                                                "Cliente não encontrado com o id: " + clienteId));

                List<Transacao> transacoes = transacaoRepository.findByCliente(cliente);

                List<TransacaoDTO> transacoesDTO = transacoes.stream()
                                .map(transacao -> mapper.map(transacao, TransacaoDTO.class))
                                .toList();

                double saldoMilhas = cliente.getSaldo_milhas();

                ExtratoDTO extrato = new ExtratoDTO();
                extrato.setCodigo(cliente.getCodigo());
                extrato.setSaldo_milhas(saldoMilhas);
                extrato.setTransacoes(transacoesDTO);

                return extrato;
        }

        public ExtratoDTO registrarReserva(ReservaCreationResponseDTO reserva) {
                Cliente cliente = repository.findByCodigo(reserva.getCodigo_cliente())
                                .orElseThrow(() -> new RuntimeException(
                                                "Cliente não encontrado com o id: "));

                double quantidade = reserva.getQuantidade_milhas();

                Transacao novaTransacao = new Transacao(
                                cliente,
                                reserva.getCodigo_reserva(),
                                reserva.getData(),
                                -quantidade,
                                reserva.getValor(),
                                reserva.getDescricao(),
                                TipoTransacao.SAIDA);

                double saldoAtualizado = cliente.getSaldo_milhas() - ((double) (quantidade -
                                (reserva.getValor() / 5)));
                cliente.setSaldo_milhas(saldoAtualizado);
                Cliente clienteAtualizado = repository.save(cliente);
                Transacao transacaoSalva = transacaoRepository.save(novaTransacao);

                return new ExtratoDTO(
                                clienteAtualizado.getCodigo(),
                                clienteAtualizado.getSaldo_milhas(),
                                List.of(mapper.map(transacaoSalva, TransacaoDTO.class)));
        }

        public ClienteDTO reembolsarReserva(ReservaOutputDTO reserva) {
                Cliente cliente = repository.findByCodigo(reserva.getCodigo_cliente())
                                .orElseThrow(() -> new RuntimeException(
                                                "Cliente não encontrado com o id: "));

                double quantidade = reserva.getQuantidade_milhas();

                Transacao novaTransacao = new Transacao(
                                cliente,
                                reserva.getCodigo(),
                                reserva.getData(),
                                quantidade,
                                0.0,
                                "REEMBOLSO",
                                TipoTransacao.ENTRADA);

                transacaoRepository.save(novaTransacao);
                cliente.setSaldo_milhas(cliente.getSaldo_milhas() + quantidade);
                Cliente clienteAtualizado = repository.save(cliente);

                return mapper.map(clienteAtualizado, ClienteDTO.class);
        }

        public void reembolsarVoo(List<ReservaOutputDTO> reservas) {
                for (ReservaOutputDTO reserva : reservas) {
                        repository.findByCodigo(reserva.getCodigo_cliente()).ifPresent(cliente -> {
                                double quantidade = reserva.getQuantidade_milhas();

                                Transacao novaTransacao = new Transacao(
                                                cliente,
                                                reserva.getCodigo(),
                                                reserva.getData(),
                                                quantidade,
                                                0.0,
                                                "REEMBOLSO",
                                                TipoTransacao.ENTRADA);

                                transacaoRepository.save(novaTransacao);
                                cliente.setSaldo_milhas((cliente.getSaldo_milhas() + quantidade));
                                repository.save(cliente);
                        });
                }
        }

        // public TransacaoDTO emitirExtrato(Long codigo) {
        // Cliente cliente = repository.findByCodigo(codigo)
        // .orElseThrow(() -> new RuntimeException(
        // "Cliente não encontrado com o id: "));

        // List<Transacao> transacoes = transacaoRepository.findByCliente(cliente);

        // return new TransacaoDTO(
        // cliente.getCodigo(),
        // cliente.getSaldo_milhas(),
        // transacoes.stream().map(t -> mapper.map(t,
        // TransacaoDTO.class)).collect(Collectors.toList()));
        // }
}
