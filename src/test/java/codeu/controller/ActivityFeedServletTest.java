package codeu.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.data.Activity;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import codeu.model.store.basic.ActivityStore;

public class ActivityFeedServletTest {
	private ActivityFeedServlet activityFeedServlet;
	private HttpServletRequest mockRequest;
	private HttpServletResponse mockResponse;
	
	private RequestDispatcher mockRequestDispatcher;
	private ConversationStore mockConversationStore;
	private MessageStore mockMessageStore;
	private UserStore mockUserStore;
	private ActivityStore mockActivityStore;
	
	@Before
	public void setup() {
		activityFeedServlet = new ActivityFeedServlet();
		mockRequest = Mockito.mock(HttpServletRequest.class);		
		mockResponse = Mockito.mock(HttpServletResponse.class);
		mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
		Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/activityfeed.jsp"))
				.thenReturn(mockRequestDispatcher);
		
		mockConversationStore = Mockito.mock(ConversationStore.class);
		activityFeedServlet.setConversationStore(mockConversationStore);

		mockMessageStore = Mockito.mock(MessageStore.class);
		activityFeedServlet.setMessageStore(mockMessageStore);

		mockUserStore = Mockito.mock(UserStore.class);
		activityFeedServlet.setUserStore(mockUserStore);
		
		mockActivityStore = Mockito.mock(ActivityStore.class);
		activityFeedServlet.setActivityStore(mockActivityStore);
	}
	
	@Test
	public void testDoGet() throws IOException, ServletException {
		activityFeedServlet.doGet(mockRequest, mockResponse);
		Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
	}
}