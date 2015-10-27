package backend

import com.google.inject.Inject
import play.api.libs.json.Json
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json._
import models._
import models.Exam_Response.{exresReads,exresWrites}
import models.Exam.{examReads,examWrites}
import models.Course_Section.{csReads,csWrites}
import models.QBank.{qbReads,qbWrites}
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.api.commands.{GetLastError, WriteConcernError}
import reactivemongo.bson.BSONDocument

/*import scala.concurrent.ExecutionContext*/
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Failure
import reactivemongo.core.commands.Count

//No Json serializer as JsObject found for type play.api.libs.json.JsObject





class ExResConnect @Inject() (val reactiveMongoApi: ReactiveMongoApi)
{
  protected def erconnection = reactiveMongoApi.db.collection[JSONCollection]("Exam_Response")
  protected def examconnection = reactiveMongoApi.db.collection[JSONCollection]("Exam")
  protected def csconnection = reactiveMongoApi.db.collection[JSONCollection]("Course_Section")
  protected def qconnection = reactiveMongoApi.db.collection[JSONCollection]("QBank")


  def addresbyExam_id(e_id:String) =
  {

    examconnection.find(Json.obj("exam_id"->e_id)).cursor[Exam].collect[List]().map( listOfExam => listOfExam.map(examobj =>
    {
      println("2nd map  ")
      csconnection.find(Json.obj("course_id" -> examobj.course_id)).cursor[Course_Section].collect[List]().map(listOfCourseSection => listOfCourseSection.map(coursesectionobj =>
      {
        println("1st foreach")
        qconnection.find(Json.obj("section_id" -> coursesectionobj.section_id)).cursor[QBank].collect[List]()onComplete
          {

            case util.Success(listOfQBank)  =>if(listOfQBank.size >= coursesectionobj.weightage.toInt)
              for (qbankobj <- listOfQBank.take(coursesectionobj.weightage.toInt))
              {

                println("SUCCESS!!  TOTAL "+listOfQBank.size+"  queations matched for given section_id->"+coursesectionobj.section_id+", but we need "+coursesectionobj.weightage+" questions")
                erconnection.insert(Json.obj("question_id"-> qbankobj.question_id,"exam_id"->e_id,"user_response" ->"NOT ATTENDED","result" -> "NOT GENERATED"))
              }
              else println("TOO FEW RECORDS FOR SECTION -> "+coursesectionobj.section_id)
            case Failure(r) => println("ERROR!!"+r.getCause)
          }
        println("end 1st foreach")
      }))
      println("End 2nd map")
    }))

  }

  def showExRes(exam_id:String) = erconnection.find(Json.obj("exam_id"->exam_id)).sort(Json.obj("question_id"->1)).cursor[Exam_Response].collect[List](20)


}
