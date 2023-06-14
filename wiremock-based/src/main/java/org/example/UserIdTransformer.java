package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.Response;
import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.example.Main.OBJECT_MAPPER;

public class UserIdTransformer extends ResponseTransformer {
//public class UserIdTransformer extends ResponseDefinitionTransformer {

//    @Override
//    public ResponseDefinition transform(Request request, ResponseDefinition responseDefinition, FileSource files, Parameters parameters) {
//        List<String> listOfSplit = Arrays.stream(reduceUrl(request.getUrl())).toList();
//        Map<String, String> pathParameters = listOfSplit.stream().collect(new MapCollector());
////
//////        JsonNode jsonBody = OBJECT_MAPPER.valueToTree(response.getBodyAsString());
//////        ((ObjectNode) jsonBody).put("id", pathParameters.get("users"));
////
//        User user = User.generateUser(Integer.parseInt(pathParameters.get("users")));
//        JsonNode body = OBJECT_MAPPER.valueToTree(user);
//
//        return new ResponseDefinitionBuilder()
//                .withHeader("Content-Type", "application/json")
//                .withStatus(200)
//                .withBody(body.toString())
//                .build();
//    }

    @SneakyThrows
    @Override
    public Response transform(Request request, Response response, FileSource files, Parameters parameters) {
        List<String> listOfSplit = Arrays.stream(reduceUrl(request.getUrl())).toList();
        Map<String, String> pathParameters = listOfSplit.stream().collect(new MapCollector());

        JsonNode jsonBody = OBJECT_MAPPER.readTree(response.getBodyAsString());
        ((ObjectNode) jsonBody).put("id", pathParameters.get("users"));

        return Response.Builder.like(response)
                .but()
                .body(jsonBody.toString())
                .build();
    }

    @Override
    public String getName() {
        return "user-id-transformer";
    }

    @Override
    public boolean applyGlobally() {
        return false;
    }

    public String[] reduceUrl(String url) {
        String[] splitResult = url.split("/");
        return (splitResult[0].equals("")) ? Arrays.copyOfRange(splitResult, 1, splitResult.length) : splitResult;
    }

}
