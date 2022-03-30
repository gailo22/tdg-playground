package com.gailo22.hex.infrastructure.adapter.article.persistence;

import com.gailo22.hex.domain.article.model.Article;
import com.gailo22.hex.domain.article.port.ArticlePort;
import com.gailo22.hex.domain.article.usecase.ArticleCreate;
import com.gailo22.hex.domain.article.usecase.ArticleQuery;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ArticleInMemoryDataAdapter implements ArticlePort {

    private final ConcurrentHashMap<Long,Article> articles = new ConcurrentHashMap<>();

    @Override
    public Article create(ArticleCreate articleCreate) {
        long id = (articles.size() + 1);
        Article article = new Article(id, articleCreate.accountId(), articleCreate.title(),
                articleCreate.body());
        articles.put(id, article);
        return article;
    }

    @Override
    public Article retrieve(Long articleId) {
        return articles.get(articleId);
    }

    @Override
    public List<Article> query(ArticleQuery articleQuery) {
        return articles.values().stream()
                .filter(a-> a.accountId().equals(articleQuery.accountId()))
                .collect(Collectors.toList());
    }
}
