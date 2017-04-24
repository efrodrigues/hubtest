package br.com.eduardo.hubtest.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue(value = "T")
public class Transferencia extends Transacao {

	@ManyToOne
	@JoinColumn(name = "CONTA_DEBITO", referencedColumnName = "ID")
	private Conta contaDebito;

	@Override
	public void execute(EntityManager em) throws Exception {

		this.setConta(em.createNamedQuery("Conta.findById", Conta.class).setParameter("id", this.getConta().getId())
				.getSingleResult());

		this.setContaDebito(em.createNamedQuery("Conta.findById", Conta.class)
				.setParameter("id", this.getContaDebito().getId()).getSingleResult());

		if (this.getConta().getContaPai() == null) {
			throw new Exception("Conta Pai não recebe Transferência");
		}

		if (!this.getConta().validaHierarquia(this.getContaDebito())) {
			throw new Exception("Contas origem e destino não pertencem a mesma hierarquia");
		}

		if (!this.getConta().getStatus().equals(StatusConta.Ativo)) {
			throw new Exception("Conta origem não Ativa");
		}

		if (!this.getContaDebito().getStatus().equals(StatusConta.Ativo)) {
			throw new Exception("Conta débito não Ativa");
		}

		if (this.getContaDebito().getValorSaldo() < this.getValorTransacao()) {
			throw new Exception("Saldo Insuficiente");
		}

		this.getConta().setValorSaldo(this.getConta().getValorSaldo() + this.getValorTransacao());
		this.getContaDebito().setValorSaldo(this.getContaDebito().getValorSaldo() - this.getValorTransacao());
	}

	public Conta getContaDebito() {
		return contaDebito;
	}

	public void setContaDebito(Conta contaDebito) {
		this.contaDebito = contaDebito;
	}

}
