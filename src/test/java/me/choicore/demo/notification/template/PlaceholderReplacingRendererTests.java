package me.choicore.demo.notification.template;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PlaceholderReplacingRendererTests {
    @Test
    void t1() {
        PlaceholderRegistry registry = new DefaultPlaceholderRegistry();
        registry.registerPlaceholder(Placeholders.as("name", "홍길동"));

        SubjectContentTemplate template =
                SubjectContentTemplate.builder()
                        .name("email-template")
                        .subject("Hello, {name}!")
                        .content("Welcome, {name}! How are you?")
                        .build();

        Renderer renderer = new PlaceholderReplacingRenderer(template, registry);
        SubjectContentTemplate render = (SubjectContentTemplate) renderer.render();
        assertThat(render.getSubject()).isEqualTo("Hello, 홍길동!");
        assertThat(render.getContent()).isEqualTo("Welcome, 홍길동! How are you?");
        assertThat(render.hasPlaceholders()).isFalse();
    }

    @Test
    void t2() {
        PlaceholderRegistry registry = new DefaultPlaceholderRegistry();
        registry.registerPlaceholder(Placeholders.as("name", "홍길동"));

        ContentTemplate template = ContentTemplate.builder()
                .name("email-template")
                .content("Hello, {name}!, {company}")
                .build();

        Renderer render = new PlaceholderReplacingRenderer(template, registry);

        assertThatThrownBy(render::render)
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

        ContentTemplate template = ContentTemplate.builder()
                .name("email-template")
                .content(content)
                .build();
        Renderer renderer = new PlaceholderReplacingRenderer(template, registry);
        Template render = renderer.render();

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
    void t4() {
        PlaceholderRegistry registry = new DefaultPlaceholderRegistry(new PlaceholderDefinition("#{", "}"));
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

        ContentTemplate template = ContentTemplate.builder()
                .name("push-template")
                .content(content)
                .build();
        Renderer renderer = new PlaceholderReplacingRenderer(template, registry);
        Template render = renderer.render();

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
        Renderer renderer = new PlaceholderReplacingRenderer(template, registry);
        Template render = renderer.render();

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
}
