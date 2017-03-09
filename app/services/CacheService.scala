package services

import javax.inject.Inject

import com.sun.corba.se.impl.ior.NewObjectKeyTemplateBase
import play.api.cache
import play.api.cache.CacheApi

import scala.collection.mutable.ListBuffer


class CacheService @Inject()(cache: CacheApi) extends CacheTrait {

  def setCache(value: String, newObject: UserDetails) :Boolean= {
    val list = cache.get[List[UserDetails]](value).toList.flatten
    cache.set(value, list :+ newObject)
    true
  }

  def getCache(value: String) = {
    cache.get[List[UserDetails]](value)
  }

  def removeCache(user: UserDetails):Boolean = {

    val list = cache.get[List[UserDetails]]("cache").toList.flatten
    //    val newList:List[UserDetails]=  {}
    println("hi")
    def iterate(newList:List[UserDetails],ls:List[UserDetails]):List[UserDetails]= {
      ls match {
        case head :: tail if (user.username.equals(head.username)) => {
          if(head.isSuspend==false)
            iterate(newList:+head.copy(isSuspend = true),tail)
          else
            iterate(newList:+head.copy(isSuspend = false ),tail)
        }
        case head :: null if (user.username.equals(head.username)) => {
          if(head.isSuspend==false)
            newList:+head.copy(isSuspend = true)
          else
            newList:+head.copy(isSuspend = false)
        }
        case head :: null => newList:+head
        case head :: tail=>iterate(newList:+head,tail)
      }
    }
    println(" line ")
    val result=iterate(List[UserDetails](),list)
    println(" li "+result)

    cache.remove("cache")
    cache.set("cache", result)
    true
  }
}