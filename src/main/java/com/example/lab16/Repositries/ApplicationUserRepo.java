package com.example.lab16.Repositries;

import com.example.lab16.Models.ApplicationUser;
import org.springframework.data.repository.CrudRepository;

public interface ApplicationUserRepo extends CrudRepository<ApplicationUser, Long> {
    ApplicationUser findByUsername(String username);


}
