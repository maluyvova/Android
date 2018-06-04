/*package com.tubitv.tubitv.Networking
import io.restassured.RestAssured;
import io.restassured.RestAssured.given
import org.junit.Test;
/**
 * Created by vburian on 3/13/18.
 */
class NetworkingConfig{

     @Test
    fun some (){
        RestAssured.baseURI="https://uapi.adrise.tv"
         given().body("{\n" +
                 "  \"type\": \"email\",\n" +
                 "  \"device_id\": \"12345\",\n" +
                 "  \"platform\": \"android\",\n" +
                 "  \"credentials\": {\"email\":\"vburian@tubi.tv\",\"password\":\"tubitv\"}\n" +
                 "}").`when`().post("/user_device/login").then().assertThat().statusCode(200)
    }











}
*/