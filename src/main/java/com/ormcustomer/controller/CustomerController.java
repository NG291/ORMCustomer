package com.ormcustomer.controller;

import com.ormcustomer.model.Customer;
import com.ormcustomer.service.HibernateCustomerService;
import com.ormcustomer.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("customers")
public class CustomerController {
    @Autowired
    private ICustomerService customerService;

    @GetMapping("")
    public String getList(Model model) {
        List<Customer> customers = customerService.findAll();
        model.addAttribute("customers", customers);
        return "/list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("customer", new Customer());
        return "/create";
    }

    @PostMapping("/save")
    public String save(Customer customer) {
        customerService.save(customer);
        return "redirect:/customers";
    }

    @GetMapping("{id}/delete")
    public String showFormDelete(@PathVariable("id") Long id, Model model) {
        model.addAttribute("customer", customerService.findById(id));
        return "/delete";
    }

    @PostMapping("/delete")
    public String delete(RedirectAttributes redirect, Customer customer) {
        customerService.remove(customer.getId());
        redirect.addAttribute("message", "Delete customer successfully");
        return "redirect:/customers";
    }

    @GetMapping("{id}/edit")
    public String showFormUpdate(@PathVariable("id") Long id, Model model) {
        model.addAttribute("customer", customerService.findById(id));
        return "/update";
    }

    @PostMapping("update")
    public String update(RedirectAttributes redirect, Customer customer) {
        customerService.save(customer);
        redirect.addAttribute("message", "Update successfully");
        return "redirect:/customers";
    }

    @GetMapping("{id}/view")
    public String showInfoCustomer(@PathVariable("id") Long id, Model model) {
        model.addAttribute("customer", customerService.findById(id));
        return "/view";
    }

}
