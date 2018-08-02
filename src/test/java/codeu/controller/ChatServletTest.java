// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//		http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package codeu.controller;

import codeu.model.data.Conversation;
import codeu.model.data.Conversation.Visibility;
import codeu.model.data.Conversation.Type;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.data.Activity;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.GroupConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import codeu.model.store.basic.ActivityStore;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import org.javatuples.Pair;
import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.blobstore.BlobKey;


public class ChatServletTest {

	private ChatServlet chatServlet;
	private HttpServletRequest mockRequest;
	private HttpSession mockSession;
	private HttpServletResponse mockResponse;
	private RequestDispatcher mockRequestDispatcher;
	private ConversationStore mockConversationStore;
	private GroupConversationStore mockGroupConversationStore;
	private MessageStore mockMessageStore;
	private UserStore mockUserStore;
	private ActivityStore mockActivityStore;

	@Before
	public void setup() {
		chatServlet = new ChatServlet();

		mockRequest = Mockito.mock(HttpServletRequest.class);
		mockSession = Mockito.mock(HttpSession.class);
		Mockito.when(mockRequest.getSession()).thenReturn(mockSession);

		mockResponse = Mockito.mock(HttpServletResponse.class);
		mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
		Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/chat.jsp"))
				.thenReturn(mockRequestDispatcher);

		mockConversationStore = Mockito.mock(ConversationStore.class);
		chatServlet.setConversationStore(mockConversationStore);

		mockGroupConversationStore = Mockito.mock(GroupConversationStore.class);
		chatServlet.setGroupConversationStore(mockGroupConversationStore);

		mockMessageStore = Mockito.mock(MessageStore.class);
		chatServlet.setMessageStore(mockMessageStore);

		mockUserStore = Mockito.mock(UserStore.class);
		chatServlet.setUserStore(mockUserStore);

		mockActivityStore = Mockito.mock(ActivityStore.class);
		chatServlet.setActivityStore(mockActivityStore);
	}

	@Test
	public void testDoGet() throws IOException, ServletException {
		UUID fakeConversationId = UUID.fromString("10000000-2222-3333-4444-555555555555");
		HashSet members = new HashSet<>();
		Type type = Type.TEXT;
		Visibility visibility = Visibility.PUBLIC;
		String avatarImageURL = "fakeURL";
		boolean isActive = true;
		String validTime = "3/SECONDS";
		String description = "fake :D";

		Conversation fakeConversation =
				new Conversation(fakeConversationId, UUID.randomUUID(), "test conversation", Instant.now(), members, type,
													visibility, avatarImageURL, validTime, description);
		Mockito.when(mockConversationStore.getConversationWithId(fakeConversationId))
				.thenReturn(fakeConversation);
		System.out.println(fakeConversation.getId());
		System.out.println(mockConversationStore.getConversationWithId(fakeConversationId));
		Mockito.when(mockRequest.getRequestURI()).thenReturn("/chat/10000000-2222-3333-4444-555555555555");



		List<Message> fakeMessageList = new ArrayList<>();
		UUID idOne = UUID.fromString("10000000-2222-3333-4444-555555555555");
		UUID conversationOne = UUID.fromString("10000001-2222-3333-4444-555555555555");
		UUID authorOne = UUID.fromString("10000002-2222-3333-4444-555555555555");
		BlobKey blobkey = null;
		Pair contentOne = new Pair<>("TestContent", blobkey);
		Instant creationOne = Instant.ofEpochMilli(1000);
		Message inputMessageOne =
				new Message(idOne, fakeConversationId, authorOne, contentOne, creationOne);

		fakeMessageList.add(inputMessageOne);

		Mockito.when(mockMessageStore.getMessagesInConversation(fakeConversationId))
				.thenReturn(fakeMessageList);

		chatServlet.doGet(mockRequest, mockResponse);

		Mockito.verify(mockRequest).setAttribute("conversation", fakeConversation);
		Mockito.verify(mockRequest).setAttribute("messages", fakeMessageList);
		Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
	}

	// @Test
	// public void testDoGet_badConversation() throws IOException, ServletException {
	// 	Mockito.when(mockRequest.getRequestURI()).thenReturn("/chat/bad_conversation");
	// 	Mockito.when(mockConversationStore.getConversationWithTitle("bad_conversation"))
	// 			.thenReturn(null);
	// 	// Mockito.when()
	// 	// Mockito.when(mockGroupConversationStore.getGroupConversationWithTitle("bad_conversation").getId())
	// 	// 		.thenReturn(null);
	//
	// 	chatServlet.doGet(mockRequest, mockResponse);
	//
	// 	Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
	// }

	@Test
	public void testDoPost_UserNotLoggedIn() throws IOException, ServletException {
		Mockito.when(mockSession.getAttribute("user")).thenReturn(null);

		chatServlet.doPost(mockRequest, mockResponse);

		Mockito.verify(mockMessageStore, Mockito.never()).addMessage(Mockito.any(Message.class));
		Mockito.verify(mockResponse).sendRedirect("/login");
	}

	@Test
	public void testDoPost_InvalidUser() throws IOException, ServletException {
		Mockito.when(mockSession.getAttribute("user")).thenReturn("test_username");
		Mockito.when(mockUserStore.getUser("test_username")).thenReturn(null);

		chatServlet.doPost(mockRequest, mockResponse);

		Mockito.verify(mockMessageStore, Mockito.never()).addMessage(Mockito.any(Message.class));
		Mockito.verify(mockResponse).sendRedirect("/login");
	}

