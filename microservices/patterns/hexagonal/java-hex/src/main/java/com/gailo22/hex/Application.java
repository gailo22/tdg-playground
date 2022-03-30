package com.gailo22.hex;

import com.gailo22.hex.domain.article.ArticleCreateUseCase;
import com.gailo22.hex.domain.article.ArticleQueryUseCase;
import com.gailo22.hex.domain.article.ArticleRetrieveUseCase;
import com.gailo22.hex.domain.article.model.Article;
import com.gailo22.hex.domain.article.usecase.ArticleCreate;
import com.gailo22.hex.domain.article.usecase.ArticleQuery;
import com.gailo22.hex.infrastructure.adapter.article.cli.ArticleCli;
import com.gailo22.hex.infrastructure.adapter.article.persistence.ArticleInMemoryDataAdapter;

import java.util.List;

public class Application {

    public static void main(String[] args) {
        ArticleInMemoryDataAdapter articleInMemoryDataAdapter = new ArticleInMemoryDataAdapter();

        ArticleCli articleCli = new ArticleCli(
                new ArticleCreateUseCase(articleInMemoryDataAdapter),
                new ArticleRetrieveUseCase(articleInMemoryDataAdapter),
                new ArticleQueryUseCase(articleInMemoryDataAdapter));

        Article article = articleCli.create(new ArticleCreate(5L, "The sample article",
                "How to create article using java"));
        System.out.println("Article is created: " + article);

        Article articleDetails = articleCli.retrieve(1L);
        System.out.println("Article details: " + articleDetails);

        List<Article> result = articleCli.query(new ArticleQuery(5L));
        System.out.println("Found articles: " + result);
    }
}
