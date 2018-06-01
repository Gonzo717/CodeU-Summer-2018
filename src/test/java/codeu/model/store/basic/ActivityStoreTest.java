package codeu.model.store.basic;

import codeu.model.data.Activity;
import codeu.model.store.persistence.PersistentStorageAgent;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ActivityStoreTest {
  private ActivityStore activityStore;
  private PersistentStorageAgent mockPersistentStorageAgent;
  
  /* Creation of activities with different types */
  private final Activity NEW_USER_ACTIVITY = new Activity( "newUser", UUID.randomUUID(), UUID.randomUUID(), Instant.ofEpochMilli(1000));
  private final Activity NEW_CONVO_ACTIVITY = new Activity( "newConvo", UUID.randomUUID(), UUID.randomUUID(), Instant.ofEpochMilli(1000));
  private final Activity NEW_MSG_ACTIVITY = new Activity( "newMessage", UUID.randomUUID(), UUID.randomUUID(), Instant.ofEpochMilli(1000));
  
  @Before
  public void setup() {
    mockPersistentStorageAgent = Mockito.mock(PersistentStorageAgent.class);
    activityStore = ActivityStore.getTestInstance(mockPersistentStorageAgent);    
  }
  
  /* Add Tests adding multiple users of all activity types */

  /* Check equivalence for activities */
  private void assertEquals(Activity expectedActivity, Activity actualActivity) {
    Assert.assertEquals(expectedActivity.getType(), actualActivity.getType());
    Assert.assertEquals(expectedActivity.getId(), actualActivity.getId());
    Assert.assertEquals(expectedActivity.getOwner(), actualActivity.getOwner());
    Assert.assertEquals(expectedActivity.getCreationTime(), actualActivity.getCreationTime());
  }
}
