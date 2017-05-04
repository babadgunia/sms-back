package org.test.sms;

import com.google.gson.Gson;
import org.junit.Before;
import org.test.sms.common.entities.university.Building;
import org.test.sms.common.entities.university.Course;
import org.test.sms.common.entities.university.Faculty;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

public class UniversityServiceTest {

    private WebTarget target;

    private Gson gson;

    @Before
    public void init() {
        Client client = ClientBuilder.newClient();
        target = client.target("http://localhost:8181/sms-service/rest/university/");

        gson = new Gson();
    }

    public void testUniversityBuildings() {
        target = target.path("buildings");
        Response result = target.request(MediaType.APPLICATION_JSON_TYPE).get();
        assertEquals(200, result.getStatus());

        String json = target.request(MediaType.APPLICATION_JSON_TYPE).get(String.class);
        gson.fromJson(json, Building[].class);
    }

    public void testUniversityFaculties() {
        target = target.path("faculties");
        Response result = target.request(MediaType.APPLICATION_JSON_TYPE).get();
        assertEquals(200, result.getStatus());

        String json = target.request(MediaType.APPLICATION_JSON_TYPE).get(String.class);
        gson.fromJson(json, Faculty[].class);
    }

    public void testUniversityCourses() {
        target = target.path("courses/1000");
        Response result = target.request(MediaType.APPLICATION_JSON_TYPE).get();
        assertEquals(200, result.getStatus());

        String json = target.request(MediaType.APPLICATION_JSON_TYPE).get(String.class);
        gson.fromJson(json, Course[].class);
    }

}