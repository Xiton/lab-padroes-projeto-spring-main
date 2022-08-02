package one.digitalinnovation.gof.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nome;
	private String dataDivida;
	private Double valorDivida;
	private String dataDividaAtualizada;
	private Double valorDividaAtualizada;
	@ManyToOne
	private Endereco endereco;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getDataDivida() {
		return dataDivida;
	}

	public void setDataDivida(String dataDivida) {
		this.dataDivida = dataDivida;
	}

	public Double getValorDivida() {
		return valorDivida;
	}
	
	public void setValorDivida(Double valorDivida) {
		this.valorDivida = valorDivida;
	}
	
	public String getDataDividaAtualizada() {
		return dataDividaAtualizada;
	}

	public void setDataDividaAtualizada(String dataDividaAtualizada) {
		this.dataDividaAtualizada = dataDividaAtualizada;
	}

	public Double getValorDividaAtualizada() {
		return valorDividaAtualizada;
	}
	
	public void setValorDividaAtualizada(Double valorDividaAtualizada) {
		this.valorDividaAtualizada = valorDividaAtualizada;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
}
