package br.insper.produto.login;

import org.springframework.stereotype.Service;

@Service
public class LoginService {

    public String validateToken(String token) {
        // Aqui você deveria consultar o Redis ou fazer uma chamada para validar o token
        // Simulando por enquanto:

        if (token.equals("admin-token")) {
            return "ADMIN";
        } else if (token.equals("user-token")) {
            return "USER";
        } else {
            throw new RuntimeException("Token inválido!");
        }
    }
}
