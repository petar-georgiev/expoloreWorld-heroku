package com.example.explore.service;

import com.example.explore.model.service.RouteServiceModel;
import com.example.explore.model.view.RouteDetailsViewModel;
import com.example.explore.model.view.RouteViewModel;

import java.util.List;

public interface RouteService {
    List<RouteViewModel> findAllRoutesView();

    void addNewRoute(RouteServiceModel routeServiceModel);

    void deleteItem(Long id);

    RouteDetailsViewModel findRouteById(Long id);

    String getGpxCoordinates(Long routeId);
}
