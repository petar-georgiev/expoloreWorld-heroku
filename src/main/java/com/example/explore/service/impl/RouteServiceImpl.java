package com.example.explore.service.impl;

import com.example.explore.model.entity.Route;
import com.example.explore.model.service.RouteServiceModel;
import com.example.explore.model.service.exceptions.ObjectNotFoundException;
import com.example.explore.model.view.RouteDetailsViewModel;
import com.example.explore.model.view.RouteViewModel;
import com.example.explore.repository.RouteRepository;
import com.example.explore.service.CategoryService;
import com.example.explore.service.RouteService;
import com.example.explore.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RouteServiceImpl implements RouteService {
    private final RouteRepository routeRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;



    public RouteServiceImpl(RouteRepository routeRepository, UserService userService, CategoryService categoryService, ModelMapper modelMapper) {
        this.routeRepository = routeRepository;
        this.userService = userService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }
    @Transactional
    @Override
    public List<RouteViewModel> findAllRoutesView() {
        return routeRepository.findAll()
                .stream()
                .map(route -> {
                    RouteViewModel routeViewModel = modelMapper.map(route, RouteViewModel.class);
                    if (route.getPictures().isEmpty()) {
                        routeViewModel.setPictureUrl("/images/troian.jpg");
                    }else {
                        routeViewModel.setPictureUrl(route.getPictures().stream().findFirst().get().getUrl());
                    }
                    return routeViewModel;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void addNewRoute(RouteServiceModel routeServiceModel) {
        Route route = modelMapper.map(routeServiceModel, Route.class);
        //todo: add new route
       // route.setAuthor(userService.findCurrentLoginUserEntity());
        route.setCategories(routeServiceModel.getCategories()
                .stream().map(categoryService::findCategoryByName)
                .collect(Collectors.toList()));

        routeRepository.save(route);
    }

    @Transactional
        @Override
        public void deleteItem(Long id) {
            routeRepository.deleteById(id);
        }

        @Transactional
    @Override
    public RouteDetailsViewModel findRouteById(Long id) {
        return routeRepository.findById(id)
                .map(route -> modelMapper.map(route, RouteDetailsViewModel.class))
                .orElse(null);
    }

    @Override
    public String getGpxCoordinates(Long routeId) {

        String gpxCoordinates = routeRepository
                .findById(routeId)
                .get()
                .getGpxCoordinates();

        if(gpxCoordinates.isEmpty()){
            throw new ObjectNotFoundException("GpxCoordinates was not found!");
        }

        return gpxCoordinates;
    }
}
