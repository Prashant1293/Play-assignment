package services

/**
  * Created by prashant on 10-03-2017.
  */
trait HashingTrait {
  def getHash(str: String) : String
  def checkHash(str: String, strHashed: String): Boolean

}
