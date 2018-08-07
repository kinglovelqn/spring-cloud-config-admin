package com.didispace.scca.rest.web;

import com.didispace.scca.rest.domain.User;
import com.didispace.scca.rest.dto.UserDto;
import com.didispace.scca.rest.dto.base.WebResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Anoyi on 2018/8/1.
 * <p>
 * Blog: https://anoyi.com/
 * Github: https://github.com/ChineseSilence
 */
@Api("Admin（管理员-用户管理）")
@Slf4j
@RestController
@RequestMapping("${scca.rest.context-path:}/admin")
//@Secured("hasRole('ADMIN')")
public class UserAdminController extends BaseController {

    @ApiOperation("Get User List / 获取用户列表")
    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public WebResp<Page<UserDto>> getUserList(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                           @RequestParam(value = "size", defaultValue = "10") Integer size) {
        // 分页获取所有用户信息
        Pageable pageable = new PageRequest(page, size, new Sort(Sort.Direction.DESC, "id"));
        Page<UserDto> users = userService.getUsers(pageable);
        return WebResp.success(users);
    }

    @ApiOperation("Save User / 添加新用户")
    @RequestMapping(method = RequestMethod.POST)
    public WebResp<String> saveUser(@RequestBody User user) {
        // 管理员添加新用户
        userService.createUser(user);
        return WebResp.success("save new user success");
    }

    @ApiOperation("Update User / 修改用户信息")
    @RequestMapping(method = RequestMethod.PUT)
    public WebResp<String> updateUser(@RequestBody User user) {
        // 管理员修改用户信息
        userService.updateUser(user);
        return WebResp.success("update user success : " + user.getUsername());
    }

    @ApiOperation("Delete User / 删除用户")
    @RequestMapping(method = RequestMethod.DELETE)
    public WebResp<String> deleteUser(@RequestParam("username") String username) {
        // 管理员删除用户
        userService.deleteUserByUsername(username);
        return WebResp.success("save new user success");
    }

}