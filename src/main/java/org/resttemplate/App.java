package org.resttemplate;

import org.resttemplate.configuration.MyConfig;
import org.resttemplate.entity.User;
import org.resttemplate.services.Communication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

@SpringBootApplication
public class App {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);

        Communication communication = context.getBean("communication", Communication.class);
        List<User> allUsers = communication.getAllUsers();
        System.out.println("ALL users:    " + allUsers);

        User user = new User(3L,"James", "Brown", 27);
        String firstPartCode = communication.saveUser(user);

        User user1 = new User(3L,"Thomas", "Shelby", 27);
        String secondPartCode = communication.updateUser(user1);
        String thirdPartCode = communication.delete(user1);

        System.out.println("ИТОГОВАЯ СТРОКА: " + firstPartCode + secondPartCode + thirdPartCode);
    }
}