package edu.northeastern.ccs.im.database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotSame;


public class UserDBTest {
    UserDB userDB;
    @BeforeEach
    public void setup(){
        userDB = new UserDB();
    }
    @Test
    public void testCreateUser(){
        userDB.createUser("srsanghavi","sanghavi.s@husky.neu.edu","12345678","Shashwat","Sanghavi");
    }

    @Test
    public void testGetAllUsers(){
        System.out.println(userDB.getUsers());
    }

    @Test
    public void testGetFilteredUsers(){
        System.out.println(userDB.getUsers("email","sanghavi.s@husky.neu.edu"));
    }

    @Test
    public void testGetFilteredUsersReturningEmptyList(){
        System.out.println(userDB.getUsers("email","sanghav.s@husky.neu.edu"));
    }

    @Test
    public void testGetFilteredUsersIllegalFilterParam(){
        System.out.println(userDB.getUsers("emailsd","sanghav.s@husky.neu.edu"));
    }

    @Test
    public void testAuthorized(){
        assertEquals(1,userDB.isAuthorized("sanghavi.s@husky.neu.edu", "12345678"));
    }

    @Test
    public void testNotAuthorized(){
        assertNotSame(1,userDB.isAuthorized("sanghavi.s@husky.neu.edu", "1234678"));
    }
}
