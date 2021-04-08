package com.lomba.undianapp.controller;

import com.lomba.undianapp.dto.CommonSearch;
import com.lomba.undianapp.service.PesertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/peserta")
public class PesertaController {

    @Autowired private PesertaService pesertaService;

    @GetMapping
    public String showPeserta(CommonSearch params, ModelMap modelMap, @PageableDefault Pageable pageable){
        modelMap.addAttribute("params",params);
        modelMap.addAttribute("datas",pesertaService.getPesertaPage(params,pageable));
        return "peserta/list";
    }

    @GetMapping("/import")
    public String showForm(){
        return "peserta/import";
    }

    @PostMapping("/import")
    public String processImport(@RequestParam String category, MultipartFile fileCsv, ModelMap modelMap, RedirectAttributes redirectAttributes) {

        try {
            pesertaService.importData(category, fileCsv);
            redirectAttributes.addFlashAttribute("successMessage","Data telah di import");
            return "redirect:/peserta?category="+category;
        }catch (Exception e){
            modelMap.addAttribute("errorMessage",e.getMessage());
            return "peserta/import";
        }

    }

    @GetMapping("/delete")
    public String deleteByCategory(@RequestParam String category, RedirectAttributes redirectAttributes) {

        try {
            pesertaService.deleteByCategory(category);
            redirectAttributes.addFlashAttribute("successMessage","Data telah dihapus");
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage",e.getMessage());
        }
        return "redirect:/peserta?category="+category;

    }
}
