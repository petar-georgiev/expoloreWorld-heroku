package com.example.explore.service.impl;

import com.example.explore.model.entity.Comment;
import com.example.explore.model.service.CommentServiceModel;
import com.example.explore.model.service.exceptions.ObjectNotFoundException;
import com.example.explore.model.view.CommentViewModel;
import com.example.explore.repository.CommentsRepository;
import com.example.explore.repository.RouteRepository;
import com.example.explore.repository.UserRepository;
import com.example.explore.service.CommentService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final RouteRepository routeRepository;
    private final UserRepository userRepository;

    private final CommentsRepository commentsRepository;

    public CommentServiceImpl(RouteRepository routeRepository, UserRepository userRepository, CommentsRepository commentsRepository) {
        this.routeRepository = routeRepository;
        this.userRepository = userRepository;
        this.commentsRepository = commentsRepository;
    }

    @Override
    public CommentViewModel createComment(CommentServiceModel commentServiceModel) {
        var route = routeRepository
                .findById(commentServiceModel.getRouteId())
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Route with id " + commentServiceModel.getRouteId() + " not found!"));

        var author = userRepository
                .findByEmail(commentServiceModel.getCreator())
                .orElseThrow(() -> new ObjectNotFoundException(
                        "User with email " + commentServiceModel.getCreator() + " not found!"));


        Comment comment = new Comment();
        comment.setApproved(false)
                .setTextContent(commentServiceModel.getMessage())
                .setCreated(LocalDateTime.now())
                .setRoute(route)
                .setAuthor(author);

       Comment savedComment = commentsRepository.save(comment);

        return mapAsComment(savedComment);
    }

    @Transactional
    @Override
    public List<CommentViewModel> getComments(Long routeId) {

        List<CommentViewModel> models = routeRepository
                .findById(routeId)
                .get().getComments()
                .stream()
                .map(this::mapAsComment)
                .collect(Collectors.toList());

        if(models.isEmpty()){
            throw new ObjectNotFoundException("Route with id " + routeId + " was not found!");
        }

        return models;
    }

    private CommentViewModel mapAsComment(Comment comment){
        CommentViewModel commentViewModel = new CommentViewModel();

        commentViewModel
                .setCommentId(comment.getId())
                .setCanApprove(comment.getApproved())
                .setCanDelete(true)
                .setCreated(comment.getCreated())
                .setMessage(comment.getTextContent())
                .setUser(comment.getAuthor().getFullName())
                .setUsername(comment.getAuthor().getUsername());


        return commentViewModel;
    }
}
