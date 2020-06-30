package andrii.romanyshyn.shopsoftserve._5_Controller;


import andrii.romanyshyn.shopsoftserve._3_DTO.Request.CriteriaRequest.ProductCriteriaRequest;
import andrii.romanyshyn.shopsoftserve._3_DTO.Request.PaginationRequest;
import andrii.romanyshyn.shopsoftserve._3_DTO.Request.ProductRequest;
import andrii.romanyshyn.shopsoftserve._3_DTO.Response.PageResponse;
import andrii.romanyshyn.shopsoftserve._3_DTO.Response.ProductResponse;
import andrii.romanyshyn.shopsoftserve._4_Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    //    =========МЕТОДИ===============================================================================================
    //    -----------------------------Початок: 1. Рандомне створення довільної к-ті поїздів----------------------------
    @PostMapping("/createRandomProducts")
    public void createRandomProducts(int quantity) {
        productService.createRandomProducts(quantity);
    }
    //    -----------------------------Кінець: 1. Рандомне створення довільної к-ті поїздів-----------------------------

    //    -----------------------------Початок: 2. Фільтрація людей ----------------------------------------------------
    @PostMapping("/findByFilter")
    public List<ProductResponse> findByFilter(@RequestBody ProductCriteriaRequest productCriteriaRequest) {
        return productService.findAllByCriteria(productCriteriaRequest);
    }
    //    -----------------------------Кінець: 2. Фільтрація людей ----------------------------------------------------

    //    -----------------------------Початок: 3. Посторіночна розбивка та сортування----------------------------------
    @GetMapping("/findPageAndSort")
    public PageResponse<ProductResponse> findPageAndSort(@Valid PaginationRequest paginationRequest) {
        return productService.findPageAndSort(paginationRequest);
    }

    @GetMapping("/findPageAndSortAndFilterByCriteria")
    public PageResponse<ProductResponse> findPageAndSortAndFilterByCriteria(
            @Valid PaginationRequest paginationRequest, ProductCriteriaRequest productCriteriaRequest) {
        return productService.findPageAndSortAndFilterByCriteria(paginationRequest, productCriteriaRequest);
    }
    //    -----------------------------Кінець: 3. Посторіночна розбивка та сортування----------------------------------

    //    =========CRUD-операції========================================================================================
    @PostMapping
    public void create(@RequestBody ProductRequest request) throws IOException {
        productService.create(request);
    }

    @PutMapping
    public void update(Long id, @RequestBody ProductRequest request) throws IOException {
        productService.update(id, request);
    }

    @DeleteMapping
    public void delete(Long id) {
        productService.delete(id);
    }

    @DeleteMapping("/deleteAll")
    public void deleteAll() {
        productService.deleteAll();
    }

    @GetMapping
    public List<ProductResponse> findAll() {
        return productService.findAll();
    }

    @GetMapping("/findOneById")
    public ProductResponse findOneByID(Long id) {
        return new ProductResponse(productService.findOneByID(id));
    }
}
