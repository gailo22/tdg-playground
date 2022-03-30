package com.gailo22.hex.domain.article;

import com.gailo22.hex.domain.article.model.Article;
import com.gailo22.hex.domain.article.port.ArticlePort;
import com.gailo22.hex.domain.article.usecase.ArticleCreate;

public class ArticleCreateUseCase {

    final ArticlePort articlePort;

    public ArticleCreateUseCase(ArticlePort articlePort) {
        this.articlePort = articlePort;
    }

    public Article create(ArticleCreate useCase){
        return this.articlePort.create(useCase);
    }
}
