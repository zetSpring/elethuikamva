package elethu.ikamva.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface IkamvaTokensService {
    void getAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
