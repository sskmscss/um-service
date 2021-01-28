package com.tcs.service.edt

import com.tcs.service.edt.eventuate.Producer
import com.tcs.service.edt.model.PrepareECMR
import com.tcs.service.edt.service.RxBus
import org.json.JSONObject
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ConfigurableApplicationContext

@SpringBootApplication(scanBasePackages = ["com.tcs.service.edt", "com.tcs.integration.common"])

class EventDrivenTestApplication

fun main(args: Array<String>) {
	val ctx: ConfigurableApplicationContext = runApplication<EventDrivenTestApplication>(*args)
	// Listen for String events only
	RxBus.listen(JSONObject::class.java).subscribe {
		try {
			when (it.optString("type")) {
				"um" -> {
					val obj = JSONObject(it.optString("data"))
					ctx.getBean(Producer::class.java).create(PrepareECMR(obj.getString("shipmentId")))
				}
			}
		} catch(e: Exception) {
			println(e)
		}
	}
}
