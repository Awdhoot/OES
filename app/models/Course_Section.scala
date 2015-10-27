package models

import play.api.libs.json.Json

/**
 * Created by synerzip on 26/10/15.
 */
case class Course_Section (weightage:String,course_id:String,section_id:String)

object Course_Section{

  implicit val csReads = Json.reads[Course_Section]
  implicit val csWrites = Json.writes[Course_Section]
}
