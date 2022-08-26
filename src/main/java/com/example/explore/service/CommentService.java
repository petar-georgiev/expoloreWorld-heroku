package com.example.explore.service;

import com.example.explore.model.service.CommentServiceModel;
import com.example.explore.model.view.CommentViewModel;

import java.util.List;

public interface CommentService {

    CommentViewModel createComment(CommentServiceModel commentServiceModel);

    List<CommentViewModel> getComments(Long routeId);
}
