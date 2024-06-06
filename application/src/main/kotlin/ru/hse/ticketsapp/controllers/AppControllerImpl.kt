package ru.hse.ticketsapp.controllers

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import ru.hse.ticketsapp.dao.OrderEntityDao
import ru.hse.ticketsapp.services.OrderService

@RestController
class AppControllerImpl(
    val orderEntityDao: OrderEntityDao,
    val orderService: OrderService,
) : AppController {
    @PostMapping("/app/buy")
    @ResponseStatus(HttpStatus.CREATED)
    override fun buyTicket(@RequestHeader token: String,
                           @RequestParam from: Long,
                           @RequestParam to: Long) {
        orderService.createOrder(token, from, to)
    }

    @GetMapping("/app/check")
    override fun checkOrderStatus(@RequestHeader token: String,
                                  @RequestParam orderId: Long) {
        orderService.checkOrderStatus(token, orderId)
    }
}