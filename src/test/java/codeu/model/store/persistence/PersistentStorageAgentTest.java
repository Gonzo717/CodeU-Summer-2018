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

import codeu.model.data.Conversation.Visibility;
import codeu.model.data.Conversation.Type;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import org.javatuples.Pair;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.blobstore.BlobKey;

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
		HashSet members = new HashSet<>();
		Type type = Type.TEXT;
		Visibility visibility = Visibility.PUBLIC;
		String avatarImageURL = "fakeURL";
		String validTime = "5/MINUTES";
		String description = "fake :D";

		Conversation conversation =
				new Conversation(UUID.randomUUID(), UUID.randomUUID(), "test_conversation", Instant.now(), members, type,
													visibility, avatarImageURL, validTime, description);

		persistentStorageAgent.writeThrough(conversation);
    try {
      Mockito.verify(mockPersistentDataStore).writeThrough(conversation);
    }
    catch(InterruptedException e) {}
    catch(ExecutionException e) {}
	}

	@Test
	public void testWriteThroughMessage() {
		UUID idOne = UUID.fromString("10000000-2222-3333-4444-555555555555");
		UUID conversationOne = UUID.fromString("10000001-2222-3333-4444-555555555555");
		UUID authorOne = UUID.fromString("10000002-2222-3333-4444-555555555555");
		BlobKey blobkey = null;
		Pair contentOne = new Pair<>("TestContent", blobkey);
		Instant creationOne = Instant.ofEpochMilli(1000);
		Message inputMessageOne =
				new Message(idOne, conversationOne, authorOne, contentOne, creationOne);

		persistentStorageAgent.writeThrough(inputMessageOne);
		try {
		Mockito.verify(mockPersistentDataStore).writeThrough(inputMessageOne);
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
