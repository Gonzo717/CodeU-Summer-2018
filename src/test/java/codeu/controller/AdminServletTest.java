// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package codeu.controller;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

import codeu.model.data.User;
import codeu.model.data.Conversation;
import codeu.model.data.Message;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.time.temporal.ChronoUnit;
import codeu.model.data.Conversation.Type;
import codeu.model.data.Conversation.Visibility;
import codeu.model.store.basic.UserStore;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.javatuples.Pair;
import com.google.appengine.api.blobstore.BlobKey;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class AdminServletTest {

  private AdminServlet adminServlet;
  private HttpServletRequest mockRequest;
  private HttpServletResponse mockResponse;
  private RequestDispatcher mockRequestDispatcher;
  private UserStore mockUserStore;
  private ConversationStore mockConvoStore;
  private MessageStore mockMessageStore;
  private Message mockMessage;
  private Conversation fakeConversation;
  private UUID fakeUserID;
  private User mockUser;

  @Before
  public void setup() {
    adminServlet = new AdminServlet();
    mockRequest = Mockito.mock(HttpServletRequest.class);
    mockResponse = Mockito.mock(HttpServletResponse.class);
    mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);

    mockUserStore = Mockito.mock(UserStore.class);
    mockConvoStore = Mockito.mock(ConversationStore.class);
    mockMessageStore = Mockito.mock(MessageStore.class);
    fakeUserID = UUID.randomUUID();
    mockUser = Mockito.mock(User.class);
    mockMessage = Mockito.mock(Message.class);

    UUID fakeConversationId = UUID.randomUUID();
		HashSet members = new HashSet<>();
    fakeConversation =
				new Conversation(
						fakeConversationId, UUID.randomUUID(), "test_conversation", Instant.now(),
						members, Type.TEXT, Visibility.PUBLIC,
						"fakeURL", ChronoUnit.DECADES, "fake :D");

    mockUserStore.addUser(mockUser);
    mockConvoStore.addConversation(fakeConversation);
    mockMessageStore.addMessage(mockMessage);

    adminServlet.setUserStore(mockUserStore);
    adminServlet.setConversationStore(mockConvoStore);
    adminServlet.setMessageStore(mockMessageStore);

    Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/admin.jsp"))
        .thenReturn(mockRequestDispatcher);
  }

  @Test
  public void testDoGet() throws IOException, ServletException {
    adminServlet.doGet(mockRequest, mockResponse);
    mockRequest.getParameter("username");
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testGetStats() throws IOException, ServletException {
    Mockito.when(mockRequest.getParameter("username")).thenReturn(null);

    List<Conversation> fakeConversationList = new ArrayList<>();

    fakeConversationList.add(fakeConversation);

    Mockito.when(mockConvoStore.getAllConversations())
        .thenReturn(fakeConversationList);

    List<Message> fakeMessageList = new ArrayList<>();
    fakeMessageList.add(mockMessage);

    Mockito.when(mockMessageStore.getAllMessages())
        .thenReturn(fakeMessageList);

    List totalUsers = mockUserStore.getUsers();
    List totalMessages = fakeMessageList;
    List totalConvos = mockConvoStore.getAllConversations();

    Mockito.when(mockUserStore.getUsers()).thenReturn(totalUsers);
    Mockito.when(mockMessageStore.getAllMessages()).thenReturn(totalMessages);
    Mockito.when(mockConvoStore.getAllConversations()).thenReturn(totalConvos);

    fakeUserID = UUID.randomUUID();
    Mockito.when(mockMessage.getAuthorId()).thenReturn(fakeUserID);
    Mockito.when(mockUserStore.getUser(fakeUserID)).thenReturn(mockUser);
    Mockito.when(mockUser.getName()).thenReturn("test username");

    Mockito.when(mockMessage.getCreationTime()).thenReturn(Instant.now());

    int numTotalUsers = totalUsers.size();
    int numTotalMessages = totalMessages.size();
    int numTotalConvos = totalConvos.size();

    HttpSession mockSession = Mockito.mock(HttpSession.class);

    Mockito.when(mockRequest.getSession()).thenReturn(mockSession);

    adminServlet.doPost(mockRequest, mockResponse);

    Mockito.verify(mockSession).setAttribute("numUsers", numTotalUsers);
    Mockito.verify(mockSession).setAttribute("numMessages", numTotalMessages);
    Mockito.verify(mockSession).setAttribute("numConvos", numTotalConvos);
    Mockito.verify(mockSession).setAttribute("mostActiveUser", "test username");

    Mockito.verify(mockResponse).sendRedirect("/admin");
  }

  @Test
  public void testDoPost_addAdmin() throws IOException, ServletException {
    Mockito.when(mockRequest.getParameter("username")).thenReturn("test username");

    adminServlet.doPost(mockRequest, mockResponse);

    Mockito.verify(mockRequest).setAttribute("success", "Admin successfully added");
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

}
