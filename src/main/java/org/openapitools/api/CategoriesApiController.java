
package org.openapitools.api;

import org.openapitools.model.Category;
import org.openapitools.model.CategoryRequest;
import org.openapitools.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import javax.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-05-22T17:38:09.228910+03:00[Europe/Moscow]", comments = "Generator version: 7.13.0")
@Controller
@RequestMapping("${openapi.budgetManagement.base-path:/api}")
public class CategoriesApiController implements CategoriesApi {

    private static final Logger log = LoggerFactory.getLogger(CategoriesApiController.class);

    private final NativeWebRequest request;
    private final CategoryService categoryService;

    @Autowired
    public CategoriesApiController(NativeWebRequest request, CategoryService categoryService) {
        this.request = request;
        this.categoryService = categoryService;
        log.info("CategoriesApiController initialized with CategoryService");
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<Category> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        log.debug("Creating new category: {}", categoryRequest);

        Category created = categoryService.createCategory(categoryRequest);

        log.info("Created category with ID: {}", created.getId());

        // Return with 201 Created status
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") Long id) {
        log.debug("Deleting category with ID: {}", id);

        Optional<Category> categoryOpt = categoryService.findById(id);
        if (!categoryOpt.isPresent()) {
            log.warn("Category not found with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        boolean deleted = categoryService.deleteCategory(id);
        if (!deleted) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("Deleted category with ID: {}", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<List<Category>> getAllCategories() {
        log.debug("Getting all categories");

        List<Category> categories = categoryService.getAllCategories();
        log.info("Returning {} categories", categories.size());

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") Long id) {
        log.debug("Getting category with ID: {}", id);

        Optional<Category> categoryOpt = categoryService.findById(id);

        if (!categoryOpt.isPresent()) {
            log.warn("Category not found with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        log.info("Found category with ID: {}", id);
        return new ResponseEntity<>(categoryOpt.get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Category> updateCategory(
            @PathVariable("id") Long id,
            @Valid @RequestBody CategoryRequest categoryRequest) {
        log.debug("Updating category with ID: {}", id);

        Optional<Category> updatedOpt = categoryService.updateCategory(id, categoryRequest);

        if (!updatedOpt.isPresent()) {
            log.warn("Category not found with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        log.info("Updated category with ID: {}", id);
        return new ResponseEntity<>(updatedOpt.get(), HttpStatus.OK);
    }
}