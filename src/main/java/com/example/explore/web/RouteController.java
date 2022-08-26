package com.example.explore.web;


import com.example.explore.model.binding.RouteAddBindingModel;
import com.example.explore.model.service.RouteServiceModel;
import com.example.explore.service.RouteService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/routes")
public class RouteController{

    private final RouteService routeService;

    private final ModelMapper modelMapper;

    public RouteController(RouteService routeService, ModelMapper modelMapper) {
        this.routeService = routeService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/all")
        public String allRoutes(Model model){
        model.addAttribute("routes", routeService.findAllRoutesView());
        return "routes";
    }

    @GetMapping("/add")
    public String add(Model model){
        return "add-route";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable Long id, Model model){
        model.addAttribute("route", routeService.findRouteById(id));
        return "route-details";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        routeService.deleteItem(id);
        return "routes";
    }

    @PostMapping("/add")
    public String addConfig(@Valid RouteAddBindingModel routeAddBindingModel,
                            BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("routeAddBindingModel", routeAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.routeAddBindingModel", bindingResult);
            return "redirect:add";
        }
        RouteServiceModel routeServiceModel =
                modelMapper.map(routeAddBindingModel, RouteServiceModel.class);

        routeServiceModel.setGpxCoordinates(new String(routeAddBindingModel
                .getGpxCoordinates().getBytes()));

        routeService.addNewRoute(routeServiceModel);

        return "redirect:all";
    }

    @ModelAttribute
    public RouteAddBindingModel routeAddBindingModel(){
        return new RouteAddBindingModel();
    }
}
