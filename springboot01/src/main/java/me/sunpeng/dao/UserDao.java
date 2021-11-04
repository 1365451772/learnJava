package me.sunpeng.dao;

import me.sunpeng.domain.User;
import org.springframework.stereotype.Component;

/**
 * @author sp
 * @date 2021-11-01 20:38
 */

public interface UserDao {
    User findById(Integer id);
    User findByName(String name);
}
