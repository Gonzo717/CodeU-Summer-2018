package codeu.model.store.persistence;

import codeu.model.data.Conversation;
import codeu.model.data.Conversation.Visibility;
import codeu.model.data.Conversation.Type;
import codeu.model.data.Message;
import java.time.Instant;
import org.javatuples.Pair;
import java.time.temporal.ChronoUnit;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import codeu.model.data.User;
import codeu.model.data.Activity;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.blobstore.BlobKey;


/**
 * Test class for PersistentDataStore. The PersistentDataStore class relies on DatastoreService,
 * which in turn relies on being deployed in an AppEngine context. Since this test doesn't run in
 * AppEngine, we use LocalServiceTestHelper to do all of the AppEngine setup so we can test. More
 * info: https://cloud.google.com/appengine/docs/standard/java/tools/localunittesting
 */
public class PersistentDataStoreTest {

	private PersistentDataStore persistentDataStore;
	private final LocalServiceTestHelper appEngineTestHelper =
			new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	@Before
	public void setup() {
		appEngineTestHelper.setUp();
		persistentDataStore = new PersistentDataStore();
	}

	@After
	public void tearDown() {
		appEngineTestHelper.tearDown();
	}

	@Test
	public void testSaveAndLoadUsers() throws PersistentDataStoreException {
		UUID idOne = UUID.fromString("10000000-2222-3333-4444-555555555555");
		UUID profileIdOne = UUID.fromString("10000000-2222-3333-6666-555555555555");
		String nameOne = "test_username_one";
		String passwordHashOne = "$2a$10$BNte6sC.qoL4AVjO3Rk8ouY6uFaMnsW8B9NjtHWaDNe8GlQRPRT1S";
		Boolean typeOne = false;
		Instant creationOne = Instant.ofEpochMilli(1000);
		User inputUserOne = new User(idOne, profileIdOne, nameOne, passwordHashOne, typeOne, creationOne);

		UUID idTwo = UUID.fromString("10000001-2222-3333-4444-555555555555");
		UUID profileIdTwo = UUID.fromString("10000001-2222-3333-7777-555555555555");
		String nameTwo = "test_username_two";
		String passwordHashTwo = "$2a$10$ttaMOMMGLKxBBuTN06VPvu.jVKif.IczxZcXfLcqEcFi1lq.sLb6i";
		Boolean typeTwo = false;
		Instant creationTwo = Instant.ofEpochMilli(2000);
		User inputUserTwo = new User(idTwo, profileIdTwo, nameTwo, passwordHashTwo, typeTwo, creationTwo);

		// save
		persistentDataStore.writeThrough(inputUserOne);
		persistentDataStore.writeThrough(inputUserTwo);

		// load
		List<User> resultUsers = persistentDataStore.loadUsers();

		// confirm that what we saved matches what we loaded
		User resultUserOne = resultUsers.get(0);
		Assert.assertEquals(idOne, resultUserOne.getId());
		Assert.assertEquals(nameOne, resultUserOne.getName());
		Assert.assertEquals(passwordHashOne, resultUserOne.getPasswordHash());
		Assert.assertEquals(creationOne, resultUserOne.getCreationTime());

		User resultUserTwo = resultUsers.get(1);
		Assert.assertEquals(idTwo, resultUserTwo.getId());
		Assert.assertEquals(nameTwo, resultUserTwo.getName());
		Assert.assertEquals(passwordHashTwo, resultUserTwo.getPasswordHash());
		Assert.assertEquals(creationTwo, resultUserTwo.getCreationTime());
	}

