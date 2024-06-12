package me.choicore.demo.notification.template;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Slf4j
public class DefaultPlaceholderValidator implements PlaceholderValidator {
    private final List<PlaceholderFormatter> embeddedCandidateFormatters = new LinkedList<>();
    private final PlaceholderFormatter formatter;

    public DefaultPlaceholderValidator(PlaceholderFormatter formatter) {
        this.formatter = formatter;
        setEmbeddedCandidateFormatters(formatter);

    }

    public DefaultPlaceholderValidator(PlaceholderDefinition definition) {
        this(new DefaultPlaceholderFormatter(definition.getStartWith(), definition.getEndWith()));
    }

    public DefaultPlaceholderValidator() {
        this(new DefaultPlaceholderFormatter());
    }

    private void setEmbeddedCandidateFormatters(PlaceholderFormatter formatter) {
        StringBuilder openBracket = new StringBuilder();
        String closeBracket = formatter.getSuffix();
        if (openBracket.length() != closeBracket.length()) {
            for (char c : closeBracket.toCharArray()) {
                openBracket.append(findOpenBracket(c));
            }
            embeddedCandidateFormatters.add(new DefaultPlaceholderFormatter(openBracket.toString(), closeBracket));
        }
    }

    private String findOpenBracket(char closeBracket) {
        return switch (closeBracket) {
            case ')':
                yield "(";
            case '}':
                yield "{";
            case ']':
                yield "[";
            case '>':
                yield "<";
            default:
                throw new IllegalArgumentException("Unsupported bracket: " + closeBracket);
        };
    }

    // FIXME: 플레이스홀더 포맷의 자유도를 높게 주면 예외 상황이 발생할 것 같다.
    @Override
    public boolean hasPlaceholders(String template) {
        Set<String> similarFormattedPlaceholders = getCandidateReplacingTarget(template);
        Set<String> extractPlaceholders = formatter.extractPlaceholders(template);
        validatePlaceholders(similarFormattedPlaceholders, extractPlaceholders);
        return !extractPlaceholders.isEmpty();
    }

    private void validatePlaceholders(Set<String> similarFormattedPlaceholders, Set<String> extractPlaceholders) {
        if (similarFormattedPlaceholders.size() != extractPlaceholders.size()) {
            for (String placeholder : extractPlaceholders) {
                placeholder = getPurePlaceholderName(formatter, placeholder);
                similarFormattedPlaceholders.remove(placeholder);
            }
            String format = """
                    Invalid placeholder '%s' detected in the template. Please use the correct format. Instead of the current prefix and suffix, use prefix '%s' and suffix '%s' respectively.
                    """;
            throw new IllegalArgumentException(format.formatted(String.join(", ", similarFormattedPlaceholders), formatter.getPrefix(), formatter.getSuffix()));
        }
    }

    private Set<String> getCandidateReplacingTarget(String template) {
        Set<String> expectedPlaceholders = new HashSet<>();
        for (PlaceholderFormatter candidate : embeddedCandidateFormatters) {
            candidate.getPlaceholderPattern()
                    .matcher(template)
                    .results()
                    .forEach(matchResult -> {
                        String placeholder = matchResult.group();
                        String placeholderName = getPurePlaceholderName(candidate, placeholder);
                        expectedPlaceholders.add(placeholderName);
                    });
        }
        return expectedPlaceholders;
    }

    private String getPurePlaceholderName(PlaceholderFormatter candidate, String placeholder) {
        return placeholder.replace(candidate.getPrefix(), "").replace(candidate.getSuffix(), "");
    }
}
