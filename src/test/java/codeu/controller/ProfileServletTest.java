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
import org.mockito.ArgumentCaptor;
import org.junit.Assert;
import org.mockito.Mockito;

public class ProfileServletTest {
  private ProfileServlet profileServlet;
  private HttpServletRequest mockRequest;
  private HttpSession mockSession;
  private HttpServletResponse mockResponse;
  private RequestDispatcher mockRequestDispatcher;
  private MessageStore mockMessageStore;
  private Message mockMessage;
  private UserStore mockUserStore;
  private User mockUser;

  @Before
  public void setup() {
    profileServlet = new ProfileServlet();

    mockRequest = Mockito.mock(HttpServletRequest.class);
    mockSession = Mockito.mock(HttpSession.class);
    Mockito.when(mockRequest.getSession()).thenReturn(mockSession);

    mockResponse = Mockito.mock(HttpServletResponse.class);
    mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
    Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/profile.jsp"))
        .thenReturn(mockRequestDispatcher);

    mockMessageStore = Mockito.mock(MessageStore.class);
    mockMessage = Mockito.mock(Message.class);
    profileServlet.setMessageStore(mockMessageStore);
    mockMessageStore.addMessage(mockMessage);

    mockUserStore = Mockito.mock(UserStore.class);
    mockUser = Mockito.mock(User.class);
    profileServlet.setUserStore(mockUserStore);
    mockUserStore.addUser(mockUser);

  }
  //
  // @Test
  // public void testDoGet() throws IOException, ServletException {
  //   Mockito.when(mockRequest.getRequestURI()).thenReturn("/user/test_user");
  //
  //   UUID fakeUserId = UUID.randomUUID();
  //   User user =
  //         new User(
  //             fakeUserId,
  //             UUID.randomUUID(),
  //             "test_user",
  //             "$2a$10$.e.4EEfngEXmxAO085XnYOmDntkqod0C384jOR9oagwxMnPNHaGLa",
  //             false,
  //             Instant.now());
  //
  //   Mockito.when(mockUserStore.getUser("test_user")).thenReturn(user);
  //
  //   UUID fakeConversationId = UUID.randomUUID();
  //   List<Message> fakeMessageList = new ArrayList<>();
  //   fakeMessageList.add(
  //       new Message(
  //           UUID.randomUUID(),
  //           fakeConversationId,
  //           fakeUserId,
  //           "test message",
  //           Instant.now()));
  //
  //   Mockito.when(mockMessageStore.getMessagesByUser(fakeUserId))
  //       .thenReturn(fakeMessageList);
  //
  //   profileServlet.doGet(mockRequest, mockResponse);
  //
  //   Mockito.verify(mockRequest).setAttribute("currentProfile", "test_user");
  //   Mockito.verify(mockRequest).setAttribute("messages",fakeMessageList);
  //   Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  // }
  //
  //
  // @Test
  // public void testDoGet_nullUser() throws IOException, ServletException {
  //   Mockito.when(mockRequest.getRequestURI()).thenReturn("/user/test_user");
  //
  //   Mockito.when(mockUserStore.getUser("test_user")).thenReturn(null);
  //
  //   profileServlet.doGet(mockRequest, mockResponse);
  //
  //   Mockito.verify(mockRequest).setAttribute("error","THAT USER DOESN'T EXIST! ENTER A VALID USERNAME");
  //   Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  // }
  //
  // @Test
  // public void testDoGet_noSentMessages()  throws IOException, ServletException {
  //   Mockito.when(mockRequest.getRequestURI()).thenReturn("/user/test_user");
  //
  //   UUID fakeUserId = UUID.randomUUID();
  //   User user =
  //         new User(
  //             fakeUserId,
  //             UUID.randomUUID(),
  //             "test_user",
  //             "$2a$10$.e.4EEfngEXmxAO085XnYOmDntkqod0C384jOR9oagwxMnPNHaGLa",
  //             false,
  //             Instant.now());
  //
  //   Mockito.when(mockUserStore.getUser("test_user")).thenReturn(user);
  //   Mockito.when(mockMessageStore.getMessagesByUser(fakeUserId))
  //       .thenReturn(null);
  //
  //   profileServlet.doGet(mockRequest, mockResponse);
  //
  //   Mockito.verify(mockRequest).setAttribute("currentProfile", "test_user");
  //   Mockito.verify(mockRequest).setAttribute("messages",null);
  //   Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  // }
  //
  // @Test
  // public void testDoPost() throws IOException, ServletException {
  //   Mockito.when(mockSession.getAttribute("user")).thenReturn("test_user");
  //
  //   UUID fakeUserId = UUID.randomUUID();
  //   User user =
  //         new User(
  //             fakeUserId,
  //             UUID.randomUUID(),
  //             "test_user",
  //             "$2a$10$.e.4EEfngEXmxAO085XnYOmDntkqod0C384jOR9oagwxMnPNHaGLa",
  //             false,
  //             Instant.now());
  //   Mockito.when(mockUserStore.getUser("test_user")).thenReturn(user);
  //
  //   profileServlet.doPost(mockRequest, mockResponse);
  //
  //   Mockito.verify(mockResponse).sendRedirect("/user/test_user");
  // }
  //
  // @Test
  // public void testDoPost_UserNotLoggedIn() throws IOException, ServletException {
  //   Mockito.when(mockSession.getAttribute("user")).thenReturn(null);
  //
  //   profileServlet.doPost(mockRequest, mockResponse);
  //
  //   Mockito.verify(mockUserStore, Mockito.never()).updateUser(Mockito.any(User.class));
  //   Mockito.verify(mockResponse).sendRedirect("/login");
  // }
  //
  // @Test
  // public void testDoPost_InvalidUser() throws IOException, ServletException {
  //   Mockito.when(mockSession.getAttribute("user")).thenReturn("test_username");
  //   Mockito.when(mockUserStore.getUser("test_username")).thenReturn(null);
  //
  //   profileServlet.doPost(mockRequest, mockResponse);
  //
  //   Mockito.verify(mockUserStore, Mockito.never()).updateUser(Mockito.any(User.class));
  //   Mockito.verify(mockResponse).sendRedirect("/login");
  // }
}
