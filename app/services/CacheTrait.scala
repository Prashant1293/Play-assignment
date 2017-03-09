package services

trait CacheTrait {

  def setCache(value:String,newObject:UserDetails):Boolean
  def getCache(value:String):Option[List[UserDetails]]
  def removeCache(value:UserDetails):Boolean
}
