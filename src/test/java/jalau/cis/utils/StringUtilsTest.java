package jalau.cis.utils;

import org.junit.jupiter.api.Test;

import static jalau.cis.utils.StringUtils.isNullOrEmpty;
import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {

  @Test
  void isNullOrEmptyTestTrue() {
    assertTrue(isNullOrEmpty("                  "));
  }
  @Test
  void isNullTest() {
    assertTrue(isNullOrEmpty(null));
  }
}