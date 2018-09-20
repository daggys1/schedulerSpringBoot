package us.ne.state.services.controller

import io.restassured.RestAssured
import io.restassured.config.HttpClientConfig
import io.restassured.config.RestAssuredConfig
import io.restassured.path.json.JsonPath
import io.restassured.response.Response
import org.junit.BeforeClass
import org.junit.Test

import static io.restassured.RestAssured.given

class SwapiPeople {
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
    void testPeopleCount() {
        Response re = given().config(config).get('/people')
        JsonPath jp = new JsonPath(re.asString())
        assert jp.get('count') == 87
    }

    @Test
    void testPeople_Luke() {
        Response re = given().config(config).get('/people')
        JsonPath jp = new JsonPath(re.asString())
        def res = jp.get('results')

        for (int i = 0; i < res.size(); i++) {
            def ress = res[i]
            assert ress.size() == 16
            for (int j = 0; j < ress.size(); j++) {
                if (ress.get('name').equalsIgnoreCase('luke skywalker')) {
                    assert ress.get('films').size == 5
                    assert ress.get('homeworld').equalsIgnoreCase('https://swapi.co/api/planets/1/')
                    assert ress.get('mass') == '77'
                    assert ress.get('hair_color').equals('blond')
                    assert ress.get('eye_color').equals('blue')
                    assert ress.get('height') == '172'
                    assert ress.get('species').size() == 1
                    assert ress.get('starships').size() == 2
                    assert ress.get('birth_year') == '19BBY'
                    assert ress.get('vehicles').size() == 2
                }

            }

        }
    }

}
