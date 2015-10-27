package backend

import models.User.userReads

import com.google.inject.Inject
import play.modules.reactivemongo.json.collection.JSONCollection
import play.modules.reactivemongo.{ ReactiveMongoApi}
import models.User
import play.modules.reactivemongo.json._
import play.api.libs.json.{ JsObject, Json}
import scala.concurrent.ExecutionContext.Implicits.global
class UserConnect @Inject() (val reactiveMongoApi: ReactiveMongoApi)
{

  protected  def usercollection = reactiveMongoApi.db.collection[JSONCollection]("User")

  def findall() =           //Future[List[user]]
  {
    usercollection.find(Json.obj()).sort(Json.obj("user_id"-> 1)).cursor[User].collect[List]()
  }

  def findByID(id:String) ={

    usercollection.find(Json.obj("user_id"->id)).cursor[User].collect[List]()
  }
  def pr() = {
    val uname = usercollection.find(Json.obj("user_id"->"I101")).cursor[User].collect[List]().map(x=> x.map(y=>println(y.uname.toString)))

  }

}
