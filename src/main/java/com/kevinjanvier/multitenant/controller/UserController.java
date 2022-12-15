package com.kevinjanvier.multitenant.controller;

import com.kevinjanvier.multitenant.config.DataSourceConfig;
import com.kevinjanvier.multitenant.dto.UserDto;
import com.kevinjanvier.multitenant.entity.User;
import com.kevinjanvier.multitenant.services.DataSourceConfigService;
import com.kevinjanvier.multitenant.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.SQLException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final DataSourceConfigService configService;

    //Endpoint to create a schema
    //create a table
    //create a sequence
    // insert into datasourceconfig
//    @PostMapping("/login")
//    public ResponseEntity<DataSourceConfig> login(@RequestBody DataSourceConfig dataSourceConfig,
//                                                  @RequestHeader("X-TenantID") String Xtenant){
//        DataSourceConfig config = configService.addDataSource(dataSourceConfig, Xtenant);
//        log.info("data source configuration {} ", config);
//        if (config!= null){
//            try {
//                Connection connect = configService.connect("localhost", 5432, "usercoding",
//                        "postgres", "postgres", Xtenant);
//                log.info("Schema to create is {} ", Xtenant);
//                configService.createSchema(connect,Xtenant);
//                configService.createTable(connect,Xtenant, "tbl_users");
//            }catch (SQLException sqlException){
//                log.info("--Exception failed--- {} ",sqlException);
//            }
//        }
//
//        return ResponseEntity.ok(config);
//
//    }
    @PostMapping("/login")
    public ResponseEntity<DataSourceConfig> login(@RequestBody DataSourceConfig dataSourceConfig,
                                                  @RequestHeader("X-TenantID") String Xtenant){
        DataSourceConfig config = configService.addDataSource(dataSourceConfig, Xtenant);
        log.info("data source configuration {} ", config);
//        if (config!= null){
//            try {
//                Connection connect = configService.connect("localhost", 5432, "usercoding",
//                        "postgres", "postgres", Xtenant);
//                log.info("Schema to create is {} ", Xtenant);
//                configService.createSchema(connect,Xtenant);
//                configService.createTable(connect,Xtenant, "tbl_users");
//            }catch (SQLException sqlException){
//                log.info("--Exception failed--- {} ",sqlException);
//            }
//        }

        return ResponseEntity.ok(config);

    }

    @PostMapping("/user")
    public ResponseEntity<User> addUser(@RequestBody UserDto userDto){
        User user = userService.saveUser(userDto);
        return ResponseEntity.ok(user);
    }


    @GetMapping("/user/{tenantId}")
    public ResponseEntity<User> getTenantByUd(@PathVariable("tenantId") String tenantId){
        User userByTenantId = userService.getUserByTenantId(tenantId);
        return ResponseEntity.ok(userByTenantId);
    }
}
