package javaframework.order_food_manage.controller.admin;

import javaframework.order_food_manage.constant.SystemConstant;
import javaframework.order_food_manage.dto.UserDTO;
import javaframework.order_food_manage.service.impl.RoleService;
import javaframework.order_food_manage.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/api")
public class UserController implements IAdminController<UserDTO>{

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/user")
    @Override
    public String showList(Model model, HttpSession session,
                           @RequestParam(defaultValue = "1") int page,
                           @RequestParam(defaultValue = "5") int limit,
                           @RequestParam(defaultValue = "") String search) {
        UserDTO userDTO = userService.getAllPagination(page, limit, search,true);
        model.addAttribute("userDTO", userDTO);
        session.setAttribute(SystemConstant.SESSION_ACTIVE,"user");
        session.setAttribute(SystemConstant.SESSION_AUTHORITY,SecurityContextHolder.getContext().getAuthentication());
        return "views/admin/user/user_management";
    }

    @PostMapping(value = "/user")
    @Override
    public String doAdd(Model model, @ModelAttribute UserDTO obj) {
        UserDTO userDTO = new UserDTO();
        if( obj.getId() == null ) {
            if( userService.findByUsernameAndStatus(obj.getUsername(),true ) == null ){
                userDTO =  userService.save(obj);
            }else{
                userDTO = obj;
                userDTO.setAlert("danger");
                userDTO.setMessage("Username already exists");
            }
        }else{
            userDTO =  userService.save(obj);
        }
        model.addAttribute("userDTO",userDTO);
        model.addAttribute("roleList",roleService.findAll());
        return "views/admin/user/editUser";
    }

    @PutMapping(value = "/user")
    @Override
    public String doEdit(Model model, @ModelAttribute UserDTO obj) {
        model.addAttribute("userDTO", userService.save(obj));
        model.addAttribute("roleList",roleService.findAll());
        return "views/admin/user/editUser";
    }

    @DeleteMapping(value="/user")
    @ResponseBody
    @Override
    public String doDelete(@RequestBody UserDTO obj) {
        userService.delete(obj.getIds());
        return "success";
    }

    @GetMapping(value={"/user/create","/user/{id}"})
    @Override
    public String showEditOrAddPage(Model model, @PathVariable(name = "id", required = false) Long id) {
        UserDTO userDTO = new UserDTO();
        if( id != null ) {
            userDTO = userService.findOne(id);
        }
        model.addAttribute("userDTO",userDTO);
        model.addAttribute("roleList",roleService.findAll());
        return "views/admin/user/editUser";
    }

    @GetMapping("/user/detail/{id}")
    @Override
    public String showDetail(Model model,  @PathVariable(name = "id", required = false) Long id) {
        UserDTO userDTO = new UserDTO();
        if( id != null ) {
            userDTO = userService.findOne(id);
        }
        model.addAttribute("userDTO",userDTO);
        model.addAttribute("roleList",roleService.findAll());
        return "views/admin/user/detailUser";
    }
}
