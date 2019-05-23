package br.com.hotel.api;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import br.com.hotel.api.dao.HospedeDAO;
import br.com.hotel.api.dao.MySQLDAO;
import br.com.hotel.api.dao.QuartoDAO;
import br.com.hotel.api.dao.ReservaDAO;
import br.com.hotel.api.model.Hospede;
import br.com.hotel.api.model.Quarto;
import br.com.hotel.api.model.Reserva;
@Path("/services") 
public class Hotel{
	private static String status = "Inicio da API...";
	@GET 
	@Path("/hospedes") 
	@Produces(MediaType.APPLICATION_XML) 
	public ArrayList<Hospede> listHospede(){
		return HospedeDAO.listHospedes();
	}
	@OPTIONS
	@Path("/hospedes") 
	@Produces(MediaType.APPLICATION_XML) 
	public String optionsHospede(){
		return "<response>GET,POST,PUT</response>";
	}
	@GET
	@Path("/hospedes/{userid}")
	@Produces(MediaType.APPLICATION_XML)
	public Hospede getHospede(@PathParam("userid") int userid){
	      return HospedeDAO.getHospede(userid);
	}
	@POST
	@Path("/hospedes")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String createHospede(@FormParam("nome") String nome,
			@FormParam("endereco") String endereco,
			@FormParam("cpf") String cpf,
			@FormParam("rg") String rg,
			@FormParam("telefone") String telefone,
			@FormParam("email") String email,
			@Context HttpServletResponse servletResponse) throws IOException{
		Hospede hospede = new Hospede(nome, endereco, Long.parseLong(cpf), Long.parseLong(rg), Long.parseLong(telefone), email);
		int cod_hospede = HospedeDAO.saveHospede(hospede);
		if(cod_hospede != 0){
			return "<response>\n\t<cod_hospede>" + Integer.toString(cod_hospede) + "</cod_hospede>\n</response>";
		}
		return "<response>failure</response>";
	}
	@PUT
	@Path("/hospedes/{userid}")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String updateHospede(@PathParam("userid") int cod_hospede,
			@FormParam("nome") String nome,
			@FormParam("endereco") String endereco,
			@FormParam("cpf") String cpf,
			@FormParam("rg") String rg,
			@FormParam("telefone") String telefone,
			@FormParam("email") String email,
			@Context HttpServletResponse servletResponse) throws IOException{
		Hospede hospede = new Hospede(nome, endereco, Long.parseLong(cpf), Long.parseLong(rg), Long.parseLong(telefone), email);
		hospede.setCod_hospede(cod_hospede);
		Boolean result = HospedeDAO.updateHospede(hospede);
		if(result){
			return "<response>Updated</response>";
		}
		return "<response>Failure</response>";
	}
	@GET 
	@Path("/quartos") 
	@Produces(MediaType.APPLICATION_XML) 
	public ArrayList<Quarto> listQuarto(){
		return QuartoDAO.listQuarto();
	}
	@GET 
	@Path("/quartos/{userid}") 
	@Produces(MediaType.APPLICATION_XML) 
	public Quarto getQuarto(@PathParam("userid") int userid){
		return QuartoDAO.getQuarto(userid);
	}
	@GET 
	@Path("/reservas") 
	@Produces(MediaType.APPLICATION_XML) 
	public ArrayList<Reserva> listReserva(){
		return ReservaDAO.listReserva();
	}
	@GET 
	@Path("/reservas/{userid}") 
	@Produces(MediaType.APPLICATION_XML) 
	public Reserva getReserva(@PathParam("userid") int userid){
		return ReservaDAO.getReserva(userid);
	}
	@GET 
	@Path("/SQLstatus") 
	@Produces(MediaType.TEXT_PLAIN) 
	public String getSQLStatus(){
		return MySQLDAO.statusConnection();
	}
	@GET 
	@Path("/hospede-status") 
	@Produces(MediaType.TEXT_PLAIN) 
	public String getHospedeStatus(){
		return HospedeDAO.statusConnection();
	}
	@GET
	@Path("/api-status")
	@Produces(MediaType.TEXT_PLAIN)
	public String statusConnection() {
		return status;
	}
}
