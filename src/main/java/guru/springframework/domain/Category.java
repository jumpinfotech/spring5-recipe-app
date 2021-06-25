package guru.springframework.domain;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by jt on 6/13/17.
 */
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    // ManyToMany - between Recipe + Category:- bidirectional, this will be mapped on the join table
    // from the other side of the relationship.
    // mappedBy - Recipe.java has private Set<Category> categories = new HashSet<>();
    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(Set<Recipe> recipes) {
        this.recipes = recipes;
    }
}
