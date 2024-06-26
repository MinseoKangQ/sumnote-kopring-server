package com.capston.sumnote.test

import com.capston.sumnote.util.response.CustomApiResponse
import com.capston.sumnote.util.security.jwt.JwtTokenProvider
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/test")
class TestController(private val jwtTokenProvider: JwtTokenProvider) {

    @GetMapping
    fun simpleTest(): String {
        return "test success (add webhook)"
    }

    @GetMapping("/authenticated")
    fun testAuthenticated(): CustomApiResponse<String> {
        return CustomApiResponse.createSuccess(200, "OK", "Request successful")
    }

    @GetMapping("/email")
    fun getUserInfo(request: HttpServletRequest): ResponseEntity<String> {
        val token = jwtTokenProvider.resolveToken(request)
        return if (token != null && jwtTokenProvider.validateToken(token)) {
            val email = jwtTokenProvider.getEmailFromToken(token)
            ResponseEntity.ok("Email retrieved: $email")
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token")
        }
    }

}