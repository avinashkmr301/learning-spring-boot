package com.yourname.learningspringboot.resource;

import com.yourname.learningspringboot.model.User;
import com.yourname.learningspringboot.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Validated
@Component
@Path("/api/v1/users")
public class UserResourceResteasy {

  private UserService userService;

  @Autowired
  public UserResourceResteasy(UserService userService) {
    this.userService = userService;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<User> fetchUsers(@QueryParam("gender") String gender) {
    return userService.getAllUsers(Optional.ofNullable(gender));
  }


  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("{userUid}")
  public User fetchUser(@PathParam("userUid") UUID userUid) {
    return userService
        .getUser(userUid)
        .orElseThrow(() -> new NotFoundException("user " + userUid + " not found"));
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public void insertNewUser(@Valid User user) {
    userService.insertUser(user);
  }

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public void updateUser(User user) {
    userService.updateUser(user);
  }

  @DELETE
  @Produces(MediaType.APPLICATION_JSON)
  @Path("{userUid}")
  public void deleteUser(@PathParam("userUid") UUID userUid) {
    userService.removeUser(userUid);
  }

  private Response getIntegerResponseEntity(int result) {
    if (result == 1) {
      return Response.ok().build();
    }
    return Response.status(Status.BAD_REQUEST).build();
  }

}
