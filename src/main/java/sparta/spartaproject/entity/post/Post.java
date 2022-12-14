package sparta.spartaproject.entity.post;

import lombok.*;
import sparta.spartaproject.dto.post.PostDto;
import sparta.spartaproject.entity.common.TimeStamped;
import sparta.spartaproject.entity.user.User;

import javax.persistence.*;

@Builder
@Entity
@Getter
@Table(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String title;

    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    public static Post of(PostDto.PostReq uploadPostReq, User user) {
        return Post.builder()
                .title(uploadPostReq.getTitle())
                .content(uploadPostReq.getContent())
                .user(user)
                .build();
    }

    public void editPost(PostDto.PostReq postReq) {
        this.title = postReq.getTitle();
        this.content = postReq.getContent();
    }

}
