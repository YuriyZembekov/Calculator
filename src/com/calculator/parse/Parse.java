package com.calculator.parse;

import com.calculator.dto.DtoParsedExpression;

public interface Parse {
    DtoParsedExpression parse(String inputString);
}
