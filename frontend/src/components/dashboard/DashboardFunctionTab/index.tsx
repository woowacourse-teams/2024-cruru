import { useParams } from 'react-router-dom';
import { useState, useRef } from 'react';
import { HiOutlineFilter } from 'react-icons/hi';

import useProcess, { SimpleProcess } from '@hooks/useProcess';

import InputField from '@components/_common/molecules/InputField';
import RatingFilter from '@components/RatingFilter';
import Popover from '@components/_common/atoms/Popover';

import { useMultiApplicant } from '@contexts/MultiApplicantContext';

import ApplicantSortDropdown from '../ApplicantSortDropdown';
import MultiSelectToggle from '../MultiSelectToggle';

import S from './style';

interface DashboardFunctionTabProps {
  processList: SimpleProcess[];
  searchedName: string;
  onSearchName: (name: string) => void;
  isRejectedApplicantsTab: boolean;
}

export default function DashboardFunctionTab({
  processList,
  searchedName,
  onSearchName,
  isRejectedApplicantsTab,
}: DashboardFunctionTabProps) {
  const { dashboardId, applyFormId } = useParams() as { dashboardId: string; applyFormId: string };

  const { applicants: selectedApplicantIds, isMultiType } = useMultiApplicant();

  const [isFilterOpened, setIsFilterOpened] = useState<boolean>(false);
  const filterButtonRef = useRef<HTMLButtonElement>(null);

  const {
    applicantSortDropdownProps: { sortOption, updateSortOption },
    ratingFilterProps,
  } = useProcess({ dashboardId, applyFormId });

  return (
    <S.Wrapper>
      <S.FunctionsContainer>
        <S.SearchInputContainer isValue={searchedName.length > 0}>
          <InputField
            type="search"
            placeholder="지원자 이름 검색"
            value={searchedName}
            onChange={(e) => onSearchName(e.target.value)}
          />
        </S.SearchInputContainer>

        <S.DropdownContainer>
          <ApplicantSortDropdown
            sortOption={sortOption}
            updateSortOption={updateSortOption}
          />
        </S.DropdownContainer>

        <S.FilterWrapper>
          <S.FilterButton
            isFilterApplied={ratingFilterProps.isFilterApplied}
            type="button"
            onClick={() => setIsFilterOpened(true)}
            ref={filterButtonRef}
          >
            <HiOutlineFilter
              size={14}
              strokeWidth={2.5}
            />
            필터
          </S.FilterButton>
          <Popover
            isOpen={isFilterOpened}
            onClose={() => setIsFilterOpened(false)}
            anchorEl={filterButtonRef.current}
          >
            <S.FilterContainer>
              <RatingFilter {...ratingFilterProps} />
            </S.FilterContainer>
          </Popover>
        </S.FilterWrapper>
      </S.FunctionsContainer>

      <S.FunctionsContainer>
        <MultiSelectToggle
          dashboardId={dashboardId}
          applyFormId={applyFormId}
          isToggled={isMultiType}
          processes={processList}
          selectedApplicantIds={selectedApplicantIds}
          isRejectedApplicantsTab={isRejectedApplicantsTab}
        />
      </S.FunctionsContainer>
    </S.Wrapper>
  );
}
