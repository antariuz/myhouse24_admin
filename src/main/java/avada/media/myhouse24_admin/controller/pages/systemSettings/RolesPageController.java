package avada.media.myhouse24_admin.controller.pages.systemSettings;

import avada.media.myhouse24_admin.model.systemSettings.pages.Role;
import avada.media.myhouse24_admin.repo.RoleRepo;
import avada.media.myhouse24_admin.repo.StaffRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Set;

@Controller
@RequestMapping("system-settings/roles")
@RequiredArgsConstructor
public class RolesPageController {

    private final RoleRepo roleRepo;
    private final StaffRepo staffRepo;

    @RequestMapping({"/", ""})
    public ModelAndView showRolesPage() {
        return new ModelAndView("pages/system-settings/roles");
    }

    @GetMapping("get-all-roles")
    public @ResponseBody Set<Role> getAllRoles() {
        return roleRepo.getRolesByNameNotLike("ROLE_DIRECTOR", Sort.by(Sort.Direction.ASC, "id"));
    }

    @PostMapping("update")
    public ResponseEntity<Void> updateRoles(@RequestBody Set<Role> roles, Principal principal) {
        roleRepo.saveAll(roles);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), staffRepo.getStaffByEmail(principal.getName()).getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuth);
        return ResponseEntity.ok().build();
    }

}
