package me.choicore.demo.notification.template;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PlaceholderReplacingTemplateTests {

    @Test
    void t1() {
        PlaceholderRegistry registry = new DefaultPlaceholderRegistry();
        registry.registerPlaceholder(Placeholders.as("name", "홍길동"));

        String content = "Hello, {name}!";
        Template template = new PlaceholderReplacingTemplate(content, registry);

        String render = template.render();
        assertThat(render).isEqualTo("Hello, 홍길동!");
    }

    @Test
    void t2() {
        PlaceholderRegistry registry = new DefaultPlaceholderRegistry();
        registry.registerPlaceholder(Placeholders.as("name", "홍길동"));

        Template template = new PlaceholderReplacingTemplate("Hello, {name}!, {company}", registry);

        assertThatThrownBy(template::render)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("{company}");
    }

    @Test
    void t3() {
        PlaceholderRegistry registry = new DefaultPlaceholderRegistry();
        registry.registerPlaceholder(Placeholders.personName("홍길동"));
        registry.registerPlaceholder(Placeholders.companyName("개발바닥"));
        registry.registerPlaceholder(Placeholders.as("link", "https://company.com"));

        String content = """
                회원가입 안내
                안녕하세요, {personName}님!
                저희 {companyName} 회원이 되신 것을 환영합니다.
                회원 가입 감사 인사를 전합니다.
                신규회원 혜택을 확인하고 다양한 혜택을 누려보세요.
                {link} 에서 확인하실 수 있습니다.
                감사합니다.
                """;
        Template template = new PlaceholderReplacingTemplate(content, registry);
        String render = template.render();

        assertThat(render).isEqualTo("""
                회원가입 안내
                안녕하세요, 홍길동님!
                저희 개발바닥 회원이 되신 것을 환영합니다.
                회원 가입 감사 인사를 전합니다.
                신규회원 혜택을 확인하고 다양한 혜택을 누려보세요.
                https://company.com 에서 확인하실 수 있습니다.
                감사합니다.
                """);

    }

    @Test
    void t4() {
        PlaceholderFormatter formatter = new DefaultPlaceholderFormatter();
        formatter.setPrefix("#{");
        formatter.setSuffix("}");

        PlaceholderRegistry registry = new DefaultPlaceholderRegistry(formatter);
        registry.registerPlaceholder(Placeholders.personName("홍길동"));
        registry.registerPlaceholder(Placeholders.companyName("개발바닥"));
        registry.registerPlaceholder(Placeholders.as("link", "https://company.com"));

        String content = """
                회원가입 안내
                안녕하세요, #{personName}님!
                저희 #{companyName} 회원이 되신 것을 환영합니다.
                회원 가입 감사 인사를 전합니다.
                신규회원 혜택을 확인하고 다양한 혜택을 누려보세요.
                #{link} 에서 확인하실 수 있습니다.
                감사합니다.
                """;
        Template template = new PlaceholderReplacingTemplate(content, registry);
        String render = template.render();

        assertThat(render).isEqualTo("""
                회원가입 안내
                안녕하세요, 홍길동님!
                저희 개발바닥 회원이 되신 것을 환영합니다.
                회원 가입 감사 인사를 전합니다.
                신규회원 혜택을 확인하고 다양한 혜택을 누려보세요.
                https://company.com 에서 확인하실 수 있습니다.
                감사합니다.
                """);
    }
}