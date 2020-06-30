package andrii.romanyshyn.shopsoftserve._5_Controller;


import andrii.romanyshyn.shopsoftserve._3_DTO.Request.CriteriaRequest.PersonCriteriaRequest;
import andrii.romanyshyn.shopsoftserve._3_DTO.Request.PaginationRequest;
import andrii.romanyshyn.shopsoftserve._3_DTO.Request.PersonRequest;
import andrii.romanyshyn.shopsoftserve._3_DTO.Response.PageResponse;
import andrii.romanyshyn.shopsoftserve._3_DTO.Response.PersonResponse;
import andrii.romanyshyn.shopsoftserve._4_Service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private PersonService personService;

    //    =========МЕТОДИ===============================================================================================
    //    -----------------------------Початок: 1. Рандомне створення довільної к-ті поїздів----------------------------
    @PostMapping("/createRandomPersons")
    public void createRandomPersons(int quantity) {
        personService.createRandomPersons(quantity);
    }
    //    -----------------------------Кінець: 1. Рандомне створення довільної к-ті поїздів-----------------------------

    //    -----------------------------Початок: 3. Посторіночна розбивка та сортування----------------------------------
    @GetMapping("/findPageAndSort")
    public PageResponse<PersonResponse> findPageAndSort(@Valid PaginationRequest paginationRequest) {
        return personService.findPageAndSort(paginationRequest);
    }

    @GetMapping("/findPageAndSortAndFilterByCriteria")
    public PageResponse<PersonResponse> findPageAndSortAndFilterByCriteria(
            @Valid PaginationRequest paginationRequest, PersonCriteriaRequest personCriteriaRequest) {
        return personService.findPageAndSortAndFilterByCriteria(paginationRequest, personCriteriaRequest);
    }
    //    -----------------------------Кінець: 3. Посторіночна розбивка та сортування----------------------------------

    //    -----------------------------Початок: 4. Операції з "Улюбленим" ---------------------------------------------
    @PostMapping("/addProductToFavoriteList")
    public void addProductToFavoriteList(Long productId, Long personId) {
        personService.addProductToFavoriteList(productId, personId);
    }
    @PostMapping("/removeProductFromFavoriteList")
    public void removeProductFromFavoriteList(Long productId, Long personId) {
        personService.removeProductFromFavoriteList(productId, personId);
    }
    //    -----------------------------Кінець: 4. Операції з "Улюбленим" ---------------------------------------------

    //    =========CRUD-операції========================================================================================
    @PostMapping
    public void create(@RequestBody PersonRequest request) throws IOException {
        personService.create(request);
    }

    @PutMapping
    public void update(Long id, @RequestBody PersonRequest request) throws IOException {
        personService.update(id, request);
    }

    @DeleteMapping
    public void delete(Long id) {
        personService.delete(id);
    }

    @DeleteMapping("/deleteAll")
    public void deleteAll() {
        personService.deleteAll();
    }

    @GetMapping
    public List<PersonResponse> findAll() {
        return personService.findAll();
    }

    @GetMapping("/findOneById")
    public PersonResponse findOneByID(Long id) {
        return new PersonResponse(personService.findOneByID(id));
    }
}
