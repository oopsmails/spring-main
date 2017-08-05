package com.oopsmails.controller;

import com.oopsmails.annotation.CustomTestAnnotation;
import com.oopsmails.model.User;
import com.oopsmails.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

//@RestController
@CustomTestAnnotation("/users")
@RequestMapping("/users")
public class UserController {

    private final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    // =========================================== Get All Users ==========================================

    /**
     * Cannot use following ...
     * public ResponseEntity<List<User>> getAll(@RequestParam(value = "id", required = false) Optional<Integer>[] ids) {
     *
     * First, in this multiple ids case, even Optional<Integer>[] will not work as "required = false",
     * i.e, get all, still expecting request param id to be passed in.
     *
     * Second, using Optional<Integer>[] will take id = "a" without error and pass it down the road,
     * making weird behavior in Service.
     *
     * @param ids
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
//    @CustomTestAnnotation(method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAll(@RequestParam(value = "id", required = false) Integer[] ids) {
        LOG.info("getting all users");
        List<User> users = new ArrayList<>();

        if (ids != null && ids.length > 0) {
            users = userService.getByIds(Arrays.asList(ids));
//            users = userService.getByIds(getIdList(ids));
        } else {
            users = userService.getAll();
        }

        if (users == null || users.isEmpty()) {
            LOG.info("no users found");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * If id = "a" is passed in, the get a List<Integer> containing "a" inside!
     *
     * @param ids
     * @return
     */
    private static List<Integer> getIdList(Optional<Integer>[] ids) {
        List<Integer> retval = new ArrayList<>();
        Arrays.stream(ids).forEach( id -> {
            if (id.isPresent()) {
                retval.add(id.get());
            }
        });
        return retval;
    }

    // =========================================== Get User By ID =========================================

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
//    @CustomTestAnnotation(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<User> get(@PathVariable("id") int id) {
        LOG.info("getting user with id: {}", id);
        User user = userService.findById(id);

        if (user == null) {
            LOG.info("user with id {} not found", id);
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    // =========================================== Create New User ========================================

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> create(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        LOG.info("creating new user: {}", user);

        if (userService.exists(user)) {
            LOG.info("a user with name " + user.getUsername() + " already exists");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        userService.create(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    // =========================================== Update Existing User ===================================

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> update(@PathVariable int id, @RequestBody User user) {
        LOG.info("updating user: {}", user);
        User currentUser = userService.findById(id);

        if (currentUser == null) {
            LOG.info("User with id {} not found", id);
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }

        currentUser.setId(user.getId());
        currentUser.setUsername(user.getUsername());

        userService.update(user);
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }

    // =========================================== Delete User ============================================

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        LOG.info("deleting user with id: {}", id);
        User user = userService.findById(id);

        if (user == null) {
            LOG.info("Unable to delete. User with id {} not found", id);
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        userService.delete(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
