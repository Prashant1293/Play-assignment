import org.scalatestplus.play.{OneAppPerTest, PlaySpec}
import play.api.mvc.Results
import play.api.test.FakeRequest
import play.api.test.Helpers._
import org.scalatest._

import scala.concurrent.Future
import play.api.mvc._
import controllers.ProfileController
import services.{CacheTrait, UserDetails}
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._


class DetailsSpec extends PlaySpec with OneAppPerTest with Results with MockitoSugar{
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
}
