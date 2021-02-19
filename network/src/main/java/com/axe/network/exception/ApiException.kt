package com.axe.network.exception

/**
 * 异常处理
 */
class ApiException : Exception {
    var code: Int = 0
    var displayMessage: String

    constructor(code: Int, displayMessage: String) {
        this.code = code
        this.displayMessage = displayMessage
    }

    constructor(code: Int, message: String?, displayMessage: String) : super(message) {
        this.code = code
        this.displayMessage = displayMessage
    }
}