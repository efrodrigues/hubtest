package br.com.eduardo.hubtest.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityManager;

@Entity
@DiscriminatorValue(value = "A")
public class Aporte extends Transacao {

	@Column(name = "CODIGO_EXTERNO")
	private String codigoExterno;

	@Override
	public void execute(EntityManager em) throws Exception {

		//if (this.getConta().getId() == 0){
			
		//}
		
		this.setConta(em.createNamedQuery("Conta.findById", Conta.class).setParameter("id", this.getConta().getId())
				.getSingleResult());

		if (this.getConta().getContaPai() != null) {
			throw new Exception("Aporte é permitido apenas para conta Matriz");
		}

		if (this.getCodigoExterno() == null || this.getCodigoExterno().equals("")) {
			throw new Exception("É obrigatório informar um código externo");
		}

		this.getConta().setValorSaldo(this.getConta().getValorSaldo() + this.getValorTransacao());

	}

	public String getCodigoExterno() {
		return codigoExterno;
	}

	public void setCodigoExterno(String codigoExterno) {
		this.codigoExterno = codigoExterno;
	}

}
