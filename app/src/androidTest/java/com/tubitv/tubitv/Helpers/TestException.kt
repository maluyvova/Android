package com.tubitv.tubitv.Helpers

import com.tubitv.tubitv.BaseTest
import com.tubitv.tubitv.deviceName

/**
 * Created by vburian on 6/5/18.
 */
class TestException(override var message: String) : Exception(message+ "on device: " + deviceName) {}

class TestExceptionWithError(message: String, io: Throwable) : Exception(message+"on device: " + deviceName, io)