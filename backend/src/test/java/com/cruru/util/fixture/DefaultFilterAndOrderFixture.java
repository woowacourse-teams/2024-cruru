package com.cruru.util.fixture;

import com.cruru.applicant.domain.EvaluationStatus;
import com.cruru.applicant.domain.SortOption;

public class DefaultFilterAndOrderFixture {

    private DefaultFilterAndOrderFixture() {

    }

    public static final Double DEFAULT_MIN_SCORE = 0.00;
    public static final Double DEFAULT_MAX_SCORE = 5.00;
    public static final EvaluationStatus DEFAULT_EVALUATION_STATUS = EvaluationStatus.ALL;
    public static final SortOption DEFAULT_SORT_BY_CREATED_AT = SortOption.DESC;
    public static final SortOption DEFAULT_SORT_BY_SCORE = SortOption.ASC;
}
