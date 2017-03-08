package controllers

import javax.inject.Inject

import play.api.cache
import play.api.cache.CacheApi
import play.api.data._
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}
import services._


class DetailsController @Inject()(cache : CacheApi,cacheService:CacheTrait) extends Controller {

  def default=Action{implicit request=>
    request.session.get("username").map { user =>
      val keyUser: Option[UserDetails] = cacheService.getcache(user)
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