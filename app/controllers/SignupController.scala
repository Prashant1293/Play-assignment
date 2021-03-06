package controllers

import javax.inject.Inject
import play.api.cache
import play.api.cache.CacheApi

import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.{Constraint, Invalid, Valid, ValidationError}
import play.api.mvc.{Action, Controller}
import services.{UserDetails,Service,HashingPassword}

class SignupController @Inject()(cache : CacheApi) extends Controller {
  //val mobileCheck: Mapping[String] = nonEmptyText(minLength = 10) .verifying("^(0|[1-9][0-9]*)$")
  val userForm = Form(
    mapping(
      "firstname" -> nonEmptyText,
      "middlename" -> text,
      "lastname" -> nonEmptyText,
      "gender" -> text,
      "hobbies" -> text,
      "email"  -> email,
      "mobile" ->  longNumber,
      "username" -> text,
      "password" -> text,
      "confirmPassword"->text
    )(UserDetails.apply)(UserDetails.unapply)
  )
  def default = Action {
    Console.println(Service.list)
    Ok(views.html.signup())
  }
  def store = Action {implicit request =>

    userForm.bindFromRequest.fold(
      formWithErrors => {
        BadRequest("Something went wrong."+formWithErrors.toString)
      },
      value => {
        if(value.mobile.toString().length==10) {
          if(value.password.equals(value.confirmPassword)) {
            //Service.list.append(value)
            //Console.println(Service.list)
            val newObject=value.copy(password = HashingPassword.getHash(value.password), confirmPassword= HashingPassword.
              getHash(value.confirmPassword))

            cache.set(value.username, newObject)
            //Service.list.append(value)
            //Console.println(Service.list)
            Redirect(routes.DetailsController.default).withSession("username" -> value.username)
          }
          else{
            BadRequest("Passwords Do Not Match.")
          }
        }
        else{
          BadRequest("Mobile Number Incorrect.")
        }
      }
    )
  }
}