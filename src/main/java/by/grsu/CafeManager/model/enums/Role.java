package by.grsu.CafeManager.model.enums;

public enum Role {
    WAITER,
    COOK,
    ADMIN;

    public String getAuthority() {
        return "ROLE_" + name();
    }
}
