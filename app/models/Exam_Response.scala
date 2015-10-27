package models

import play.api.libs.json.Json

/**
 * Created by synerzip on 23/10/15.
 */
case class Exam_Response (question_id:String,exam_id:String,user_response:String,result:String)

object Exam_Response{
  implicit val exresReads = Json.reads[Exam_Response]
  implicit val exresWrites = Json.writes[Exam_Response]

}