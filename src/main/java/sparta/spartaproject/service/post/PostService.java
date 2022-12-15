package sparta.spartaproject.service.post;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.spartaproject.dto.post.PostDto;
import sparta.spartaproject.entity.post.Post;
import sparta.spartaproject.entity.user.User;
import sparta.spartaproject.exception.InvalidTokenException;
import sparta.spartaproject.exception.NotExistPostException;
import sparta.spartaproject.exception.NotExistUserException;
import sparta.spartaproject.exception.UnauthorizedException;
import sparta.spartaproject.jwt.JwtUtil;
import sparta.spartaproject.repository.post.PostRepository;
import sparta.spartaproject.repository.user.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final JwtUtil jwtUtil;

    @Transactional(readOnly = true)
    public PostDto.PostRes getOnePost(Long id) {
        Post findPost = postRepository.findById(id).orElseThrow(NotExistPostException::new);
        return PostDto.PostRes.of(findPost);
    }

    @Transactional(readOnly = true)
    public List<PostDto.PostRes> getAllPosts() {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        return posts.stream().map(PostDto.PostRes::of).toList();
    }

    @Transactional
    public void uploadPost(PostDto.PostReq postReq, HttpServletRequest request) {
        String loginId = validateTokenAndGetLoginId(request);
        User user = userRepository.findUserByLoginId(loginId).orElseThrow(NotExistUserException::new);
        Post post = Post.of(postReq, user);
        postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long id, HttpServletRequest request) {
        String loginId = validateTokenAndGetLoginId(request);
        User user = userRepository.findUserByLoginId(loginId).orElseThrow(NotExistUserException::new);
        Post post = postRepository.findById(id).orElseThrow(NotExistPostException::new);
        validateAccess(user, post);
        postRepository.delete(post);
    }

    @Transactional
    public PostDto.PostRes modifyPost(Long id, PostDto.PostReq postReq, HttpServletRequest request) {
        String loginId = validateTokenAndGetLoginId(request);
        User user = userRepository.findUserByLoginId(loginId).orElseThrow(NotExistUserException::new);
        Post post = postRepository.findById(id).orElseThrow(NotExistPostException::new);
        validateAccess(user, post);
        post.editPost(postReq);
        postRepository.saveAndFlush(post);
        return PostDto.PostRes.of(post);
    }

    private String validateTokenAndGetLoginId(HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if(token != null) {
            if (jwtUtil.validateToken(token))
                claims = jwtUtil.getUserInfoFromToken(token);
            else
                throw new InvalidTokenException();
        } else {
            throw new InvalidTokenException();
        }
        return claims.getSubject();
    }

    private void validateAccess(User user, Post post) {
        if(!user.hasThisPost(post))
            throw new UnauthorizedException();
    }

}
