import { useParams } from 'react-router-dom';
import { useEffect, useState, useRef } from 'react';
import { HiOutlineFilter } from 'react-icons/hi';

import useProcess, { SimpleProcess } from '@hooks/useProcess';

import InputField from '@components/_common/molecules/InputField';
import RatingFilter from '@components/RatingFilter';
import Popover from '@components/_common/atoms/Popover';

import { useMultiApplicant } from '@contexts/MultiApplicantContext';
import { useSearchApplicant } from '../useSearchApplicant';

import ApplicantSortDropdown from '../ApplicantSortDropdown';
import MultiSelectToggle from '../MultiSelectToggle';

import S from './style';

interface DashboardFunctionTabProps {
  processList: SimpleProcess[];
  onSearchName: (name: string) => void;
}

export default function DashboardFunctionTab({ processList, onSearchName }: DashboardFunctionTabProps) {
  const { dashboardId, applyFormId } = useParams() as { dashboardId: string; applyFormId: string };

  const { debouncedName, name, updateName } = useSearchApplicant();
  const { applicants: selectedApplicantIds, isMultiType } = useMultiApplicant();

  const [isFilterOpened, setIsFilterOpened] = useState<boolean>(false);
  const filterButtonRef = useRef<HTMLButtonElement>(null);

  const {
    applicantSortDropdownProps: { sortOption, updateSortOption },
    ratingFilterProps,
  } = useProcess({ dashboardId, applyFormId });

  useEffect(() => {
    onSearchName(debouncedName);
  }, [onSearchName, debouncedName]);

  return (
    <S.Wrapper>
      <S.FunctionsContainer>
        <S.SearchInputContainer isValue={name.length > 0}>
          <InputField
            type="search"
            placeholder="지원자 이름 검색"
            value={name}
            onChange={(e) => updateName(e.target.value)}
          />
        </S.SearchInputContainer>

        <S.DropdownContainer>
          <ApplicantSortDropdown
            sortOption={sortOption}
            updateSortOption={updateSortOption}
          />
        </S.DropdownContainer>

        <S.FilterWrapper>
          <button
            type="button"
            onClick={() => setIsFilterOpened(true)}
            ref={filterButtonRef}
          >
            <S.FilterButtonContent>
              <HiOutlineFilter size={12} />
              필터
            </S.FilterButtonContent>
          </button>
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
          isToggled={isMultiType}
          processes={processList}
          selectedApplicantIds={selectedApplicantIds}
        />
      </S.FunctionsContainer>
    </S.Wrapper>
  );
}
