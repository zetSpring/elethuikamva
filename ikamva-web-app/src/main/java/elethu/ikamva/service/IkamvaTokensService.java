package elethu.ikamva.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface IkamvaTokensService {
    void getAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
