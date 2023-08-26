package elethu.ikamva.dao;

import elethu.ikamva.domain.enums.Role;

public record UserRegistrationRequestData(String investmentId, String email, String password, Role role) {}
