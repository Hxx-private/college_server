package com.hxx.demo.utils;

import com.hxx.demo.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Hxx
 */
public class UserUtils {
    public static User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
