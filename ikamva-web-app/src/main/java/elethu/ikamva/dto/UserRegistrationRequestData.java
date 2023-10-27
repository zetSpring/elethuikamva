package elethu.ikamva.dto;

import elethu.ikamva.domain.enums.Role;

public record UserRegistrationRequestData(String investmentId, String email, String password, Role role) {}
