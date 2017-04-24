package br.com.eduardo.hubtest.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.eduardo.hubtest.entitymanager.JpaEntityManager;
import br.com.eduardo.hubtest.model.Transacao;

@Path("/transacao")
public class TransacaoService {

	private EntityManager em = new JpaEntityManager().getEntityManager();

	@GET
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Transacao> lista() {
		List<Transacao> transacoes = em.createNamedQuery("Transacao.findAll", Transacao.class).getResultList();
		return transacoes;
	}

	@POST
	@Path("/cadastrar")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response cadastrar(Transacao transacao) {
		try {
			transacao.setDataTransacao(new Date());
			transacao.execute(em);
		} catch (Exception e) {
			return Response.status(400).entity(e.getMessage()).build();
		}
		em.getTransaction().begin();
		em.persist(transacao);
		em.getTransaction().commit();
		em.close();
		return Response.status(200).entity("SUCESSO").build();
	}
}
