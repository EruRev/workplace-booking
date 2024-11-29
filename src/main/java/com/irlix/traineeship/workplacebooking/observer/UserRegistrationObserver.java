package com.irlix.traineeship.workplacebooking.observer;

import com.irlix.traineeship.workplacebooking.entities.UserEntity;

public interface UserRegistrationObserver {
    void update(UserEntity userEntity);
}
