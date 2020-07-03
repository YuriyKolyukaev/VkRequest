package ru.vkinquiry.rest.model.response;

import java.util.ArrayList;
import java.util.List;

import ru.vkinquiry.model.Group;
import ru.vkinquiry.model.Owner;
import ru.vkinquiry.model.Profile;

public class ItemWithSendersResponse<T> extends BaseItemResponse<T> {                               // Нужен для того чтобы парсить не только Item'ы но и такие поля как Profiles и Groups
    public List<Profile> profiles = new ArrayList<>();                                              // Список профилей
    public List<Group> groups = new ArrayList<>();                                                  // Список групп

    public List<Profile> getProfiles() {
        return profiles;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public List<Owner> getAllSenders() {
        List<Owner> all = new ArrayList<>();                                                        // список всех отправителей (и профилей и групп)
        all.addAll(getProfiles());
        all.addAll(getGroups());
        return all;
    }

    public Owner getSender (int id) {                                                               // метод получения конкретного отправителя,
        for (Owner owner : getAllSenders()) {                                                       // где мы перебираем список всех отправителей и находим отправителя записи по id
            if (owner.getId() == Math.abs(id)) {
                return owner;
            }
        }
        return null;
    }
}
