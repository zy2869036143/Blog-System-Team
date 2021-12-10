package com.markerhub.controller;
import com.markerhub.common.dto.CommentDeleteRequest;
import com.markerhub.common.lang.Result;
import com.markerhub.entity.Comment;
import com.markerhub.service.CommentService;
import com.markerhub.service.UserService;
import com.markerhub.service.UserinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.*;
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;
    @Autowired
    UserinfoService userinfoService;

    @PostMapping("/get")
    public Result getComment(@RequestParam("id") Long id){
        System.out.println("hello !");
        System.out.println(LocalDateTime.now());
        ArrayList<ArrayList<Comment>> totalComments = new ArrayList<ArrayList<Comment>>();
        List<Comment> commentList = commentService.getByCommentById(id,0L);
        commentList.sort(new Comparator<Comment>() {
            @Override
            public int compare(Comment o1, Comment o2) {
                return (int)(o2.getId() - o1.getId());
            }
        });
        for (int i=0; i < commentList.size(); ++i){
            ArrayList<Comment> onePieComment = new ArrayList<Comment>();
            totalComments.add(onePieComment);
            Comment comment1 = commentList.get(i);
            String username = userinfoService.getinfo(comment1.getUserId()).getUsername();
            comment1.setUsername(username);
            onePieComment.add(commentList.get(i));
            addd(id, commentList.get(i), onePieComment);
           // List<Comment> followComments = commentService.getByCommentById(id, commentList.get(i).getId());
        }
        return Result.succ(totalComments);
    }

    private void addd(Long id, Comment comment, ArrayList<Comment> total){
        List<Comment> commentList = commentService.getByCommentById(id, comment.getId());
        if (commentList.size()==0) return;
        for (int i=0; i < commentList.size(); ++i){
            Comment comment1 = commentList.get(i);
            String username = userinfoService.getinfo(comment1.getUserId()).getUsername();
            comment1.setUsername(username);
            total.add(commentList.get(i));
            addd(id, commentList.get(i), total);
        }
    }

    @PostMapping("/save")
    public Result saveComment(@Validated @RequestBody Comment comment){

        comment.setTime(LocalDateTime.now());
        commentService.save(comment);
        return Result.succ("success");

    }
    @PostMapping("/delete")
    public Result deleteComment(@Validated @RequestBody CommentDeleteRequest commentD){
        Long commentId = commentD.getCommentId();
        try {
            Comment comment = commentService.getById(commentId);
            if (comment.getUserId()==commentD.getUserId()){
                commentService.removeById(commentId);
                return  Result.succ("success");
            }
            return Result.fail("只能删除自己的评论。");
        }catch (NullPointerException e){
            return Result.fail("评论不存在");
        }
    }
}
