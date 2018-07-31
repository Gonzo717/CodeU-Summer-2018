// file Serve.java
package codeu.controller;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import codeu.model.data.User;
import codeu.model.store.basic.UserStore;
import codeu.model.data.Profile;
import codeu.model.store.basic.ProfileStore;
import java.util.UUID;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProfileServe extends HttpServlet {
    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    /** Store class that gives access to Users. */
    private UserStore userStore;
    /** Store class that gives access to Profiles. */
    private ProfileStore profileStore;

    public void init() throws ServletException {
      super.init();
      setUserStore(UserStore.getInstance());
      setProfileStore(ProfileStore.getInstance());
    }

    /**
     * Sets the UserStore used by this servlet. This function provides a common setup method for use
     * by the test framework or the servlet's init() function.
     */
    void setUserStore(UserStore userStore) {
      this.userStore = userStore;
    }
    /**
     * Sets the ProfileStore used by this servlet. This function provides a common setup method for use
     * by the test framework or the servlet's init() function.
     */
     void setProfileStore(ProfileStore profileStore) {
       this.profileStore  = profileStore;
     }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws IOException {
          String username = (String) req.getSession().getAttribute("user");
            if (username == null) {
              // user is not logged in, don't let them create a conversation
              res.sendRedirect("/WEB-INF/view/profile.jsp");
              return;
            }

            User user = userStore.getUser(username);
            if (user == null) {
              // user was not found, don't let them create a conversation
              System.out.println("User not found: " + username);
              res.sendRedirect("/WEB-INF/view/profile.jsp");
              return;
            }

            Profile profile = profileStore.getProfile(user.getProfileID());
            String key = profile.getBlobKey();

            if (key == null || key.isEmpty()) {
                res.sendRedirect("/WEB-INF/view/profile.jsp");
                return;
            }

            BlobKey blobKey = new BlobKey(key);
            blobstoreService.serve(blobKey, res);
        }
}
