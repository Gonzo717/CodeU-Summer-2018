/* Used activityTest.java as reference */

package codeu.model.data;

import codeu.model.data.Activity;
import codeu.model.data.Activity.ActivityType;
import java.time.Instant;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;

public class ActivityTest {

	@Test
	public void testCreateUser() {
		ActivityType type = ActivityType.USER;
		UUID id = UUID.randomUUID();
		UUID ownerId = UUID.randomUUID();
		Instant creation = Instant.now();

		Activity activity = new Activity(type, id, ownerId, creation);
		Assert.assertEquals(type, activity.getType());
		Assert.assertEquals(id, activity.getId());
		Assert.assertEquals(ownerId, activity.getOwnerId());
		Assert.assertEquals(creation, activity.getCreationTime());
	}

	@Test
	public void testCreateConvo() {
		ActivityType type = ActivityType.CONVERSATION;
		UUID id = UUID.randomUUID();
		UUID ownerId = UUID.randomUUID();
		Instant creation = Instant.now();

		Activity activity = new Activity(type, id, ownerId, creation);
		Assert.assertEquals(type, activity.getType());
		Assert.assertEquals(id, activity.getId());
		Assert.assertEquals(ownerId, activity.getOwnerId());
		Assert.assertEquals(creation, activity.getCreationTime());
	}

	@Test
	public void testCreateMessage() {
		ActivityType type = ActivityType.MESSAGE;
		UUID id = UUID.randomUUID();
		UUID ownerId = UUID.randomUUID();
		Instant creation = Instant.now();

		Activity activity = new Activity(type, id, ownerId, creation);
		Assert.assertEquals(type, activity.getType());
		Assert.assertEquals(id, activity.getId());
		Assert.assertEquals(ownerId, activity.getOwnerId());
		Assert.assertEquals(creation, activity.getCreationTime());
	}
}
