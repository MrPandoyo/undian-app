package com.lomba.undianapp.controller;

import com.lomba.undianapp.dto.CommonSearch;
import com.lomba.undianapp.entity.Undian;
import com.lomba.undianapp.service.UndianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UndianController {

    @Autowired private UndianService undianService;

    @GetMapping("/undian")
    public String showUndian(CommonSearch params, ModelMap modelMap){
        if(StringUtils.hasText(params.getValue())){
            Undian undian = undianService.getById(params);
            params.setCategory(undian.getCategory());

            modelMap.addAttribute("undian",undian);
        }
        modelMap.addAttribute("params",params);
        return "undian/list";
    }

    @GetMapping("/undian/undi")
    public String proceedUndian(@RequestParam String category, RedirectAttributes redirectAttributes){
        try {
            Undian undian = undianService.undiPemenang(category);
            redirectAttributes.addFlashAttribute("successMessage","Undian telah berhasil, selamat kepada pemenang");
            return "redirect:/undian?value="+undian.getId();
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage",e.getMessage());
            return "redirect:/undian?category="+category;
        }
    }

    @GetMapping("/history")
    public String showHistory(CommonSearch params, ModelMap modelMap,
                              @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable){
        modelMap.addAttribute("datas", undianService.getPagePemenangUndian(params, pageable));
        modelMap.addAttribute("params",params);
        return "undian/history";
    }

    @GetMapping("/history/delete")
    public String deleteAll(@RequestParam String category, RedirectAttributes redirectAttributes){
        try {
            undianService.deleteByCategory(category);
            redirectAttributes.addFlashAttribute("successMessage","Data telah dihapus");
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage","Data gagal dihapus");
        }
        return "redirect:/history?category="+category;
    }
}
