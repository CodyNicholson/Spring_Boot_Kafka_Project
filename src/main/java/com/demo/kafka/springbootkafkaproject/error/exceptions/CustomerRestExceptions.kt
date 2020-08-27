package com.demo.kafka.springbootkafkaproject.error.exceptions

class BadRequestException(errorMessage: String?, cause: Throwable?) : Exception(errorMessage, cause)

class UnauthorizedException(errorMessage: String?, cause: Throwable?) : Exception(errorMessage, cause)

class ForbiddenException(errorMessage: String?, cause: Throwable?) : Exception(errorMessage, cause)

class NotFoundException(errorMessage: String?, cause: Throwable?) : Exception(errorMessage, cause)

class ConflictException(errorMessage: String?, cause: Throwable?) : Exception(errorMessage, cause)

class ServiceUnavailableException(errorMessage: String?, cause: Throwable?) : Exception(errorMessage, cause)

class InternalServerErrorException(errorMessage: String?, cause: Throwable?) : Exception(errorMessage, cause)
