package controllers

import javax.inject.Inject
import play.api.cache
import play.api.cache.CacheApi
import play.api.data._
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}
import services.{Service, UserAuthentication, UserDetails}


class DetailsController @Inject()(cache : CacheApi) extends Controller {

  def default=Action{implicit request=>
    request.session.get("username").map { user =>
      val keyUser: Option[UserDetails] = cache.get[UserDetails](user)
      Console.println(keyUser)
      keyUser match {
        case Some(result) if (user.equals(result.username))=>Ok(views.html.display(result.firstname,result.middlename,result.lastname,user,result.mobile,result.gender,result.hobbies))
        case None =>Ok(views.html.display("","","","",0,"",""))
      }
    }.getOrElse {
      Ok(views.html.display("","","","",0,"",""))
    }
  }
}