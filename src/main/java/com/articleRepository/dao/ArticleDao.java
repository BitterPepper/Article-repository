package com.articleRepository.dao;

import java.util.Collection;
import java.util.Map;

public interface ArticleDao {
    void addArticle(String key, String article);

    String getArticle(String key);

    Collection<String> findArticles(String tokens);

    Map<String, String> getArticles();

    Collection<String> getKeys();
}
