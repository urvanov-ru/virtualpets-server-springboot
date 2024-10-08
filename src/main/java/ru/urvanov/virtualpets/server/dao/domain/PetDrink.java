package ru.urvanov.virtualpets.server.dao.domain;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

/**
 * Запись о количестве напитка у питомца.
 */
@Entity
@Table(name="pet_drink")
public class PetDrink {

    /**
     * Первичный ключ. Генерируемый.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="pet_drink_seq")
    @SequenceGenerator(name="pet_drink_seq",
        sequenceName="pet_drink_id_seq", allocationSize=1)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Pet pet;

    @ManyToOne(fetch = FetchType.LAZY)
    private Drink drink;

    private int drinkCount;

    /**
     * Для оптимистичной блокировки.
     */
    @Version
    private int version;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Drink getDrink() {
        return drink;
    }

    public void setDrink(Drink drink) {
        this.drink = drink;
    }

    public int getDrinkCount() {
        return drinkCount;
    }

    public void setDrinkCount(int drinkCount) {
        this.drinkCount = drinkCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(drink.getId(), drinkCount, pet.getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PetDrink other = (PetDrink) obj;
        return Objects.equals(drink.getId(), other.drink.getId())
                && Objects.equals(drinkCount, other.drinkCount)
                && Objects.equals(pet.getId(), other.pet.getId());
    }

    @Override
    public String toString() {
        return "PetDrink [id=" + id + ", pet.id=" + pet.getId()
                + ", drink.id=" + drink.getId()
                + ", drinkCount=" + drinkCount
                + ", version=" + version + "]";
    }

}
