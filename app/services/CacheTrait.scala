package services

trait CacheTrait {

  def setcache(value:String,newObject:UserDetails)
  def getcache(value:String):Option[UserDetails]
}
