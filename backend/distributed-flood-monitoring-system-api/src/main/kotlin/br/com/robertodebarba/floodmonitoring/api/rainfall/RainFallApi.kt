package br.com.robertodebarba.floodmonitoring.api.rainfall

import br.com.robertodebarba.floodmonitoring.api.IllegalApiArgumentException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
class RainFallApi {

    @Autowired
    lateinit var service: RainFallService

    @CrossOrigin
    @RequestMapping("rainfall")
    fun getRainFalls(@RequestParam(required = false) stationName: String?,
                     @RequestParam(required = false) city: String?,
                     @RequestParam(required = false) federationUnit: String?,
                     pageable: Pageable): Page<RainFallDTO> {

        return if (stationName.isNullOrBlank() && city.isNullOrBlank() && federationUnit.isNullOrBlank()) {
            service.getRainFalls(pageable)

        } else if (!stationName.isNullOrBlank() && !city.isNullOrBlank() && !federationUnit.isNullOrBlank()) {
            service.getRainFallByStationNameAndCityAndFederationUnit(stationName!!, city!!, federationUnit!!, pageable)

        } else {
            throw IllegalApiArgumentException("stationName, city and/or federationUnit not informed")
        }

    }

}

