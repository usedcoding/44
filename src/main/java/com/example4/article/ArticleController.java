package com.example4.article;

import com.example4.user.SiteUser;
import com.example4.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {
    private final ArticleService articleService;
    private final UserService userService;
    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "keyword",defaultValue = "") String keyword) {
        List<Article> article = this.articleService.getList(keyword);
        model.addAttribute("articleList", article);
        return"article_list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public  String create() {
        return"article_create";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String createArticle(@RequestParam(value = "title")String title, @RequestParam(value = "content")String content, Principal principal) {
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.articleService.create(title, content, siteUser);
        return"redirect:/article/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable(value = "id")Long id, Model model) {
        Article article = this.articleService.getArticle(id);
        model.addAttribute("article", article);
        return"article_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(value = "id")Long id, Model model) {
        Article article = this.articleService.getArticle(id);
        model.addAttribute("article", article);
        this.articleService.delete(article);
        return "redirect:/article/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modify(@PathVariable(value = "id")Long id, Model model, Principal principal) {
        Article article = this.articleService.getArticle(id);
        model.addAttribute("article", article);
        if(!article.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        return "article_modify";
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modifyArticle(@PathVariable(value = "id") Long id, Model model, @RequestParam("title") String title, @RequestParam("content") String content, Principal principal) {
        Article article = this.articleService.getArticle(id);
        model.addAttribute("article", article);
        if(!article.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.articleService.modify(article, title, content);
        return "redirect:/article/list";
    }



}
