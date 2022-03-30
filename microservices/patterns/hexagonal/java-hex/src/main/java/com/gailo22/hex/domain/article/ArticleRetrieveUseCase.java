package com.gailo22.hex.domain.article;

import com.gailo22.hex.domain.article.model.Article;
import com.gailo22.hex.domain.article.port.ArticlePort;
import com.gailo22.hex.domain.article.usecase.ArticleRetrieve;

public class ArticleRetrieveUseCase {

    final ArticlePort articlePort;

    public ArticleRetrieveUseCase(ArticlePort articlePort) {
        this.articlePort = articlePort;
    }

    public Article retrieve(ArticleRetrieve useCase){
        return this.articlePort.retrieve(useCase.id());
    }
}
