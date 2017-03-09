package services

import javax.inject.Inject
import play.api.cache.CacheApi
import org.mindrot.jbcrypt.BCrypt

class HashingPassword @Inject()(cache: CacheApi) extends HashingTrait{
  def getHash(str: String) : String = {
    BCrypt.hashpw(str, BCrypt.gensalt())
  }
  def checkHash(str: String, strHashed: String): Boolean = {
    BCrypt.checkpw(str,strHashed)
  }
}