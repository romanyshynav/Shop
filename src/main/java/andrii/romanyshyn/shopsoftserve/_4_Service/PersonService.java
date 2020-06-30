package andrii.romanyshyn.shopsoftserve._4_Service;


import andrii.romanyshyn.shopsoftserve.Specification.PersonSpecification;
import andrii.romanyshyn.shopsoftserve._1_Entity.Person;
import andrii.romanyshyn.shopsoftserve._1_Entity.Product;
import andrii.romanyshyn.shopsoftserve._2_Repository.PersonRepository;
import andrii.romanyshyn.shopsoftserve._3_DTO.Request.CriteriaRequest.PersonCriteriaRequest;
import andrii.romanyshyn.shopsoftserve._3_DTO.Request.PaginationRequest;
import andrii.romanyshyn.shopsoftserve._3_DTO.Request.PersonRequest;
import andrii.romanyshyn.shopsoftserve._3_DTO.Response.PageResponse;
import andrii.romanyshyn.shopsoftserve._3_DTO.Response.PersonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.random;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private CartService cartService;
    @Autowired
    private FileService fileService;

    //    =========МЕТОДИ===============================================================================================
    public String getFileName(String imageB64Format) throws IOException {
        String fileName = null;
        if (imageB64Format != null && !imageB64Format.isEmpty()) {
            fileName = fileService.saveFile(imageB64Format);
        } else {
            fileName = "/Standart/No Person Foto.png";
        }
        return fileName;
    }

    //    -----------------------------Початок: 1. Рандомне створення довільної к-ті поїздів----------------------------
    public void createRandomPersons(int countPerson) {
        for (int i = 0; i < countPerson; i++) {
            Person person = new Person(randomName(), randomMoney(), "/Standart/No Person Foto.png",
                    randomPersonFavoriteProductList());
            personRepository.save(person);
            cartService.createRandomCart(person);
        }
    }

    public String randomName() {
        List<String> names = new ArrayList<>();
        Collections.addAll(names,
                "Andriy", "Petro", "Ivan", "Ostap", "Nazar", "Mykola", "Yura", "Oksana", "Ivanka",
                "Tetiana", "Olga", "Sergiy");
        String randomName = names.get((int) (random() * (names.size())));
        return randomName;
    }

    public int randomMoney() {
        int min = 1000, max = 5000;
        return (int) (min + Math.random() * (max - min + 1));
    }

    public Set<Product> randomPersonFavoriteProductList() {
        Set<Product> productList = new HashSet<>();
        Collections.addAll(productList,
                productService.randomProduct(), productService.randomProduct(), productService.randomProduct());
        return productList;
    }
    //    -----------------------------Кінець: 1. Рандомне створення довільної к-ті поїздів-----------------------------

    //    -----------------------------Початок: 3. Посторіночна розбивка та сортування----------------------------------
    public PageResponse<PersonResponse> findPageAndSort(PaginationRequest paginationRequest) {
        Page<Person> page = personRepository.findAll(paginationRequest.toPageble());
        return new PageResponse<>(page.getTotalPages(), page.getTotalElements(),
                page.get().map(PersonResponse::new).collect(Collectors.toList()));
    }

    public PageResponse<PersonResponse> findPageAndSortAndFilterByCriteria(
            PaginationRequest paginationRequest, PersonCriteriaRequest personCriteriaRequest) {
        Page<Person> page = personRepository.findAll(new PersonSpecification(personCriteriaRequest),
                paginationRequest.toPageble());
        return new PageResponse<>(page.getTotalPages(), page.getTotalElements(),
                page.get().map(PersonResponse::new).collect(Collectors.toList()));
    }
    //    -----------------------------Кінець: 3. Посторіночна розбивка та сортування----------------------------------

    //    -----------------------------Початок: 4. Операції з "Улюбленим" ---------------------------------------------
    public void addProductToFavoriteList(Long productId, Long personId) {
        Person person = findOneByID(personId);
        Product product = productService.findOneByID(productId);
        person.getPersonFavoriteProductList().add(product);
        personRepository.save(person);
    }

    public void removeProductFromFavoriteList(Long productId, Long personId) {
        Person person = findOneByID(personId);
        Product product = productService.findOneByID(productId);
        person.getPersonFavoriteProductList().remove(product);
        personRepository.save(person);
    }
    //    -----------------------------Кінець: 4. Операції з "Улюбленим" ---------------------------------------------

    //    =========CRUD-операції========================================================================================
    public void create(PersonRequest request) throws IOException {
        Person person = Person.builder().fname(request.getFname()).money(request.getMoney())
                .imageFileName(getFileName(request.getImageB64Format())).build();
        personRepository.save(person);
        cartService.create(person);
    }

    public void update(Long id, PersonRequest request) throws IOException {
        Person person = findOneByID(id);
        person.setFname(request.getFname());
        person.setMoney(request.getMoney());
        person.setImageFileName(getFileName(request.getImageB64Format()));
        if (request.getPersonFavoriteProductIdList() != null) {
            person.setPersonFavoriteProductList(productService.convertIdListIntoProductSetList(request.getPersonFavoriteProductIdList()));
        }
        personRepository.save(person);
    }

    public void delete(Long id) {
        personRepository.delete(findOneByID(id));
    }

    public void deleteAll() {
        personRepository.deleteAll();
    }

    public Person findOneByID(Long id) {
        return personRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(
                "Person with ID - " + id + " not exist.\n"));
    }

    public List<PersonResponse> findAll() {
        return personRepository.findAll().stream()
                .map(person -> new PersonResponse(person))
                .collect(Collectors.toList());
    }
}
