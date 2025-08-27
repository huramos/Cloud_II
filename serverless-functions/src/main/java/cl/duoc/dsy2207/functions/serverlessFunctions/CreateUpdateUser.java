package cl.duoc.dsy2207.functions.serverlessFunctions;

import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.logging.Level;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class CreateUpdateUser {
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Cambiar private a public para que otras clases puedan acceder
    public static final Map<String, JsonNode> userDB = new ConcurrentHashMap<>();

    @FunctionName("CreateUpdateUser")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.POST, HttpMethod.PUT}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<String> request,
            final ExecutionContext context) {
        
        context.getLogger().log(Level.INFO, "Java HTTP trigger processed a request for CreateUpdateUser.");

        String body = request.getBody();

        if (body == null || body.isEmpty()) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body("{\"message\": \"No data provided. User creation/update failed.\"}")
                    .header("Content-Type", "application/json")
                    .build();
        }

        try {
            JsonNode jsonNode = objectMapper.readTree(body);

            String idFromQuery = request.getQueryParameters().get("id");
            String idFromBody = jsonNode.has("id") ? jsonNode.get("id").asText() : null;

            String id = idFromQuery != null ? idFromQuery : idFromBody;
            if (id == null || id.isEmpty()) {
                return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                        .body("{\"message\": \"User ID is required either in query string or body.\"}")
                        .header("Content-Type", "application/json")
                        .build();
            }

            String name = jsonNode.has("name") ? jsonNode.get("name").asText() : null;
            if (name == null || name.isEmpty()) {
                return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                        .body("{\"message\": \"User name is required.\"}")
                        .header("Content-Type", "application/json")
                        .build();
            }

            // Guardamos o actualizamos el usuario (simulación)
            userDB.put(id, jsonNode);

            String action = HttpMethod.POST.equals(request.getHttpMethod()) ? "created" : "updated";
            String responseMessage = String.format("User %s (ID: %s) %s successfully", name, id, action);

            return request.createResponseBuilder(HttpStatus.OK)
                    .header("Content-Type", "application/json")
                    .body("{\"message\": \"" + responseMessage + "\"}")
                    .build();

        } catch (Exception e) {
            context.getLogger().log(Level.SEVERE, "Error processing request: " + e.getMessage());
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Content-Type", "application/json")
                    .body("{\"message\": \"Error processing request: " + e.getMessage() + "\"}")
                    .build();
        }
    }
}
