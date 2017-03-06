package controllers

import javax.inject.Inject

import play.api.data._
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}
import services.{Service, UserAuthentication, UserDetails}


class DetailsController @Inject() extends Controller {

  def default=Action{implicit request=>
    request.session.get("username").map { user =>
      def iterate(ls:List[UserDetails]):UserDetails= {
        ls match {
          case head :: tail if (user.equals(head.username)) => head
          case head :: Nil if (user.equals(head.username)) => head
          case head :: tail=>iterate(tail)
          case Nil=>null
        }
      }
      val result=iterate(Service.list.toList)
      if(result!=null)
        Ok(views.html.display(result.firstname,result.middlename,result.lastname,user,result.mobile,result.gender,result.hobbies))
      else
        Ok(views.html.display("","","","",0,"",""))
    }.getOrElse {
      Ok(views.html.display("","","","",0,"",""))
    }
  }
}