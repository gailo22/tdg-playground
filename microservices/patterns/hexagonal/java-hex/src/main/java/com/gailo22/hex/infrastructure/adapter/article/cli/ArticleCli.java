package com.gailo22.hex.infrastructure.adapter.article.cli;

import com.gailo22.hex.domain.article.ArticleCreateUseCase;
import com.gailo22.hex.domain.article.ArticleQueryUseCase;
import com.gailo22.hex.domain.article.ArticleRetrieveUseCase;
import com.gailo22.hex.domain.article.model.Article;
import com.gailo22.hex.domain.article.port.ArticlePort;
import com.gailo22.hex.domain.article.usecase.ArticleCreate;
import com.gailo22.hex.domain.article.usecase.ArticleQuery;
import com.gailo22.hex.domain.article.usecase.ArticleRetrieve;

import java.util.List;

public class ArticleCli implements ArticlePort {

    private final ArticleCreateUseCase articleCreateUseCase;
    private final ArticleRetrieveUseCase articleRetrieveUseCase;
    private final ArticleQueryUseCase articleQueryUseCase;

    public ArticleCli(ArticleCreateUseCase articleCreateUseCase,
                      ArticleRetrieveUseCase articleRetrieveUseCase,
                      ArticleQueryUseCase articleQueryUseCase) {
        this.articleCreateUseCase = articleCreateUseCase;
        this.articleRetrieveUseCase = articleRetrieveUseCase;
        this.articleQueryUseCase = articleQueryUseCase;
    }

    @Override
    public Article create(ArticleCreate articleCreate) {
        ArticleCreate article = new ArticleCreate(articleCreate.accountId(), articleCreate.title(), articleCreate.body());
        return this.articleCreateUseCase.create(article);
    }

    @Override
    public Article retrieve(Long articleId) {
        return this.articleRetrieveUseCase.retrieve(ArticleRetrieve.from(articleId));
    }

    @Override
    public List<Article> query(ArticleQuery articleQuery) {
        return this.articleQueryUseCase.query(ArticleQuery.from(articleQuery.accountId()));
    }
}
