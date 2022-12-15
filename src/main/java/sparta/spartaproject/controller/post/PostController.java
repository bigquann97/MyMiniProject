package sparta.spartaproject.controller.post;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sparta.spartaproject.dto.post.PostDto;
import sparta.spartaproject.service.post.PostService;
import sparta.spartaproject.result.ResultService;
import sparta.spartaproject.result.Result;
import sparta.spartaproject.result.Status;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static sparta.spartaproject.dto.post.PostDto.*;

@RequestMapping("/api/post")
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final ResultService resultService;

    @GetMapping("/{id}")
    public ResponseEntity<Result> getOnePost(@PathVariable Long id) {
        PostRes post = postService.getOnePost(id);
        Result result = resultService.getSuccessDataResult(Status.S_POST_VIEW, post);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping
    public ResponseEntity<Result> getAllPosts() {
        List<PostRes> posts = postService.getAllPosts();
        Result result = resultService.getSuccessDataResult(Status.S_POST_VIEW, posts);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping
    public ResponseEntity<Result> uploadPost(@RequestBody PostReq postReq, HttpServletRequest request) {
        postService.uploadPost(postReq, request);
        Result result = resultService.getSuccessResult(Status.S_POST_UPLOAD);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Result> modifyPost(@PathVariable Long id, @RequestBody PostReq postReq, HttpServletRequest request) {
        PostRes modifiedPost = postService.modifyPost(id, postReq, request);
        Result result = resultService.getSuccessDataResult(Status.S_POST_MODIFY, modifiedPost);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result> deletePost(@PathVariable Long id, HttpServletRequest request) {
        postService.deletePost(id, request);
        Result result = resultService.getSuccessResult(Status.S_POST_DELETE);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
