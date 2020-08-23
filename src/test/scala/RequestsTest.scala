import io.gatling.core.Predef._
import io.gatling.http.Predef._

class RequestsTest extends Simulation {

  val plots = tsv("data/plots.tsv")
  val genres = csv("data/genres.csv")

  val requestNumber = 100 * 10000
  val usersAtOnce = 1

  val httpProtocol = http
    .baseUrl("http://localhost:8080")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0");

  val createDocScenario = scenario("create document").repeat(requestNumber) {
    feed(plots.random)
    .feed(genres.circular)
    .exec(http("create document")
    .put("/genreDocument")
    .queryParam("documentText", "${Text}")
    .queryParam("genre", "${genre}")
    .queryParam("docId", "${ID}")
    .check(status.is(200)));
  }

  val deleteDocScenario = scenario("delete document").repeat(requestNumber) {
    feed(plots.random)
    .feed(genres.circular)
    .exec(http("delete document")
    .delete("/genreDocument")
    .queryParam("documentText", "${Text}")
    .queryParam("genre", "${genre}")
    .queryParam("docId", "${ID}")
    .check(status.in(200,404)));
  }

  val getDocScenario = scenario("get documents").repeat(requestNumber) {
    feed(genres.circular)
    .exec(http("get documents")
    .get("/genreDocuments")
    .queryParam("genre", "${genre}")
    .check(status.in(200,404)));
  }

  setUp(
    getDocScenario.inject(atOnceUsers(usersAtOnce)).protocols(httpProtocol),
    createDocScenario.inject(atOnceUsers(usersAtOnce)).protocols(httpProtocol),
    deleteDocScenario.inject(atOnceUsers(usersAtOnce)).protocols(httpProtocol),
  )

}