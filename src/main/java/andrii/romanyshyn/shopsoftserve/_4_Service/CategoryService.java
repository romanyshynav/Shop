package andrii.romanyshyn.shopsoftserve._4_Service;


import andrii.romanyshyn.shopsoftserve.Specification.CategorySpecification;
import andrii.romanyshyn.shopsoftserve._1_Entity.Category;
import andrii.romanyshyn.shopsoftserve._2_Repository.CategoryRepository;
import andrii.romanyshyn.shopsoftserve._3_DTO.Request.CategoryRequest;
import andrii.romanyshyn.shopsoftserve._3_DTO.Request.CriteriaRequest.CategoryCriteriaRequest;
import andrii.romanyshyn.shopsoftserve._3_DTO.Request.PaginationRequest;
import andrii.romanyshyn.shopsoftserve._3_DTO.Response.CategoryResponse;
import andrii.romanyshyn.shopsoftserve._3_DTO.Response.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Math.random;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductService productService;

    //    =========МЕТОДИ===============================================================================================
    //    -----------------------------Початок: 1. Рандомне створення довільної к-ті поїздів----------------------------
    public void createRandomCategory(int countCategory) {
        for (int i = 0; i < countCategory; i++) {
            Category category = new Category(categoryNameCompareDB());
            if (category.getName() == "exit") {
                break;
            }
            categoryRepository.save(category);
        }
    }

    public String categoryNameCompareDB() {
        String categoryNewName = "exit";
        String categoryRandomName = randomName();

        List<Category> categoryListFromDB = findAllCategories();
        List<String> categoryBaseNameList = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : productService.categoriesWithProducts().entrySet()) {
            categoryBaseNameList.add(entry.getKey());
        }
        List<String> categoryNameListFromDB = new ArrayList<>();

        if (!categoryListFromDB.isEmpty()) {
            categoryNameListFromDB = categoryListFromDB.stream().map(category -> category.getName())
                    .collect(Collectors.toList());
        } else {
            categoryNewName = categoryRandomName;
        }

        if (!categoryNameListFromDB.isEmpty()) {
            if (!categoryNameListFromDB.contains(categoryRandomName)) {
                categoryNewName = categoryRandomName;
            } else if (!categoryNameListFromDB.containsAll(categoryBaseNameList)) {
                categoryNewName = categoryNameCompareDB();
            }
        }

        return categoryNewName;
    }

    public String randomName() {
        List<String> names = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : productService.categoriesWithProducts().entrySet()) {
            names.add(entry.getKey());
        }
        String randomName = names.get((int) (random() * (names.size())));
        return randomName;
    }
    //    -----------------------------Кінець: 1. Рандомне створення довільної к-ті поїздів-----------------------------

    //    -----------------------------Початок: 2. Фільтрація людей ----------------------------------------------------
    public List<CategoryResponse> findAllByCriteria(CategoryCriteriaRequest categoryCriteriaRequest) {
        return categoryRepository.findAll(new CategorySpecification(categoryCriteriaRequest)).stream()
                .map(CategoryResponse::new).collect(Collectors.toList());
    }
    //    -----------------------------Кінець: 2. Фільтрація людей ----------------------------------------------------

    //    -----------------------------Початок: 3. Посторіночна розбивка та сортування----------------------------------
    public PageResponse<CategoryResponse> findPageAndSort(PaginationRequest paginationRequest) {
        Page<Category> page = categoryRepository.findAll(paginationRequest.toPageble());
        return new PageResponse<>(page.getTotalPages(), page.getTotalElements(),
                page.get().map(CategoryResponse::new).collect(Collectors.toList()));
    }

    public PageResponse<CategoryResponse> findPageAndSortAndFilterByCriteria(
            PaginationRequest paginationRequest, CategoryCriteriaRequest categoryCriteriaRequest) {
        Page<Category> page = categoryRepository.findAll(new CategorySpecification(categoryCriteriaRequest),
                paginationRequest.toPageble());
        return new PageResponse<>(page.getTotalPages(), page.getTotalElements(),
                page.get().map(CategoryResponse::new).collect(Collectors.toList()));
    }
    //    -----------------------------Кінець: 3. Посторіночна розбивка та сортування----------------------------------


    //    =========CRUD-операції========================================================================================
    public void create(CategoryRequest request) {
        Category person = Category.builder().name(request.getName()).build();
        categoryRepository.save(person);
    }

    public void update(Long id, CategoryRequest request) {
        Category category = findOneByID(id);
        category.setName(request.getName());
        categoryRepository.save(category);
    }

    public void delete(Long id) {
        categoryRepository.delete(findOneByID(id));
    }

    public void deleteAll() {
        categoryRepository.deleteAll();
    }

    public Category findOneByID(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(
                "Category with ID - " + id + " not exist.\n"));
    }

    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll().stream()
                .map(category -> new CategoryResponse(category))
                .collect(Collectors.toList());
    }

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }
}
