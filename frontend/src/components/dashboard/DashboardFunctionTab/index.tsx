import { useParams } from 'react-router-dom';
import { useEffect } from 'react';

import useProcess, { SimpleProcess } from '@hooks/useProcess';

import InputField from '@components/_common/molecules/InputField';
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
  const {
    applicantSortDropdownProps: { sortOption, updateSortOption },
  } = useProcess({ dashboardId, applyFormId });

  useEffect(() => {
    onSearchName(debouncedName);
  }, [onSearchName, debouncedName]);

  return (
    <S.Wrapper>
      <S.FunctionsContainer>
        <InputField
          type="search"
          placeholder="지원자 이름 검색"
          value={name}
          onChange={(e) => updateName(e.target.value)}
        />
        <ApplicantSortDropdown
          sortOption={sortOption}
          updateSortOption={updateSortOption}
        />
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
