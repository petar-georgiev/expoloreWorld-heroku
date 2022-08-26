package com.example.explore.web;

import com.example.explore.model.binding.NewCommentBindingModel;
import com.example.explore.model.service.CommentServiceModel;
import com.example.explore.model.validation.ApiError;
import com.example.explore.model.view.CommentViewModel;
import com.example.explore.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class CommentRestController {

    private final CommentService commentService;
    private final ModelMapper modelMapper;

    public CommentRestController(CommentService commentService,
                                 ModelMapper modelMapper) {
        this.commentService = commentService;

        this.modelMapper = modelMapper;
    }


    @GetMapping("/api/{routeId}/comments")
    public ResponseEntity<List<CommentViewModel>> getComments(
            @PathVariable Long routeId
    ) {
        return ResponseEntity.ok(commentService.getComments(routeId));
    }

    @PostMapping("/api/{routeId}/comments")
    public ResponseEntity<CommentViewModel> newComment(
            @AuthenticationPrincipal UserDetails principal,
            @PathVariable Long routeId,
            @RequestBody @Valid NewCommentBindingModel newCommentBindingModel
    ) {
CommentServiceModel commentServiceModel =
        modelMapper.map(newCommentBindingModel, CommentServiceModel.class);


commentServiceModel.setRouteId(routeId)
        .setCreator(principal.getUsername());

        CommentViewModel newComment =
        commentService.createComment(commentServiceModel);

      URI locationOfNewComment =
              URI.create(String.format("/api/%s/comments/%s", routeId, newComment.getCommentId()));

      return ResponseEntity
              .created(locationOfNewComment)
              .body(newComment);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> onValidationFailure(
            MethodArgumentNotValidException exc) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        exc.getFieldErrors().forEach(fe -> apiError.addFieldWithError(fe.getField()));
        return ResponseEntity.badRequest().body(apiError);
    }
}
