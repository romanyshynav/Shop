package andrii.romanyshyn.shopsoftserve._5_Controller;


import andrii.romanyshyn.shopsoftserve._3_DTO.Request.CategoryRequest;
import andrii.romanyshyn.shopsoftserve._3_DTO.Request.CriteriaRequest.CategoryCriteriaRequest;
import andrii.romanyshyn.shopsoftserve._3_DTO.Request.PaginationRequest;
import andrii.romanyshyn.shopsoftserve._3_DTO.Response.CategoryResponse;
import andrii.romanyshyn.shopsoftserve._3_DTO.Response.PageResponse;
import andrii.romanyshyn.shopsoftserve._4_Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    //    =========МЕТОДИ===============================================================================================
    //    -----------------------------Початок: 1. Рандомне створення довільної к-ті поїздів----------------------------
    @PostMapping("/createRandomCategory")
    public void createRandomCategory(int quantity) {
        categoryService.createRandomCategory(quantity);
    }
    //    -----------------------------Кінець: 1. Рандомне створення довільної к-ті поїздів-----------------------------

    //    -----------------------------Початок: 2. Фільтрація людей ----------------------------------------------------
    @PostMapping("/findByFilter")
    public List<CategoryResponse> findByFilter(@RequestBody CategoryCriteriaRequest categoryCriteriaRequest) {
        return categoryService.findAllByCriteria(categoryCriteriaRequest);
    }
    //    -----------------------------Кінець: 2. Фільтрація людей ----------------------------------------------------

    //    -----------------------------Початок: 3. Посторіночна розбивка та сортування----------------------------------
    @GetMapping("/findPageAndSort")
    public PageResponse<CategoryResponse> findPageAndSort(@Valid PaginationRequest paginationRequest) {
        return categoryService.findPageAndSort(paginationRequest);
    }

    @GetMapping("/findPageAndSortAndFilterByCriteria")
    public PageResponse<CategoryResponse> findPageAndSortAndFilterByCriteria(
            @Valid PaginationRequest paginationRequest, CategoryCriteriaRequest categoryCriteriaRequest) {
        return categoryService.findPageAndSortAndFilterByCriteria(paginationRequest, categoryCriteriaRequest);
    }
    //    -----------------------------Кінець: 3. Посторіночна розбивка та сортування----------------------------------

    //    =========CRUD-операції========================================================================================
    @PostMapping
    public void create(@RequestBody CategoryRequest request) {
        categoryService.create(request);
    }

    @PutMapping
    public void update(Long id, @RequestBody CategoryRequest request) {
        categoryService.update(id, request);
    }

    @DeleteMapping
    public void delete(Long id) {
        categoryService.delete(id);
    }

    @DeleteMapping("/deleteAll")
    public void deleteAll() {
        categoryService.deleteAll();
    }

    @GetMapping
    public List<CategoryResponse> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/findOneById")
    public CategoryResponse findOneByID(Long id) {
        return new CategoryResponse(categoryService.findOneByID(id));
    }
}
