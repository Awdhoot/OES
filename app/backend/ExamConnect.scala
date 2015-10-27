package backend


import models.Exam.examReads

import com.google.inject.Inject
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json.collection.JSONCollection
import play.api.libs.json.Json
import play.modules.reactivemongo.json._ //No Json serializer as JsObject found for type play.api.libs.json.JsObject
import scala.concurrent.ExecutionContext.Implicits.global

import models.Exam




class ExamConnect @Inject() (val reactiveMongoApi: ReactiveMongoApi)
{
  protected  def examcollection = reactiveMongoApi.db.collection[JSONCollection]("Exam")

  def findByUser(u_id:String) ={
    examcollection.find(Json.obj("user_id" ->u_id)).sort(Json.obj("exam_id"->1)).cursor[Exam].collect[List]()

  }
}
