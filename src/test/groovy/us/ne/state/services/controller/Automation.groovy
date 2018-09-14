package us.ne.state.services.controller

import io.restassured.RestAssured
import io.restassured.config.HttpClientConfig
import io.restassured.config.RestAssuredConfig
import io.restassured.http.Method
import io.restassured.response.Response
import io.restassured.specification.RequestSpecification
import org.apache.http.params.CoreConnectionPNames
import org.junit.BeforeClass
import org.junit.Test
class Automation {


    static private RestAssuredConfig config

    @BeforeClass
    static void init() {
        RestAssured.baseURI = 'http://192.168.1.94:9090/api/downloadpdfg/court_report'
        config = RestAssured.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                .setParam(CoreConnectionPNames.CONNECTION_TIMEOUT, 1000)
                .setParam(CoreConnectionPNames.SO_TIMEOUT, 1000));
    }

    @Test
    void getReport() throws Exception{
        RequestSpecification httpRequest = RestAssured.given().header("Authorization", "Basic eHh4QW1pdCw6").config(config)
        Response response = httpRequest.request(Method.GET, "/individualId/77")
        if(response.statusCode() == 200){
            println('Aop reports is live')
        } else {
            println(response.getBody().asString())
            throw new Exception("Aop reports id dead!!")
        }
    }

    @Test
    void getReport1() throws Exception{
        RequestSpecification httpRequest = RestAssured.given().header("Authorization", "Basic eHh4QW1pdCw6").config(config)
        Response response = httpRequest.request(Method.GET, "/individualId/77")
        if(response.statusCode() == 200){
            println('Aop reports is live')
        } else {
            println(response.getBody().asString())
            throw new Exception("Aop reports id dead!!")
        }
    }

    @Test
    void getReports() throws Exception{
        RequestSpecification httpRequest = RestAssured.given().header("Authorization", "Basic eHh4QW1pdCw6").config(config)
        Response response = httpRequest.request(Method.GET, "/scheduleInstanceId/21368")
        if(response.statusCode() == 502){
            println('DB is down')
            println(response.getBody().asString())
        } else {
            println(response.getBody().asString())
            throw new Exception("Error generating report")
        }
    }


}
