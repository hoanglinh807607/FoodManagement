package javaframework.order_food_manage.controller.admin;

import javaframework.order_food_manage.constant.SystemConstant;
import javaframework.order_food_manage.dto.CategoryDTO;
import javaframework.order_food_manage.service.impl.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/api")
public class CategoryController implements IAdminController<CategoryDTO> {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category")
    @Override
    public String showList(Model model, HttpSession session,
                           @RequestParam(defaultValue = "1") int page,
                           @RequestParam(defaultValue = "5") int limit,
                           @RequestParam(defaultValue = "") String search) {
        CategoryDTO categoryDTO = categoryService.getAllPagination(page, limit, search,true);
        model.addAttribute("categoryDTO", categoryDTO);
        session.setAttribute(SystemConstant.SESSION_ACTIVE,"category");
        return "views/admin/category/category_management";
    }

    @PostMapping("/category")
    @Override
    public String doAdd(Model model, @ModelAttribute CategoryDTO obj) {
        model.addAttribute("categoryDTO", categoryService.save(obj));
        return "views/admin/category/editCategory";
    }

    @PutMapping("/category")
    @Override
    public String doEdit(Model model, @ModelAttribute CategoryDTO obj) {
        model.addAttribute("categoryDTO", categoryService.save(obj));
        return "views/admin/category/editCategory";
    }

    @DeleteMapping("/category")
    @Override
    @ResponseBody
    public String doDelete(@RequestBody CategoryDTO obj) {
        categoryService.delete(obj.getIds());
        return "success";
    }

    @GetMapping(value={"/category/create","/category/{id}"})
    @Override
    public String showEditOrAddPage(Model model, @PathVariable(name = "id", required = false) Long id) {
        CategoryDTO categoryDTO = new CategoryDTO();
        if( id != null ) {
            categoryDTO = categoryService.findOne(id);
        }
        model.addAttribute("categoryDTO",categoryDTO);
        return "views/admin/category/editCategory";
    }

    @GetMapping(value={"/category/detail/{id}"})
    @Override
    public String showDetail(Model model, @PathVariable(name = "id", required = false) Long id) {
        CategoryDTO categoryDTO = new CategoryDTO();
        if( id != null ) {
            categoryDTO = categoryService.findOne(id);
        }
        model.addAttribute("categoryDTO",categoryDTO);
        return "views/admin/category/detailCategory";
    }
}
