package rest;

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import ejb.PhoneNumbersDAOwJPA;
import entities.PhoneNumber;

@Path("/numbers")
@Stateless
@LocalBean
public class PhoneNumberWS {

    @EJB
    private PhoneNumbersDAOwJPA numbersDAO;
    
    @GET
    @Produces("application/json")
    public List<PhoneNumber> getAllPhones() {
        return numbersDAO.getAllPhoneNumbers();
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public PhoneNumber getPhoneNumberById(@PathParam("id") int id) {
        return numbersDAO.getPhoneNumberById(id);
    }
    
    @GET
    @Path("/email/{email}")
    @Produces("application/json")
    public List<PhoneNumber> getPhoneNumberByEmail(@PathParam("email") String email) {
        return numbersDAO.getPhoneNumbersByEmail(email);
    }
    
    @POST
    @Produces({"application/xml", "application/json"})
    @Consumes("application/x-www-form-urlencoded")
    public Response addPhoneNumber(@FormParam("email") String email,
            @FormParam("fullname") String fullname,
            @FormParam("address") String address,
            @FormParam("number") String number,
            @FormParam("isPrivate") boolean isPrivate,
            @Context HttpServletResponse servletResponse) throws IOException {
        if (numbersDAO.addPhoneNumber(email, fullname, address, number, isPrivate) == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Unable to create a phone record with these params!").build();
        } else {
            return Response.status(Response.Status.OK).build();
        }
    }
    
    @PUT
    @Path("/{id}")
    @Produces("application/json")
    @Consumes("application/x-www-form-urlencoded")
    public Response updatePhoneById(@PathParam("id") int id,
            @FormParam("email") String email,
            @FormParam("fullname") String fullname,
            @FormParam("address") String address,
            @FormParam("number") String number,
            @Context HttpServletResponse servletResponse) throws IOException {
        if (numbersDAO.updateUser(id, email, fullname, address, number) == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Unable to update this record (" + id + ")!").build();
        } else {
            return Response.status(Response.Status.OK).build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    @Produces({"application/xml", "application/json"})
    public Response deletePhoneNumber(@PathParam("id") int id){
        if(numbersDAO.deletePhoneNumber(id)){
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("Unable to delete record " + id + "!").build();
    }
    
    @DELETE
    @Path("/email/{email}")
    @Produces({"application/xml", "application/json"})
    public Response deletePhoneNumbersByEmail(@PathParam("email") String email){
        if(numbersDAO.deletePhonesByEmail(email)){
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("Unable to delete records by email " + email + "!").build();
    }
    
    @GET
    @Path("/amount")
    @Produces("text/plain")
    public Long getAmountOfPhones() {
        return numbersDAO.getAmountOfPhones();
    }
    
    @GET
    @Path("/email/{email}/amount")
    @Produces("text/plain")
    public Long getAmountOfPhonesByEmail(@PathParam("email") String email) {
        return numbersDAO.getAmountOfPhonesByEmail(email);
    }
    
    @GET
    @Path("/page/{pageNumber}/size/{recordsPerPage}")
    @Produces("application/json")
    public List<PhoneNumber> getPhonesAtPage(@PathParam("pageNumber") int pageNumber,
            @PathParam("recordsPerPage") int recordsPerPage) {
        return numbersDAO.getPhonesAtPage(pageNumber, recordsPerPage);
    }
    
    @GET
    @Path("/search/{searchString}")
    @Produces("application/json")
    public List<PhoneNumber> getSearchedPhonesAtPage(@PathParam("searchString") String searchString) {
        return numbersDAO.getSearchedPhones(searchString);
    }
    
}
