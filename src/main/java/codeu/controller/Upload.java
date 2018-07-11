// package com.blobstore_upload;
//
// import java.io.IOException;
// import java.util.List;
// import java.util.Map;
// import java.util.logging.Level;
// import java.util.logging.Logger;
//
// import javax.servlet.ServletException;
// import javax.servlet.http.HttpServlet;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
//
// import com.google.appengine.api.blobstore.BlobKey;
// import com.google.appengine.api.blobstore.BlobstoreService;
// import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
//
// public class Upload extends HttpServlet {
//
// 	private static final Logger log= Logger.getLogger(Upload.class.getName());
//
//     private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
//
//
//     public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//
//
// 		log.setLevel(Level.INFO);
//
// 		log.info("contextpath: "+req.getContextPath());
// 		System.out.println("contextpath: "+req.getContextPath());
// 		log.info("getmethod: "+req.getMethod());
// 		System.out.println("getmethod: "+req.getMethod());
// 		log.info("getpathInfo: "+req.getPathInfo());
// 		System.out.println("getpathInfo: "+req.getPathInfo());
// 		log.info("getPathTranslated: "+req.getPathTranslated());
// 		System.out.println("getPathTranslated: "+req.getPathTranslated());
// 		log.info("queryString: "+req.getQueryString());
// 		System.out.println("queryString: "+req.getQueryString());
// 		log.info("reqURIpath:"+req.getRequestURI());
// 		System.out.println("reqURIpath: "+req.getRequestURI());
// 		log.info("reqURLpath:"+req.getRequestURL());
// 		System.out.println("reqURLpath: "+req.getRequestURL());
// 		log.info("servletpath:"+req.getServletPath());
// 		System.out.println("servletpath: "+req.getServletPath());
//
//
// 		Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
// 		List<BlobKey> blobKeys = blobs.get("files");
// 		//wanna have some descriptions to kinda keep track of this stuff!
// 		//So one description could be like conversationAvatarImages, BlobKey
//
//
// 		log.info("blobkeys size:"+blobKeys.size());
//
//         // BlobKeyCache bc = BlobKeyCache.getBlobKeyCache();
//
//         if (blobKeys == null){
//         	log.info("blobkey is null");
//             System.out.println("blobkey is null");
//         }
//         else {
//         	// for(BlobKey blobkey:blobKeys){
//         	// 	bc.add(blobkey);
//         	// }
//             res.sendRedirect("/serve.jsp?blob-key=" + blobKeys.get(0).getKeyString() + );
//         }
//     }
// }
