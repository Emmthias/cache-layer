package com.emmthias.cache.controller;

import io.swagger.annotations.Api;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.emmthias.cache.constants.Constants.POM_VERSION;

/**
 * Default Controller.
 */
@RestController
@Api("default controller")
public class DefaultController {

    @Value(POM_VERSION)
    private String codeVersion;

    @GetMapping(path = {"/_health"}, produces = {"text/plain"})
    public String health() {
        return "{\"message\":\"Welcome to Cache Layer System\"}";
    }

    /**
     * info endpoint.
     *
     * @return info related to project version.
     * @throws JSONException ex.
     */
    @GetMapping(path = {"/info"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public String info() throws JSONException {

        JSONObject response = new JSONObject();
        response.put("deployedVersion", codeVersion);

        return response.toString();
    }
}
