package quarks;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
//import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;

import java.util.*;
import java.math.BigDecimal;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

@Path("/accounts")
@ApplicationScoped
//@Singleton
public class AccountResource {

    Set<Account> accounts = new HashSet<>();

    @PostConstruct
    public void setup() {
      accounts.add(new Account(123456789L, 987654321L, "George Baird",
        new BigDecimal("354.23")));
      accounts.add(new Account(121212121L, 888777666L, "Mary Taylor",
        new BigDecimal("560.03")));
      accounts.add(new Account(545454545L, 222444999L, "Diana Rigg",
        new BigDecimal("422.00")));
    }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  //@Produces(MediaType.TEXT_PLAIN)
  public Set<Account> allAccounts() {
    return accounts;
    //return Collections.emptySet();
  } 

  @GET
  @Path("/{accountNumber}")
  @Produces(MediaType.APPLICATION_JSON)
  public Account getAccount(@PathParam("accountNumber") Long accountNumber)
  {
    Account response = null;
    for (Account acct : accounts) {
      if (acct.getAccountNumber().equals(accountNumber)) {
        response = acct;
        break;
      }
    }

    if (response == null) {
      throw new WebApplicationException("Account with id of " + accountNumber +
      " does not exist.", 404);
      /*throw new NotFoundException("Account with id of " + accountNumber +
      " does not exist.");*/
    }

    return response;
  }

  /* @Provider
  public static class ErrorMapper implements ExceptionMapper<Exception>
  {

    @Override
    public Response toResponse(Exception exception) {

    int code = 500;
    if (exception instanceof WebApplicationException) {
    code = ((WebApplicationException) exception).getResponse().getStatus();
    }

    JsonObjectBuilder entityBuilder = Json.createObjectBuilder()
        .add("exceptionType", exception.getClass().getName())
        .add("code", code);

      if (exception.getMessage() != null) {
      entityBuilder.add("error", exception.getMessage());
    }

    return Response.status(code)
        .entity(entityBuilder.build())
        .build();
    }
  } */
  
}
