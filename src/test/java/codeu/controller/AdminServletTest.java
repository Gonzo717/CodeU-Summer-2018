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
import codeu.model.store.basic.UserStore;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

  @Before
  public void setup() {
    adminServlet = new AdminServlet();
    mockRequest = Mockito.mock(HttpServletRequest.class);
    mockResponse = Mockito.mock(HttpServletResponse.class);
    mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);

    mockUserStore = Mockito.mock(UserStore.class);
    mockConvoStore = Mockito.mock(ConversationStore.class);
    mockMessageStore = Mockito.mock(MessageStore.class);

    User fakeUser =
        new User(
            UUID.randomUUID(),
            "test username",
            "$2a$10$bBiLUAVmUFK6Iwg5rmpBUOIBW6rIMhU1eKfi3KR60V9UXaYTwPfHy",
            Instant.now());

    mockUserStore.addUser(fakeUser);

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

    UUID fakeConversationId = UUID.randomUUID();
    List<Conversation> fakeConversationList = new ArrayList<>();
    Conversation fakeConversation =
        new Conversation(fakeConversationId, UUID.randomUUID(), "test_conversation", Instant.now());
    
    fakeConversationList.add(fakeConversation);

    Mockito.when(mockConvoStore.getAllConversations())
        .thenReturn(fakeConversationList);

    List<Message> fakeMessageList = new ArrayList<>();
    fakeMessageList.add(
        new Message(
            UUID.randomUUID(),
            fakeConversationId,
            UUID.randomUUID(),
            "test message",
            Instant.now()));
    Mockito.when(mockMessageStore.getAllMessages())
        .thenReturn(fakeMessageList);

    List totalUsers = mockUserStore.getUsers();
    List totalMessages = fakeMessageList;
    List totalConvos = mockConvoStore.getAllConversations();

    Mockito.when(mockUserStore.getUsers()).thenReturn(totalUsers);
    Mockito.when(mockMessageStore.getAllMessages()).thenReturn(totalMessages);
    Mockito.when(mockConvoStore.getAllConversations()).thenReturn(totalConvos);

    int numTotalUsers = totalUsers.size();
    int numTotalMessages = totalMessages.size();
    int numTotalConvos = totalConvos.size(); 

    HttpSession mockSession = Mockito.mock(HttpSession.class);
    
    Mockito.when(mockRequest.getSession()).thenReturn(mockSession);

    adminServlet.doPost(mockRequest, mockResponse);

    Mockito.verify(mockSession).setAttribute("numUsers", numTotalUsers);
    Mockito.verify(mockSession).setAttribute("numMessages", numTotalMessages);
    Mockito.verify(mockSession).setAttribute("numConvos", numTotalConvos);

    Mockito.verify(mockResponse).sendRedirect("/admin");
  }

  @Test
  public void testDoPost_addAdmin() throws IOException, ServletException {
    Mockito.when(mockRequest.getParameter("username")).thenReturn("test username");

    adminServlet.doPost(mockRequest, mockResponse);

    Mockito.verify(mockRequest).setAttribute("success", "Admin successfully added");
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  // @Test
  // public void testDoPost_ExistingUser() throws IOException, ServletException {

  //   User user =
  //       new User(
  //           UUID.randomUUID(),
  //           "test username",
  //           "$2a$10$.e.4EEfngEXmxAO085XnYOmDntkqod0C384jOR9oagwxMnPNHaGLa",
  //           Instant.now());

  //   Mockito.when(mockRequest.getParameter("username")).thenReturn("test username");
  //   Mockito.when(mockRequest.getParameter("password")).thenReturn("test password");

  //   UserStore mockUserStore = Mockito.mock(UserStore.class);
  //   Mockito.when(mockUserStore.isUserRegistered("test username")).thenReturn(true);
  //   Mockito.when(mockUserStore.getUser("test username")).thenReturn(user);
  //   adminServlet.setUserStore(mockUserStore);


  //   HttpSession mockSession = Mockito.mock(HttpSession.class);
  //   Mockito.when(mockRequest.getSession()).thenReturn(mockSession);

  //   loginServlet.doPost(mockRequest, mockResponse);

  //   Mockito.verify(mockUserStore, Mockito.never()).addUser(Mockito.any(User.class));
  //   Mockito.verify(mockSession).setAttribute("user", "test username");
  //   Mockito.verify(mockResponse).sendRedirect("/conversations");
  // }

  // Tests to check for the administrator get stats functionality

}
