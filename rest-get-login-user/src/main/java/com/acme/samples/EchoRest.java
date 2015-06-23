package com.acme.samples;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.annotation.security.RolesAllowed;
import org.exoplatform.services.security.ConversationState;
import org.exoplatform.services.rest.resource.ResourceContainer;

@Path("/echo")
public class EchoRest implements ResourceContainer {
	//
	@RolesAllowed("users")
	@GET
	@Path("/whoami")
	public Response whoami() {
		//
		String currentUserId = ConversationState.getCurrent().getIdentity().getUserId();
		return Response.ok(currentUserId, MediaType.TEXT_PLAIN).build();
	}
}
