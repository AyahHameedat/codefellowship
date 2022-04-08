package com.example.lab16.Repositries;

import com.example.lab16.Models.ApplicationUser;
import com.example.lab16.Models.PostUsers;
import org.springframework.data.repository.CrudRepository;

public interface PostRepo  extends CrudRepository<PostUsers, Long> {
}
