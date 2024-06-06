package ru.hse.ticketsauth.controllers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.hse.ticketsauth.controllers.entities.InfoEntity
import ru.hse.ticketsauth.mapper.InfoEntityMapper
import ru.hse.ticketsauth.services.AuthService
import ru.hse.ticketsauth.services.TokenService

@RestController
class AuthControllerImpl (
    val authService: AuthService,
    val tokenService: TokenService,
    val infoEntityMapper: InfoEntityMapper
) : AuthController {

    @PostMapping("/auth/create")
    override fun createUser(@RequestParam userName: String,
                            @RequestParam email: String,
                            @RequestParam password: String) : ResponseEntity<String> {
        authService.createUser(userName, email, password)
        return ResponseEntity.status(HttpStatus.CREATED).body("Registration was successful.")
    }

    @GetMapping("/auth/login")
    override fun logIn(@RequestParam email: String,
                       @RequestParam password: String): String {
        val user = authService.authUser(email, password)
        val token = tokenService.getTokenByUserId(user.id)
        return token
    }

    @GetMapping("/info/{token}")
    override fun getInfo(@PathVariable token: String) : InfoEntity {
        val info = authService.getInfo(token)
        return infoEntityMapper.infoEntityServiceToInfoEntityController(info)
    }
}