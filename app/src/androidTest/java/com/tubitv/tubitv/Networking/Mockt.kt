package com.tubitv.tubitv.Networking

import com.squareup.okhttp.mockwebserver.MockResponse
import com.squareup.okhttp.mockwebserver.MockWebServer

/**
 * Created by vburian on 4/14/18.
 */
class Mockt {
    public fun mock(){

        var server = MockWebServer()
        server.enqueue(MockResponse().setResponseCode(200).setBody("\"total_count\": 1,\"more\": false,\"items\":" +
                " [{\"content_id\": 300842,\"content_type\": \"movie\",\"user_id\": 31547498,\"created_at\":" +
                " \"2018-04-15T02:39:06.958Z\",\"updated_at\": \"2018-04-15T02:39:06.958Z\",\"id\":" +
                " \"5ad2bb4a6e828653182ed48a\"}]"))
        server.start()
        val baseUrl = server.url("https://uapi.adrise.tv/user_device/queues?page_enabled=false&platform=android&device_id=0ace3e9b-9a57-435a-9e23-35461ea8b01e&app_id=tubitv&user_id=31547498")

      /* var mockedResponse= MockResponse()
        mockedResponse.setResponseCode(200)
        mockedResponse.equals(mockedResponse.setBody("\"total_count\": 1,\"more\": false,\"items\":" +
                " [{\"content_id\": 300842,\"content_type\": \"movie\",\"user_id\": 31547498,\"created_at\":" +
                " \"2018-04-15T02:39:06.958Z\",\"updated_at\": \"2018-04-15T02:39:06.958Z\",\"id\":" +
                " \"5ad2bb4a6e828653182ed48a\"}]"))

*/
    }}
