// package codeu.model.store.basic;
//
// import codeu.model.data.Activity;
// import codeu.model.data.Activity.ActivityType;
// import codeu.model.store.persistence.PersistentStorageAgent;
// import java.time.Instant;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.UUID;
// import org.junit.Assert;
// import org.junit.Before;
// import org.junit.Test;
// import org.mockito.Mockito;
//
// public class ActivityStoreTest {
// 	private ActivityStore activityStore;
// 	private PersistentStorageAgent mockPersistentStorageAgent;
//
// 	/* Creation of activities with different types */
// 	private final Activity NEW_USER_ACTIVITY = new Activity( ActivityType.USER, UUID.randomUUID(), UUID.randomUUID(), Instant.ofEpochMilli(1000));
// 	private final Activity NEW_CONVO_ACTIVITY = new Activity( ActivityType.CONVERSATION, UUID.randomUUID(), UUID.randomUUID(), Instant.ofEpochMilli(1000));
// 	private final Activity NEW_MSG_ACTIVITY = new Activity( ActivityType.MESSAGE, UUID.randomUUID(), UUID.randomUUID(), Instant.ofEpochMilli(1000));
//
// 	@Before
// 	public void setup() {
// 		mockPersistentStorageAgent = Mockito.mock(PersistentStorageAgent.class);
// 		activityStore = ActivityStore.getTestInstance(mockPersistentStorageAgent);
// 	}
//
// 	/* Add Tests adding multiple users of all activity types */
// 	@Test
// 	public void testAddActivities() {
// 		activityStore.addActivity(NEW_USER_ACTIVITY);
// 		activityStore.addActivity(NEW_CONVO_ACTIVITY);
// 		activityStore.addActivity(NEW_MSG_ACTIVITY);
//
// 		List<Activity> expectedActivities = activityStore.getAllActivities();
//
// 		assertEquals(expectedActivities.get(0), NEW_USER_ACTIVITY);
// 		assertEquals(expectedActivities.get(1), NEW_CONVO_ACTIVITY);
// 		assertEquals(expectedActivities.get(2), NEW_MSG_ACTIVITY);
//
//
// 	}
//
// 	/* Check equivalence for activities */
// 	private void assertEquals(Activity expectedActivity, Activity actualActivity) {
// 		Assert.assertEquals(expectedActivity.getType(), actualActivity.getType());
// 		Assert.assertEquals(expectedActivity.getId(), actualActivity.getId());
// 		Assert.assertEquals(expectedActivity.getOwnerId(), actualActivity.getOwnerId());
// 		Assert.assertEquals(expectedActivity.getCreationTime(), actualActivity.getCreationTime());
// 	}
// }
