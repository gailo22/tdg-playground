package com.gailo22.hex.domain.article.usecase;

public record ArticleRetrieve(Long id) {

    public static ArticleRetrieve from(Long id) {
        return new ArticleRetrieve(id);
    }

}
