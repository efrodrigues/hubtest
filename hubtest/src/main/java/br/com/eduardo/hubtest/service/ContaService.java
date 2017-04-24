package br.com.eduardo.hubtest.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.eduardo.hubtest.entitymanager.JpaEntityManager;
import br.com.eduardo.hubtest.model.Conta;
import br.com.eduardo.hubtest.model.Pessoa;

@Path("/conta")
public class ContaService {

	private EntityManager em = new JpaEntityManager().getEntityManager();

	@GET
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Conta> listar() {
		String query = "Select c from Conta c";
		List<Conta> contas = em.createQuery(query, Conta.class).getResultList();
		em.close();
		return contas;
	}

	@POST
	@Path("/cadastrar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response cadastrar(Conta conta) {
		try {

			TypedQuery<Pessoa> query = em.createNamedQuery("Pessoa.findByCpfCnpj", Pessoa.class);
			Pessoa pessoa = query.setParameter("cpfCnpj", conta.getPessoa().getCpfCnpj()).getSingleResult();
			conta.setPessoa(pessoa);

			if (conta.getContaPai() != null) {
				conta.setContaPai(em.createNamedQuery("Conta.findById", Conta.class)
						.setParameter("id", conta.getContaPai().getId()).getSingleResult());
			}

			conta.setDataCriacao(new Date());

			em.getTransaction().begin();
			em.persist(conta);
			em.getTransaction().commit();
			em.close();
			return Response.status(200).entity("Conta cadastrada com sucesso").build();
		} catch (Exception e) {
			return Response.status(400).entity("Erro ao cadastrar conta: " + e.getMessage()).build();
		}
	}

}
