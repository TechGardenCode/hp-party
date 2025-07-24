package gg.techgarden.houseparty.party.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import gg.techgarden.houseparty.party.model.ActivityType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Activity {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private UUID partyId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserInfo user;
    private ActivityType type;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Activity parent;
    @OneToMany(mappedBy = "parent")
    private List<Activity> children;
    private String content;
    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;
}
