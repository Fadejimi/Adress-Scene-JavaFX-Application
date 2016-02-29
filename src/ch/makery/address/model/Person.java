/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.makery.address.model;

import ch.makery.address.util.LocalDateAdapter;
import java.time.LocalDate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
/**
 *
 * @author Test
 */
public class Person {
    private final StringProperty firstName, lastName, street, city;
    private final IntegerProperty postalCode;
    private final ObjectProperty<LocalDate> birthday;
    
    public Person()
    {
        this(null, null);
    }
    
    public Person(String firstname, String lastname)
    {
        this.firstName = new SimpleStringProperty(firstname);
        this.lastName = new SimpleStringProperty(lastname);
        
        this.street = new SimpleStringProperty("some street");
        this.postalCode = new SimpleIntegerProperty(1234);
        this.city = new SimpleStringProperty("some city");
        this.birthday = new SimpleObjectProperty<>(LocalDate.of(1999, 2, 21));
    }
    
    public void setFirstName(String fname)
    {
        this.firstName.set(fname);
    }
    
    public String getFirstname()
    {
        return firstName.get();
    }
    
    public StringProperty firstNameProperty()
    {
        return firstName;
    }
    
    public void setLastName(String lname)
    {
        this.lastName.set(lname);
    }
    
    public String getLastName()
    {
        return lastName.get();
    }
    
    public StringProperty lastNameProperty()
    {
        return lastName;
    }
    
    public void setCity(String c)
    {
        this.city.set(c);
    }
    
    public String getCity()
    {
        return this.city.get();
    }
    
    public StringProperty cityProperty()
    {
        return city;
    }
    
    public void setStreet(String str)
    {
        this.street.set(str);
    }
    
    public String getStreet()
    {
        return street.get();
    }
    
    public StringProperty streetProperty()
    {
        return street;
    }
    
    public void setBirthday(LocalDate date)
    {
        this.birthday.set(date);
    }
    
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getBirthday()
    {
        return birthday.get();
    }
    
    public ObjectProperty<LocalDate> birthDayProperty()
    {
        return birthday;
    }
    
    public void setPostalCode(int code)
    {
        this.postalCode.set(code);
    }
    
    public int getPostalCode()
    {
        return this.postalCode.get();
    }
    
    public IntegerProperty postalCodeProperty()
    {
        return postalCode;
    }
}
