package tr.gov.gib.odeme;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@OpenAPIDefinition(info =
    @Info(
            title = "Odeme Service API",
            version = "1.0",
            description = "Documentation Odeme Service API v1.0",
            contact = @Contact(
                    name = "Melih Atalay",
                    email = "melih.atalay@gelirler.gov.tr",
                    url = "https://www.gib.gov.tr/"
            ),
            license = @License(
                    url = "https://www.gib.gov.tr/",
                    name = "Gelir İdaresi Başkanlığı Lisanslıdır"
            )
    )
)
public class OdemeApplication {

    public static void main(String[] args) {
        SpringApplication.run(OdemeApplication.class, args);
    }

}
