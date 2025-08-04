package net.etfbl.ip.urban_motion.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Client extends User{
    private String idDocument;
    private String email;
    private String phone;
    private String avatarPicture;
    private boolean blocked;

    @OneToMany(mappedBy = "client")
    private List<Rental> rentals=new ArrayList<>();

    public Client(Long id, String username, String password, String firstName, String lastName, String email, String phone, boolean blocked) {
        super(id, username, password, firstName, lastName, Role.CLIENT);
        this.email = email;
        this.phone = phone;
        this.blocked=blocked;
    }
}
