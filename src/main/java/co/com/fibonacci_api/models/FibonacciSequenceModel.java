package co.com.fibonacci_api.models;

import jakarta.persistence.*;
import java.time.Duration;

@Entity
@Table(name = "fibonacci_sequences")
public class FibonacciSequenceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String sequence;

    @Column(name = "generated_at", nullable = false)
    private String generatedAt;

    @Column(name = "mail_sent", nullable = false)
    private Boolean mailSent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(String generatedAt) {
        this.generatedAt = generatedAt;
    }

    public Boolean getMailSent() {
        return mailSent;
    }

    public void setMailSent(Boolean mailSent) {
        this.mailSent = mailSent;
    }
}
