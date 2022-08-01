package br.com.pagseguro.basicapp.controller;

import br.com.pagseguro.basicapp.client.ActuatorClient;
import br.com.pagseguro.basicapp.model.postgres.PostgresModel;
import br.com.pagseguro.basicapp.repository.postgres.PostgresRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/example")
public class ExampleController {

    private static final Logger LOG = LoggerFactory.getLogger(ExampleController.class);

    @Autowired
    ActuatorClient actuatorClient;

    @Autowired
    PostgresRepository repo;

    @RequestMapping(method = RequestMethod.GET, path = "/health")
    public Object getHealth(@RequestParam(required = false) final Map<String, String> params) {
        params.entrySet()
                .forEach(it -> LOG.info("ParamName: {}, ParamValue: {}", it.getKey(), it.getValue()));

        final HashMap<String, String> response = new HashMap<>();
        response.put("health", actuatorClient.getHealth());
        return response;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/list")
    public ResponseEntity<List<PostgresModel>> listAll() {
        return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
    }

}
