package us.ne.state.services.controller

import io.restassured.RestAssured
import io.restassured.http.Method
import io.restassured.response.Response
import io.restassured.specification.RequestSpecification
import org.junit.Test

class Automation {
    @Test
    void getReport() throws Exception{

        RestAssured.baseURI = "http://192.168.1.94:9090/api/downloadpdfg/court_report/individualId"
        RequestSpecification httpRequest = RestAssured.given().header("Authorization", "Basic eHh4QW1pdCw6")

        Response response = httpRequest.request(Method.GET, "/77")
        if(response.statusCode() == 200){
            println('Aop reports is live')
        } else {
            println(response.getBody().asString())
            throw new Exception("Aop reports id dead!!")
        }
    }

    @Test
    void getReport1() throws Exception{

        RestAssured.baseURI = "http://192.168.1.94:9090/api/downloadpdfg/court_report/individualId"
        RequestSpecification httpRequest = RestAssured.given().header("Authorization", "Basic eHh4QW1pdCw6")

        Response response = httpRequest.request(Method.GET, "/77")
        if(response.statusCode() == 200){
            println('Aop reports is live')
        } else {
            println(response.getBody().asString())
            throw new Exception("Aop reports id dead!!")
        }
    }

    @Test
    void getReports() throws Exception{

        RestAssured.baseURI = "http://192.168.1.94:9090/api/downloadpdfg/court_report/scheduleInstanceId"
        RequestSpecification httpRequest = RestAssured.given().header("Authorization", "Basic eHh4QW1pdCw6")

        Response response = httpRequest.request(Method.GET, "/21368")
        if(response.statusCode() == 200){
            println('Aop reports is live')
            println(response.getBody().asString())
        } else {
            println(response.getBody().asString())
            throw new Exception("Error generating report")
        }
    }
}
