package com.example4.article;

import com.example4.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public List<Article> getList(String keyword) {
        return this.articleRepository.findAllByKeyword(keyword);
    }

    public void create(String title, String content, SiteUser author) {
        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setCreateDate(LocalDateTime.now());
        article.setAuthor(author);

        this.articleRepository.save(article);
    }

    public Article getArticle(Long id) {
        Optional<Article> article = this.articleRepository.findById(id);
        return article.get();
    }

    public void delete(Article article) {
        this.articleRepository.delete(article);
    }

    public void modify(Article article, String title, String content) {
        article.setTitle(title);
        article.setContent(content);
        article.setCreateDate(LocalDateTime.now());

        this.articleRepository.save(article);

    }
}
