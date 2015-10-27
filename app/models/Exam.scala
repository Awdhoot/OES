package models

import play.api.libs.json.Json

/**
 * Created by synerzip on 23/10/15.
 */
case class Exam (exam_id:String,course_id:String,user_id:String,result:String)

object Exam{

  implicit val examReads = Json.reads[Exam] //to Exam
  implicit val examWrites = Json.writes[Exam]  //To JsObj

}
