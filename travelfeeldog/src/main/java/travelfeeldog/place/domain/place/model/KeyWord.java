package travelfeeldog.place.domain.place.model;


import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class KeyWord {
    private String content;

    public boolean isKeyWordIsEmpty() {
        return content.trim().length() != 0;
    }

    public String getNoramalizedKeyWord() {
        return content.trim().toLowerCase();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        KeyWord keyWord = (KeyWord) o;
        return Objects.equals(content, keyWord.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}
