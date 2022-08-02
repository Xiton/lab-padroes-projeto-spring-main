package one.digitalinnovation.gof.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import one.digitalinnovation.gof.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import one.digitalinnovation.gof.service.ClienteService;
import one.digitalinnovation.gof.service.SelicService;
import one.digitalinnovation.gof.service.ViaCepService;

/**
 * Implementação da <b>Strategy</b> {@link ClienteService}, a qual pode ser
 * injetada pelo Spring (via {@link Autowired}). Com isso, como essa classe é um
 * {@link Service}, ela será tratada como um <b>Singleton</b>.
 * 
 * @author Xiton
 */
@Service
public class ClienteServiceImpl implements ClienteService {

	// Singleton: Injetar os componentes do Spring com @Autowired.
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private ViaCepService viaCepService;
	@Autowired
	private SelicService selicService;
	
	// Strategy: Implementar os métodos definidos na interface.
	// Facade: Abstrair integrações com subsistemas, provendo uma interface simples.

	@Override
	public Iterable<Cliente> buscarTodos() {
		// Buscar todos os Clientes.
		return clienteRepository.findAll();
	}

	@Override
	public Cliente buscarPorId(Long id) {
		// Buscar Cliente por ID.
		Optional<Cliente> cliente = clienteRepository.findById(id);
		return cliente.get();
	}

	@Override
	public void inserir(Cliente cliente) {
		salvarClienteComCepSelic(cliente);
	}

	@Override
	public void atualizar(Long id, Cliente cliente) {
		// Buscar Cliente por ID, caso exista:
		Optional<Cliente> clienteBd = clienteRepository.findById(id);
		if (clienteBd.isPresent()) {
			salvarClienteComCepSelic(cliente);
		}
	}

	@Override
	public void deletar(Long id) {
		// Deletar Cliente por ID.
		clienteRepository.deleteById(id);
	}

	private void salvarClienteComCepSelic(Cliente cliente) {

		// busca valores Selic na data da divida informada
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dataDivida = LocalDate.parse(cliente.getDataDivida(), formato);

		double somatoriaSelic = 0.00;
		// busca taxas Selic
		List<Selic> retornoSelic = selicService.consultarSelic();

		// Realiza uma somatória com os valores selic onde a data seja maior que a da dívida.
		for (Selic selic : retornoSelic) {
			LocalDate dataSelic = LocalDate.parse(selic.getData(), formato);
			if (dataSelic.compareTo(dataDivida) >= 0) {
				{
					somatoriaSelic += Math.abs(Double.parseDouble(selic.getValor()));
				}
			}
		}

		double valorAdicionalSelic = cliente.getValorDivida() * (somatoriaSelic / 100);
		double valorDividaAtualizada = cliente.getValorDivida() + Math.round(valorAdicionalSelic);

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		cliente.setDataDividaAtualizada(dtf.format(LocalDateTime.now()));
		cliente.setValorDividaAtualizada(valorDividaAtualizada);

		// Verificar se o Endereco do Cliente já existe (pelo CEP).
		String cep = cliente.getEndereco().getCep();
		Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
			// Caso não exista, integrar com o ViaCEP e persistir o retorno.
			Endereco novoEndereco = viaCepService.consultarCep(cep);
			enderecoRepository.save(novoEndereco);
			return novoEndereco;
		});
		cliente.setEndereco(endereco);

		// Inserir Cliente, vinculando o Endereco (novo ou existente).
		clienteRepository.save(cliente);
	}

}
