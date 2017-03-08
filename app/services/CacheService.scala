package services

import javax.inject.Inject

import com.sun.corba.se.impl.ior.NewObjectKeyTemplateBase
import play.api.cache
import play.api.cache.CacheApi

class CacheService @Inject() (cache: CacheApi) extends CacheTrait{

  def setcache(value:String,newObject:UserDetails)={

    cache.set(value, newObject)
  }
  def getcache(value:String)={

    cache.get[UserDetails](value)
  }
}
