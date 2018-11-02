package com.tubitv.tubitv.Helpers

/**
 * Created by vburian on 6/5/18.
 */
class TestException(override var message: String) : Exception(message) {}
class TextExceptionWithError(message: String,io:Throwable):Exception(message,io)