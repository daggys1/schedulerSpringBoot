package com.yoga.services.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class TestService {

public static String NAME = "Daggula Yogananda";

    public static  void main(String[] args) {
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        String url = "https://swapi.co/api/people/1/";
        ResponseEntity<String> res = rt.exchange(url, HttpMethod.GET, entity, String.class);
        System.out.println(res);

       // return swapi;
    }



    public void swapName(){
        System.out.println(NAME);
        // TODO swap the name put a comma between and print it to console
    }

    /*public static  void main(String[] args) {
        String name="Daggula Yogananda";
        System.out.println("Before:\n"+name);
        String firstName=name.substring(0,name.indexOf(" "));
        String lastName=name.substring(name.indexOf(" "));
        String swapName=lastName+" , "+firstName;
        System.out.println("After:\n"+swapName);

    }*/
    }



