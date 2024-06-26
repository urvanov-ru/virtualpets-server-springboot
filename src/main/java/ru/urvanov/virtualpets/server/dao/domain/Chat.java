package ru.urvanov.virtualpets.server.dao.domain;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;


@Entity
@Table(name="chat")
@NamedQueries({@NamedQuery(name="Chat.findLast", query="from Chat c where (c.addressee is null or c.addressee.id = :userId or c.sender.id = :userId) order by c.sendTime desc, c.id desc"),
    @NamedQuery(name="Chat.findFromId", query="from Chat c where c.id > :fromId and (c.addressee is null or c.addressee.id = :userId or c.sender.id = :userId) order by c.sendTime asc")})
public class Chat implements Serializable{
    
    private static final long serialVersionUID = 6614311694772485588L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="chat_seq")
    @SequenceGenerator(name="chat_seq",
        sequenceName="chat_id_seq", allocationSize=1)
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private User addressee;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private User sender;
    
    @Column(name="send_time")
    private OffsetDateTime sendTime;
    
    @Column
    @Size(max=250)
    private String message;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getAddressee() {
        return addressee;
    }

    public void setAddressee(User addressee) {
        this.addressee = addressee;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public OffsetDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(OffsetDateTime sendTime) {
        this.sendTime = sendTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, sendTime, sender);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Chat other = (Chat) obj;
        return Objects.equals(message, other.message)
                && Objects.equals(sendTime, other.sendTime)
                && Objects.equals(sender, other.sender);
    }

    @Override
    public String toString() {
        return "Chat [id=" + id + ", addressee=" + addressee + ", sender="
                + sender + ", sendTime=" + sendTime + ", message=" + message
                + "]";
    }

}
