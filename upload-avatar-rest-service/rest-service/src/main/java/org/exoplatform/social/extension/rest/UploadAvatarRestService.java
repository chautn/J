package org.exoplatform.social.extension.rest;

import java.util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.annotation.security.RolesAllowed;

import org.apache.commons.fileupload.FileItem;
import org.exoplatform.common.http.HTTPStatus;
import org.exoplatform.container.ExoContainerContext;
import org.exoplatform.services.security.ConversationState;
import org.exoplatform.services.rest.resource.ResourceContainer;
import org.exoplatform.social.core.manager.IdentityManager;
import org.exoplatform.social.core.model.AvatarAttachment;
import org.exoplatform.social.core.identity.model.*;
import org.exoplatform.social.core.identity.provider.OrganizationIdentityProvider;
import org.exoplatform.social.core.image.ImageUtils;

/**
 * 
 * @author chautn
 *
 */

@Path("/me")
public class UploadAvatarRestService implements ResourceContainer {
	
	private IdentityManager identityManager = (IdentityManager) ExoContainerContext.getCurrentContainer()
																												.getComponentInstanceOfType(IdentityManager.class);
		
	@POST
	@Path("/updateAvatar")
	@Consumes("multipart/*")
	@RolesAllowed("users")
	public Response updateAvatar(Iterator<FileItem> iterator) throws Exception {
		
		//Get the uploaded file item.
		FileItem fileItem = null;
		if (iterator == null) {
			return Response.status(HTTPStatus.BAD_REQUEST).build();
		}
		while (iterator.hasNext()) {
			fileItem = iterator.next();
		}
		
		//Create AvatarAttachment.
		AvatarAttachment attachment = new AvatarAttachment(	fileItem.getFieldName(), 
																				fileItem.getName(), 
																				fileItem.getContentType(), 
																				fileItem.getInputStream(), 
																				null, 
																				System.currentTimeMillis());
		if (fileItem.getInputStream().available() == 0) {
			return Response.status(422).build();
		}
		
		//Get the current user profile.
		String currentUserId = ConversationState.getCurrent().getIdentity().getUserId();
		Identity currentIdentity = identityManager.getOrCreateIdentity(OrganizationIdentityProvider.NAME, currentUserId, false);
		Profile profile = currentIdentity.getProfile();
		
		//Remove avatar url and size properties before update.
		Map<String, Object> properties = profile.getProperties();
		for (String key : properties.keySet()) {
			if (key.startsWith(Profile.AVATAR + ImageUtils.KEY_SEPARATOR)) {
				profile.removeProperty(key);
			}
		}
		
		//Update avatar.
		profile.setProperty(Profile.AVATAR, attachment);
		identityManager.updateProfile(profile);
		
		return Response.ok().build();
	}	
}
