package com.articleRepository.controllers;

import com.articleRepository.dao.ArticleDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/")
public class RepositoryController {
    private final ArticleDao articleDao;

    public RepositoryController(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    @Path("storeArticle")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public Response storeArticle(@FormParam("articleKey") String articleKey,
                                 @FormParam("article") String article,
                                 @Context HttpServletRequest request) {

        Map<String, String> checkParams = new HashMap<>();
        checkParams.put("Key of article", articleKey);
        checkParams.put("text of the article", article);
        String errors = checkData(checkParams);

        if (errors.isEmpty()) {
            articleDao.addArticle(articleKey, article);
        }

        HttpSession session = request.getSession();
        session.setAttribute("addErrors", errors);
        session.setAttribute("articleKeys", articleDao.getKeys().stream().collect(Collectors.joining("<br>")));
        return buildResponse(request.getContextPath());
    }

    @Path("getArticle")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response getArticle(@QueryParam("articleKey") String articleKey,
                               @Context HttpServletRequest request) {

        Map<String, String> checkParams = new HashMap<>();
        checkParams.put("Key of article", articleKey);
        String errors = checkData(checkParams);

        HttpSession session = request.getSession();
        session.setAttribute("articleKey", articleKey);
        session.setAttribute("getErrors", errors);

        if (errors.isEmpty()) {
            String article = articleDao.getArticle(articleKey);
            session.setAttribute("article", article);
            session.setAttribute("getNotExist", article != null ? "" : "Not found");
        }

        return buildResponse(request.getContextPath());
    }


    @Path("findArticles")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public Response findArticle(@FormParam("tokens") String tokens,
                                @Context HttpServletRequest request) {

        Map<String, String> checkParams = new HashMap<>();
        checkParams.put("tokens", tokens);
        String errors = checkData(checkParams);

        HttpSession session = request.getSession();
        session.setAttribute("findErrors", errors);
        session.setAttribute("tokens", tokens);

        if (errors.isEmpty()) {
            Collection<String> findErrors = articleDao.findArticles(tokens);
            session.setAttribute("foundKeys", findErrors.stream().collect(Collectors.joining("<br>")));
        }

        return buildResponse(request.getContextPath());
    }

    private Response buildResponse(String path) {// add found articles
        UriBuilder uriBuilder = UriBuilder.fromUri(URI.create(path));
        uriBuilder.path("/index.jsp");
        URI uri = uriBuilder.build();

        return Response.seeOther(uri).build();
    }

    private String checkData(Map<String, String> checkParams) {
        StringBuilder errorsBuilder = new StringBuilder();
        for (String key : checkParams.keySet()) {
            if (checkParams.get(key).isEmpty()) {
                if (errorsBuilder.length() != 0) {
                    errorsBuilder.append("<br>");
                }

                errorsBuilder.append("The " + key + " should be set");
            }
        }

        return errorsBuilder.toString();
    }
}
