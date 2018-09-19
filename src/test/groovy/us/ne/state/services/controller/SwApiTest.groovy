package us.ne.state.services.controller

import io.restassured.RestAssured
import io.restassured.config.HttpClientConfig
import io.restassured.config.RestAssuredConfig
import io.restassured.http.ContentType
import io.restassured.path.json.JsonPath
import io.restassured.response.Response
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test
import org.springframework.web.client.ResourceAccessException
import static com.jayway.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath
import static org.hamcrest.Matchers.equalTo

import static io.restassured.RestAssured.given

class SwApiTest {
    static private RestAssuredConfig config

    @BeforeClass
    static void init() {
        RestAssured.baseURI = 'https://swapi.co/api'
        config = RestAssured.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                .setParam("http.connection.timeout", 1000)
                .setParam("http.socket.timeout", 1000))
    }

    @Test
    void getLuke() throws Exception {
        try {
            given().config(config)
                    .get('/people/1').then()
                    .log()
                    .all()
                    .statusCode(200)
        } catch (SocketTimeoutException e) {
            println "SWAPI is DOWN $e.message"
            throw new ResourceAccessException(e.message)
        }

    }

    /**
     *{*     "name": "Luke Skywalker",
     *     "height": "172",
     *     "mass": "77",
     *     "hair_color": "blond",
     *     "skin_color": "fair",
     *     "eye_color": "blue",
     *     "birth_year": "19BBY",
     *     "gender": "male",
     *     "homeworld": "https://swapi.co/api/planets/1/",
     *     "films": [
     *         "https://swapi.co/api/films/2/",
     *         "https://swapi.co/api/films/6/",
     *         "https://swapi.co/api/films/3/",
     *         "https://swapi.co/api/films/1/",
     *         "https://swapi.co/api/films/7/"
     *     ],
     *     "species": [
     *         "https://swapi.co/api/species/1/"
     *     ],
     *     "vehicles": [
     *         "https://swapi.co/api/vehicles/14/",
     *         "https://swapi.co/api/vehicles/30/"
     *     ],
     *     "starships": [
     *         "https://swapi.co/api/starships/12/",
     *         "https://swapi.co/api/starships/22/"
     *     ],
     *     "created": "2014-12-09T13:50:51.644000Z",
     *     "edited": "2014-12-20T21:17:56.891000Z",
     *     "url": "https://swapi.co/api/people/1/"
     *}*/

    @Test
    void verifyLuke() {
        //behavior-driven development (BDD)
        Response response = given().get('/people/1')
        Assert.assertEquals(200, response.statusCode())
        println response.asString()


        JsonPath jsonPath = new JsonPath(response.asString())
        println jsonPath.get('url')

        Assert.assertEquals('Luke Skywalker', jsonPath.get('name'))
        Assert.assertEquals('172', jsonPath.get('height'))
        Assert.assertEquals('77', jsonPath.get('mass'))
        Assert.assertEquals('blond', jsonPath.get('hair_color'))
        Assert.assertEquals('fair', jsonPath.get('skin_color'))
        Assert.assertEquals('blue', jsonPath.get('eye_color'))
        Assert.assertEquals('19BBY', jsonPath.get('birth_year'))
        Assert.assertEquals('male', jsonPath.get('gender'))
        Assert.assertEquals('https://swapi.co/api/planets/1/', jsonPath.get('homeworld'))
        Assert.assertEquals(5, jsonPath.getList('films').size())
        Assert.assertEquals(1, jsonPath.getList('species').size())
        Assert.assertEquals(2, jsonPath.getList('vehicles').size())
        Assert.assertEquals(2, jsonPath.getList('starships').size())
    }


    @Test
    void testJsonOfLuke() throws Exception {
        given().get('/people/1')
        .then()
        .body(matchesJsonSchemaInClasspath('luke.json')).log().all(true)
        .body("name",equalTo('Luke Skywalker'))
    }

    @Test
    void oneANotFound(){
        given().get('/people/1a').then().log().all().statusCode(404).body("detail",equalTo('Not found')).contentType(ContentType.JSON)
    }

    /**
     * {
     *     "people": "https://swapi.co/api/people/",
     *     "planets": "https://swapi.co/api/planets/",
     *     "films": "https://swapi.co/api/films/",
     *     "species": "https://swapi.co/api/species/",
     *     "vehicles": "https://swapi.co/api/vehicles/",
     *     "starships": "https://swapi.co/api/starships/"
     * }
     */

    @Test
    void testSwapiApi(){
        given().get().then().statusCode(200).
                contentType(ContentType.JSON)
                .body(matchesJsonSchemaInClasspath('api.json'))
    }


    @Test
    void swapiApi(){
        Response response = given().get()
        JsonPath jsonPath = new JsonPath(response.asString())
        println response.body().asString()
        Assert.assertEquals('https://swapi.co/api/people/',jsonPath.get('people'))
        Assert.assertEquals('https://swapi.co/api/planets/',jsonPath.get('planets'))
        Assert.assertEquals('https://swapi.co/api/films/',jsonPath.get('films'))
        Assert.assertEquals('https://swapi.co/api/species/',jsonPath.get('species'))
        Assert.assertEquals('https://swapi.co/api/vehicles/',jsonPath.get('vehicles'))
        Assert.assertEquals('https://swapi.co/api/starships/',jsonPath.get('starships'))
    }
}
