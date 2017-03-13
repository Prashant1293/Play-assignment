package controllers

import javax.inject.Inject

import play.api.cache.CacheApi
import play.api.mvc.{Action, Controller}
import services.{CacheTrait, HashingPassword, UserDetails}

class MaintenanceController @Inject()(cache: CacheApi, cacheService: CacheTrait) extends Controller {

  def default = Action {implicit request=>

    val keyUser: List[UserDetails] = cacheService.getCache("cache").toList.flatten
    request.session.get("username").map { user =>
      Ok(views.html.maintenance(user,keyUser))
    }.getOrElse {
      Ok(views.html.maintenance("",keyUser))
    }
  }

  def suspendUser(user: String) = Action {implicit request=>

    val keyUser: List[UserDetails] = cacheService.getCache("cache").toList.flatten
    def iterate(ls:List[UserDetails]):UserDetails= {
      ls match {
        case head :: tail if (user.equals(head.username)) => head
        case head :: Nil if (user.equals(head.username)) => head
        case head :: tail=>iterate(tail)
        case Nil=>null
      }
    }
    val result=iterate(keyUser)
    if(result!=null){
      cacheService.removeCache(result)

    }
    println(""+cacheService.getCache("cache").toList.flatten)
    request.session.get("username").map { user =>
      //Ok("Success")
      Ok(views.html.maintenance(user,cacheService.getCache("cache").toList.flatten))
    }.getOrElse {
      Ok(views.html.maintenance("",cacheService.getCache("cache").toList.flatten))
    }
    //Ok("")
  }
  def resumeUser(user: String) = Action {implicit request=>

    val keyUser: List[UserDetails] = cacheService.getCache("cache").toList.flatten

    def iterate(ls:List[UserDetails]):UserDetails= {
      ls match {
        case head :: tail if (user.equals(head.username)) => head
        case head :: Nil if (user.equals(head.username)) => head
        case head :: tail=>iterate(tail)
        case Nil=>null
      }
    }
    val result=iterate(keyUser)

    if(result!=null){
      cacheService.removeCache(result)
    }
    println(""+cacheService.getCache("cache").toList.flatten)
    request.session.get("username").map { user =>
      Ok(views.html.maintenance(user,cacheService.getCache("cache").toList.flatten))
    }.getOrElse {
      Ok(views.html.maintenance(user,cacheService.getCache("cache").toList.flatten))
    }

  }

}