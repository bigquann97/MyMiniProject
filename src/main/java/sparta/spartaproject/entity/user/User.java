package sparta.spartaproject.entity.user;

import lombok.*;
import sparta.spartaproject.dto.user.UserDto;
import sparta.spartaproject.entity.common.TimeStamped;
import sparta.spartaproject.entity.post.Post;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String loginPw;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    private Integer age;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    public static User of(UserDto.SignUpReq signUpReq) {
        return User.builder()
                .loginId(signUpReq.getLoginId())
                .loginPw(signUpReq.getLoginPw())
                .age(signUpReq.getAge())
                .email(signUpReq.getEmail())
                .name(signUpReq.getName())
                .role(UserRole.USER)
                .build();
    }

    public boolean hasThisPost(Post findPost) {
        for (Post post : posts) {
            if (post.equals(findPost)) {
                return true;
            }
        }
        return false;
    }

}
