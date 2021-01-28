package com.tcs.service.edt.controller.v1

import com.tcs.service.edt.integration.UM
import org.springframework.web.bind.annotation.*

@RestController
class Controller(private val um: UM) {
    @RequestMapping(value = ["/api/postEvents/{type}"], method = [RequestMethod.POST])
    fun getMessage(@PathVariable type: String, @RequestBody payload: String) {
        when(type) {
            "um"    ->  um.publishMessage(payload)
            else    ->  "No publisher found"
        }
    }

    // Not required. In case of issues we can use this endpoint
    @RequestMapping(value = ["/api/getEvents/{type}"], method = [RequestMethod.GET])
    @ResponseBody
    fun getMessage(@PathVariable type: String): String? {
        return when(type) {
            "um"    ->  um.subscribeMessage(type)
            else    ->  "No subscriber found"
        }
    }
}