	// @Test
	public void testSaveAndLoadConversations() throws PersistentDataStoreException {
		UUID idOne = UUID.fromString("10000000-2222-3333-4444-555555555555");
		UUID ownerOne = UUID.fromString("10000001-2222-3333-4444-555555555555");
		String titleOne = "Test_Title";
		Instant creationOne = Instant.ofEpochMilli(1000);
		HashSet members = new HashSet<UUID>();
		members.add(ownerOne);
		Type type = Type.TEXT;
		Visibility visibility = Visibility.PUBLIC;
		String avatarImageURL = "fakeURL";
		String validTime = "4/SECONDS";
		String description = "fake :D";
		Conversation inputConversationOne = new Conversation(idOne, ownerOne, titleOne, creationOne,
																													members, type, visibility, avatarImageURL,
																													validTime, description);

		UUID idTwo = UUID.fromString("10000002-2222-3333-4444-555555555555");
		UUID ownerTwo = UUID.fromString("10000003-2222-3333-4444-555555555555");
		String titleTwo = "Test_Title_Two";
		Instant creationTwo = Instant.ofEpochMilli(2000);
		Conversation inputConversationTwo = new Conversation(idTwo, ownerTwo, titleTwo, creationTwo,
																													members, type, visibility, avatarImageURL,
																													validTime, description);

		// save
		persistentDataStore.writeThrough(inputConversationOne);
		persistentDataStore.writeThrough(inputConversationTwo);

		// load
		List<Conversation> resultConversations = persistentDataStore.loadConversations();

		// confirm that what we saved matches what we loaded
		Conversation resultConversationOne = resultConversations.get(0);
		Assert.assertEquals(idOne, resultConversationOne.getId());
		Assert.assertEquals(ownerOne, resultConversationOne.getOwnerId());
		Assert.assertEquals(titleOne, resultConversationOne.getTitle());
		Assert.assertEquals(creationOne, resultConversationOne.getCreationTime());
		Assert.assertEquals(members, resultConversationOne.getMembers());
		Assert.assertEquals(type, resultConversationOne.getConversationType());
		Assert.assertEquals(visibility, resultConversationOne.getConversationVisibility());
		Assert.assertEquals(avatarImageURL, resultConversationOne.getAvatarImageURL());
		Assert.assertEquals(validTime, resultConversationOne.getValidTime());
		Assert.assertEquals(description, resultConversationOne.getDescription());

		Conversation resultConversationTwo = resultConversations.get(1);
		Assert.assertEquals(idTwo, resultConversationTwo.getId());
		Assert.assertEquals(ownerTwo, resultConversationTwo.getOwnerId());
		Assert.assertEquals(titleTwo, resultConversationTwo.getTitle());
		Assert.assertEquals(creationTwo, resultConversationTwo.getCreationTime());
		Assert.assertEquals(members, resultConversationOne.getMembers());
		Assert.assertEquals(type, resultConversationOne.getConversationType());
		Assert.assertEquals(visibility, resultConversationOne.getConversationVisibility());
		Assert.assertEquals(avatarImageURL, resultConversationOne.getAvatarImageURL());
		Assert.assertEquals(validTime, resultConversationOne.getValidTime());
		Assert.assertEquals(description, resultConversationOne.getDescription());
	}

