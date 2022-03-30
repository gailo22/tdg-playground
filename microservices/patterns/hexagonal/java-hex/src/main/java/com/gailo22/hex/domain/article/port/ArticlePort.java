package com.gailo22.hex.domain.article.port;

import com.gailo22.hex.domain.article.model.Article;
import com.gailo22.hex.domain.article.usecase.ArticleCreate;
import com.gailo22.hex.domain.article.usecase.ArticleQuery;

import java.util.List;

public interface ArticlePort {

    Article create(ArticleCreate articleCreate);

    Article retrieve(Long articleId);

    List<Article> query(ArticleQuery articleQuery);
}
