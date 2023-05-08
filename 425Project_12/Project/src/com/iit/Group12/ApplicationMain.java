package com.iit.Group12;

import java.sql.SQLException;


import com.iit.Group12.service.MenuService;

public class ApplicationMain {

    private static final MenuService menuService = MenuService.getInstance();

    public static void main(String[] args) throws SQLException {
        menuService.init();
    }

}