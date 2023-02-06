package org.example.ui.thymeleaf;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

public class ThymeleafEngine {
    private static class Holder {
        private static final ThymeleafEngine INSTANCE = new ThymeleafEngine();
    }

     public static TemplateEngine getInstance() {
        return Holder.INSTANCE.templateEngine;
    }

    private final TemplateEngine templateEngine;

    private ThymeleafEngine() {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding("UTF-8");
        resolver.setPrefix("/thyme/");
        resolver.setSuffix(".html");
//        resolver.setCacheable(false);
        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(resolver);
    }
}
