package edu.northeastern.ccs.im;

import edu.northeastern.ccs.im.api.Route;
import org.junit.jupiter.api.Test;

public class RouteTest {

    @Test
    public void testGetConversations(){
        System.out.println(Route.getResponseGet("getConversations/", "{user_id:1}"));
    }

    @Test
    public void testCreateUserDuplicateUser(){
        System.out.println(Route.getResponsePost("registerUser/","{first_name:test;last_name:user;username:testUser;email:test@prattle.com;password:'12345678'}"));
    }

    @Test
    public void testGetUserGroups(){
        System.out.println(Route.getResponseGet("getGroups/","{user_id:1}"));
    }
}
