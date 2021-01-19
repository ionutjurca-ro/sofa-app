package com.sda.productionproject.controller;

import com.sda.productionproject.dto.ArticleListDto;
import com.sda.productionproject.dto.MaterialDto;
import com.sda.productionproject.service.ArticleListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
public class ArticleListController {

    private ArticleListService articleListService;

    public ArticleListController(ArticleListService articleListService) {
        this.articleListService = articleListService;
    }

    @GetMapping("/articleLists")
    public String showAllArticleLists(Model model) {
        return findPaginated(1, "model", "asc", model);
    }

    @GetMapping("/articleList/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        log.info("show all article lists");
        int pageSize = 2;

        Page<ArticleListDto> page = articleListService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<ArticleListDto> articleLists = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("articleLists", articleLists);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "articleLists";
    }

    @GetMapping("/articleList/new")
    public String newArticleList(Model model) {
        log.info("Show new article list form");
        model.addAttribute("articleList", new ArticleListDto());
        return "articleList-form";
    }

    @PostMapping("articleList/save")
    public String saveArticleList(@ModelAttribute("articleList") ArticleListDto articleListDto) {
        log.info("action: save new article list");
        ArticleListDto savedArticleListDto = articleListService.save(articleListDto);
        return "redirect:/articleList/view/" + savedArticleListDto.getArticleListId();
    }

    @GetMapping("/articleList/view/{id}")
    public String showArticleListPage(@PathVariable(name = "id") Long id, Model model) {
        log.info("Show article list page");
        ArticleListDto articleListDto = articleListService.findById(id);
        model.addAttribute("articleList", articleListDto);
        model.addAttribute("materials", articleListDto.getMaterials());
        return "articleList-view";
    }

    @GetMapping("/articleList/edit/{id}")
    public String editArticleList(@PathVariable(name = "id") Long id, Model model) {
        log.info("Show edit article list form");
        model.addAttribute("articleList", articleListService.findById(id));
        return "articleList-edit";
    }

    @GetMapping("/articleList/delete/{id}")
    public String deleteArticleList(@PathVariable(name = "id") Long id) {
        log.info("delete articleList with id {}", id);
        articleListService.deleteById(id);
        return "redirect:/articleLists";
    }

}
