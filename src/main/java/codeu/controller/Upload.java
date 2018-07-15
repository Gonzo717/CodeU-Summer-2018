package com.blobstore_upload;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

public class Upload extends HttpServlet {
    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();


    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {


		Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
		List<BlobKey> blobKeys = blobs.get("files");
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
            res.sendRedirect("/serve.jsp?blob-key=" + blobKeys.get(0).getKeyString());
        }
    }
}
