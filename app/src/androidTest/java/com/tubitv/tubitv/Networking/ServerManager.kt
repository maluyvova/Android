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
    private val listOfTitles= mutableListOf<Int>(432261,363094,294111,307864,310136,322944,316459,304525,300452,326245,199555,312933
           , 322955,300448,229885)
    private val idOfTitleInQueue= mutableListOf<String>()
    private val accesToken = mutableListOf<String>()
    private val idOfUser=mutableListOf<Int>()
    public fun setup() {
        FuelManager.instance.basePath="https://uapi.adrise.tv/user_device"
          //FuelManager.instance.baseParams= listOf("type" to "email", "device_id" to "12345", "platform" to "android","credentials" to "{\"email\":\"vburian@tubi.tv\",\"password\":\"tubitv\"}")

    }
   // @Test
  /*  fun deleteQueue (){
        val token:String=getAccesToken()
        getQueueid(token)
        val idOfMovies=getQueueid(token)
        if(!idOfMovies.isEmpty()){
        deleteFormQueue(token,idOfMovies)}*/
   // }


    fun addToQueueTest(){
      getAccesTokenAndId()
        for (i in 0..listOfTitles.size-1){
            addToQueue(listOfTitles.get(i))
        }
    }



    fun deleteFromQueueTest(){
        getAccesTokenAndId()
        getQueueid()
        for(i in 0..idOfTitleInQueue.size-1)
            deleteFormQueue(idOfTitleInQueue.get(i))

    }

    private fun  getAccesTokenAndId() {
            val formData = listOf("type" to "email", "device_id" to "12345", "platform" to "android", "credentials" to "{\"email\":\"testingnetwork@gmail.com\",\"password\":\"tubitvtubitv\"}")
            val (request, response, result) = Fuel.upload("https://uapi.adrise.tv/user_device/login", parameters = formData).dataParts { request, url -> listOf<DataPart>() }.responseJson()
            result.fold(success = { json ->
                val token: String = json.obj().getString("access_token")
                val id : Int =json.obj().getInt("user_id")
                accesToken.add(0,token)
                idOfUser.add(0,id)
            }, failure = { error ->
                Assert.fail()
            })

        }

    private fun addToQueue(idOfTitle:Int){
        FuelManager.instance.baseHeaders= mapOf("Authorization" to accesToken.get(0))
        val url="https://uapi.adrise.tv/user_device/queues"
        val json=listOf("user_id" to idOfUser.get(0).toString(),"content_id" to idOfTitle,"content_type" to "movie")
        FuelManager.instance.baseHeaders= mapOf("Authorization" to accesToken.get(0))
        val (request, response, result)=Fuel.upload(url,parameters = json).dataParts{request, url -> listOf<DataPart>()}.responseJson()
        result.fold(success = {json ->
            val token :String=json.obj().getString("content_type")
        },failure = {
            error->Assert.fail()
        })
    }


    private fun deleteFormQueue(id:String){
        FuelManager.instance.baseHeaders= mapOf("Authorization" to accesToken.get(0))
        val url ="https://uapi.adrise.tv/user_device/queues/$id"
        val(request,response,result)=url.httpDelete().responseJson()
        result.fold(success = {},failure = {Assert.fail()})

    }

    private fun getQueueid(){
        FuelManager.instance.baseHeaders= mapOf("Authorization" to accesToken.get(0))
        val url="https://uapi.adrise.tv/user_device/queues"
        val(request,response,result)=url.httpGet().responseJson()
        result.fold(success = {json ->
        val items=json.obj().getJSONArray("items")
           for (i in 0..(items.length()-1)){
               val titles=items.getJSONObject(i)
               val idOfMovie=titles.getString("id")
               idOfTitleInQueue.add(i,idOfMovie.toString())

           }
        }, failure = {
            error ->
            Assert.fail()
        } )

    }
}






