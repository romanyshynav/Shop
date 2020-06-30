package andrii.romanyshyn.shopsoftserve._4_Service;


import andrii.romanyshyn.shopsoftserve.Specification.ProductSpecification;
import andrii.romanyshyn.shopsoftserve._1_Entity.Category;
import andrii.romanyshyn.shopsoftserve._1_Entity.Product;
import andrii.romanyshyn.shopsoftserve._2_Repository.ProductRepository;
import andrii.romanyshyn.shopsoftserve._3_DTO.Request.CriteriaRequest.ProductCriteriaRequest;
import andrii.romanyshyn.shopsoftserve._3_DTO.Request.PaginationRequest;
import andrii.romanyshyn.shopsoftserve._3_DTO.Request.ProductRequest;
import andrii.romanyshyn.shopsoftserve._3_DTO.Response.PageResponse;
import andrii.romanyshyn.shopsoftserve._3_DTO.Response.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.random;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private FileService fileService;

    //    =========МЕТОДИ===============================================================================================
    public Set<Product> convertIdListIntoProductSetList(List<Long> listId) {
        Set<Product> productList = listId.stream().map(id -> findOneByID(id)).collect(Collectors.toSet());
        return productList;
    }

    public List<Product> convertIdListIntoProductList(List<Long> listId) {
        List<Product> productList = listId.stream().map(id -> findOneByID(id)).collect(Collectors.toList());
        return productList;
    }

    //    -----------------------------Початок: 1. Рандомне створення довільної к-ті поїздів----------------------------
    public void createRandomProducts(int countProduct) {
        for (int i = 0; i < countProduct; i++) {
            Map<Long, String> productData = randomProductData();
            Long categoryId = null;
            String productName = null;
            for (Map.Entry<Long, String> entry : productData.entrySet()) {
                categoryId = entry.getKey();
                productName = entry.getValue();
            }
            Product product = new Product(productName, randomPrice(), "/Standart/No Product Foto.png",
                    categoryService.findOneByID(categoryId));
            productRepository.save(product);
        }
    }

    public Map<Long, String> randomProductData() {
        Map<String, List<String>> categoriesWithProducts = categoriesWithProducts();
        Long categoryId = randomCategoryId();
        String categoryName = categoryService.findOneByID(categoryId).getName();

        Map<Long, String> productData = new HashMap<>();
        String productNameFromCategory;

        List<String> productNameList = categoriesWithProducts.get(categoryName);

        if (productNameList != null) {
            productNameFromCategory = productNameList.get((int) (random() * (productNameList.size())));
        } else {
            return productData = randomProductData();
        }

        productData.put(categoryId, productNameFromCategory);
        return productData;
    }

    public Map<String, List<String>> categoriesWithProducts() {
        Map<String, List<String>> categoriesWithProducts = new HashMap<>();
        String nuts = "Горіхи";
        List<String> nutsList = new ArrayList<>();
        Collections.addAll(nutsList, "Фісташки", "Фундук", "Грецький горіх", "Арахіс", "Кешью");

        String milk = "Молочне";
        List<String> milkList = new ArrayList<>();
        Collections.addAll(milkList, "Молоко", "Сметана", "Кефір", "Масло", "Йогурт");

        String meet = "Мясне";
        List<String> meetList = new ArrayList<>();
        Collections.addAll(meetList, "Ковбаса", "Шинка", "Сало", "Печінка", "Філейка");

        String bean = "Бобові";
        List<String> beanList = new ArrayList<>();
        Collections.addAll(beanList, "Фасоля", "Маш", "Нут", "Сочевиця зелена", "Сочевиця червона");

        String telephone = "Телефони";
        List<String> telephoneList = new ArrayList<>();
        Collections.addAll(telephoneList, "Айфон", "Слайдер", "Стаціонарний", "Китайський", "Жабка");

        String stationery = "Канцелярія";
        List<String> stationeryList = new ArrayList<>();
        Collections.addAll(stationeryList, "Ручка", "Олівець", "Зошит", "Гумка", "Папір", "Чорнило",
                "Скрепкі", "Коректори");

        categoriesWithProducts.put(nuts, nutsList);
        categoriesWithProducts.put(milk, milkList);
        categoriesWithProducts.put(meet, meetList);
        categoriesWithProducts.put(bean, beanList);
        categoriesWithProducts.put(telephone, telephoneList);
        categoriesWithProducts.put(stationery, stationeryList);
        return categoriesWithProducts;
    }

    public int randomPrice() {
        int min = 10, max = 500;
        return (int) (min + Math.random() * (max - min + 1));
    }

    public Long randomCategoryId() {
        List<Category> categoryList = categoryService.findAllCategories();
        int categoriesQuantity = categoryList.size() - 1;// к-ть категорій в БД
        List<Long> categoryIdList = categoryList.stream().map(category -> category.getId()).collect(Collectors.toList());

        int min = 0, max = categoriesQuantity;
        int index = (int) (min + Math.random() * (max - min + 1));

        return categoryIdList.get(index);
    }

    public Long randomProductId() {
        List<Product> productList = findAllProducts();
        List<Long> productIdList = productList.stream().map(product -> product.getId()).collect(Collectors.toList());
        int productsQuantity = productList.size() - 1;

        int min = 0, max = productsQuantity;
        int index = (int) (min + Math.random() * (max - min + 1));

        return productIdList.get(index);
    }

    public Product randomProduct() {
        return findOneByID(randomProductId());
    }
    //    -----------------------------Кінець: 1. Рандомне створення довільної к-ті поїздів-----------------------------

    //    -----------------------------Початок: 2. Фільтрація людей ----------------------------------------------------
    public List<ProductResponse> findAllByCriteria(ProductCriteriaRequest productCriteriaRequest) {
        return productRepository.findAll(new ProductSpecification(productCriteriaRequest)).stream()
                .map(ProductResponse::new).collect(Collectors.toList());
    }
    //    -----------------------------Кінець: 2. Фільтрація людей ----------------------------------------------------

    //    -----------------------------Початок: 3. Посторіночна розбивка та сортування---------------------------------
    public PageResponse<ProductResponse> findPageAndSort(PaginationRequest paginationRequest) {
        Page<Product> page = productRepository.findAll(paginationRequest.toPageble());
        return new PageResponse<>(page.getTotalPages(), page.getTotalElements(),
                page.get().map(ProductResponse::new).collect(Collectors.toList()));
    }

    public PageResponse<ProductResponse> findPageAndSortAndFilterByCriteria(
            PaginationRequest paginationRequest, ProductCriteriaRequest productCriteriaRequest) {
        Page<Product> page = productRepository.findAll(new ProductSpecification(productCriteriaRequest),
                paginationRequest.toPageble());
        return new PageResponse<>(page.getTotalPages(), page.getTotalElements(),
                page.get().map(ProductResponse::new).collect(Collectors.toList()));
    }
    //    -----------------------------Кінець: 3. Посторіночна розбивка та сортування----------------------------------

    //    =========CRUD-операції========================================================================================
    public void create(ProductRequest request) throws IOException {
        String fileName = null;
        if (request.getImageB64Format() != null && !request.getImageB64Format().isEmpty()) {
            fileName = fileService.saveFile(request.getImageB64Format());
        } else {
            fileName = "/Standart/No Product Foto.png";
        }
        Product product = Product.builder().name(request.getName()).price(request.getPrice()).imageFileName(fileName)
                .category(categoryService.findOneByID(request.getCategoryId())).build();
        productRepository.save(product);
    }

    public void update(Long id, ProductRequest request) throws IOException {
        String fileName = null;
        if (request.getImageB64Format() != null && !request.getImageB64Format().isEmpty()) {
            fileName = fileService.saveFile(request.getImageB64Format());
        } else {
            fileName = "/Standart/No Product Foto.png";
        }
        Product product = findOneByID(id);
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setImageFileName(fileName);
        product.setCategory(categoryService.findOneByID(request.getCategoryId()));
        productRepository.save(product);
    }

    public void delete(Long id) {
        productRepository.delete(findOneByID(id));
    }

    public void deleteAll() {
        productRepository.deleteAll();
    }

    public Product findOneByID(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(
                "Product with ID - " + id + " not exist.\n"));
    }

    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream()
                .map(product -> new ProductResponse(product))
                .collect(Collectors.toList());
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }
}
