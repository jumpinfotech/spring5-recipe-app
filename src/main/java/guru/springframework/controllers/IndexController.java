package guru.springframework.controllers;

import guru.springframework.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by jt on 6/1/17.
 */
@Controller
public class IndexController {

    // injecting a service
    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    // pass in the Model
    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {

        // pass back the Set<Recipe> to thymeleaf, assigning it to the view model's "recipes" property
        model.addAttribute("recipes", recipeService.getRecipes());

        return "index";
    }
}
