package com.tubitv.tubitv.Networking
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.core.DataPart
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpDelete
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import junit.framework.Assert
import org.junit.Before
import org.junit.Test

/**
 * Created by vburian on 3/13/18.
 */
class ServerManager{


    public fun setup() {
        FuelManager.instance.basePath="https://uapi.adrise.tv/user_device"
          //FuelManager.instance.baseParams= listOf("type" to "email", "device_id" to "12345", "platform" to "android","credentials" to "{\"email\":\"vburian@tubi.tv\",\"password\":\"tubitv\"}")

    }
    @Test
    fun deleteQueue (){
        val token:String=getAccesToken()
        getQueueid(token)
        val idOfMovies=getQueueid(token)
        if(!idOfMovies.isEmpty()){
        deleteFormQueue(token,idOfMovies)}
    }

 fun  getAccesToken():String {
     val token=""
            val formData = listOf("type" to "email", "device_id" to "12345", "platform" to "android", "credentials" to "{\"email\":\"tubitv@tubitv.tubitv\",\"password\":\"tubitv\"}")
            val (request, response, result) = Fuel.upload("https://uapi.adrise.tv/user_device/login", parameters = formData).dataParts { request, url -> listOf<DataPart>() }.responseJson()
            result.fold(success = { json ->
                val token: String = json.obj().getString("access_token")
             return token
            }, failure = { error ->
                Assert.fail()
            })
     return token
        }

    fun deleteFormQueue(accessToken: String,id:String){
        FuelManager.instance.baseHeaders= mapOf("Authorization" to "$accessToken")
        val url ="https://uapi.adrise.tv/user_device/queues/$id"
        val(request,response,result)=url.httpDelete().responseJson()
        result.fold(success = {},failure = {Assert.fail()})

    }

    fun getQueueid(accessToken:String):String{

        FuelManager.instance.baseHeaders= mapOf("Authorization" to "$accessToken")
        val idOfMovie="";
        val url="https://uapi.adrise.tv/user_device/queues"
        val(request,response,result)=url.httpGet().responseJson()
        result.fold(success = {json ->
        val items=json.obj().getJSONArray("items")
           for (i in 0..(items.length()-1)){
               val titles=items.getJSONObject(i)
               val idOfMovie=titles.getString("id")
               return idOfMovie
           }
        }, failure = {
            error ->
            Assert.fail()
        } )
        return idOfMovie
    }
}






