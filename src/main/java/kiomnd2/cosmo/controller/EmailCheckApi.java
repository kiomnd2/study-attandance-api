package kiomnd2.cosmo.controller;

import kiomnd2.cosmo.dto.AccountDto;
import kiomnd2.cosmo.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
@Slf4j
public class EmailCheckApi {

    private final AccountService accountService;

    @GetMapping("/check-email-token")
    public String checkEmailToken(String token, Long id, Model model) {
        log.debug("token = {}, id = {}", token, id);
        AccountDto accountDto = accountService.checkEmailToken(token, id);
        model.addAttribute("nickname", accountDto.getNickname());
        return "json";
    }


}
