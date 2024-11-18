package com.example.bankingapp.controller;

import com.example.bankingapp.dto.AccountDto;
import com.example.bankingapp.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // Display all accounts
    @GetMapping
    public String listAccounts(Model model) {
        List<AccountDto> accounts = accountService.getAllAccounts();
        model.addAttribute("accounts", accounts);
        return "accounts-list"; // Thymeleaf template for listing accounts
    }

    // Show the form for creating a new account
    @GetMapping("/new")
    public String showCreateAccountForm(Model model) {
        model.addAttribute("account", new AccountDto()); // Initialize an empty AccountDto
        return "account-form"; // Thymeleaf template for creating an account
    }

    // Process the creation of a new account
    @PostMapping("/new")
    public String createAccount(@ModelAttribute("account") AccountDto accountDto, RedirectAttributes redirectAttributes) {
        try {
            // Create the account and retrieve the created account with ID
            AccountDto createdAccount = accountService.createAccount(accountDto);

            // Add success message to be displayed after the redirect
            redirectAttributes.addFlashAttribute("message", "Account created successfully!");
            return "redirect:/accounts"; // Redirect to the account list page
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error creating account: " + e.getMessage());
            return "redirect:/accounts/new"; // Redirect back to the create account form
        }
    }

    // Show the form for depositing/withdrawing from an account
    @GetMapping("/{id}/edit")
    public String showEditAccountForm(@PathVariable Long id, Model model) {
        AccountDto account = accountService.getAccountById(id);
        model.addAttribute("account", account);
        return "account-edit"; // Thymeleaf template for editing account
    }

    // Process deposit
    @PostMapping("/{id}/deposit")
    public String deposit(@PathVariable Long id, @RequestParam Double amount, RedirectAttributes redirectAttributes) {
        try {
            accountService.deposit(id, amount);
            redirectAttributes.addFlashAttribute("message", "Deposit successful!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/accounts/{id}/edit"; // Redirect to the edit page of the account
    }

    // Process withdrawal
    @PostMapping("/{id}/withdraw")
    public String withdraw(@PathVariable Long id, @RequestParam Double amount, RedirectAttributes redirectAttributes) {
        try {
            accountService.withdraw(id, amount);
            redirectAttributes.addFlashAttribute("message", "Withdrawal successful!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/accounts/{id}/edit"; // Redirect to the edit page of the account
    }

    // Delete an account
    @PostMapping("/{id}/delete")
    public String deleteAccount(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            accountService.deleteAccount(id);
            redirectAttributes.addFlashAttribute("message", "Account deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error during account deletion: " + e.getMessage());
        }
        return "redirect:/accounts"; // Redirect to the account list page after deletion
    }
}