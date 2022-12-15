package sparta.spartaproject.dto.post;

import lombok.Builder;
import lombok.Getter;
import sparta.spartaproject.entity.post.Post;

import java.time.LocalDateTime;

public class PostDto {
    @Getter
    public static class PostReq {
        private String title;
        private String content;
    }

    @Getter
    @Builder
    public static class PostRes {

        private String author;
        private String title;
        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAy;

        public static PostRes of(Post post) {
            return PostRes.builder()
                    .author(post.getUser().getLoginId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .createdAt(post.getCreatedAt())
                    .modifiedAy(post.getModifiedAt())
                    .build();
        }

    }
}
