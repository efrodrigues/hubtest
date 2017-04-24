package br.com.eduardo.hubtest.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;

@Entity
@Table(name = "TRANSACAO")
@DiscriminatorColumn(name = "TIPO_TRANSACAO")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQueries({ @NamedQuery(name = "Transacao.findAll", query = "Select t From Transacao t"),
		@NamedQuery(name = "Transacao.findById", query = "Select t From Transacao t Where t.id = :id") })
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@JsonSubTypes({ @JsonSubTypes.Type(value = Transferencia.class, name = "Transferencia"),
		@JsonSubTypes.Type(value = Aporte.class, name = "Aporte") })
public abstract class Transacao {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@ManyToOne
	@JoinColumn(name = "CONTA_CREDORA", referencedColumnName = "ID")
	private Conta conta;

	@Column(name = "DATA_TRANSACAO")
	private Date dataTransacao;

	@Column(name = "VALOR_TRANSACAO")
	private double valorTransacao;

	public abstract void execute(EntityManager em) throws Exception;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public Date getDataTransacao() {
		return dataTransacao;
	}

	public void setDataTransacao(Date dataTransacao) {
		this.dataTransacao = dataTransacao;
	}

	public double getValorTransacao() {
		return valorTransacao;
	}

	public void setValorTransacao(double valorTransacao) {
		this.valorTransacao = valorTransacao;
	}

}
