package models

import play.api.libs.json.Json

/**
 * Created by synerzip on 26/10/15.
 */
case class QBank (question:String,ch1:String,ch2:String,ch3:String,ch4:String,correctans:String,question_id:String,section_id:String)

object QBank{

  implicit val qbReads = Json.reads[QBank]
  implicit val qbWrites = Json.writes[QBank]
}