	@Test
	public void testDoPost_ConversationNotFound() throws IOException, ServletException {
		Mockito.when(mockRequest.getRequestURI()).thenReturn("/chat/10000000-2222-3333-4444-555555555555");
		Mockito.when(mockSession.getAttribute("user")).thenReturn("test_username");

		User fakeUser =
				new User(
						UUID.randomUUID(),
						UUID.randomUUID(),
						"test_username",
						"$2a$10$bBiLUAVmUFK6Iwg5rmpBUOIBW6rIMhU1eKfi3KR60V9UXaYTwPfHy",
						false,
						Instant.now());
		Mockito.when(mockUserStore.getUser("test_username")).thenReturn(fakeUser);

		Mockito.when(mockConversationStore.getConversationWithTitle("test_conversation")).thenReturn(null);

		Mockito.when(mockConversationStore.getConversationWithId(UUID.fromString("10000000-2222-3333-4444-555555555555"))).thenReturn(null);

		Mockito.when(mockGroupConversationStore.getGroupConversationWithTitle("test_conversation")).thenReturn(null);

		chatServlet.doPost(mockRequest, mockResponse);

		Mockito.verify(mockMessageStore, Mockito.never()).addMessage(Mockito.any(Message.class));
		Mockito.verify(mockResponse).sendRedirect("/conversations");
	}

	@Test
	public void testDoPost_StoresMessage() throws IOException, ServletException {
		Mockito.when(mockRequest.getRequestURI()).thenReturn("/chat/10000000-2222-3333-4444-555555555555");
		Mockito.when(mockSession.getAttribute("user")).thenReturn("test_username");

		User fakeUser =
				new User(
						UUID.randomUUID(),
						UUID.randomUUID(),
						"test_username",
						"$2a$10$bBiLUAVmUFK6Iwg5rmpBUOIBW6rIMhU1eKfi3KR60V9UXaYTwPfHy",
						false,
						Instant.now());
		Mockito.when(mockUserStore.getUser("test_username")).thenReturn(fakeUser);
		HashSet members = new HashSet<>();
		members.add(fakeUser);
		Type type = Type.TEXT;
		Visibility visibility = Visibility.PUBLIC;
		String avatarImageURL = "fakeURL";
		String validTime = "9/DAYS";
		String description = "fake :D";
		UUID conversationID = UUID.fromString("10000000-2222-3333-4444-555555555555");
		String msgText = "Test message";
		BlobKey msgMedia = null;

		Conversation fakeConversation =
				new Conversation(conversationID, UUID.randomUUID(), "test_conversation", Instant.now(), members, type,
													visibility, avatarImageURL, validTime, description);
		// Mockito.when(mockConversationStore.getConversationWithTitle("test_conversation"))
		// 		.thenReturn(fakeConversation);
		Mockito.when(mockConversationStore.getConversationWithId(conversationID))
				.thenReturn(fakeConversation);

		Pair messageContent = new Pair<>(msgText, msgMedia);

		Mockito.when(mockRequest.getParameter("messageText")).thenReturn("Test message");
		Mockito.when(mockRequest.getParameter("messageMedia")).thenReturn(null);

		chatServlet.doPost(mockRequest, mockResponse);

		ArgumentCaptor<Message> messageArgumentCaptor = ArgumentCaptor.forClass(Message.class);
		Mockito.verify(mockMessageStore).addMessage(messageArgumentCaptor.capture());
		Assert.assertEquals(messageContent, messageArgumentCaptor.getValue().getContent());

		Mockito.verify(mockResponse).sendRedirect("/chat/10000000-2222-3333-4444-555555555555");
	}

	@Test
	public void testDoPost_CleansHtmlContent() throws IOException, ServletException {
		Mockito.when(mockRequest.getRequestURI()).thenReturn("/chat/10000000-2222-3333-4444-555555555555");
		Mockito.when(mockSession.getAttribute("user")).thenReturn("test_username");

		User fakeUser =
				new User( UUID.randomUUID(), UUID.randomUUID(), "test_username", "$2a$10$eDhncK/4cNH2KE.Y51AWpeL8/5znNBQLuAFlyJpSYNODR/SJQ/Fg6", false, Instant.now());
		Mockito.when(mockUserStore.getUser("test_username")).thenReturn(fakeUser);

		Type type = Type.TEXT;
		Visibility visibility = Visibility.PUBLIC;
		String avatarImageURL = "fakeURL";
		boolean isActive = true;
		String validTime = "4/HOURS";
		String description = "fake :D";

		HashSet members = new HashSet<>();
		members.add(fakeUser);
		UUID conversationID = UUID.fromString("10000000-2222-3333-4444-555555555555");
		Conversation fakeConversation =
				new Conversation(conversationID, UUID.randomUUID(), "test_conversation", Instant.now(), members, type,
													visibility, avatarImageURL, validTime, description);

		// Mockito.when(mockConversationStore.getConversationWithTitle("test_conversation"))
		// 		.thenReturn(fakeConversation);

		Mockito.when(mockConversationStore.getConversationWithId(conversationID))
				.thenReturn(fakeConversation);

		Mockito.when(mockRequest.getParameter("messageText"))
				.thenReturn("Contains <b>html</b> and <script>JavaScript</script>content.");
		// BlobKey blobkey = null;
		// String messagetext = "Contains <b>html</b> and <script>JavaScript</script>content.";
		// Pair messageContent = new Pair<>(messagetext, blobkey);

		chatServlet.doPost(mockRequest, mockResponse);

		ArgumentCaptor<Message> messageArgumentCaptor = ArgumentCaptor.forClass(Message.class);
		Mockito.verify(mockMessageStore).addMessage(messageArgumentCaptor.capture());
		Assert.assertEquals(
				"Contains html and content.", messageArgumentCaptor.getValue().getContent().getValue0());

		Mockito.verify(mockResponse).sendRedirect("/chat/10000000-2222-3333-4444-555555555555");
	}
}
