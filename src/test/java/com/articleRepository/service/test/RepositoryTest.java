package com.articleRepository.service.test;

import com.articleRepository.dao.ArticleDao;
import com.articleRepository.dao.MemoryArticleDao;
import org.junit.Test;

import java.util.Collection;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RepositoryTest{
    private ArticleDao dao = new MemoryArticleDao();
    {
        dao.addArticle("First article", "First article test");
        dao.addArticle("Second article", "Second article test");
    }

    @Test
    public void testGetAllArticles() {
        Map<String, String> articles = dao.getArticles();
        assertEquals("The test amount of articles is 2", 2, articles.size());
    }

    @Test
    public void testGetArticle() {
        String testArticle = dao.getArticle("Second article");
        assertNotNull("Should return not empty string", testArticle);
    }

    @Test
    public void testGetKeys() {
        Collection<String> articlesKeys = dao.getKeys();
        assertEquals("The test amount keys of articles is 2", 2, articlesKeys.size());
    }

    @Test
    public void testFindArticles() {
        Collection<String> foundKeys = dao.findArticles("First, test");
        assertEquals("Should return size 1", 1, foundKeys.size());
        assertEquals("Should return size 'First article' ", true, foundKeys.contains("First article"));

        foundKeys = dao.findArticles("test, article");
        assertEquals("Should return size 2", 2, foundKeys.size());
        assertEquals("Should return size 'Second article' ", true, foundKeys.contains("Second article"));

        foundKeys = dao.findArticles("test, articles");
        assertEquals("Should return empty collection", 0, foundKeys.size());
     }
}
