<img src="https://github.com/zsofi-gagyi/linkUpvoter/blob/master/screenshots/2.png" width="870px"></img> 

<h2>LinkUpvoter</h2>
<h3>Deployed <a href="https://link-upvoter.herokuapp.com/">here</a></h3>
<br/>
<br/>
<p>This is a simple forum featuring</p>

- threaded comments, displayed using recursion
- Reddit-like upvoting and downvoting mechanics
- paging, with an user-cosen minimum nr of posts/page
- relative time ("posted x years, y months and z days ago")

I also used this project to understand the concept of storing state client-side, without yet focusing on the details - that's how everything has ended up as a path variable. (I have used real tools for user authentication and authorization in the project <a href="https://github.com/zsofi-gagyi/notTrello">TaskManager</a>.)

---

On a technical level, this project uses 

- Java + Spring Boot + Gradle
- PostgreSQL with Hybernate for storing data
- JUnit for unit testing, with Mockito for mocking and Jsoup for HTML parsing
- Thymeleaf for HTML templating (using fragments)
- Bootstrap for generating CSS (without it being the main focus)
- Heroku for hosting
