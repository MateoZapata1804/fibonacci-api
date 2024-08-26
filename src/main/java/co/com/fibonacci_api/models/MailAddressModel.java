package co.com.fibonacci_api.models;

import jakarta.persistence.*;

@Entity
@Table(name = "email_addresses")
public class MailAddressModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name = "disabled", nullable = false)
    private Boolean disabled;

}
