package controllers

import javax.inject.Inject

import play.api.cache
import play.api.cache.CacheApi
import play.api.data._
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}
import services.{HashingTrait, _}


class LoginController @Inject()(cache : CacheApi,cacheService: CacheTrait,hashing:HashingTrait) extends Controller {
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
        val keyUser:List[UserDetails] = cacheService.getCache("cache").toList.flatten

        def iterate(ls:List[UserDetails]):UserDetails= {
          ls match {
            case head :: tail if (value.username.equals(head.username) && hashing.checkHash(value.password,head.password)==true) => head
            case head :: Nil if (value.username.equals(head.username) && hashing.checkHash(value.password,head.password)==true) => head
            case head :: tail=>iterate(tail)
            case Nil=>null
          }
        }
        val result=iterate(keyUser)
        if(result!=null && result.isSuspend==false)
          Redirect(routes.DetailsController.default).withSession("username" -> result.username)

        else if(result!=null && result.isSuspend==true)
          Ok(views.html.login())
        else
          Ok(views.html.login())


        //        keyUser match {
//          case Some(result) if (value.username.equals(result.username) &&
//            HashingPassword.checkHash(value.password, result.password) == true) => {
//            Redirect(routes.DetailsController.default).withSession("username" -> result.username)
//          }
//          case _ => Ok(views.html.login())
//        }
     }
    )
  }
}