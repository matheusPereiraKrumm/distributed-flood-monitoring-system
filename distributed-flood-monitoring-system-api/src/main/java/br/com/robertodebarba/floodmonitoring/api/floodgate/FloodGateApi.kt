package br.com.robertodebarba.floodmonitoring.api.floodgate

import br.com.robertodebarba.floodmonitoring.core.Dam
import br.com.robertodebarba.floodmonitoring.core.FloodGate
import br.com.robertodebarba.floodmonitoring.core.database.MongoDatabase
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("floodgate")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

class FloodGateApi {

    @GET
    fun getFloodGates(@QueryParam("name") name: String?, @QueryParam("city") city: String?, @QueryParam("federationunit") federationUnit: String?): Response {
        try {
            if (name.isNullOrBlank() && city.isNullOrBlank() && federationUnit.isNullOrBlank()){
                val floodgates = MongoDatabase.instance.createQuery(FloodGate::class.java).asList()
                return Response.ok().entity(floodgates).build()
            } else if (!name.isNullOrBlank() && !city.isNullOrBlank() && !federationUnit.isNullOrBlank()) {
                val floodgates = MongoDatabase.instance.createQuery(FloodGate::class.java)
                        .field(Dam::name.name).equal(name)
                        .field(Dam::city.name).equal(city)
                        .field(Dam::federationUnit.name).equal(federationUnit)
                        .asList()
                return Response.ok().entity(floodgates).build()
            } else {
                return Response.status(Response.Status.BAD_REQUEST).build()
            }

        } catch (e: Exception) {
            return Response.serverError().entity(e).build()
        }
    }

}