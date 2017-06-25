package br.com.robertodebarba.floodmonitoring.api.riverlevel

import br.com.robertodebarba.floodmonitoring.core.RiverLevel
import br.com.robertodebarba.floodmonitoring.core.database.MongoDatabase
import org.mongodb.morphia.query.FindOptions
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("riverlevel")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class RiverLevelApi {

    @GET
    fun getRiverLevels(@QueryParam("river") river: String?, @QueryParam("city") city: String?, @QueryParam("federationunit") federationUnit: String?): Response {
        try {
            val riverLevelId = RiverLevel::id.name
            val findOptions = FindOptions().limit(10)

            if (river.isNullOrBlank() && city.isNullOrBlank() && federationUnit.isNullOrBlank()){
                val riverLevels = MongoDatabase.instance.createQuery(RiverLevel::class.java)
                        .order("-$riverLevelId")
                        .asList(findOptions)
                return Response.ok().entity(riverLevels).build()
            } else if (!river.isNullOrBlank() && !city.isNullOrBlank() && !federationUnit.isNullOrBlank()) {
                val riverLevels = MongoDatabase.instance.createQuery(RiverLevel::class.java)
                        .field(RiverLevel::river.name).equal(river)
                        .field(RiverLevel::city.name).equal(city)
                        .field(RiverLevel::federationUnit.name).equal(federationUnit)
                        .order("-$riverLevelId")
                        .asList(findOptions)
                return Response.ok().entity(riverLevels).build()
            } else {
                return Response.status(Response.Status.BAD_REQUEST).build()
            }

        } catch (e: Exception) {
            return Response.serverError().entity(e).build()
        }
    }

}
