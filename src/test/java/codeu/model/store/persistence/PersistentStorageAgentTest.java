package codeu.model.store.persistence;
import java.lang.InterruptedException;
import java.util.concurrent.ExecutionException;
import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.data.Activity;
import codeu.model.data.Activity.ActivityType;
import java.time.Instant;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Contains tests of the PersistentStorageAgent class. Currently that class is just a pass-through
 * to PersistentDataStore, so these tests are pretty trivial. If you modify how
 * PersistentStorageAgent writes to PersistentDataStore, or if you swap out the backend to something
 * other than PersistentDataStore, then modify these tests.
 */
public class PersistentStorageAgentTest {

	private PersistentDataStore mockPersistentDataStore;
	private PersistentStorageAgent persistentStorageAgent;

	@Before
	public void setup() {
		mockPersistentDataStore = Mockito.mock(PersistentDataStore.class);
		persistentStorageAgent = PersistentStorageAgent.getTestInstance(mockPersistentDataStore);
	}

	@Test
	public void testLoadUsers() throws PersistentDataStoreException {
		persistentStorageAgent.loadUsers();
		Mockito.verify(mockPersistentDataStore).loadUsers();
	}

	@Test
	public void testLoadConversations() throws PersistentDataStoreException {
		persistentStorageAgent.loadConversations();
		Mockito.verify(mockPersistentDataStore).loadConversations();
	}

	@Test
	public void testLoadMessages() throws PersistentDataStoreException {
		persistentStorageAgent.loadMessages();
		Mockito.verify(mockPersistentDataStore).loadMessages();
	}

	@Test
	public void testLoadActivities() throws PersistentDataStoreException {
		persistentStorageAgent.loadActivities();
		Mockito.verify(mockPersistentDataStore).loadActivities();
	}

	@Test
	public void testWriteThroughUser() {
		User user =
				new User(
						UUID.randomUUID(),
						UUID.randomUUID(),
						"test_username",
						"$2a$10$5GNCbSPS1sqqM9.hdiE2hexn1w.vnNoR.CaHIztFEhdAD7h82tqX.",
						false,
						Instant.now());
		persistentStorageAgent.writeThrough(user);
    try {
      Mockito.verify(mockPersistentDataStore).writeThrough(user);
    }
    catch(InterruptedException e) {}
    catch(ExecutionException e) {}
	}

	@Test
	public void testWriteThroughConversation() {
		Conversation conversation =
				new Conversation(UUID.randomUUID(), UUID.randomUUID(), "test_conversation", Instant.now());
		persistentStorageAgent.writeThrough(conversation);
    try {
      Mockito.verify(mockPersistentDataStore).writeThrough(conversation);
    }
    catch(InterruptedException e) {}
    catch(ExecutionException e) {}
	}

	@Test
	public void testWriteThroughMessage() {
		Message message =
				new Message(
						UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), "test content", Instant.now());
		persistentStorageAgent.writeThrough(message);
    try {
      Mockito.verify(mockPersistentDataStore).writeThrough(message);
    }
    catch(InterruptedException e) {}
    catch(ExecutionException e) {}
	}

	@Test
	public void testWriteThroughActivity() {
		Activity activity =
				new Activity(ActivityType.USER, UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), Instant.now());
		persistentStorageAgent.writeThrough(activity);
    try {
      Mockito.verify(mockPersistentDataStore).writeThrough(activity);
		}
		catch(InterruptedException e) {}
		catch(ExecutionException e) {}
	}
}
