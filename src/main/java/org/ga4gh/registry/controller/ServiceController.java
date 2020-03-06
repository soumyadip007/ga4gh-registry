package org.ga4gh.registry.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.model.ServiceType;
import org.ga4gh.registry.util.HibernateQuerier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/services")
public class ServiceController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getServices() {

        String output = null;
        try {
            String queryString = 
            "select i from Implementation i "
            + "JOIN FETCH i.standardVersion "
            + "JOIN FETCH i.implementationCategory "
            + "JOIN FETCH i.organization";
            HibernateQuerier<Implementation> querier =
                new HibernateQuerier<>(Implementation.class, queryString);
            List<Implementation> implementations = querier.query();
            ObjectMapper mapper = new ObjectMapper();
            output = mapper.writeValueAsString(implementations);
        } catch (IOException e) {
            System.out.println("Could not serialize object as JSON " + e);
        }
        return output;
    }

    @GetMapping(path = "/{serviceId}",
                produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getServiceById(@PathVariable("serviceId") String serviceId) {
        String output = null;

        try {
            String queryString = 
                "select i from Implementation i "
                + "JOIN FETCH i.standardVersion "
                + "JOIN FETCH i.implementationCategory "
                + "JOIN FETCH i.organization "
                + "WHERE i.id='" + serviceId + "'";
            HibernateQuerier<Implementation> querier =
                new HibernateQuerier<>(Implementation.class, queryString);
            List<Implementation> implementations = querier.query();
            ObjectMapper mapper = new ObjectMapper();
            output = mapper.writeValueAsString(implementations.get(0));
        } catch (IOException e) {
            System.out.println("Could not serialize object as JSON " + e);
        }
        return output;
    }

    @GetMapping(path = "/types",
                produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getServiceTypes() {
        String output = null;
        
        try {
            String queryString = 
                "select i from Implementation i "
                + "JOIN FETCH i.standardVersion "
                + "JOIN FETCH i.implementationCategory "
                + "JOIN FETCH i.organization "
                + "WHERE i.implementationCategory.category='APIService'";
            HibernateQuerier<Implementation> querier =
                new HibernateQuerier<>(Implementation.class, queryString);
            List<Implementation> implementations = querier.query();
            Set<ServiceType> serviceTypes = new HashSet<>();
            for (Implementation implementation: implementations) {
                serviceTypes.add(implementation.getServiceType());
            }
            ObjectMapper mapper = new ObjectMapper();
            output = mapper.writeValueAsString(serviceTypes);
        } catch (IOException e) {
            System.out.println("Could not serialize object as JSON " + e);
        }
        return output;
    }
}