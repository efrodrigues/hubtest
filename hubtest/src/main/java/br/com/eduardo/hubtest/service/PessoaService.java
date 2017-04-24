package br.com.eduardo.hubtest.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.eduardo.hubtest.entitymanager.JpaEntityManager;
import br.com.eduardo.hubtest.model.Pessoa;

@Path("/pessoa")
public class PessoaService {

	private EntityManager em = new JpaEntityManager().getEntityManager();

	@GET
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Pessoa> listar() {
		String query = "Select p from Pessoa p";
		List<Pessoa> pessoas = em.createQuery(query, Pessoa.class).getResultList();
		em.close();
		return pessoas;
	}

	@GET
	@Path("/buscar/{cpfCnpj}")
	@Produces(MediaType.APPLICATION_JSON)
	public Pessoa findByCpfCnpj(@PathParam("cpfCnpj") long cpfCnpj) {
		Pessoa pessoa = em.createNamedQuery("Pessoa.findByCpfCnpj", Pessoa.class).setParameter("cpfCnpj", cpfCnpj).getSingleResult();
		em.close();
		return pessoa;
	}
	
	@POST
	@Path("/cadastrar")
	@Consumes("application/json")
	public Response cadastrar(Pessoa pessoa) {
		try {
			em.getTransaction().begin();
			em.persist(pessoa);
			em.getTransaction().commit();
			em.close();
			return Response.status(200).entity("Pessoa salva com sucesso").build();
		} catch (Exception e) {
			return Response.status(200).entity("Erro ao salvar Pessoa: " + e.getMessage()).build();
		}
	}

}
