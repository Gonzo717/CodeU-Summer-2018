package codeu.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import codeu.model.data.User;
import codeu.model.store.basic.UserStore;
import codeu.model.data.Profile;
import codeu.model.store.basic.ProfileStore;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;



public class ProfileUpload extends HttpServlet {
    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    /** Store class that gives access to Users. */
    private UserStore userStore;
    /** Store class that gives access to Profiles.  */
    private ProfileStore profileStore;


    @Override
    public void init() throws ServletException {
      super.init();
      setUserStore(UserStore.getInstance());
      setProfileStore(ProfileStore.getInstance());
    }

    /**
     * Sets the UserStore and ProfileS used by this servlet. This function provides a common setup method for use
     * by the test framework or the servlet's init() function.
     */
    void setUserStore(UserStore userStore) {
      this.userStore = userStore;
    }
    /**
     * Sets the ProfileStore and ProfileS used by this servlet. This function provides a common setup method for use
     * by the test framework or the servlet's init() function.
     */
     void setProfileStore(ProfileStore profileStore) {
       this.profileStore = profileStore;
     }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {

        Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
        List<BlobKey> blobKeys = blobs.get("myFile");

        if (blobKeys == null || blobKeys.isEmpty()) {
            res.sendRedirect("/WEB-INF/view/profile.jsp");
        } else {
          String username = (String) req.getSession().getAttribute("user");
            if (username == null) {
              // user is not logged in, don't let them create a conversation
              res.sendRedirect("/WEB-INF/view/profile.jsp");
              return;
            }

            User user = userStore.getUser(username);
            Profile profile = profileStore.getProfile(user.getProfileID());
            if (user == null) {
              // user was not found, don't let them create a conversation
              System.out.println("User not found: " + username);
              res.sendRedirect("/WEB-INF/view/profile.jsp");
              return;
            }
            //get BlobKey instance and save it.
          profile.setBlobKey(blobKeys.get(0).getKeyString());
          profileStore.updateProfile(profile);
          res.sendRedirect("/WEB-INF/view/profile.jsp");
        }
    }
}
