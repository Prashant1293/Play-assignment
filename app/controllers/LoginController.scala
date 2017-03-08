package controllers

import javax.inject.Inject
import play.api.cache
import play.api.cache.CacheApi

import play.api.data._
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}
import services._


class LoginController @Inject()(cache : CacheApi,cacheService: CacheTrait) extends Controller {
  val userForm = Form(
    mapping(
      "username" -> text,
      "password" -> text
    )(UserAuthentication.apply)(UserAuthentication.unapply)
  )
  def default = Action {
    Console.println(Service.list)
    Ok(views.html.login())
  }

  def check = Action{implicit request=>

    userForm.bindFromRequest.fold(
      formWithErrors => {
        BadRequest("Something went wrong.")
      },
      value => {
        val keyUser: Option[UserDetails] = cacheService.getcache(value.username)
        keyUser match {
          case Some(result) if (value.username.equals(result.username) &&
            HashingPassword.checkHash(value.password, result.password) == true) => {
            Redirect(routes.DetailsController.default).withSession("username" -> result.username)
          }
          case _ => Ok(views.html.login())
        }
      }
    )
  }
}