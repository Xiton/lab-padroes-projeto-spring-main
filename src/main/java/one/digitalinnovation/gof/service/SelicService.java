package one.digitalinnovation.gof.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import one.digitalinnovation.gof.model.Selic;

import java.util.List;

/**
 * Client HTTP, criado via <b>OpenFeign</b>, para o consumo da API do Banco Central com taxas
 * <b>Selic</b>.
 * 
 * @see <a href="https://spring.io/projects/spring-cloud-openfeign">Spring Cloud OpenFeign</a>
 * @see <a href="https://api.bcb.gov.br/dados/serie/bcdata.sgs.4390">Selic</a>
 * 
 * @author Xiton
 */
@FeignClient(name = "selic", url = "https://api.bcb.gov.br/dados/serie/bcdata.sgs.4390")
public interface SelicService {
	@GetMapping("/dados?formato=json")
	//List<Selic> consultarSelic(@PathVariable("data") String data);
	List<Selic> consultarSelic();
}