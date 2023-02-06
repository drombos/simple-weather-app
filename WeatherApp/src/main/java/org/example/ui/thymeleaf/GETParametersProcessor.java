package org.example.ui.thymeleaf;

import org.thymeleaf.web.IWebRequest;

public interface GETParametersProcessor {
    void takeGETParameters(IWebRequest request);
}
