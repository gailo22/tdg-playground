package com.gailo22.hex.domain.article;

import com.gailo22.hex.domain.article.model.Article;
import com.gailo22.hex.domain.article.port.ArticlePort;
import com.gailo22.hex.domain.article.usecase.ArticleQuery;

import java.util.List;

public class ArticleQueryUseCase {

    final ArticlePort articlePort;

    public ArticleQueryUseCase(ArticlePort articlePort) {
        this.articlePort = articlePort;
    }

    public List<Article> query(ArticleQuery useCase){
        return articlePort.query(useCase);
    }
}