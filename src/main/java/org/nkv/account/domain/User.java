package org.nkv.account.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Account account;

    public User(String username) {
        this.username = username;
    }

    public void setAccount(Account account) {
        if (account == null) {
            if (this.account != null) {
                this.account.setUser(null);
            }
        } else {
            account.setUser(this);
        }
        this.account = account;
    }
}