package com.notReddit.exercise.controller;

import com.notReddit.exercise.model.representation.PageView;
import com.notReddit.exercise.model.representation.PostView;
import com.notReddit.exercise.service.MainService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.junit.Assert.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MainPageController.class)
public class MainPageControllerTest {

  @Autowired
  private MockMvc _mockMvc;

  @MockBean
  MainService _mainService;

  private MediaType _htmlType = new MediaType(MediaType.TEXT_HTML.getType(),
    MediaType.TEXT_HTML.getSubtype(),
    Charset.forName("utf8"));

  @Test
  public void getPage_httpStatusOk_formatHTML() throws Exception{
    prepareMainServiceMock();

    _mockMvc.perform(get("/users/0/postsPerPage/1/page/1"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(_htmlType));
  }

  @Test
  public void getPage_displaysPosts() throws Exception{
   prepareMainServiceMock();

    MvcResult result =_mockMvc.perform(get("/users/0/postsPerPage/1/page/1"))
      .andReturn();

    String resultString = result.getResponse().getContentAsString();
    Document resultDocument = Jsoup.parse(resultString);

    List<Element> posts = resultDocument.select("a[data-test=post]");
    int postsNumber = posts.size();
    String firstPostTitle = posts.get(0).text();
    String lastPostTitle = posts.get(1).text();

    assertEquals(postsNumber, 2);
    assertEquals(firstPostTitle, "first test post title");
    assertEquals(lastPostTitle, "last test post title");
  }

  @Test
  public void getPage_displaysOtherPageLinks() throws Exception{
    prepareMainServiceMock();

    MvcResult result =_mockMvc.perform(get("/users/0/postsPerPage/1/page/1"))
      .andReturn();

    String resultString = result.getResponse().getContentAsString();
    Document resultDocument = Jsoup.parse(resultString);

    List<Element> otherPageLinks = resultDocument.select("a[data-test=otherPageNumber]");
    int otherPageLinkNr = otherPageLinks.size();
    String firstOtherPageAdress = otherPageLinks.get(0).attr("href");
    String secondOtherPageName = otherPageLinks.get(1).text();

    assertEquals(otherPageLinkNr, 2);
    assertEquals(firstOtherPageAdress, "/users/0/postsPerPage/1/page/2");
    assertEquals(secondOtherPageName, "page 3");
  }

  @Test
  public void getPage_displaysSamePageLink() throws Exception{
    prepareMainServiceMock();

    MvcResult result =_mockMvc.perform(get("/users/0/postsPerPage/1/page/1"))
      .andReturn();

    String resultString = result.getResponse().getContentAsString();
    Document resultDocument = Jsoup.parse(resultString);

    List<Element> samePageLinks = resultDocument.select("prepareMock");
    int samePageLinkNr = samePageLinks.size();
    String samePageAdress = samePageLinks.get(0).attr("href");
    String samePageName = samePageLinks.get(0).text();

    assertEquals(samePageLinkNr, 1);
    assertEquals(samePageAdress, "/users/0/postsPerPage/1/page/1");
    assertEquals(samePageName, "page 1");
  }

  private void prepareMainServiceMock(){
      PageView pageViewToReturn = new PageView();
      pageViewToReturn.pageNumbers = Arrays.asList(1, 2, 3);

      Mockito.when(_mainService.createPage(1, 1))
        .thenReturn(pageViewToReturn);

      PostView firstPostToReturn = new PostView();
      firstPostToReturn.setTitle("first test post title");

      PostView lastPostToReturn = new PostView();
      lastPostToReturn.setTitle("last test post title");

      Mockito.when(_mainService.translatePosts(new ArrayList<>(), 0L))
        .thenReturn(Arrays.asList(firstPostToReturn, lastPostToReturn));
  }
}