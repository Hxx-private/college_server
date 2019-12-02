package com.hxx.demo.controller;

import com.hxx.demo.entity.Menu;
import com.hxx.demo.entity.Result;
import com.hxx.demo.entity.Role;
import com.hxx.demo.service.MenuRoleService;
import com.hxx.demo.service.MenuService;
import com.hxx.demo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hxx
 */
@RestController
@RequestMapping("/system/basic")
public class SystemBasicController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private MenuRoleService menuRoleService;



    @DeleteMapping(value = "/role/{rid}")
    public Result deleteRole(@PathVariable("rid") Long rid) {
        if (roleService.deleteRoleById(rid) == 1) {
            return Result.success("删除成功!");
        }
        return Result.fail("删除失败!");
    }

    @PostMapping(value = "/addRole")
    public Result addNewRole(String role, String roleZh) {
        if (roleService.addNewRole(role, roleZh) == 1) {
            return Result.success("添加成功!");
        }
        return Result.fail("添加失败!");
    }

    @RequestMapping(value = "/menuTree/{rid}", method = RequestMethod.GET)
    public Map<String, Object> menuTree(@PathVariable Long rid) {
        Map<String, Object> map = new HashMap<>();
        List<Menu> menus = menuService.menuTree();
        map.put("menus", menus);
        List<Long> selMids = menuService.getMenusByRid(rid);
        map.put("mids", selMids);
        return map;
    }

    @RequestMapping(value = "/updateMenuRole", method = RequestMethod.PUT)
    public Result updateMenuRole(Long rid, Long[] mids) {
        if (menuRoleService.updateMenuRole(rid, mids) == mids.length) {
            return Result.success("更新成功!");
        }
        return Result.fail("更新失败!");
    }

    @RequestMapping("/roles")
    public List<Role> allRoles() {
        return roleService.roles();
    }


}
