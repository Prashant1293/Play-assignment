package controllers

import javax.inject._

import play.api._
import play.api.mvc._
/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() extends Controller {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def getSignUp= Action {
    Ok(views.html.signup())
  }

  def getLogin= Action {
    Ok(views.html.login())
  }

 /* def getError=Action.async{
    val err=new ErrorHandler

      err.onClientError( request ,404,"Page Not Found").map(err=>
      Ok(err)
      )

  }*/
}
