package guru.springframework.domain;

import javax.persistence.*;

/**
 * Created by jt on 6/13/17.
 */
@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // notes has a 1 to 1 relationship with recipe
    @OneToOne
    // We don't specify a cascade as recipe owns this.
    // We have no cascade operation therefore deleting the notes object won't delete the recipe object.
    private Recipe recipe;

    @Lob
    // clob = character large object
    // String for hibernate + JPA is 255 characters, we want more on the notes field.
    // @Lob - against a String, JPA expects to store it in a Clob field in the db.
    private String recipeNotes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public String getRecipeNotes() {
        return recipeNotes;
    }

    public void setRecipeNotes(String recipeNotes) {
        this.recipeNotes = recipeNotes;
    }
}
