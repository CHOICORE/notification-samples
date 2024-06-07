package me.choicore.demo.notification.email;

import me.choicore.demo.notification.PlaceholderExpression;
import me.choicore.demo.notification.PlaceholderExpressions;
import me.choicore.demo.notification.PlaceholderRegistry;
import me.choicore.demo.notification.PlaceholderResolver;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EmailTemplateTests {

    @Test
    void t1() {
        // given
        String subject = "{personName}님 환영합니다";
        String body = "안녕하세요, {personName}님. {companyName}에 오신 것을 환영합니다. 가입 확인을 위해 아래 링크를 클릭해주세요. {link}";

        // when
        EmailTemplate sut = new EmailTemplate(subject, body);

        // then
        assertThat(sut.getPlaceholders()).containsExactlyInAnyOrder("{personName}", "{companyName}", "{link}");
        assertThat(sut.getPlaceholderCount()).isEqualTo(4);
    }

    @Test
    void t2() {
        // given
        String subject = "{personName}님 환영합니다";
        String body = "안녕하세요, {personName}님. {companyName}에 오신 것을 환영합니다. 가입 확인을 위해 아래 링크를 클릭해주세요. {link}";
        EmailTemplate emailTemplate = new EmailTemplate(subject, body);
        PlaceholderRegistry registry = new PlaceholderRegistry();
        registry.register(
                PlaceholderExpressions.personName("홍길동"),
                PlaceholderExpressions.companyName("개발바닥"),
                PlaceholderExpressions.as("link", "https://company.com"),
                new PlaceholderExpression("link", "https://company.com")
        );

        // sut
        PlaceholderResolver sut = new PlaceholderResolver();

        // when
        EmailTemplate resolved = (EmailTemplate) sut.resolve(emailTemplate, registry);

        // then
        assertThat(resolved.getSubject()).isEqualTo("홍길동님 환영합니다");
        assertThat(resolved.getTemplate()).isEqualTo("안녕하세요, 홍길동님. 개발바닥에 오신 것을 환영합니다. 가입 확인을 위해 아래 링크를 클릭해주세요. https://company.com");

    }
}
