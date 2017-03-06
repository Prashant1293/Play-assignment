package controllers

import javax.inject.Inject

import play.api.data._
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}
import services.{Service, UserAuthentication, UserDetails}


class LoginController @Inject() extends Controller {

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
        def iterate(ls:List[UserDetails]):UserDetails= {
          ls match {
            case head :: tail if (value.username.equals(head.username) && value.password.equals(head.password)) => head
            case head :: Nil if (value.username.equals(head.username) && value.password.equals(head.password)) => head
            case head :: tail=>iterate(tail)
            case Nil=>null
          }
        }
        val result=iterate(Service.list.toList)
        if(result!=null)
          Redirect(routes.DetailsController.default).withSession("username"->result.username)
        else
          Ok("Invalid Username or Password !!")
      }
    )

  }
}