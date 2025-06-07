package mscliente.mscliente.services;

import java.time.ZonedDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mscliente.mscliente.DTO.ClienteDTO;
import mscliente.mscliente.DTO.ExtratoDTO;
import mscliente.mscliente.DTO.MilhasDTO;
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

                float quantidade = (float) milhas.getQuantidade();
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

                float saldoMilhas = cliente.getSaldo_milhas();

                ExtratoDTO extrato = new ExtratoDTO();
                extrato.setCodigo(cliente.getCodigo());
                extrato.setSaldo_milhas(saldoMilhas);
                extrato.setTransacoes(transacoesDTO);

                return extrato;
        }

        // public TransacaoDTO registrarReserva(ReservaCreationResponseDTO reserva) {
        // Cliente cliente =
        // repository.findByCodigoAndAtivoTrue(reserva.getCodigoCliente())
        // .orElseThrow(() -> new ResourceNotFoundException(
        // "Cliente não encontrado com o ID: " + reserva.getCodigoCliente()));

        // float quantidade = reserva.getQuantidadeMilhas();

        // Transacao novaTransacao = new Transacao(
        // cliente,
        // reserva.getCodigoReserva(),
        // reserva.getData(),
        // -quantidade,
        // reserva.getValor(),
        // reserva.getDescricao(),
        // TipoTransacao.SAIDA);

        // float saldoAtualizado = cliente.getsaldo_milhas() - ((float) (quantidade -
        // (reserva.getValor() / 5)));
        // cliente.setsaldo_milhas(saldoAtualizado);
        // Cliente clienteAtualizado = repository.save(cliente);
        // Transacao transacaoSalva = transacaoRepository.save(novaTransacao);

        // return new TransacaoDTO(
        // clienteAtualizado.getId(),
        // clienteAtualizado.getsaldo_milhas(),
        // List.of(mapper.map(transacaoSalva, TransacaoDTO.class)));
        // }

        // public ClienteDTO reembolsarReserva(ReservaOutputDTO reserva) {
        // Cliente cliente =
        // repository.findByCodigoAndAtivoTrue(reserva.getCodigoCliente())
        // .orElseThrow(() -> new ResourceNotFoundException(
        // "Cliente não encontrado com o ID: " + reserva.getCodigoCliente()));

        // float quantidade = reserva.getQuantidadeMilhas();

        // Transacao novaTransacao = new Transacao(
        // cliente,
        // reserva.getCodigo(),
        // reserva.getData(),
        // quantidade,
        // 0.0,
        // "REEMBOLSO",
        // TipoTransacao.ENTRADA);

        // transacaoRepository.save(novaTransacao);
        // cliente.setsaldo_milhas(cliente.getsaldo_milhas() + quantidade);
        // Cliente clienteAtualizado = repository.save(cliente);

        // return mapper.map(clienteAtualizado, ClienteDTO.class);
        // }

        // public void reembolsarVoo(List<ReservaOutputDTO> reservas) {
        // for (ReservaOutputDTO reserva : reservas) {
        // repository.findByCodigoAndAtivoTrue(reserva.getCodigoCliente()).ifPresent(cliente
        // -> {
        // float quantidade = reserva.getQuantidadeMilhas();

        // Transacao novaTransacao = new Transacao(
        // cliente,
        // reserva.getCodigo(),
        // reserva.getData(),
        // quantidade,
        // 0.0,
        // "REEMBOLSO",
        // TipoTransacao.ENTRADA);

        // transacaoRepository.save(novaTransacao);
        // cliente.setsaldo_milhas(cliente.getsaldo_milhas() + quantidade);
        // repository.save(cliente);
        // });
        // }
        // }

        // public TransacaoDTO emitirExtrato(Long codigo) {
        // Cliente cliente = repository.findByCodigoAndAtivoTrue(codigo)
        // .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com
        // o ID: " + codigo));

        // List<Transacao> transacoes = transacaoRepository.findByCliente(cliente);

        // return new TransacaoDTO(
        // cliente.getId(),
        // cliente.getsaldo_milhas(),
        // transacoes.stream().map(t -> mapper.map(t,
        // TransacaoDTO.class)).collect(Collectors.toList()));
        // }
}
