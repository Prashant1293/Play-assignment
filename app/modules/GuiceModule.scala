package modules

import com.google.inject.AbstractModule
import play.api.{Configuration, Environment}
import services.{CacheService, CacheTrait, HashingPassword, HashingTrait}

class GuiceModule(environment: Environment,configuration: Configuration) extends AbstractModule{

  override def configure()={
    bind(classOf[CacheTrait]).to(classOf[CacheService])
    bind(classOf[HashingTrait]).to(classOf[HashingPassword])
  }
}
