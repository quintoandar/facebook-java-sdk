package br.com.quintoandar.facebook.api.audience;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.base.Strings;
import com.google.common.hash.Hashing;

import br.com.quintoandar.facebook.api.common.PhoneUtils;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class UsersPayload {

  @JsonProperty("schema")
  private List<Schema> schema;

  @JsonProperty("data")
  List<List<String>> data;

  @JsonProperty("is_raw")
  Integer isRaw;

  public UsersPayload() {
    schema = new ArrayList<>(Arrays.asList(Schema.FIRST_NAME, Schema.LAST_NAME, Schema.EMAIL, Schema.PHONE));
  }

  @AllArgsConstructor
  public enum Schema implements FacebookDataSerialization {
    FIRST_NAME("FN"),
    LAST_NAME("LN"),
    EMAIL,
    PHONE {
      @Override
      public String facebookHashValue(String value) {
        if (!Strings.isNullOrEmpty(value)) {
          String normalizedPhoneNumber =
              PhoneUtils.getNumberE164Format(value).substring(1);

          return normalizeAndHash(normalizedPhoneNumber);
        }
        return "";
      }
    };

    String tag;

    Schema() {
      this.tag = this.name();
    }

    public String facebookHashValue(String value) {
      return !Strings.isNullOrEmpty(value) ? normalizeAndHash(value) : "";
    }

    @JsonValue
    public String jsonValue() {
      return this.tag;
    }

    private static String normalizeAndHash(String value) {
      String normalizedValue = value.trim().toLowerCase();
      return Hashing.sha256().hashString(normalizedValue, StandardCharsets.UTF_8).toString();
    }
  }

}