	@Test
	public void testSaveAndLoadMessages() throws PersistentDataStoreException {
		UUID idOne = UUID.fromString("10000000-2222-3333-4444-555555555555");
		UUID conversationOne = UUID.fromString("10000001-2222-3333-4444-555555555555");
		UUID authorOne = UUID.fromString("10000002-2222-3333-4444-555555555555");
		BlobKey blobkey = new BlobKey("boop");
		// BlobKey blobkey = null;
		Pair contentOne = new Pair<String, BlobKey>("TestText", blobkey);
		Instant creationOne = Instant.ofEpochMilli(1000);
		Message inputMessageOne =
				new Message(idOne, conversationOne, authorOne, contentOne, creationOne);

		UUID idTwo = UUID.fromString("10000003-2222-3333-4444-555555555555");
		UUID conversationTwo = UUID.fromString("10000004-2222-3333-4444-555555555555");
		UUID authorTwo = UUID.fromString("10000005-2222-3333-4444-555555555555");
		Pair contentTwo = new Pair<String, BlobKey>("TestText", blobkey);
		Instant creationTwo = Instant.ofEpochMilli(2000);
		Message inputMessageTwo =
				new Message(idTwo, conversationTwo, authorTwo, contentTwo, creationTwo);

		// save
		persistentDataStore.writeThrough(inputMessageOne);
		persistentDataStore.writeThrough(inputMessageTwo);

		// load
		List<Message> resultMessages = persistentDataStore.loadMessages();

		// confirm that what we saved matches what we loaded
		Message resultMessageOne = resultMessages.get(0);
		Assert.assertEquals(idOne, resultMessageOne.getId());
		Assert.assertEquals(conversationOne, resultMessageOne.getConversationId());
		Assert.assertEquals(authorOne, resultMessageOne.getAuthorId());
		Assert.assertEquals("TestText", resultMessageOne.getText());
		Assert.assertEquals(blobkey, resultMessageOne.getMedia());
		Assert.assertEquals(contentOne, resultMessageOne.getContent());
		Assert.assertEquals(creationOne, resultMessageOne.getCreationTime());

		Message resultMessageTwo = resultMessages.get(1);
		Assert.assertEquals(idTwo, resultMessageTwo.getId());
		Assert.assertEquals(conversationTwo, resultMessageTwo.getConversationId());
		Assert.assertEquals(authorTwo, resultMessageTwo.getAuthorId());
		Assert.assertEquals("hybrid", resultMessageOne.getMessageType());
		Assert.assertEquals(contentTwo, resultMessageTwo.getContent());
		Assert.assertEquals(creationTwo, resultMessageTwo.getCreationTime());
	}

	@Test
	public void testSaveAndLoadActivities() throws PersistentDataStoreException {
		UUID idOne = UUID.fromString("10000000-2222-3333-4444-555555555555");
		UUID idTwo = UUID.fromString("10000001-2222-3333-4444-555555555555");
		UUID idThree = UUID.fromString("10000002-2222-3333-4444-555555555555");

		UUID idFour = UUID.fromString("10000003-2222-3333-4444-555555555555");
		UUID idFive = UUID.fromString("10000004-2222-3333-4444-555555555555");
		UUID idSix = UUID.fromString("10000005-2222-3333-4444-555555555555");

		Instant creationOne = Instant.ofEpochMilli(1000);
		Instant creationTwo = Instant.ofEpochMilli(2000);
		Instant creationThree = Instant.ofEpochMilli(3000);

		Activity userAct = new Activity( "newUser", idOne, idFour, creationOne);
		Activity convoAct = new Activity( "newConvo", idTwo, idFive, creationTwo);
		Activity msgAct = new Activity( "newMessage", idThree, idSix, creationThree);

		//save
		persistentDataStore.writeThrough(userAct);
		persistentDataStore.writeThrough(convoAct);
		persistentDataStore.writeThrough(msgAct);

		//load
		List<Activity> resultActivities = persistentDataStore.loadActivities();

		//confirm that what we saved matches what we loaded
		Activity resultUserAct = resultActivities.get(2);
		Assert.assertEquals("newUser", resultUserAct.getType());
		Assert.assertEquals(idOne, resultUserAct.getId());
		Assert.assertEquals(idFour, resultUserAct.getOwner());
		Assert.assertEquals(creationOne, resultUserAct.getCreationTime());

		Activity resultConvoAct = resultActivities.get(1);
		Assert.assertEquals("newConvo", resultConvoAct.getType());
		Assert.assertEquals(idTwo, resultConvoAct.getId());
		Assert.assertEquals(idFive, resultConvoAct.getOwner());
		Assert.assertEquals(creationTwo, resultConvoAct.getCreationTime());

		Activity resultMsgAct = resultActivities.get(0);
		Assert.assertEquals("newMessage", resultMsgAct.getType());
		Assert.assertEquals(idThree, resultMsgAct.getId());
		Assert.assertEquals(idSix, resultMsgAct.getOwner());
		Assert.assertEquals(creationThree, resultMsgAct.getCreationTime());
	}
}
