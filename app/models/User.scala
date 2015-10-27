package models

import play.api.libs.json.Json

/**
 * Created by synerzip on 23/10/15.
 */
case class  User(user_id:String,uname:String,pwd:String)

object User{
  implicit val userReads = Json.reads[User] // to User
  implicit val userWries =Json.writes[User]//to JObj

}
