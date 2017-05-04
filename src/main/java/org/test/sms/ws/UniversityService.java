package org.test.sms.ws;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.test.sms.common.service.CachingService;
import org.test.sms.common.service.university.CourseService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/university")
public class UniversityService {

    private static final String TYPE_JSON = "application/json; charset=UTF-8";

    private Gson gson;

    private CachingService cachingService;

    private CourseService courseService;

    public UniversityService() {
        gson = new GsonBuilder().create();
    }

    @GET
    @Path("buildings")
    @Produces(TYPE_JSON)
    public String getBuildings() {
        return gson.toJson(cachingService.getBuildings());
    }

    @GET
    @Path("faculties")
    @Produces(TYPE_JSON)
    public String getFaculties() {
        return gson.toJson(cachingService.getFaculties(null));
    }

    @GET
    @Path("/courses/{facultyId}")
    @Produces(TYPE_JSON)
    public String getCourses(@SuppressWarnings("unused") @PathParam("facultyId") Long facultyId) {
        return gson.toJson(courseService.getList(null));
    }
}