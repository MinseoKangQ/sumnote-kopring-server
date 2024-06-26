package com.capston.sumnote.util.exception

import com.capston.sumnote.util.response.CustomApiResponse
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidationException(e: MethodArgumentNotValidException): ResponseEntity<CustomApiResponse<*>> {
        val errorMessage = e.bindingResult.allErrors.joinToString("; ") {
            it.defaultMessage ?: "Unknown error"
        }

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(CustomApiResponse.createFailWithoutData(HttpStatus.BAD_REQUEST.value(), errorMessage))
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(e: ConstraintViolationException): ResponseEntity<CustomApiResponse<*>> {
        val errorMessage = e.constraintViolations.joinToString("; ") {
            it.message
        }

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(CustomApiResponse.createFailWithoutData(HttpStatus.BAD_REQUEST.value(), errorMessage))
    }

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFoundException(e: EntityNotFoundException): ResponseEntity<CustomApiResponse<*>> {
        val message = e.message ?: "Not found" // e.message가 null -> "Not found"를 기본 메시지로 사용
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(CustomApiResponse.createFailWithoutData(HttpStatus.NOT_FOUND.value(), message))
    }

    @ExceptionHandler(EntityDuplicatedException::class)
    fun handleEntityDuplicatedException(e: EntityDuplicatedException): ResponseEntity<CustomApiResponse<*>> {
        val message = e.message ?: "Conflict" // e.message가 null -> "Conflict"를 기본 메시지로 사용
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(CustomApiResponse.createFailWithoutData(HttpStatus.CONFLICT.value(), message))
    }

    @ExceptionHandler(CustomValidationException::class)
    fun handleCustomValidationException(e: CustomValidationException): ResponseEntity<CustomApiResponse<*>> {
        val status = HttpStatus.valueOf(e.statusCode)
        val response = CustomApiResponse.createFailWithoutData(status.value(), e.message ?: "유효하지 않은 요청입니다.")
        return ResponseEntity(response, status)
    }

}