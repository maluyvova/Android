package com.tubitv.tubitv.Helpers

import com.tubitv.tubitv.BaseTest

/**
 * Created by vburian on 6/5/18.
 */
class TestException(override var message: String) : Exception(message+ "on device: " + BaseTest().deviceName) {}

class TestExceptionWithError(message: String, io: Throwable) : Exception(message+"on device: " + BaseTest().deviceName, io)