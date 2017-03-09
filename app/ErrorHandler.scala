import play.api.http.HttpErrorHandler
import play.api.mvc._
import play.api.mvc.Results._
import scala.concurrent._
import javax.inject.Singleton;

@Singleton
class ErrorHandler extends HttpErrorHandler {

  def onClientError(request: RequestHeader, statusCode: Int, message: String) = {
    println(message)
    statusCode match {
      case 404 => Future.successful(
        Status(statusCode)("Page not found")
      )
      case 400 => Future.successful(
        Status(statusCode)("Bad Request")
      )
      case 403 => Future.successful(
        Status(statusCode)("Forbidden area")
      )
      case 407 => Future.successful(
        Status(statusCode)("Proxy Authentication Required")
      )
      case _=> Future.successful(
        Status(statusCode)("A client error occurred: " + message)
      )
    }
  }

  def onServerError(request: RequestHeader, exception: Throwable) = {
    Future.successful(
      InternalServerError("A server error occurred: " + exception.getMessage)
    )
  }
}

