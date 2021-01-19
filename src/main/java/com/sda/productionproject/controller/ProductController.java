package com.sda.productionproject.controller;

import com.sda.productionproject.dto.MaterialDto;
import com.sda.productionproject.dto.ProductDto;
import com.sda.productionproject.service.MaterialService;
import com.sda.productionproject.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class ProductController {

    private ProductService productService;
    private MaterialService materialService;

    public ProductController(ProductService productService, MaterialService materialService) {
        this.productService = productService;
        this.materialService = materialService;
    }

    @GetMapping("/products")
    public String showAllProductList(Model model) {
        return findPaginated(1, "model", "asc", model);
    }

    @GetMapping("/product/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        log.info("show all products");
        int pageSize = 10;

        Page<ProductDto> page = productService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<ProductDto> products = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalProducts", page.getTotalElements());
        model.addAttribute("products", products);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "products";
    }

    @GetMapping("/product/new")
    public String newProduct(Model model) {
        log.info("Show new product form");
        model.addAttribute("product", new ProductDto());
        model.addAttribute("savedMaterials", materialService.findAll().stream().map(MaterialDto::getName).collect(Collectors.toSet()));
        return "product-form";
    }

    @PostMapping("/product/save")
    public String saveProduct(@Valid @ModelAttribute("product") ProductDto productDto) {
        log.info("action: save product");
        ProductDto savedProduct = productService.save(productDto);
        return "redirect:/product/view/" + savedProduct.getProductId();
    }

    @GetMapping("/product/view/{id}")
    public String showProductPage(@PathVariable(name = "id") Long id, Model model) {
        log.info("Show product page");
        model.addAttribute("product", productService.findById(id));
        return "product-view";
    }

    @GetMapping("/product/edit/{id}")
    public String editProduct(@PathVariable(name = "id") Long id, Model model) {
        log.info("Show edit product form");
        model.addAttribute("product", productService.findById(id));
//        model.addAttribute("savedMaterials", materialService.findAll().stream().map(MaterialDto::getName).collect(Collectors.toSet()));
        model.addAttribute("savedMaterials", materialService.findAll());
        model.addAttribute("materials", productService.findById(id).getMaterials());

        return "product-edit";
    }

    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") Long id) {
        log.info("delete product with id {}", id);
        productService.deleteById(id);
        return "redirect:/products";
    }

    @GetMapping("/products/without-materials")
    public String showAllProductsWithoutMaterials(Model model) {
        log.info("Show products without materials page");
        model.addAttribute("products", productService.productsWithoutMaterials());
        return "without-materials";
    }

    @PostMapping("/products/assign-materials")
    public String assignMaterials(@ModelAttribute("product") ProductDto product) {
        log.info("Show products without materials page");
        Set<ProductDto> productsWithoutMaterials = productService.productsWithoutMaterials();
        productService.assignMaterials(productsWithoutMaterials);
        for (ProductDto productDto : productsWithoutMaterials) {
            productService.save(productDto);
        }
        return "without-materials";
    }
}
