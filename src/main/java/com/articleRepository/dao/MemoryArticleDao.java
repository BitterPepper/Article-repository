package com.articleRepository.dao;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MemoryArticleDao implements ArticleDao {

    private Map<String, String> articles = new ConcurrentHashMap<>();

    @Override
    public void addArticle(String key, String article) {
        articles.put(key, article);
    }

    @Override
    public String getArticle(String key) {
        return articles.get(key);
    }

    @Override
    public Map<String, String> getArticles() {
        return articles;
    }

    @Override
    public Collection<String> getKeys() {
        return articles.keySet();
    }

    @Override
    public Collection<String> findArticles(String tokens) {
        String regexToken = "(?=.*" + Arrays.asList(tokens.split(",")).stream()
                .collect(Collectors.joining(")(?=.*")) + ")";
        Pattern findPattern = Pattern.compile(regexToken);

        Collection<String> foundKeys = articles.entrySet().stream().filter(map ->
                findPattern.matcher(map.getValue()).find())
                .map(map -> map.getKey())
                .collect(Collectors.toList());

        return foundKeys;
    }
}
