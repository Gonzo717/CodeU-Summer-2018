package codeu.model.data;

import java.time.Instant;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;


enum ActivityType{ newUser, newConvo, newMessage;} //Find a way so this only has to be declared once

public class ActivityTest {

	@Test
	public void testCreateUser() {
		ActivityType type = newUser;
		UUID id = UUID.randomUUID();
		Instant creation = Instant.now();

		Activity activty = new Activity(type, id, creation);
    Assert.assertEquals(type, conversation.getType());
		Assert.assertEquals(id, conversation.getId());
    Assert.assertEquals(creation, conversation.getCreationTime());
	}
	
	@Test
	public void testCreateConvo() {
		ActivityType type = newConvo;
		UUID id = UUID.randomUUID();
		Instant creation = Instant.now();

		Activity activty = new Activity(type, id, creation);
    Assert.assertEquals(type, conversation.getType());
		Assert.assertEquals(id, conversation.getId());
    Assert.assertEquals(creation, conversation.getCreationTime());
	}
	
	@Test
	public void testCreateMessage() {
		ActivityType type = newMessage;
		UUID id = UUID.randomUUID();
		Instant creation = Instant.now();

		Activity activty = new Activity(type, id, creation);
    Assert.assertEquals(type, conversation.getType());
		Assert.assertEquals(id, conversation.getId());
    Assert.assertEquals(creation, conversation.getCreationTime());
	}
}