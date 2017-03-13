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
      val keyUser: List[UserDetails] = cacheService.getCache("cache").toList.flatten
      Console.println(keyUser+"key User")
      def iterate(ls:List[UserDetails]):UserDetails= {
        ls match {
          case head :: tail if (user.equals(head.username)) => head
          case head :: tail=>iterate(tail)
          case Nil=>null
        }
      }
      val result=iterate(keyUser)
      if(result!=null){
        if(result.isAdmin==false)
          Ok(views.html.display("",result.firstname,result.middlename,result.lastname,result.gender,result.mobile,
            result.hobbies,user))
        else
          Ok(views.html.display("Maintenance",result.firstname,result.middlename,result.lastname,result.gender,
            result.mobile,result.hobbies,user))
      }
      else
        Ok(views.html.display("","","","","",0,"",""))

    }.getOrElse {
      Ok(views.html.display("","","","","",0,"",""))
    }
  }
}