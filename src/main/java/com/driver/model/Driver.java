package com.driver.model;
import javax.persistence.*;
import java.util.*;
@Entity
@Table(name = "drivers")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int driverId;
    @Column(unique = true, nullable = false)
    private String mobile;
    @Column(unique = true, nullable = false)
    private String password;
    @OneToOne
    Cab cab;
}