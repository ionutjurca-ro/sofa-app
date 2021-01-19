package com.sda.productionproject.controller;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.sda.productionproject.dto.MaterialDto;
import com.sda.productionproject.model.Material;
import com.sda.productionproject.repository.MaterialRepository;
import com.sda.productionproject.service.MaterialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Set;

@Slf4j
@Controller
public class MaterialController {

    private MaterialService materialService;
    private MaterialRepository materialRepository;


    public MaterialController(MaterialService materialService, MaterialRepository materialRepository) {
        this.materialService = materialService;
        this.materialRepository = materialRepository;
    }

    @GetMapping("/materialsList")
    public ResponseEntity<Set<MaterialDto>> viewMaterialsList() {
        return ResponseEntity.ok(materialService.findAll());
    }

    @PostMapping("/save/material")
    public ResponseEntity<MaterialDto> materialSave(@RequestBody MaterialDto materialDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(materialService.save(materialDto));
    }

    @GetMapping("/materials")
    public String showAllMaterialList(Model model) { return findPaginated(1, "name", "asc", model); }

    @GetMapping("/material/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        log.info("show all materials");
        int pageSize = 2;

        Page<MaterialDto> page = materialService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<MaterialDto> materials = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalMaterials", page.getTotalElements());
        model.addAttribute("materials", materials);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "materials";
    }

    @GetMapping("/material/new")
    public String newMaterial(Model model) {
        log.info("Show new material form");
        model.addAttribute("material", new MaterialDto());
        return "material-form";
    }

    @PostMapping("/material/save")
    public String saveMaterial(@ModelAttribute("material") MaterialDto material) {
        log.info("action: save new material");
        MaterialDto savedMaterial = materialService.save(material);
        return "redirect:/material/view/" + savedMaterial.getMaterialId();
    }

    @GetMapping("/material/view/{id}")
    public String showMaterialPage(@PathVariable(name = "id") Long id, Model model) {
        log.info("Show material page");
        model.addAttribute("material", materialService.findById(id));
        return "material-view";
    }

    @GetMapping("/material/edit/{id}")
    public String editMaterial(@PathVariable(name = "id") Long id, Model model) {
        log.info("Show edit material form");
        model.addAttribute("material", materialService.findById(id));
        return "material-edit";
    }

    @GetMapping("/material/delete/{id}")
    public String deleteMaterial(@PathVariable(name = "id") Long id) {
        log.info("delete material with id {}", id);
        materialService.deleteById(id);
        return "redirect:/materials";
    }

    @GetMapping("/material/upload")
    public String showMaterialUpload(){
        return "material-upload";
    }

    @PostMapping("/material/upload/save")
    public String uploadCSVFile(@RequestParam("file") MultipartFile file, Model model) {

        // validate file
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a CSV file to upload.");
            model.addAttribute("status", false);
        } else {

            // parse CSV file to create a list of `Material` objects
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

                // create csv bean reader
                CsvToBean<Material> csvToBean = new CsvToBeanBuilder(reader)
                        .withType(Material.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                // convert `CsvToBean` object to list of materials
                List<Material> materials = csvToBean.parse();
                materialRepository.saveAll(materials);

                // save materials list on model
                model.addAttribute("materials", materials);
                model.addAttribute("status", true);

            } catch (Exception ex) {
                model.addAttribute("message", "An error occurred while processing the CSV file.");
                model.addAttribute("status", false);
            }
        }
        return "file-upload-status";
    }

}
