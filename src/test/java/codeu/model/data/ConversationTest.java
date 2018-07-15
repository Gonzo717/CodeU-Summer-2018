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

package codeu.model.data;

import java.time.Instant;
import java.util.HashSet;
import java.time.temporal.ChronoUnit;
import codeu.model.data.Conversation.Type;
import codeu.model.data.Conversation.Visibility;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;

public class ConversationTest {

  @Test
  public void testCreate() {
    UUID id = UUID.randomUUID();
    UUID owner = UUID.randomUUID();
		String title = "Test_Title";
		Instant creation = Instant.now();
		HashSet members = new HashSet<>();
		Type type = Type.TEXT;
		Visibility visibility = Visibility.PUBLIC;
		String avatarImageURL = "fakeURL";
		ChronoUnit validTime = ChronoUnit.DECADES;
		String description = "fake :D";


    Conversation conversation = new Conversation(id, owner, title, creation,
																									members, type, visibility,
																									avatarImageURL, validTime, description);

    Assert.assertEquals(id, conversation.getId());
    Assert.assertEquals(owner, conversation.getOwnerId());
    Assert.assertEquals(title, conversation.getTitle());
    Assert.assertEquals(creation, conversation.getCreationTime());
		Assert.assertEquals(members, conversation.getMembers());
		Assert.assertEquals(type, Type.valueOf(conversation.getConversationType()));
		Assert.assertEquals(visibility, Visibility.valueOf(conversation.getConversationVisibility()));
		Assert.assertEquals(avatarImageURL, conversation.getAvatarImageURL());
		Assert.assertEquals(validTime, conversation.getValidTime());
		Assert.assertEquals(description, conversation.getDescription());

  }
}
