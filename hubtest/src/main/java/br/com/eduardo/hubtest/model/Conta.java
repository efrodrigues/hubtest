package br.com.eduardo.hubtest.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "CONTA")
@NamedQueries({ @NamedQuery(name = "Conta.findById", query = "Select c From Conta c Where c.id = :id") })
public class Conta {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ManyToOne
	@JoinColumn(name = "ID_PESSOA")
	private Pessoa pessoa;

	@ManyToOne
	@JoinColumn(name = "CONTA_PAI", referencedColumnName = "ID")
	private Conta contaPai;

	@Column(name = "DATA_CRIACAO")
	private Date dataCriacao;

	@Column(name = "NOME_CONTA")
	private String nomeConta;

	@Column(name = "STATUS_CONTA")
	@Enumerated(EnumType.STRING)
	private StatusConta status;

	@Column(name = "VALOR_SALDO")
	private double valorSaldo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Conta getContaPai() {
		return contaPai;
	}

	public void setContaPai(Conta contaPai) {
		this.contaPai = contaPai;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public String getNomeConta() {
		return nomeConta;
	}

	public void setNomeConta(String nomeConta) {
		this.nomeConta = nomeConta;
	}

	public StatusConta getStatus() {
		return status;
	}

	public void setStatus(StatusConta status) {
		this.status = status;
	}

	public double getValorSaldo() {
		return valorSaldo;
	}

	public void setValorSaldo(double valorSaldo) {
		this.valorSaldo = valorSaldo;
	}

	public boolean validaHierarquia(Conta conta) {
		if (this.getContaPai() == null) {
			if (conta.getId() == this.getId()) {
				return true;
			} else if (conta.getContaPai() == null) {
				return false;
			} else {
				return this.validaHierarquia(conta.getContaPai());
			}
		} else if (this.getContaPai() == conta.getContaPai()) {
			return true;
		} else {
			return this.getContaPai().validaHierarquia(conta);
		}
	}

}
