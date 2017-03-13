package controllers

import javax.inject.Inject
import play.api.Configuration
import play.api.cache._
import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.{Constraint, Invalid, Valid, ValidationError}
import play.api.mvc.{Action, Controller}
import services._

import scala.collection.mutable.ListBuffer

class SignupController @Inject() (cache : CacheApi,cacheService : CacheTrait, hashing : HashingTrait,
                                 configuration : Configuration) extends Controller {
    val userForm = Form(
    mapping(
      "firstname" -> nonEmptyText,
      "middlename" -> text,
      "lastname" -> nonEmptyText,
      "gender" -> text,
      "hobbies" -> text,
      "email"  -> email,
      "mobile" -> longNumber,
      "username" -> text,
      "password" -> text,
      "confirmPassword"->text,
      "isAdmin" ->boolean,
      "isSuspend"->boolean
    )(UserDetails.apply)(UserDetails.unapply)
  )

  def default = Action {
    Console.println("list wont get form values"+Service.list)
    Ok(views.html.signup())
  }
  def store = Action {implicit request =>

    userForm.bindFromRequest.fold(
      formWithErrors => {
        BadRequest("Something went wrong."+formWithErrors.toString)
      },
      value => {
        val keyUser: List[UserDetails] = cacheService.getCache("cache").toList.flatten
        def iterate(ls:List[UserDetails]):UserDetails= {
          ls match {
            case head :: tail if (value.username.equals(head.username)) => head
            case head :: Nil if (value.username.equals(head.username)) => head
            case head :: tail=>iterate(tail)
            case Nil=>null
          }
        }
        val result=iterate(keyUser)

        if(value.mobile.toString().length==10) {
          if(value.password.equals(value.confirmPassword)) {
            val config=configuration.getString("type")
            config match{
              case Some(check) if check.equals("Admin")=>{
            val newObject=value.copy(password =hashing.getHash(value.password), confirmPassword= hashing.
              getHash(value.confirmPassword),isAdmin = true)

                cacheService.setCache(value.username, newObject)
                Redirect(routes.DetailsController.default).withSession("username" -> value.username)
          }
              case Some(check) if check.equals("Normal")=>{

                val newObject=value.copy(password = hashing.getHash(value.password),confirmPassword = hashing.
                  getHash(value.confirmPassword))

                cacheService.setCache("cache", newObject)

                Redirect(routes.DetailsController.default).withSession("username" -> value.username)
              }
              case None=>Ok("")
            }

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