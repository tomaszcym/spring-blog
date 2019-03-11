package com.tomaszcymerys.springblog.controller;

import com.tomaszcymerys.springblog.model.Profile;
import com.tomaszcymerys.springblog.repository.ProfileRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProfileControllerTest {

    @Autowired
    private ProfileController profileController;

    @Autowired
    private ProfileRepository profileRepository;

    private Long profileId;
    private static int tests = 1;

    @Before
    @Transactional
    public void setUp() {
        Profile profile = new Profile();
        profile.setUsername("mortes" + tests);
        profile.setEmail("mortes" + tests + "@demo.com");
        profile.setFirstName("Demo");
        profile.setLastName("User");

        Profile saved = this.profileRepository.save(profile);
        this.profileId = saved.getId();
        tests++;
    }

    @Test
    public void returnHttpStatusCode404() {
        ResponseEntity re = this.profileController.get(0L);
        System.out.println("Status code: " + re.getStatusCodeValue());
    }

    @Test
    public void whenProfileFound_returnProfile() {
        ResponseEntity re = this.profileController.get(this.profileId);

        if(re.getStatusCode().isError()) {
            System.out.println("Status code: " + re.getStatusCodeValue());
            System.out.println(re.getBody());
        }
        else {
            System.out.println(re.getBody());
            assertNotNull(re.getBody());
        }
    }

    @Test
    public void whenProfileUpdateSuccess_returnProfile() {
        Profile profile = new Profile();
        profile.setUsername("obecny");
        profile.setEmail("mortes_update@demo.com");
        profile.setFirstName("Demono");
        profile.setLastName("Demononanonyyw");

        ResponseEntity re = this.profileController.update(profile, this.profileId);

        if(re.getStatusCode().isError()) {
            System.out.println("Status code: " + re.getStatusCodeValue());
            System.out.println(re.getBody());
        }
        else {
            System.out.println(re.getBody());
            assertNotNull(re.getBody());
        }
    }

}