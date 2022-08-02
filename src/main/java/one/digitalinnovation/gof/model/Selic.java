package one.digitalinnovation.gof.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Os atributos desse modelo foram gerados automaticamente pelo site
 * jsonschema2pojo.org. Para isso, usamos o JSON de retorno da API do Banco Central Selic.
 * 
 * @see <a href="https://www.jsonschema2pojo.org">jsonschema2pojo.org</a>
 * @see <a href="https://dados.gov.br/dataset/4390-taxa-de-juros-selic-acumulada-no-mes/resource/449efbb5-366b-4907-820f-8143a63733e1">Banco Central - Taxa Selic</a>
 * 
 * @author Xiton
 */

@Entity
public class Selic {

	@Id
	private String data;
	private String valor;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
}
