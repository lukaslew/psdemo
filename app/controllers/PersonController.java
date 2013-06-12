package controllers;

import models.Person;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.IOException;

public class PersonController extends Controller {
    private static ObjectMapper mapper = new ObjectMapper();

    public static Result getAll() throws IOException {
        return ok(mapper.valueToTree(Person.find.all()));
    }

    public static Result get(Integer id) throws IOException {
        Person person = Person.find.byId(id);
        Result result;

        if (person == null) {
            result = notFound();
        } else {
            ObjectNode json = Json.newObject();
            json.put("name", person.getName());
            json.put("gender", person.getGender().name());

            result = ok(json);
        }

        return result;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result create() throws IOException {
        JsonNode request = request().body().asJson();
        Person person = null;
        Result result = null;

        try {
            person = mapper.readValue(request, Person.class);
        } catch (IOException e) {
            result = internalServerError(e.getMessage());
        }

        if (person != null) {
            person.save();
            result = ok();
        }

        return result;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result update(Integer id) throws IOException {
        JsonNode request = request().body().asJson();
        Person person = Person.find.byId(id);
        Person update = null;
        Result result = null;

        if (person == null) {
            result = notFound();
        } else {
            try {
                update = mapper.readValue(request, Person.class);
            } catch (IOException e) {
                result = internalServerError(e.getMessage());
            }

            if (update != null) {
                person.setName(update.getName());
                person.setGender(update.getGender());
                person.update();
                result = ok();
            }
        }

        return result;
    }

    public static Result delete(Integer id) {
        Person person = Person.find.byId(id);
        Result result;

        if (person == null) {
            result = notFound();
        } else {
            person.delete();
            result = ok();
        }

        return result;
    }

}
