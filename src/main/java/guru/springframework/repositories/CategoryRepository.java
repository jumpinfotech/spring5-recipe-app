package guru.springframework.repositories;

import guru.springframework.domain.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created by jt on 6/13/17.
 */
// extends CrudRepository<T, ID> T is the entity + ID is the id type of the entity - usually Long??
// Spring data constructs a repository using reflection + generics.
// Naming convention = use the (domain /model /entity (3 names for 1 thing!!!) e.g. Category) 
// followed by Repository otherwise we might confuse things.
// If we run the application + nothing is using these CRUD repositories, 
// they are still created as Spring beans in the context, inject in these repositories to use them.     
public interface CategoryRepository extends CrudRepository<Category, Long> {


    // JT puts repositories in package repositories.
    // For inspiration look at org.springframework.data.repository.CrudRepository
    // Spring Data since Spring 5 is using Optional (see CrudRepository), before Spring Data was returning null.
    // intelliJ>type in findBy>CTRL+Spacebar for suggestions>e.g. Description>And is suggested for chaining.
    Optional<Category> findByDescription(String description);

// To use the above simply do the following.
// Constructor based dependency injection, no @Autowired required, Spring autowires CategoryRepository in for us:-
//    private final CategoryRepository categoryRepository;
//    public MyClassName(CategoryRepository categoryRepository ) {
//        this.categoryRepository = categoryRepository;
//    }
//    public void myMethod() {
//        Optional<Category> americanCategory = categoryRepository.findByDescription("American");
//        System.out.println("americanCategory id=" + americanCategory.get().getId());
//    }
}
