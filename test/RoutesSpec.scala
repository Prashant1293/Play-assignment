import org.scalatestplus.play.{OneAppPerTest, PlaySpec}
import play.api.mvc.Results
import play.api.test.FakeRequest
import play.api.test.Helpers._
import org.scalatest._

import scala.concurrent.Future
import play.api.mvc._
import controllers.DetailsController
import services.{CacheTrait, HashingTrait, UserDetails}
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._

class RoutesSpec extends PlaySpec with OneAppPerTest with Results with MockitoSugar{

  "Maintenance Controller#default" should {
    "should be valid" in {
      val logRoute=route(app,FakeRequest(GET,"/maintenance")).get
      contentType(logRoute) mustBe Some("text/html")
      status(logRoute) mustBe OK
    }
  }

  "Maintenance Controller" should {
    "render the maintenance page" in {
      val mockDataService = mock[CacheTrait]
      when(mockDataService.getCache("")) thenReturn Some(List[UserDetails]())
      when(mockDataService.removeCache(UserDetails("","","","","","",0,"","","",false,false))) mustBe true
      val mockService = mock[HashingTrait]
      when(mockService.checkHash("","")) thenReturn true
      val home = route(app, FakeRequest(POST, "/login")).get
      status(home) equals 303
    }

  }
  "Example Page#index" should {
    "should be valid" in {
      val mockDataService = mock[CacheTrait]
      when(mockDataService.getCache("")) thenReturn Some(List[UserDetails]())
      val home = route(app, FakeRequest(GET, "/profile")).get
      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include("User Details")
    }
  }
  "Front Conroller#front" should {
    "should be valid" in {
      val logRoute=route(app,FakeRequest(GET,"/")).get
      contentType(logRoute) mustBe Some("text/html")
      status(logRoute) mustBe OK
    }
  }
  "Login Page#default" should {
    "should be valid" in {
      val logRoute=route(app,FakeRequest(GET,"/login")).get
      contentType(logRoute) mustBe Some("text/html")
      status(logRoute) mustBe OK
    }
  }

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
