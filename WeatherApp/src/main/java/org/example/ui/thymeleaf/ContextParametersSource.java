package org.example.ui.thymeleaf;

import org.thymeleaf.context.WebContext;

import java.util.Map;

public interface ContextParametersSource {
    Map<String, Object> getContextVariables(WebContext context);
}
