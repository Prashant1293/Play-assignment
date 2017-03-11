import controllers.SignupController
import org.mockito.Mockito.when
import org.scalatestplus.play.{OneAppPerTest, PlaySpec}
import play.api.mvc.Results
import play.api.test.FakeRequest
import play.api.test.Helpers._
import services.{CacheTrait, HashingTrait, UserDetails}
import org.scalatest.mock.MockitoSugar


class SignupSpec extends PlaySpec with OneAppPerTest with Results with MockitoSugar {

  "Signup Page#default" should {
    "should be valid" in {
      //val controller = new SignupController()
      val logRoute = route(app, FakeRequest(GET, "/signup")).get
      contentType(logRoute) mustBe Some("text/html")
      status(logRoute) mustBe OK
    }
  }

  "SignupController" should {

    "render the Profile page" in {
      val mockDataService = mock[CacheTrait]
      when(mockDataService.getCache("")) thenReturn Some(List[UserDetails]())
      val mockService = mock[HashingTrait]
      when(mockService.getHash("")) thenReturn ""
      val home = route(app, FakeRequest(POST, "/signup")).get
      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include("User")
    }

  }
}