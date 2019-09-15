package com.notReddit.exercise.controller.shared;

import com.notReddit.exercise.model.representation.PageView;
import com.notReddit.exercise.model.representation.PostView;
import com.notReddit.exercise.service.MainService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest()
@RunWith(SpringRunner.class)
public class HeaderTest {

  @Autowired
  private MockMvc _mockMvc;

  @MockBean
  private MainService _mainService;

  private static List<String> _urlsToTest;

  @BeforeClass
  public static void setUp() {
    _urlsToTest =  Arrays.asList(
      "/users/0/postsPerPage/1/page/1",
      "/users/0/postsPerPage/1/page/1/submitPost",
      "/users/0/postsPerPage/1/page/1/posts/1/submitComment");
  }

  @Before
  public void setUpMock() {
    prepareMainServiceMock();
  }

  @Test
  public void allPages_showNavbar() throws Exception{
    for (int i = 0; i < _urlsToTest.size(); i++) {
      MvcResult result = _mockMvc.perform(get(_urlsToTest.get(i)))
        .andReturn();

      String resultString = result.getResponse().getContentAsString();
      Document resultDocument = Jsoup.parse(resultString);

      List<Element> samePageLinks = resultDocument.select("nav");
      int navbarNr = samePageLinks.size();

      assertEquals(navbarNr, 1);
    }
  }

  @Test
  public void allPages_showSiteName_asLink() throws Exception{
    for (int i = 0; i < _urlsToTest.size(); i++) {
      MvcResult result = _mockMvc.perform(get(_urlsToTest.get(i)))
        .andReturn();

      String resultString = result.getResponse().getContentAsString();
      Document resultDocument = Jsoup.parse(resultString);

      List<Element> mainTitle = resultDocument.select("h1[data-test=mainTitle]");
      int mainTitleNr = mainTitle.size();
      String mainTitleText = mainTitle.get(0).text();

      List<Element> mainTitleLinks = resultDocument.select("a[data-test=titleLink]");
      String mainTitleHref = mainTitleLinks.get(0).attr("href");

      assertEquals(mainTitleNr, 1);
      assertEquals(mainTitleText, "LinkUpvoter");
      assertEquals(mainTitleHref, "/users/0/postsPerPage/1/page/1");
    }
  }

  private void prepareMainServiceMock(){
    PostView postView = new PostView(1, "title", "url", 3, "author",  false);
    PageView pageViewToReturn = new PageView();
    pageViewToReturn.pageNumbers = Arrays.asList(1, 2, 3);
    pageViewToReturn.posts = new ArrayList<>();

    Mockito.when(this._mainService.createPage(1, 1))
      .thenReturn(pageViewToReturn);

    Mockito.when(this._mainService.translatePosts(pageViewToReturn.posts, 0L))
      .thenReturn(Arrays.asList(postView));

    Mockito.when(this._mainService.getPostView(1, 0))
      .thenReturn(postView);
  }
}
