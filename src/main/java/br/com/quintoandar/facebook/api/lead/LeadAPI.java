package br.com.quintoandar.facebook.api.lead;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.com.quintoandar.facebook.api.filter.Filter;

@Path("/api/v2")
public interface LeadAPI {

	@GET
	@Path("/{formId}/leads")
	@Produces(MediaType.APPLICATION_JSON)
	public LeadList listFormLeads(@QueryParam("access_token") String auth, @PathParam("formId") String formId, @FormParam("filtering") List<Filter> filter);
	
	@GET
	@Path("/{adId}/leads")
	@Produces(MediaType.APPLICATION_JSON)
	public LeadList listAdLeads(@QueryParam("access_token") String auth, @PathParam("adId") String formId, @FormParam("filtering") List<Filter> filter);
	
	@GET
	@Path("/{leadId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Lead getLead(@QueryParam("access_token") String auth, @PathParam("leadId") String leadId);
	
}
