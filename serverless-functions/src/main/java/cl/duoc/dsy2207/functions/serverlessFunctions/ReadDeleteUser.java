package cl.duoc.dsy2207.functions.serverlessFunctions;

import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

import java.util.logging.Level;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ReadDeleteUser {

    // Compartimos la base simulada
    private static final Map<String, JsonNode> userDB = CreateUpdateUser.userDB;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @FunctionName("ReadDeleteUser")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET, HttpMethod.DELETE}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<String> request,
            final ExecutionContext context) {

        context.getLogger().log(Level.INFO, "Java HTTP trigger processed a request for ReadDeleteUser.");

        String id = request.getQueryParameters().get("id");

        if (id == null || id.isEmpty()) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .header("Content-Type", "application/json")
                    .body("{\"message\": \"No ID provided. Operation failed.\"}")
                    .build();
        }

        try {
            JsonNode user = userDB.get(id);

            if (HttpMethod.GET.equals(request.getHttpMethod())) {
                if (user == null) {
                    return request.createResponseBuilder(HttpStatus.NOT_FOUND)
                            .header("Content-Type", "application/json")
                            .body("{\"message\": \"User with ID: " + id + " not found.\"}")
                            .build();
                }

                // Devuelve el usuario completo como JSON
                return request.createResponseBuilder(HttpStatus.OK)
                        .header("Content-Type", "application/json")
                        .body(user.toPrettyString())
                        .build();

            } else if (HttpMethod.DELETE.equals(request.getHttpMethod())) {
                if (user == null) {
                    return request.createResponseBuilder(HttpStatus.NOT_FOUND)
                            .header("Content-Type", "application/json")
                            .body("{\"message\": \"User with ID: " + id + " not found. Cannot delete.\"}")
                            .build();
                }

                String userName = user.has("name") ? user.get("name").asText() : "Unknown";

                userDB.remove(id);

                return request.createResponseBuilder(HttpStatus.OK)
                        .header("Content-Type", "application/json")
                        .body("{\"message\": \"User '" + userName + "' (ID: " + id + ") deleted successfully\"}")
                        .build();
            }

            // Método no soportado
            return request.createResponseBuilder(HttpStatus.METHOD_NOT_ALLOWED)
                    .header("Content-Type", "application/json")
                    .body("{\"message\": \"Unsupported HTTP method\"}")
                    .build();

        } catch (Exception e) {
            context.getLogger().log(Level.SEVERE, "Error: " + e.getMessage());
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Content-Type", "application/json")
                    .body("{\"message\": \"Error processing request: " + e.getMessage() + "\"}")
                    .build();
        }
    }
}
