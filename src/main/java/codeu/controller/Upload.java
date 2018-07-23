package com.blobstore_upload;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import codeu.model.data.Conversation;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

import codeu.model.store.basic.ConversationStore;


import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.api.images.ServingUrlOptions.Builder;

public class Upload extends HttpServlet {

		private ConversationStore conversationStore;
    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		private ImagesService imagesService = ImagesServiceFactory.getImagesService();

		@Override
	  public void init() throws ServletException {
	    super.init();
	    setConversationStore(ConversationStore.getInstance());
	  }

		void setConversationStore(ConversationStore conversationStore) {
			this.conversationStore = conversationStore;
		}

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Conversation conversation = (Conversation) req.getAttribute("conversation");
		// List<Conversation> conversations =
		// 	(List<Conversation>) request.getParameter("conversation");
		String conversationTitle = (String) request.getParameter("conversation");
		Conversation conversation = conversationStore.getConversationWithTitle(conversationTitle);

		Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(request); //gets all blobs lol
		// System.out.println(conversation.getTitle());
		// List<BlobKey> blobKeys = blobs.get("avatarImage/" + conversation.getTitle());
		// BlobKey avatarBlobKey = blobKeys.get(0);
		List<BlobKey> blobKeys = null;
		// for(Conversation conversation: conversations){
		// 	blobKeys = blobs.get(conversation.getTitle());
		// 	BlobKey avatarBlobKey = blobKeys.get(0);
		// 	ServingUrlOptions servingURLOptions = ServingUrlOptions.Builder.withBlobKey(avatarBlobKey);
		// 	String avatarImageURL = imagesService.getServingUrl(servingURLOptions);
		// 	System.out.println("avatarImageURL");
		// 	System.out.println(avatarImageURL);
		// 	conversation.setAvatarImageURL(avatarImageURL);
		// }

		blobKeys = blobs.get(conversationTitle);
		BlobKey avatarBlobKey = blobKeys.get(0);
		ServingUrlOptions servingURLOptions = ServingUrlOptions.Builder.withBlobKey(avatarBlobKey);
		String avatarImageURL = imagesService.getServingUrl(servingURLOptions);
		System.out.println("avatarImageURL");
		System.out.println(avatarImageURL);
		conversation.setAvatarImageURL(avatarImageURL);

		// ServingUrlOptions servingURLOptions = ServingUrlOptions.Builder.withBlobKey(avatarBlobKey);
		// String avatarImageURL = imagesService.getServingUrl(servingURLOptions);
		// System.out.println("avatarImageURL");
		// System.out.println(avatarImageURL);
		// conversation.setAvatarImageURL(avatarImageURL);

		//wanna have some descriptions to kinda keep track of this stuff!
		//So one description could be like conversationAvatarImages, BlobKey
        // BlobKeyCache bc = BlobKeyCache.getBlobKeyCache();

        if (blobKeys == null){
            System.out.println("blobkey is null");
        }
        else {
        	// for(BlobKey blobkey:blobKeys){
        	// 	bc.add(blobkey);
        	// }

        	response.sendRedirect("/conversations");
        }
    }
}
