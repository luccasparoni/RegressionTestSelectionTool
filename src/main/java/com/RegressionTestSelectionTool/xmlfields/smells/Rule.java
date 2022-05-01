package com.RegressionTestSelectionTool.xmlfields.smells;

import com.RegressionTestSelectionTool.CodeSmellsDetector.PmdRules;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
  import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;

@XStreamAlias("rule")
@XStreamConverter(value=ToAttributedValueConverter.class, strings={})

public class Rule {

  @XStreamAlias("ref")
  private String ref;

  public Rule(String rule) {
    this.ref = "category/java/" + getRuleCategory(rule) +".xml/" + rule;
  }

  private String getRuleCategory(String rule) {
    if (PmdRules.IsBestPracticeRule(rule)){
      return "bestpractices";
    }
    else if (PmdRules.IsCodeStyleRule(rule)){
      return "codestyle";
    }
    else if (PmdRules.IsDesignRule(rule)){
      return "design";
    }
    else if (PmdRules.IsDocumentationRule(rule)){
      return "documentation";
    }
    else {
      throw new IllegalArgumentException("Invalid rule: " + rule + ". You can check the available rules list" +
        "at: https://pmd.github.io/latest/pmd_rules_java.html");
    }   
  }
}
