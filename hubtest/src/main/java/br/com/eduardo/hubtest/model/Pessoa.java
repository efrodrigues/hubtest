package br.com.eduardo.hubtest.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;

@Entity
@Table(name = "PESSOA")
@DiscriminatorColumn(name = "TIPO_PESSOA")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQueries({
		@NamedQuery(name = "Pessoa.findByCpfCnpj", query = "Select p From Pessoa p Where p.cpfCnpj = :cpfCnpj") })
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@JsonSubTypes({ @JsonSubTypes.Type(value = PessoaFisica.class, name = "PessoaFisica"),
		@JsonSubTypes.Type(value = PessoaJuridica.class, name = "PessoaJuridica") })
public abstract class Pessoa {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "CPF_CNPJ", unique = true)
	private long cpfCnpj;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(long cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

}
