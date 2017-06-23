package br.com.robertodebarba.floodmonitoring.api.rainfall

import br.com.robertodebarba.floodmonitoring.core.RainFall
import br.com.robertodebarba.floodmonitoring.core.database.MongoDatabase
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("rainfall")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

class RainFallApi {

    @GET
    fun getRainFalls(@QueryParam("stationname") stationName: String?, @QueryParam("city") city: String?, @QueryParam("federationunit") federationUnit: String?): Response {
        try {
            if (stationName.isNullOrBlank() && city.isNullOrBlank() && federationUnit.isNullOrBlank()){
                val rainfalls = MongoDatabase.instance.createQuery(RainFall::class.java).asList()
                return Response.ok().entity(rainfalls).build()
            } else if (!stationName.isNullOrBlank() && !city.isNullOrBlank() && !federationUnit.isNullOrBlank()) {
                val rainfalls = MongoDatabase.instance.createQuery(RainFall::class.java)
                        .field(RainFall::stationName.name).equal(stationName)
                        .field(RainFall::city.name).equal(city)
                        .field(RainFall::federationUnit.name).equal(federationUnit)
                        .asList()
                return Response.ok().entity(rainfalls).build()
            } else {
                return Response.status(Response.Status.BAD_REQUEST).build()
            }

        } catch (e: Exception) {
            return Response.serverError().entity(e).build()
        }
    }

}