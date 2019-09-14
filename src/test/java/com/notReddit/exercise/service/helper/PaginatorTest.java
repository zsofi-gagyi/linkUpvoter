package com.notReddit.exercise.service.helper;

import com.notReddit.exercise.model.database.Post;
import com.notReddit.testUtilities.PaginatorTestData;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;


public class PaginatorTest {

  private static Paginator _paginator;
  private static List<Post> _allPosts;
  private static List<PaginatorTestData> _pagination;
  private static int _postsPerPage;

  @BeforeClass
  public static void setUp(){
    _paginator = new Paginator();
    _allPosts = PaginatorTestData.getAllPosts();
    _pagination = PaginatorTestData.getPaginationForTwoPerPage();
    _postsPerPage = 2;
  }

  @Test
  public void findOnWhichPageIsPostThatIsNotComment_correctInput_correctPage() {
    for (PaginatorTestData postTitleAndPageNumber : _pagination){
      Post post = _allPosts.stream()
        .filter(p -> p.getTitle().equals(postTitleAndPageNumber.postTitle))
        .findFirst()
        .get();

      int result = _paginator.findOnWhichPageIsPostThatIsNotComment(_postsPerPage, post, _allPosts);
      assertEquals(postTitleAndPageNumber.pageNumber, result);
    }
  }
}