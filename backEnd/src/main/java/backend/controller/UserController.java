package backend.controller;

import backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author user
 * @since 2022-12-29
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    
    @GetMapping("/{id}")
    public Object test(@PathVariable("id") Long id) {
        System.out.println(id);
        return userService.getById(id);
    }


}
