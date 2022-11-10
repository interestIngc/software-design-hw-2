package search;

import org.junit.Test;
import post.Post;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class VKSearchResponseParserTest {
    private static final String response = "{\n" +
            "\"response\":{\n" +
            "\"count\":2\n" +
            ",\"items\":[\n" +
            "{\n" +
            "\"id\":3737\n" +
            ",\"date\":1668015189\n" +
            ",\"owner_id\":-215466861\n" +
            ",\"from_id\":-215466861\n" +
            ",\"is_favorite\":false\n" +
            ",\"post_type\":\"post\"\n" +
            ",\"text\":\"#анекдоты #шутка #lol\"\n" +
            ",\"marked_as_ads\":0\n" +
            ",\"likes\":{\n" +
            "\"can_like\":1\n" +
            ",\"count\":0\n" +
            ",\"user_likes\":0\n" +
            ",\"can_publish\":1\n" +
            "}\n" +
            ",\"reposts\":{\n" +
            "\"count\":0\n" +
            ",\"user_reposted\":0\n" +
            "}\n" +
            ",\"donut\":{\n" +
            "\"is_donut\":false\n" +
            "}\n" +
            ",\"short_text_rate\":0.8\n" +
            "},\n" +
            "{\n" +
            "\"id\":5297\n" +
            ",\"date\":1668014743\n" +
            ",\"owner_id\":-84680673\n" +
            ",\"from_id\":-84680673\n" +
            ",\"is_favorite\":false\n" +
            ",\"post_type\":\"post\"\n" +
            ",\"text\":\"#lol что за жиза.\"\n" +
            ",\"marked_as_ads\":0\n" +
            ",\"likes\":{\n" +
            "\"can_like\":1\n" +
            ",\"count\":0\n" +
            ",\"user_likes\":0\n" +
            ",\"can_publish\":1\n" +
            "}\n" +
            ",\"reposts\":{\n" +
            "\"count\":0\n" +
            ",\"user_reposted\":0\n" +
            "}\n" +
            ",\"views\":{\n" +
            "\"count\":4\n" +
            "}\n" +
            ",\"donut\":{\n" +
            "\"is_donut\":false\n" +
            "}\n" +
            ",\"short_text_rate\":0.8\n" +
            "}]}}\n";

    @Test
    public void parsesResponse() {
        List<Post> posts = VKSearchResponseParser.parse(response);

        assertThat(
                posts.stream().map(Post::getDate)
        ).containsExactly(1668015189L, 1668014743L);
    }
}
