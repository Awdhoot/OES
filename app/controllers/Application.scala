package controllers

import com.google.inject.Inject
import play.api._
import play.api.libs.json.Json
import play.api.mvc._
import backend.ExamConnect
import backend.UserConnect
import backend.ExResConnect
import scala.concurrent.ExecutionContext.Implicits.global
import play.modules.reactivemongo.{ReactiveMongoApi, ReactiveMongoComponents, MongoController}

class Application @Inject()(val reactiveMongoApi: ReactiveMongoApi)extends Controller with MongoController with ReactiveMongoComponents {

  def index = Action
  {
    userObj.pr()
    Ok(views.html.index("Your new application is ready."))
  }

  protected val userObj = new UserConnect(reactiveMongoApi)
  protected val examObj = new ExamConnect(reactiveMongoApi)
  protected val exresObj = new ExResConnect(reactiveMongoApi)

  def showall = Action.async{ implicit req =>
    userObj.findall().map{listOfUsers=> Ok(Json.toJson(listOfUsers))}
  }

  def show(id:String) = Action.async{req=>
  userObj.findByID(id).map{listOfUsers => Ok(Json.toJson(listOfUsers))}

  }

  def exambyuserID(u_id:String) = Action.async{req =>
    examObj.findByUser(u_id).map{listOfExams => Ok(Json.toJson(listOfExams))}
  }
  def examResbyexamID(e_id:String)=Action{ req=>


    exresObj.addresbyExam_id(e_id)
    Ok("RESPONSE ADDED TO EXAM_RESPONSE TABLE")
  }
  def showExamRes(exam_id:String) = Action.async{req =>
  exresObj.showExRes(exam_id).map(listOfExamResponse => Ok(Json.toJson(listOfExamResponse)))
  }





}
