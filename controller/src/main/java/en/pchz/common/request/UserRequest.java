package en.pchz.common.request;

public record UserRequest(String username, String password, String role, Integer catMasterId) {
}
