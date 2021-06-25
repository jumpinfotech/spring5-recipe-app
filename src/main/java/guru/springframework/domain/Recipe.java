package guru.springframework.domain; // common convention to put your entities under domain or model

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jt on 6/13/17.
 */
@Entity // class is now the Entity, don't forget to add field id
public class Recipe {

    // id is a Long, if you use an integer you limit the number of ids + can run out.
    @Id //  @Id is a standard javax persistence
    // GenerationType.IDENTITY uses the underlying persistence framework to generate an id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // GenerationType.IDENTITY - relational databases will auto generate a sequence.
    // Several databases have an auto-generated primary key property we can use.
    // Need to check Oracle support though, JT had problems.
    
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;

    // earlier we had as a String - hibernate would have created a default field of 255 characters.
    @Lob
    private String directions;
    
    // CascadeType.ALL - Recipe is our primary object we won't be working with notes + ingredients independently
    // of Recipe - there isn't a need for a Spring Data Repository for notes + ingredients.
    // Category + UnitOfMeasure will be maintained independently of Recipe,
    // we need CategoryRepository + UnitOfMeasureRepository.
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    // Recipe owns this relationship
    // CascadeType.ALL - persists all operations, we want to delete ingredients assigned to recipe.
    // mappedBy - the target property on the mapped child Ingredient entity is called recipe, 
    // this configures the Recipe to Ingredient relationship.
    // We have a bidirectional relationship - we can navigate the object graph from either direction
    // From experience I like using a Set to get unique values, some people use List.
    // ingredients are initialised to a new HashSet.
    private Set<Ingredient> ingredients = new HashSet<>();
    
    // Will get created as a binary Blob field
    // blob = binary large object
    @Lob  
    private Byte[] image;
    
    // @Enumerated is from javax persistence, we have 2 choices 
    // Assume: 
    // initial ENUM=LFC, EFC
    // updated ENUM=LFC, MFC, EFC
    // 1. EnumType.STRING - it gets persisted as LFC + EFC, use if you can because the database mapping will still work with the updated ENUM 
    // 2. EnumType.ORDINAL - the default, initial ENUM is persisted as 1 or 2 - harder to read db, our database mapping will be corrupted if updated ENUM was later applied. 
    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    // @OneToOne creates the 1 to 1 relationship recipe has with notes
    // cascade is on this side of the relationship, so recipe is the owner
    // CascadeType.ALL if we delete the recipe we will delete notes.
    @OneToOne(cascade = CascadeType.ALL) 
    private Notes notes;
    
    // http://localhost:8080/h2-console > JDBC URL: jdbc:h2:mem:testdb
    // User Name: sa Password: nothing/empty
    // recipe_category = the join table name, 
    // for id's Hibernate's default naming convention is to use tablename then id e.g. recipe_id + category_id
    // inverseJoinColumns is for the opposite way we have category_id.
    // In db, table name=RECIPE_CATEGORY, with 2 columns RECIPE_ID + CATEGORY_ID
    // @ManyToMany is one of the most difficult things in JPA mapping,
    // you don't see that many @ManyToMany relationships, maybe every 2 months - JT forgets how to set it up + googles it.
    // Look at the other side of the relationship in class Category + field recipes.
    // categories are initialised to a new HashSet, helps avoid a null pointer error if you forget to initialise it elsewhere.
    @ManyToMany
    @JoinTable(name = "recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

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

    public Integer getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(Integer prepTime) {
        this.prepTime = prepTime;
    }

    public Integer getCookTime() {
        return cookTime;
    }

    public void setCookTime(Integer cookTime) {
        this.cookTime = cookTime;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public Byte[] getImage() {
        return image;
    }

    public void setImage(Byte[] image) {
        this.image = image;
    }

    public Notes getNotes() {
        return notes;
    }

    public void setNotes(Notes notes) {
        this.notes = notes;
        // we set the recipe which builds the bidirectional association, 
        // centralised - we don't need to do it outside of the class - encapsulates the logic
        // common design pattern when working with JPA entities, 
        // if you don't add constraints you can easily forget to set these when managing 
        // both sides of the bidirectional relationship
        notes.setRecipe(this);
    }

    // removeIngredient would be nice to have also
    public Recipe addIngredient(Ingredient ingredient){
        // build the bidirectional association
        ingredient.setRecipe(this);
        this.ingredients.add(ingredient);
        return this;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
