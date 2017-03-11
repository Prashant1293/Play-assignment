import controllers.routes
import org.mockito.Mockito.when
import org.scalatestplus.play.{OneAppPerTest, PlaySpec}
import play.api.mvc.Results
import play.api.test.FakeRequest
import play.api.test.Helpers._
import org.scalatest.mock.MockitoSugar
import services.{CacheTrait, HashingTrait, UserDetails}


class SigninSpec extends PlaySpec with OneAppPerTest with Results with MockitoSugar{

  "LoginController" should {

    "render the Details page" in {
      val home = route(app, FakeRequest(GET, "/login")).get

      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("username")
    }

  }



  "Login Page#default" should {
    "should be valid" in {
      val logRoute=route(app,FakeRequest(GET,"/login")).get
      contentType(logRoute) mustBe Some("text/html")
      status(logRoute) mustBe OK
    }
  }

}
