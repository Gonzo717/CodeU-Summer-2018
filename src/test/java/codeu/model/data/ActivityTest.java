/* Used activityTest.java as reference */

package codeu.model.data;

import codeu.model.data.Activity;
import java.time.Instant;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;

enum ActivityType{ newUser, newConvo, newMessage;} 

public class ActivityTest {

	@Test
	public void testCreateUser() {
		ActivityType type = ActivityType.newUser;
		UUID id = UUID.randomUUID();
		Instant creation = Instant.now();

		Activity activity = new Activity(type, id, creation);
    Assert.assertEquals(type, activity.getType());
		Assert.assertEquals(id, activity.getId());
    Assert.assertEquals(creation, activity.getCreationTime());
	}
	
	@Test
	public void testCreateConvo() {
		ActivityType type = ActivityType.newConvo;
		UUID id = UUID.randomUUID();
		Instant creation = Instant.now();

		Activity activity = new Activity(type, id, creation);
    Assert.assertEquals(type, activity.getType());
		Assert.assertEquals(id, activity.getId());
    Assert.assertEquals(creation, activity.getCreationTime());
	}
	
	@Test
	public void testCreateMessage() {
		ActivityType type = ActivityType.newMessage;
		UUID id = UUID.randomUUID();
		Instant creation = Instant.now();

		Activity activity = new Activity(type, id, creation);
    Assert.assertEquals(type, activity.getType());
		Assert.assertEquals(id, activity.getId());
    Assert.assertEquals(creation, activity.getCreationTime());
	}
}