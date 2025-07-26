package br.com.littleme.url_shortener.docs.web;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Hidden
public class DocsController {

    @Value("${springdoc.api-docs.path:/v3/api-docs}")
    private String apiDocsPath;

    @GetMapping("/docs")
    public String redirectToSwaggerUi() {
        return "redirect:/docs/swagger";
    }

    @GetMapping("/docs/redoc")
    @ResponseBody
    public String getRedoc() {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<title>ReDoc</title>" +
                "<meta charset=\"utf-8\"/>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">" +
                "<link href=\"https://fonts.googleapis.com/css?family=Montserrat:300,400,700|Roboto:300,400,700\" rel=\"stylesheet\">" +
                "<style> body { margin: 0; padding: 0; } </style>" +
                "</head>" +
                "<body>" +
                "<redoc spec-url='" + apiDocsPath + "'></redoc>" +
                "<script src=\"https://cdn.jsdelivr.net/npm/redoc@latest/bundles/redoc.standalone.js\"> </script>" +
                "</body>" +
                "</html>";
    }
}
