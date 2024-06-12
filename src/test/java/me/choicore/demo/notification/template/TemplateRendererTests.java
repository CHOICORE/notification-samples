package me.choicore.demo.notification.template;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TemplateRendererTests {
    private final TemplateRenderer sut = new TemplateRenderer();

    @Test
    void t1() {
        PlaceholderRegistry registry = new DefaultPlaceholderRegistry();
        registry.registerPlaceholder(Placeholders.as("name", "홍길동"));

        Template template = ContentTemplate.builder()
                .name("content-template")
                .content("Hello, {name}!")
                .build();

        Template rendered = sut.render(template, registry);

        assertThat(rendered.hasPlaceholders()).isFalse();
        assertThat(rendered.getContent()).isEqualTo("Hello, 홍길동!");
    }

    @Test
    void t2() {
        PlaceholderRegistry registry = new DefaultPlaceholderRegistry();
        registry.registerPlaceholder(Placeholders.as("name", "홍길동"));

        Template template = ContentTemplate.builder()
                .name("content-template")
                .content("Hello, {name}!, {company}")
                .build();

        assertThatThrownBy(() -> sut.render(template, registry))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("{company}");
    }

    @Test
    void t3() {
        PlaceholderRegistry registry = new DefaultPlaceholderRegistry();
        registry.registerPlaceholder(Placeholders.as("name", "홍길동"));

        Template template = SubjectContentTemplate.builder()
                .name("subject-content-template")
                .subject("Hello, {name}!")
                .content("Hello, {name}!, {company}")
                .build();

        assertThatThrownBy(() -> sut.render(template, registry))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("{company}");
    }

    @Test
    void t4() {
        SubjectContentTemplate template = SubjectContentTemplate.builder()
                .name("subject-content-template")
                .subject("Hello, {name}!")
                .content("Hello, {name}!, {company}")
                .build();

        PlaceholderRegistry registry = new DefaultPlaceholderRegistry();
        registry.registerPlaceholder(Placeholders.as("name", "홍길동"));
        registry.registerPlaceholder(Placeholders.as("company", "개발바닥"));

        SubjectContentTemplate rendered = (SubjectContentTemplate) sut.render(template, registry);
        assertThat(rendered.hasPlaceholders()).isFalse();
        assertThat(rendered.getSubject()).isEqualTo("Hello, 홍길동!");
        assertThat(rendered.getContent()).isEqualTo("Hello, 홍길동!, 개발바닥");
    }

    @Test
    void t5() throws Exception {
        PlaceholderRegistry registry = new DefaultPlaceholderRegistry(new PlaceholderDefinition("#{", "}"));
        registry.registerPlaceholder(Placeholders.personName("홍길동"));
        registry.registerPlaceholder(Placeholders.companyName("개같이 코딩"));
        registry.registerPlaceholder(Placeholders.as("link", "https://company.com"));

        ClassPathResource resource = new ClassPathResource("templates/notification/welcome-template.html");

        ContentTemplate template = ContentTemplate.builder()
                .name("push-template")
                .content(resource.getFile())
                .build();

        Template render = sut.render(template, registry);
        assertThat(render.getContent()).isEqualTo("""
                <!DOCTYPE html>
                <html lang="kr">
                <head>
                    <meta charset="UTF-8">
                    <title>회원가입 환영 메시지</title>
                </head>
                <body>
                <h2>회원가입 환영 메시지</h2>
                <p>안녕하세요, 홍길동님!</p>
                <p>저희 개같이 코딩 회원이 되신 것을 환영합니다.</p>
                <p><a href="https://company.com">회원 혜택 보러 가기</a></p>
                </body>
                </html>
                """.strip());
    }

    @Test
    void t6() {
        PlaceholderRegistry registry = new DefaultPlaceholderRegistry(PlaceholderDefinitions.withDefault());
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

        ContentTemplate template = ContentTemplate.builder()
                .name("push-template")
                .content(content)
                .build();
        Template render = sut.render(template, registry);

        assertThat(render.getContent()).isEqualTo("""
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
    void t7() {
        PlaceholderRegistry registry = new DefaultPlaceholderRegistry(PlaceholderDefinitions.create("#{", "}"));
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

        ContentTemplate template = ContentTemplate.builder()
                .name("push-template")
                .content(content)
                .build();

        assertThatThrownBy(() -> sut.render(template, registry))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid placeholder")
                .hasMessageContaining("personName")
                .hasMessageContaining("companyName")
                .hasMessageContaining("link");
    }
}