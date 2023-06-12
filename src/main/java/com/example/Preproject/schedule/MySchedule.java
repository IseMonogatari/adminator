package com.example.Preproject.schedule;

import com.example.Preproject.model.User;
import com.example.Preproject.repository.RolesRepository;
import com.example.Preproject.repository.UsersRepository;
import com.example.Preproject.service.email.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MySchedule {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private MailSender mailSender;

    @Value("${notifications.users.birthday}")
    private int daysBeforeBirthday;

    private final String SUBJECT_BIRTHDAY = "День рождения";

    LocalDate nowTime = LocalDate.now();

//    @Scheduled(fixedDelay = 120000)
    public void sendBirthdayMessageToAdmins() {
        List<User> userList = usersRepository.findAllByRoles(rolesRepository.findByRole("ROLE_ADMIN"));
        if (!userList.isEmpty() && !getUserStringsWithBirthday().isEmpty()) {
            userList.forEach(user -> {
                StringBuilder message = new StringBuilder(String.format("Уважаемый %s!\n\n", user.getName()));
                getUserStringsWithBirthday().forEach(message::append);
                message.append("\nНе забудьте поздравить этих людей!");
                mailSender.send(user.getEmail(), SUBJECT_BIRTHDAY, message.toString());
            });
        }
    }

    private List<String> getUserStringsWithBirthday() {
        return usersRepository.findAll().stream()
                .filter(user -> user.getBirthday().getMonthValue() == nowTime.getMonthValue() &&
                        (user.getBirthday().getDayOfMonth() - nowTime.getDayOfMonth()) == daysBeforeBirthday)
                .map(user -> String.format("%d числа пользователю \"%s %s\" исполнится %d лет/года.\n",
                        user.getBirthday().getDayOfMonth(), user.getLastName(),
                        user.getName(), userAge(user)))
                .collect(Collectors.toList());
    }

    private Integer userAge(User user) {
        return Period.between(user.getBirthday(), nowTime).getYears();
    }
}