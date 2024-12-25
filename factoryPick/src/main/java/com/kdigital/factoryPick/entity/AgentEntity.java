package com.kdigital.factoryPick.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "agent")
public class AgentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq_no")
    private Long seqNo;  // Primary Key

    @Column(name = "agent_email", nullable = false, length = 255)
    private String agentEmail;  // Foreign Key to users

    @Column(name = "enroll", length = 300)
    private String enroll;  // Enrollment details

    @Column(name = "file", length = 500)
    private String file;  // File path or name
}
